#!/usr/bin/env bash
set -e

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
SRC_ROOT="$PROJECT_ROOT/backend/src/main/java"
OUT_DIR="$PROJECT_ROOT/backend/out"

rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

javac -d "$OUT_DIR" -sourcepath "$SRC_ROOT" \
  "$SRC_ROOT/com/couriertracking/CourierTrackingApplication.java"

java -cp "$OUT_DIR" com.couriertracking.CourierTrackingApplication
