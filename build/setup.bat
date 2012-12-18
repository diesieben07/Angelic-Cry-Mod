@echo off
echo Setting up the local workspace...
echo.

echo Creating temporary forge workspace...
rmdir /q /s temp
md temp

echo.  Unpacking forge...
for /r zips %%g in (*.zip) do cscript unzip.vbs "%CD%\zips\%%~nxg" "%CD%\temp"

echo.  Installing forge...

cd temp\forge
fml\python\python_fml install.py

IF ERRORLEVEL 1 GOTO ABORT

echo.  Merging forge workspace with repo...

REM Goto repo root dir
cd ..\..\..

xcopy build\temp\*.* . /E /Y

PAUSE
EXIT
:ABORT
echo .
echo Setup failed or aborted due to user wish.
PAUSE