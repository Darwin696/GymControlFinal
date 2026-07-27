## UNIVERSIDAD NACIONAL DE LOJA

**Facultad de la Energía, las Industrias y los Recursos Naturales No Renovables**
**Carrera de Ingeniería en Sistemas**

---

# GymControl
### Sistema de gestión integral para gimnasios

**Asignatura:** Programación Orientada a Objetos

**Docente:** Wilman Chamba Zaragocín

**Integrantes:**
- Darwin Campoverde
- Pablo Pineda
- Galo Benítez
- Erick Rogel
- Jahir Campoverde

**Periodo académico:** 2026

---

## 1. Descripción del proyecto

**GymControl** es una aplicación web que automatiza la administración de un gimnasio: registro de miembros, control de membresías, cobros, ventas de productos, gestión de instructores y generación de reportes. El proyecto se desarrolló como aplicación de los principios de la **Programación Orientada a Objetos (POO)** sobre una arquitectura Jakarta EE, utilizando persistencia de datos en PostgreSQL.

## 2. Objetivos

### 2.1 Objetivo general
Desarrollar un sistema web orientado a objetos que permita administrar los procesos operativos de un gimnasio (miembros, membresías, pagos, productos, instructores y reportes), aplicando los pilares de la POO: **abstracción, encapsulamiento, herencia y polimorfismo**.

### 2.2 Objetivos específicos
- Modelar el dominio del negocio mediante clases, jerarquías de herencia e interfaces.
- Implementar un módulo de autenticación para el acceso al sistema.
- Aplicar polimorfismo en el cálculo de vigencia de los distintos tipos de membresía.
- Persistir la información del sistema en una base de datos relacional (PostgreSQL) mediante JPA.
- Diseñar una interfaz web funcional con JSF y PrimeFaces.

## 3. Justificación

La administración manual de un gimnasio (control de pagos, vencimiento de membresías, inventario de productos) es propensa a errores y pérdida de información. GymControl centraliza estos procesos en un sistema único, y sirve además como caso de estudio práctico de los conceptos de POO vistos en la asignatura: clases abstractas, herencia, sobrescritura de métodos (`@Override`) y relaciones entre objetos persistentes.

## 4. Alcance / Módulos funcionales

| Módulo | Descripción |
|---|---|
| **Autenticación** | Inicio de sesión del administrador del sistema |
| **Dashboard** | Vista general del estado del gimnasio |
| **Miembros** | Registro, edición, consulta y baja de clientes (CRUD) |
| **Planes de membresía** | Gestión de membresías Mensual, Anual y VIP (CRUD) |
| **Pagos / Facturas** | Registro de cobros generados por membresías o productos |
| **Productos / Tienda** | Control de stock y ventas que generan factura |
| **Instructores** | Gestión del personal técnico del gimnasio (CRUD) |
| **Reportes** | Informes generales de la operación del gimnasio |

## 5. Tecnologías utilizadas

| Componente | Tecnología |
|---|---|
| Lenguaje | Java 21 |
| Framework backend | Jakarta EE 10 (Web Profile) |
| Framework de vistas | JSF (Jakarta Faces) + PrimeFaces 15 |
| Persistencia | Jakarta Persistence (JPA) — implementación EclipseLink |
| Base de datos | PostgreSQL |
| Servidor de aplicaciones | Open Liberty |
| Gestor de dependencias | Maven |
| Contenerización | Docker / Docker Compose |

## 6. Aplicación de los principios de la POO

El proyecto evidencia los cuatro pilares de la Programación Orientada a Objetos directamente en el modelo de dominio (`unl.edu.ec.gymcontrol.domain`):

**Abstracción**
- `Persona` y `Membresia` son clases **abstractas** que definen atributos y comportamientos comunes, sin poder instanciarse directamente.

**Encapsulamiento**
- Todos los atributos de las entidades son `private`, expuestos únicamente mediante métodos `get`/`set`, protegiendo la integridad de los datos.

**Herencia**
- `Cliente` y `Empleado` heredan de `Persona`.
- `Instructor` hereda de `Empleado` (herencia de dos niveles).
- `MembresiaMensual`, `MembresiaAnual` y `MembresiaVIP` heredan de la clase abstracta `Membresia`.

