@echo off
echo Updating Pac-Man...
pause
if exist new.jar call :update
:update
ren pac_man.jar old.jar
echo Successfully removed old version
ren new.jar pac_man.jar
echo Update Success!
del old.jar
pause