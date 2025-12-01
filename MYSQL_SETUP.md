# Sistema de Reserva de Salas - Integración MySQL

## ✅ Lo que se ha configurado

1. **Base de datos MySQL**
   - Script SQL: `database/schema.sql`
   - Tablas: usuarios, salas, reservas
   - Datos de prueba incluidos

2. **Conexión a la base de datos**
   - Clase: `database/DatabaseConnection.java`
   - URL: `jdbc:mysql://localhost:3306/reserva_salas`
   - Usuario por defecto: `root`
   - Contraseña por defecto: vacía

3. **Repositorio MySQL**
   - Implementación: `repo/MySQLReservaRepository.java`
   - Métodos: save(), findBySala(), findAll()

4. **Driver JDBC**
   - MySQL Connector/J 8.0.33
   - Ubicación: `lib/mysql-connector-j-8.0.33.jar`

5. **Scripts de compilación**
   - `compile.ps1`: Compila el proyecto con MySQL
   - `run.ps1`: Ejecuta la aplicación

## 🚀 Pasos para usar MySQL

### 1. Instalar MySQL Server
Si no lo tienes instalado:
- Descarga desde: https://dev.mysql.com/downloads/mysql/
- O con Chocolatey: `choco install mysql`

### 2. Crear la base de datos
Abre MySQL y ejecuta:

```bash
mysql -u root -p < database/schema.sql
```

O copia el contenido de `database/schema.sql` y ejecútalo en MySQL Workbench.

### 3. Configurar credenciales (si es necesario)
Edita `database/DatabaseConnection.java` líneas 7-9:

```java
private static final String URL = "jdbc:mysql://localhost:3306/reserva_salas";
private static final String USER = "root";  // Tu usuario MySQL
private static final String PASSWORD = "";   // Tu contraseña MySQL
```

### 4. Compilar y ejecutar

```powershell
# Compilar
.\compile.ps1

# Ejecutar
.\run.ps1
```

## 🔄 Volver a usar memoria (InMemory)

Si prefieres no usar MySQL temporalmente, edita `ui/AppContext.java` línea 19:

```java
// Cambiar de:
public final ReservaRepository repo = new MySQLReservaRepository();

// A:
public final ReservaRepository repo = new InMemoryReservaRepository();
```

Y recompila:
```powershell
.\compile.ps1
.\run.ps1
```

## 📊 Verificar datos en MySQL

```sql
USE reserva_salas;

-- Ver usuarios
SELECT * FROM usuarios;

-- Ver salas
SELECT * FROM salas;

-- Ver reservas
SELECT * FROM reservas;
```

## ⚠️ Solución de problemas

### Error: "Communications link failure"
- Verifica que MySQL esté ejecutándose
- Revisa que el puerto sea 3306

### Error: "Access denied for user"
- Verifica usuario y contraseña en `DatabaseConnection.java`

### Error: "Unknown database 'reserva_salas'"
- Ejecuta el script `database/schema.sql`

## 📁 Estructura del proyecto

```
Reserva-de-salas/
├── database/
│   ├── DatabaseConnection.java  (Conexión a MySQL)
│   ├── schema.sql               (Script de BD)
│   └── README.md
├── repo/
│   ├── ReservaRepository.java
│   ├── InMemoryReservaRepository.java
│   └── MySQLReservaRepository.java  (Nueva implementación)
├── lib/
│   └── mysql-connector-j-8.0.33.jar
├── compile.ps1
└── run.ps1
```

¡Listo! Tu aplicación ahora persiste datos en MySQL. 🎉
