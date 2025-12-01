# Configuración de Base de Datos MySQL

## Requisitos
- MySQL Server 8.0 o superior
- MySQL Connector/J (JDBC Driver)

## Instalación

### 1. Instalar MySQL
Descarga e instala MySQL desde: https://dev.mysql.com/downloads/mysql/

### 2. Descargar MySQL Connector/J
Descarga el driver JDBC desde: https://dev.mysql.com/downloads/connector/j/
- Versión recomendada: 8.0 o superior
- Extrae el archivo `.jar` en la carpeta `lib/` del proyecto

### 3. Crear la base de datos
Ejecuta el script SQL ubicado en `database/schema.sql`:

```bash
mysql -u root -p < database/schema.sql
```

O desde MySQL Workbench/CLI:
```sql
source C:/Users/zxite/Documents/proyectos/Reserva-de-salas/database/schema.sql
```

### 4. Configurar credenciales
Edita el archivo `database/DatabaseConnection.java` y ajusta:
- `URL`: jdbc:mysql://localhost:3306/reserva_salas
- `USER`: tu usuario MySQL (por defecto: root)
- `PASSWORD`: tu contraseña MySQL

## Compilar y ejecutar con MySQL

```bash
# Compilar con el driver MySQL en el classpath
javac -encoding UTF-8 -cp "lib/mysql-connector-j-8.0.33.jar" -d bin database/*.java domain/*.java repo/*.java usecase/*.java validation/*.java notification/*.java ui/*.java App.java

# Ejecutar con el driver MySQL en el classpath
java -cp "bin;lib/mysql-connector-j-8.0.33.jar" app.ui.MainFrame
```

## Estructura de tablas

- **usuarios**: id, nombre, rol
- **salas**: id, nombre, capacidad
- **reservas**: id, sala_id, usuario_id, inicio, fin, estado

## Datos de prueba
El script incluye datos de ejemplo:
- 3 usuarios (Ana, Carlos, María)
- 3 salas (Sala A, B, C)
