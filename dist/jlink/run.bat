@echo off
set JLINK_VM_OPTIONS=
set DIR=%~dp0
"%DIR%\RR\bin\java" %JLINK_VM_OPTIONS% -m RR/core.RR %*
