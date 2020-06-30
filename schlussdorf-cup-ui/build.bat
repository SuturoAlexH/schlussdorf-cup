@echo off

echo start building app
echo.

echo 1. create build folder
> nul rmdir /s /q build
> nul mkdir build
> nul cd build

echo 2. create save folder
> nul mkdir save

echo 3. create images folder
> nul mkdir images

echo 4. copy libs folder
> nul cd ".."
> nul robocopy /E libs build/libs

echo 5. copy template folder
> nul robocopy /E template build/template

echo 6. build app.exe
> nul call mvn clean install -DskipTests
> nul cd target

> nul copy app.exe "../build"
echo.
echo finished build

@pause