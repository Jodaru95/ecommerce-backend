# 🛒 Ecommerce Full Stack Project

Proyecto profesional de **Ecommerce Full Stack** desarrollado con **Spring Boot**, **PostgreSQL**, **Docker** y preparado para integración con **Angular** en frontend.

Diseñado siguiendo buenas prácticas de arquitectura, seguridad y escalabilidad, simulando un entorno real de empresa.

---

# 🚀 Tech Stack

## Backend
- Java 21+
- Spring Boot 4
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication
- Maven

## Base de Datos
- PostgreSQL

## DevOps
- Docker
- Docker Compose
- pgAdmin

## API Documentation
- Swagger / OpenAPI

## Frontend (en progreso)
- Angular 20
- TypeScript
- RxJS

---

# 📦 Funcionalidades Implementadas

## 👤 Autenticación
- Registro de usuarios
- Login con JWT
- Refresh Token
- Logout seguro

## 🛍️ Productos
- CRUD completo
- Búsqueda por nombre
- Filtro por precio
- Paginación

## 🛒 Carrito
- Añadir productos
- Actualizar cantidades
- Eliminar productos
- Ver carrito actual

## 📦 Pedidos
- Checkout
- Historial de pedidos
- Detalle de pedido
- Cancelación de pedidos

## 💳 Pagos
- Simulación de pago por tarjeta
- Cambio automático de estado

## 🏢 Panel Admin
- Dashboard resumen
- Ventas totales
- Pedidos por estado
- Productos más vendidos
- Ventas por mes
- Gestión de pedidos
- Cambio de estados

---

# 🧱 Arquitectura Backend

Proyecto estructurado por capas:

```text
app
├── application
│   ├── dto
│   └── usecases
├── domain
│   ├── entity
│   └── enums
├── infraestructure
│   ├── adapters
│   │   ├── input.rest
│   │   └── output.jpa
│   └── config