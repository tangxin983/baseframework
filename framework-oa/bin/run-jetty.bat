@echo off
echo [INFO] Use maven jetty-plugin run the project.

%~d0
cd %~dp0
cd ..

call mvn jetty:run

cd bin
pause
