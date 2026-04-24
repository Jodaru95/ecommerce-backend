# 🛒 Ecommerce Full Stack Platform

Proyecto profesional de **Ecommerce Full Stack** desarrollado con **Spring Boot**, **PostgreSQL**, **Docker** y preparado para integración con **Angular** en frontend.

Diseñado para simular una aplicación real de empresa, aplicando buenas prácticas de arquitectura, seguridad, organización de código y escalabilidad.

---

# 🚀 Tecnologías Utilizadas

## Backend
- Java 21
- Spring Boot 4
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

## Base de Datos
- PostgreSQL

## Seguridad
- JWT Authentication
- Refresh Tokens persistidos en base de datos
- Roles USER / ADMIN

## DevOps
- Docker
- Docker Compose
- pgAdmin

## Documentación API
- Swagger / OpenAPI

## Frontend (próxima fase)
- Angular 20
- TypeScript
- RxJS

---

# ⚙️ Instalación

## Requisitos previos

Antes de ejecutar el proyecto necesitas tener instalado:

- Java 21+
- Maven 3.9+
- Docker Desktop (opcional, recomendado)
- Git

## 1. Clonar repositorio

```bash id="txv7iv"
git clone https://github.com/jodaru95/ecommerce-backend.git
cd ecommerce-backend
```

## 2. Ejecutar en local
```bash id="txv7iv"
./mvnw spring-boot:run
```
En Windows:
```bash id="txv7iv"
mvnw.cmd spring-boot:run
```
## 3. Ejecutar con Docker
```bash id="txv7iv"
docker compose up --build
```

## 4. URLs disponibles
| Servicio    | URL                                      |
|-------------|------------------------------------------|
| API Backend | 	http://localhost:8080                   |
| Swagger UI  | 	http://localhost:8080/swagger-ui.html   |
| pgAdmin     | 	http://localhost:5050                   | 


## 5. Credenciales demo
| Rol   | Usuario | Password |
|-------|---------|----------|
| ADMIN | admin   | admin123 |
| USER  | user    | user123  |

---

# 📦 Funcionalidades Implementadas

## 👤 Autenticación
- Registro de usuarios
- Login con JWT
- Refresh Token
- Logout seguro

## 🛍️ Productos
- Obtener catálogo completo
- Obtener producto por ID
- Crear producto (ADMIN)
- Editar producto (ADMIN)
- Eliminar producto (ADMIN)
- Búsqueda por nombre
- Filtro por precio
- Paginación

## 🛒 Carrito
- Añadir productos
- Actualizar cantidad
- Eliminar productos
- Ver carrito actual

## 📦 Pedidos
- Checkout
- Historial de pedidos del usuario
- Detalle de pedido
- Cancelación de pedido
- Gestión total para ADMIN

## 💳 Pagos
- Simulación de pago con tarjeta
- Cambio automático de estado del pedido

## 🏢 Panel de Administración
- Dashboard general
- Total ventas
- Pedidos por estado
- Productos más vendidos
- Ventas por mes
- Gestión de pedidos
- Cambio de estado manual

## 📍 Direcciones
- Guardar direcciones
- Listar direcciones
- Eliminar dirección
- Marcar dirección por defecto

## 👤 Perfil Usuario
- Ver datos personales
- Editar perfil

---

# 🧱 Arquitectura Backend

Estructura organizada por capas:

```text
src/main/java/com/josedavid/ecommerce/app
├── application
│   ├── dto
│   └── usecases
├── domain
│   ├── entity
│   └── enums
└── infraestructure
    ├── adapters
    │   ├── input/rest
    │   └── output/jpa
    └── config
```
---

# 📈 Estado del Proyecto

## ✅ Backend Finalizado

- Autenticación JWT
- Refresh Token
- Roles USER / ADMIN
- CRUD de productos
- Búsqueda, filtros y paginación
- Carrito de compra
- Checkout y pedidos
- Historial de pedidos
- Cancelación de pedidos
- Simulación de pagos
- Dashboard de administración
- Métricas de ventas
- Gestión de direcciones
- Perfil de usuario
- Swagger / OpenAPI
- Dockerización completa
- PostgreSQL + pgAdmin

---

## 🚧 Próximas Mejoras

- Frontend completo en Angular 20
- Testing unitario (JUnit / Mockito)
- Testing de integración
- CI/CD con GitHub Actions
- Deploy en cloud (Render / Railway / AWS)
- Integración de pagos reales (Stripe)
- Emails automáticos
- Panel admin frontend

---

## 🎯 Objetivo

Convertir este proyecto en una plataforma Full Stack profesional lista para producción y portfolio técnico.

---

# 👨‍💻 Autor

**Jose David Ruiz Montoya**  
Full Stack Developer (Java + Spring Boot + Angular)

LinkedIn: https://www.linkedin.com/in/jose-david-ruiz-montoya-030269193/ 
GitHub: https://github.com/Jodaru95