@echo off

set services=analytic-service api-gateway eureka-server player-service statistics-service team-service

for %%s in (%services%) do (
  echo Building %%s...
  cd %%s
  mvn clean package
  if errorlevel 1 (
    echo Build failed for %%s
    exit /b 1
  )
  cd..
  echo %%s built successfully
)

echo All services built successfully
