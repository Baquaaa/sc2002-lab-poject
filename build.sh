#!/bin/sh
# Compiles the game into out/ (needs JDK 17 or newer).
cd "$(dirname "$0")"
rm -rf out
javac --release 17 -d out src/rush/*.java src/rush/*/*.java && echo "Build OK. Start the game with ./run.sh"
