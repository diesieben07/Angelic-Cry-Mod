@echo off
echo Recompiling...
cd ..\forge\mcp
runtime\bin\python\python_mcp runtime\recompile.py
echo Reobfuscating...
runtime\bin\python\python_mcp runtime\reobfuscate.py
echo Preparing Temp-dir...
cd ..\..\build
rmdir /q /s temp
md temp
cd temp
echo Copying classes...
%windir%\system32\xcopy ..\..\forge\mcp\reobf\minecraft\*.* . /E /Y

echo Copying resources...
cd demonmodders
cd crymod
md resource
cd resource
%windir%\system32\xcopy ..\..\..\..\..\forge\mcp\src\minecraft\demonmodders\crymod\resource\*.* . /E /Y
echo Creating Manifest...
cd ..\..\..

echo FMLCorePlugin: demonmodders.crymod.common.core.CrymodCore> crymod.manifest

echo Creating jar...
jar cfm Summoningmod.jar crymod.manifest demonmodders\*
pause