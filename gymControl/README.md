# GymControl

Sistema web para la gestión integral de un gimnasio, desarrollado con **Jakarta EE 10**, **JSF + PrimeFaces**, **JPA (EclipseLink)** y **PostgreSQL**, desplegado sobre **Open Liberty**.

## ✨ Funcionalidades

- **Login** con autenticación básica (usuario: `admin`, contraseña: `admin123`)
- **Dashboard** general del gimnasio
- **Miembros**: alta, baja, edición y consulta de clientes (CRUD)
- **Planes de membresía**: mensual, anual y VIP (CRUD)
- **Pagos / Facturas**: registro de cobros a los miembros
- **Productos / Tienda**: control de stock y ventas que generan factura
- **Instructores**: gestión de personal (CRUD)
- **Reportes**: informes generales de la actividad del gimnasio

## 🛠️ Stack tecnológico

| Componente        | Tecnología                                   |
|--------------------|-----------------------------------------------|
| Lenguaje           | Java 21                                       |
| Framework web      | Jakarta EE 10 (Web Profile) + JSF             |
| Componentes UI     | PrimeFaces 15                                 |
| Persistencia       | Jakarta Persistence (EclipseLink)             |
| Base de datos      | PostgreSQL 16 / 18                            |
| Servidor de apps   | Open Liberty                                  |
| Build              | Maven (con wrapper `mvnw` / `mvnw.cmd`)       |
| Contenedores       | Docker / Docker Compose                       |

## 📁 Estructura del proyecto

```
GymControlFinal/
└── gymControl/
    ├── src/main/java/unl/edu/ec/gymcontrol/
    │   ├── bean/       # LoginBean, VistaGymBean (managed beans JSF)
    │   ├── domain/     # Persona, Cliente, Empleado, Instructor,
    │   │               # Membresia (Mensual/Anual/VIP), Pago, Producto...
    │   └── service/     # GymService
    ├── src/main/resources/META-INF/persistence.xml
    ├── src/main/liberty/config/server.xml   # configuración del servidor y datasource
    ├── src/main/webapp/                     # vistas .xhtml (login, miembros, planes,
    │                                         # pagos, productos, instructores, reportes)
    ├── Dockerfile
    ├── docker-compose-dev-pg.yml            # PostgreSQL para desarrollo
    ├── docker-env                           # variables de entorno de la BD
    └── pom.xml
```

## ✅ Requisitos previos

- JDK 21
- Maven (o usar el wrapper incluido `./mvnw` / `mvnw.cmd`)
- Docker y Docker Compose (para levantar PostgreSQL fácilmente)

## 🚀 Puesta en marcha (desarrollo local)

### 1. Levantar la base de datos con Docker

```bash
cd gymControl
docker compose -f docker-compose-dev-pg.yml up -d
```

Esto crea un contenedor PostgreSQL con:
- Base de datos: `gym`
- Usuario: `gymuser`
- Contraseña: `gymUser2626`
- Puerto publicado: `5435`

> Estos valores deben coincidir con el `dataSource` configurado en `src/main/liberty/config/server.xml`.

### 2. Compilar y ejecutar la aplicación

**Linux / macOS**
```bash
cd gymControl
./mvnw clean package liberty:run
```

**Windows (PowerShell)**
```powershell
cd gymControl
.\mvnw.cmd clean package liberty:run
```

Otros comandos útiles del plugin de Liberty:
```bash
./mvnw liberty:dev     # modo desarrollo con hot-reload
./mvnw liberty:start   # arranca el servidor en background
./mvnw liberty:stop    # detiene el servidor en background
./mvnw verify          # pruebas de integración con servidor gestionado
```

### 3. Acceder a la aplicación

Abre [http://localhost:9080](http://localhost:9080) e inicia sesión con:

- **Usuario:** `admin`
- **Contraseña:** `admin123`

## 🐳 Despliegue con Docker (aplicación)

El `Dockerfile` empaqueta un artefacto ya compilado sobre una imagen de Open Liberty. Antes de construir la imagen, genera el `.war`:

```bash
cd gymControl
./mvnw clean package -DskipTests
docker build -t gymcontrol .
```

## 🔧 Configuración

La conexión a la base de datos se define en `src/main/liberty/config/server.xml` (host, puerto, nombre de la BD, usuario y contraseña) y en `docker-env` para las variables usadas en Docker. Ajusta ambos archivos si cambias las credenciales o el puerto de PostgreSQL.

## 📄 Licencia

Este proyecto no especifica una licencia. Agrega un archivo `LICENSE` si deseas definir los términos de uso y distribución.