**Polimorfismo**
- La clase `Membresia` declara el método abstracto `calcularFechaVencimiento()`, y cada subclase lo **sobrescribe** (`@Override`) con su propia regla de negocio:
  - `MembresiaMensual` → vence en 1 mes.
  - `MembresiaAnual` → vence en 1 año.
  - `MembresiaVIP` → vence en 1 año (con beneficios adicionales de negocio).
- Al invocar `membresia.calcularFechaVencimiento()` sobre una referencia de tipo `Membresia`, el sistema ejecuta la implementación específica de la subclase real en tiempo de ejecución.

### 6.1 Diagrama de herencia (simplificado)

```
Persona (abstracta)
 ├── Cliente
 └── Empleado
      └── Instructor

Membresia (abstracta)
 ├── MembresiaMensual
 ├── MembresiaAnual
 └── MembresiaVIP
```

## 7. Arquitectura del sistema

```
gymControl/
├── src/main/java/unl/edu/ec/gymcontrol/
│   ├── bean/       # LoginBean, VistaGymBean → controladores JSF (managed beans)
│   ├── domain/     # Entidades del negocio (modelo POO)
│   └── service/    # GymService → lógica de negocio y acceso a datos
├── src/main/resources/META-INF/persistence.xml   # unidad de persistencia (JPA)
├── src/main/liberty/config/server.xml             # servidor, puerto y datasource
├── src/main/webapp/                                # vistas .xhtml (JSF + PrimeFaces)
├── Dockerfile
├── docker-compose-dev-pg.yml                       # PostgreSQL para desarrollo
└── pom.xml
```

La aplicación sigue una separación de capas típica de Jakarta EE:
- **Capa de presentación:** vistas `.xhtml` (JSF/PrimeFaces).
- **Capa de control:** *managed beans* (`bean/`).
- **Capa de negocio:** `GymService`.
- **Capa de persistencia:** entidades JPA (`domain/`) mapeadas a PostgreSQL.

## 8. Manual de instalación y ejecución

### 8.1 Requisitos previos
- JDK 21
- Maven (o el wrapper incluido `./mvnw` / `mvnw.cmd`)
- Docker y Docker Compose

### 8.2 Levantar la base de datos
```bash
cd gymControl
docker compose -f docker-compose-dev-pg.yml up -d
```
Esto crea la base `gym` en PostgreSQL (usuario `gymuser`, puerto `5435`), acorde a la configuración del `server.xml`.

### 8.3 Compilar y ejecutar la aplicación

**Windows (PowerShell)**
```powershell
cd gymControl
.\mvnw.cmd clean package liberty:run
```

**Linux / macOS**
```bash
cd gymControl
./mvnw clean package liberty:run
```

### 8.4 Acceder al sistema
Abrir el navegador en: **http://localhost:9080**

Credenciales de acceso:
- **Usuario:** `admin`
- **Contraseña:** `admin123`

## 9. Conclusiones

- El desarrollo de GymControl permitió aplicar de forma práctica los cuatro pilares de la POO sobre un caso de negocio real (gestión de un gimnasio).
- El uso de clases abstractas (`Persona`, `Membresia`) facilitó la reutilización de código y estableció un contrato claro para las subclases.
- El polimorfismo en `calcularFechaVencimiento()` demostró cómo un mismo mensaje enviado a distintos objetos produce comportamientos diferentes, sin necesidad de estructuras condicionales explícitas.
- El uso de Jakarta EE y JPA permitió mapear directamente el modelo de objetos al modelo relacional, reforzando la relación entre POO y persistencia de datos.

## 10. Recomendaciones / trabajo futuro

- Incorporar pruebas unitarias (JUnit) para las reglas de negocio de cada tipo de membresía.
- Agregar roles de usuario adicionales (recepcionista, instructor) más allá del administrador único actual.
- Implementar notificaciones automáticas de vencimiento de membresía.

## 11. Referencias

- Oracle. *Jakarta EE 10 Platform Specification.*
- PrimeFaces. *PrimeFaces 15 User Guide.*
- Open Liberty. *Open Liberty Documentation.*
- PostgreSQL Global Development Group. *PostgreSQL 16 Documentation.*
