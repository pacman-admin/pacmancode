@echo off
echo Updating Pac-Man...
cd app
if exist new.jar call :update

:update
ren pac_man.jar old.jar
del old.jar
echo Successfully removed old version
ren new.jar pac_man.jar
echo Update Success!
pause