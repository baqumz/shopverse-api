# 🛍️ ShopVerse API

API REST profesional con gestión de productos y categorías, seguridad básica, validaciones automáticas, documentación Swagger y procesamiento CSV.

## 📌 Tecnologías usadas
- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- Hibernate Validator
- Spring Security (Basic Auth)
- Spring Batch (procesos CSV)
- SpringDoc OpenAPI (Swagger UI)
- Base de datos embebida H2
- Maven

## 📂 Estructura del proyecto
```
com.technova.shopverseapi
├── config        → Configuración (seguridad, batch)
├── controller    → Endpoints REST documentados
├── service       → Lógica de negocio
│   └── impl      → Implementaciones concretas
├── dto           → Data Transfer Objects
├── model         → Entidades JPA validadas
├── repository    → Acceso a base de datos
├── exception     → Manejo global de errores
└── batch         → Procesos CSV automatizados
```

## 🌐 Endpoints principales
- `GET /api/products`
- `POST /api/products` *(solo ADMIN)*
- `GET /api/products/{id}`
- `GET /api/products/by-category/{categoryId}`
- `GET /api/categories`
- `POST /api/categories` *(solo ADMIN)*


## 📖 Documentación interactiva
Disponible en:
```
http://localhost:8081/swagger-ui.html
```

## ⚙️ Ejecutar el proyecto
```bash
mvn spring-boot:run
```

Luego accede desde:
- Swagger: `http://localhost:8081/swagger-ui.html`
- Consola H2: `http://localhost:8081/h2-console`

