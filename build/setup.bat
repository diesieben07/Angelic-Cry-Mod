@echo off
echo === REPO SETUP BEGIN ===
echo .

REM Remove any previous work dirs

echo Removing old Temp dir...

cd ..
rmdir /q /s work

REM We start by unpacking forge and mcp

echo .
echo Creating Temp dir...

md work

echo Unpacking ZIP-files...
for /r build\zips %%g in (*.zip) do Cscript build\unzip.vbs "%CD%\build\zips\%%~nxg" "%CD%\work"

echo Copying Jar Files...

xcopy build\jars work\jars /E

cd work\forge

..\runtime\bin\python\python_mcp install.py

IF ERRORLEVEL 1 GOTO ABORT

cd ..\..

echo Setting up Workspace...

xcopy work\*.* MCP /E /Y

echo Clearing Temp dir...

rmdir /q /s work

PAUSE
EXIT
:ABORT
echo .
echo Setup failed or aborted due to user wish.
PAUSE