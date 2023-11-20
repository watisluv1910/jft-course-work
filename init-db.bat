@echo off
setlocal enableextensions disabledelayedexpansion

for /f "tokens=1,* delims==" %%a in ('type .env') do (
    set "%%a=%%b"
)

set "INIT_SCRIPT=mysql-database\schema.sql"
set "TMP_INIT_SCRIPT=mysql-database\temp_schema.sql"

copy /Y %INIT_SCRIPT% %TMP_INIT_SCRIPT%

set "search=${DB_SCHEMA}"
set "replace=%DB_SCHEMA%"

for /f "delims=" %%i in ('type "%TMP_INIT_SCRIPT%" ^& break ^> "%TMP_INIT_SCRIPT%" ') do (
    set "line=%%i"
    setlocal enabledelayedexpansion
    >>"%TMP_INIT_SCRIPT%" echo(!line:%search%=%replace%!
    endlocal
)

mysql -u"%DB_USER%" -p"%DB_PASSWORD%" < %TMP_INIT_SCRIPT%

del %TMP_INIT_SCRIPT%

endlocal
