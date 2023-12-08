@echo off
setlocal enableextensions disabledelayedexpansion

:: Set the current directory to the script's directory
cd %~dp0

for /f "tokens=1,* delims==" %%a in ('type ..\.env') do (
    set "%%a=%%b"
)

set "INIT_SCRIPT=schema.sql"
set "TMP_INIT_SCRIPT=temp_schema.sql"

copy /Y %INIT_SCRIPT% %TMP_INIT_SCRIPT%

set "search=${DB_SCHEMA}"
set "replace=%MYSQL_DATABASE%"

for /f "delims=" %%i in ('type "%TMP_INIT_SCRIPT%" ^& break ^> "%TMP_INIT_SCRIPT%" ') do (
    set "line=%%i"
    setlocal enabledelayedexpansion
    >>"%TMP_INIT_SCRIPT%" echo(!line:%search%=%replace%!
    endlocal
)

mysql -u"%MYSQL_USER%" -p"%MYSQL_PASSWORD%" < %TMP_INIT_SCRIPT%

del %TMP_INIT_SCRIPT%

endlocal
