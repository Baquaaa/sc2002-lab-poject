@echo off
rem Compiles the game into out (needs JDK 17 or newer).
cd /d "%~dp0"
if exist out rmdir /s /q out
javac --release 17 -d out src\rush\Main.java src\rush\entity\*.java src\rush\control\*.java src\rush\boundary\*.java || exit /b 1
echo Build OK. Start the game with run.bat
