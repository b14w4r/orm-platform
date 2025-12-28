#!/usr/bin/env bash
set -euo pipefail

# One-command verification for the project.
# 1) Runs automated integration tests (Testcontainers + PostgreSQL)
# 2) Optionally runs a quick smoke run of the app + curl checks (uses docker-compose postgres)

MODE="${1:-tests}"  # tests | smoke

require() {
  command -v "$1" >/dev/null 2>&1 || { echo "Missing dependency: $1" >&2; exit 1; }
}

require mvn
require docker

if [[ "$MODE" == "tests" ]]; then
  echo "==> Running mvn test (uses Testcontainers; Docker must be running)"
  mvn -q test
  echo "OK"
  exit 0
fi

if [[ "$MODE" != "smoke" ]]; then
  echo "Usage: ./verify.sh [tests|smoke]" >&2
  exit 2
fi

require curl

echo "==> Starting postgres via docker compose"
docker compose up -d

APP_PID=""
cleanup() {
  if [[ -n "${APP_PID}" ]] && kill -0 "${APP_PID}" 2>/dev/null; then
    echo "==> Stopping app (pid=${APP_PID})"
    kill "${APP_PID}" || true
    wait "${APP_PID}" 2>/dev/null || true
  fi
  echo "==> Stopping postgres"
  docker compose down -v
}
trap cleanup EXIT

echo "==> Starting app"
# Run in background; logs in app.log
mvn -q spring-boot:run > app.log 2>&1 &
APP_PID=$!

echo "==> Waiting for app on http://localhost:8080 ..."
for i in {1..60}; do
  if curl -fsS http://localhost:8080/actuator/health >/dev/null 2>&1; then
    break
  fi
  # If actuator is not enabled, fallback to swagger/openapi
  if curl -fsS http://localhost:8080/v3/api-docs >/dev/null 2>&1; then
    break
  fi
  sleep 1
  if [[ $i -eq 60 ]]; then
    echo "App did not start. Check app.log" >&2
    exit 1
  fi
done

# Scenario 1: enroll
curl -fsS -X POST http://localhost:8080/api/courses/1/enroll \
  -H 'Content-Type: application/json' \
  -d '{"studentId":2}' | grep -q '"status":"ACTIVE"'

# Scenario 2: submit assignment
curl -fsS -X POST http://localhost:8080/api/assignments/1/submit \
  -H 'Content-Type: application/json' \
  -d '{"studentId":2,"content":"my answer"}' | grep -q '"content":"my answer"'

# Scenario 3: take quiz
curl -fsS -X POST http://localhost:8080/api/quizzes/1/take \
  -H 'Content-Type: application/json' \
  -d '{"studentId":2,"answers":{"1":2,"2":3}}' | grep -q '"score":2'

echo "OK (smoke)"
