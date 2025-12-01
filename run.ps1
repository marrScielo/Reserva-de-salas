# Script para ejecutar la aplicación con MySQL
$java = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot\bin\java.exe"
$mysqlJar = "lib/mysql-connector-j-8.0.33.jar"
$outputDir = "bin"

Write-Host "Iniciando aplicación Reserva de Salas..." -ForegroundColor Cyan

& $java -cp "$outputDir;$mysqlJar" app.ui.MainFrame
