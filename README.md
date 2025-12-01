# Sistema de Reserva de Salas 🏢

Sistema de gestión de reservas de salas con interfaz gráfica en Java Swing y persistencia en MySQL.

## 🎯 Características

- ✅ Interfaz gráfica intuitiva con Java Swing
- ✅ Validación de conflictos de horario
- ✅ Restricción a horario laboral (08:00 - 18:00)
- ✅ Persistencia en MySQL
- ✅ Fallback automático a memoria si MySQL no está disponible
- ✅ Notificaciones en consola

## 📋 Requisitos

- Java JDK 17 o superior
- MySQL Server 8.0 o superior (opcional, puede funcionar en memoria)
- PowerShell (Windows)

## 🚀 Inicio Rápido

### Opción 1: Ejecutar sin MySQL (modo memoria)

```powershell
# Compilar
.\compile.ps1

# Ejecutar
.\run.ps1
```

**Nota:** En modo memoria, los datos se pierden al cerrar la aplicación.

### Opción 2: Ejecutar con MySQL (persistencia)

1. **Instalar MySQL** (si no lo tienes):
   ```powershell
   choco install mysql
   ```

2. **Configurar credenciales**:
   - Abre `app/database/DatabaseConnection.java`
   - Ajusta las líneas 8-12:
     ```java
     private static final String HOST = "localhost";
     private static final String PORT = "3306";
     private static final String DATABASE = "reserva_salas";
     private static final String USER = "root";          // Tu usuario
     private static final String PASSWORD = "tu_password"; // Tu contraseña
     ```

3. **Crear base de datos**:
   ```bash
   mysql -u root -p < app/database/schema.sql
   ```
   
   O manualmente:
   ```sql
   CREATE DATABASE reserva_salas;
   USE reserva_salas;
   -- Luego ejecuta el contenido de app/database/schema.sql
   ```

4. **Probar conexión**:
   ```powershell
   .\test-mysql.ps1
   ```

5. **Compilar y ejecutar**:
   ```powershell
   .\compile.ps1
   .\run.ps1
   ```

## 📁 Estructura del Proyecto

```
Reserva-de-salas/
├── app/
│   ├── database/           # Conexión y configuración de BD
│   │   ├── DatabaseConnection.java
│   │   ├── DatabaseInitializer.java
│   │   └── schema.sql
│   ├── domain/             # Modelos del dominio
│   │   ├── Reserva.java
│   │   ├── Sala.java
│   │   └── Usuario.java
│   ├── notification/       # Sistema de notificaciones
│   │   ├── Notifier.java
│   │   └── ConsoleNotifier.java
│   ├── repo/              # Repositorios (persistencia)
│   │   ├── ReservaRepository.java
│   │   ├── InMemoryReservaRepository.java
│   │   ├── MySQLReservaRepository.java
│   │   ├── MySQLSalaRepository.java
│   │   └── MySQLUsuarioRepository.java
│   ├── ui/                # Interfaz gráfica
│   │   ├── MainFrame.java
│   │   ├── AppContext.java
│   │   ├── HomePanel.java
│   │   ├── NuevaReservaPanel.java
│   │   ├── MisReservasPanel.java
│   │   ├── Theme.java
│   │   └── UIUtils.java
│   ├── usecase/           # Casos de uso
│   │   └── CrearReservaUseCase.java
│   ├── validation/        # Reglas de validación
│   │   ├── ReglaValidacion.java
│   │   ├── ReglaConflictoHorario.java
│   │   └── ReglaHorarioLaboral.java
│   ├── App.java           # Aplicación consola
│   └── TestMySQL.java     # Test de conexión
├── lib/
│   └── mysql-connector-j-8.0.33.jar
├── bin/                   # Archivos compilados
├── compile.ps1           # Script de compilación
├── run.ps1              # Script de ejecución
└── test-mysql.ps1       # Test de MySQL

```

## 💻 Uso de la Aplicación

### Pantalla Principal
- **Nueva reserva**: Crear una nueva reserva
- **Mis reservas**: Ver todas las reservas registradas

### Crear Reserva
1. Selecciona hora de inicio y fin
2. Click en "Reservar"
3. El sistema valida:
   - Horario laboral (08:00 - 18:00)
   - Conflictos con otras reservas
   - Que la hora fin sea mayor a la hora inicio

### Ver Reservas
- Lista todas las reservas de la sala
- Muestra: ID, fecha/hora inicio, fecha/hora fin, estado
- Click en "Refrescar" para actualizar la lista

## 🗄️ Base de Datos

### Tablas

**usuarios**
- `id` VARCHAR(50) PRIMARY KEY
- `nombre` VARCHAR(100)
- `rol` VARCHAR(50)

**salas**
- `id` VARCHAR(50) PRIMARY KEY
- `nombre` VARCHAR(100)
- `capacidad` INT

**reservas**
- `id` VARCHAR(50) PRIMARY KEY
- `sala_id` VARCHAR(50) FK
- `usuario_id` VARCHAR(50) FK
- `inicio` DATETIME
- `fin` DATETIME
- `estado` VARCHAR(20) (PENDIENTE, APROBADA, RECHAZADA)

### Datos de Prueba

El script incluye:
- 3 usuarios: Ana (Empleado), Carlos (Gerente), María (Empleado)
- 3 salas: Sala A (6 personas), Sala B (10 personas), Sala C (4 personas)

## 🔧 Solución de Problemas

### "Communications link failure"
- Verifica que MySQL esté ejecutándose: `net start MySQL80`
- Revisa el puerto (por defecto 3306)

### "Access denied for user"
- Verifica usuario y contraseña en `DatabaseConnection.java`
- Asegúrate de tener permisos: `GRANT ALL PRIVILEGES ON reserva_salas.* TO 'root'@'localhost';`

### "Unknown database 'reserva_salas'"
- Ejecuta el script: `mysql -u root -p < app/database/schema.sql`

### "Driver MySQL no encontrado"
- Verifica que exista `lib/mysql-connector-j-8.0.33.jar`
- Recompila: `.\compile.ps1`

### La aplicación usa memoria en lugar de MySQL
- Ejecuta `.\test-mysql.ps1` para diagnosticar
- Revisa las credenciales en `DatabaseConnection.java`
- Verifica que MySQL esté activo

## 📝 Comandos Útiles

```powershell
# Compilar
.\compile.ps1

# Ejecutar aplicación GUI
.\run.ps1

# Probar conexión MySQL
.\test-mysql.ps1

# Ver logs de MySQL
# Los logs se muestran en la consola al ejecutar

# Limpiar compilación
Remove-Item -Recurse -Force bin/*
```

## 🏗️ Arquitectura

El proyecto sigue una arquitectura por capas:

1. **UI (Presentación)**: Swing panels y frames
2. **UseCase (Aplicación)**: Lógica de negocio
3. **Domain (Dominio)**: Entidades del negocio
4. **Repository (Persistencia)**: Acceso a datos
5. **Database (Infraestructura)**: Conexión a MySQL

**Principios aplicados:**
- Inversión de dependencias
- Separación de responsabilidades
- Repository pattern
- Uso de interfaces

## 📚 Documentación Adicional

- `MYSQL_SETUP.md` - Guía detallada de configuración MySQL
- `app/database/README.md` - Documentación de la base de datos
- `app/database/schema.sql` - Script SQL completo

## 👥 Autor

Sistema desarrollado como proyecto educativo de arquitectura de software.

## 📄 Licencia

Este proyecto es de código abierto para fines educativos.
