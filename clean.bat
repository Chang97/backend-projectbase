@echo off
set JAVA_HOME=C:\\devTool\\openjdk-17.0.2_windows-x64_bin\\jdk-17.0.2
set PATH=%JAVA_HOME%\bin;%PATH%
gradlew clean bootRun
