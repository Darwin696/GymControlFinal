# GymControl - Sistema completo de gimnasio

## Módulos
- Login (admin / admin123)
- Dashboard
- Miembros (CRUD)
- Planes de membresía (CRUD)
- Pagos / Facturas (registrar cobros)
- Productos / Tienda (stock + ventas que generan factura)
- Instructores (CRUD)
- Reportes

## Estructura
```
src/main/java/unl/edu/ec/gymcontrol/
├── domain/   Persona, Cliente, Membresia*, Instructor, Producto...
└── bean/     LoginBean, VistaGymBean
```

## Ejecutar (Windows PowerShell)
```
cd gymControl   # si está anidado
.\mvnw.cmd clean package liberty:run
```
http://localhost:9080
