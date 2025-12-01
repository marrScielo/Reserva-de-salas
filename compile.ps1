# Script para compilar el proyecto con MySQL
$javac = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot\bin\javac.exe"
$mysqlJar = "lib/mysql-connector-j-8.0.33.jar"
$outputDir = "bin"

Write-Host "Compilando proyecto con soporte MySQL..." -ForegroundColor Green

# Crear directorio de salida si no existe
if (!(Test-Path $outputDir)) {
    New-Item -ItemType Directory -Path $outputDir | Out-Null
}

# Compilar
& $javac -encoding UTF-8 -cp $mysqlJar -d $outputDir `
    app/database/*.java `
    app/domain/*.java `
    app/repo/*.java `
    app/usecase/*.java `
    app/validation/*.java `
    app/notification/*.java `
    app/ui/*.java `
    app/App.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Compilación exitosa" -ForegroundColor Green
} else {
    Write-Host "✗ Error en la compilación" -ForegroundColor Red
    exit 1
}
