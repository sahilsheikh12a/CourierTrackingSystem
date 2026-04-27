#!/bin/bash
set -e

ROOT="$(cd "$(dirname "$0")" && pwd)"
BACKEND="$ROOT/backend"
FRONTEND="$ROOT/frontend"

BACKEND_LOG="/tmp/courier-backend.log"
BACKEND_PID=""
FRONTEND_PID=""

cleanup() {
    echo ""
    echo "Stopping servers..."
    [ -n "$BACKEND_PID" ]  && kill "$BACKEND_PID"  2>/dev/null
    [ -n "$FRONTEND_PID" ] && kill "$FRONTEND_PID" 2>/dev/null
    wait 2>/dev/null
    echo "Done."
    exit 0
}
trap cleanup SIGINT SIGTERM

echo "========================================"
echo "   Courier Tracking System"
echo "========================================"

# --- Backend ---
echo "[1/2] Starting Spring Boot backend..."
cd "$BACKEND"
bash mvnw spring-boot:run > "$BACKEND_LOG" 2>&1 &
BACKEND_PID=$!

echo "      Waiting for backend on :8080 ..."
for i in $(seq 1 30); do
    if curl -s http://localhost:8080/api/tracking/ping > /dev/null 2>&1 || \
       grep -q "Started CourierTrackingApplication" "$BACKEND_LOG" 2>/dev/null; then
        break
    fi
    sleep 2
done
echo "      Backend ready  →  http://localhost:8080"

# --- Frontend ---
echo "[2/2] Starting React frontend..."
cd "$FRONTEND"
npm run dev > /dev/null 2>&1 &
FRONTEND_PID=$!
sleep 3
echo "      Frontend ready →  http://localhost:5173"

echo ""
echo "========================================"
echo "  App running. Press Ctrl+C to stop."
echo "========================================"
echo ""

wait
