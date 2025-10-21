@echo off
echo Running Spotless code formatting...
call gradlew.bat spotlessApply
echo.
echo Running Git status to check for remaining issues...
git status
echo.
echo If you see changes after running this script, commit them with:
echo git add .
echo git commit -m "Apply Spotless formatting"
echo git push origin main

