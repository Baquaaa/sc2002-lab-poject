#!/bin/sh
# Starts the game. To replay a saved run: ./run.sh --echo < runs/stage1-victory.txt
cd "$(dirname "$0")"
java -cp out rush.Main "$@"
