# Script para probar la conexión a MySQL
$java = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot\bin\java.exe"
$mysqlJar = "lib/mysql-connector-j-8.0.33.jar"
$outputDir = "bin"

Write-Host "Probando conexión a MySQL..." -ForegroundColor Cyan
Write-Host ""

& $java -cp "$outputDir;$mysqlJar" app.TestMySQL

Write-Host ""
Read-Host "Presiona Enter para continuar"
