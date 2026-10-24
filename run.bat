@echo off
rem Starts the game. To replay a saved run: run.bat --echo ^< runs\stage1-victory.txt
cd /d "%~dp0"
java -cp out rush.Main %*
