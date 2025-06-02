# 🛍️ ShopVerse API

API REST profesional con gestión de productos y categorías, seguridad básica, validaciones automáticas, documentación Swagger, procesamiento CSV y despliegue en Docker.

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
- Docker

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

## 🐳 Despliegue con Docker
Construye la imagen:
```bash
mvn clean package
docker build -t shopverse-api .
docker run -p 8080:8080 shopverse-api
```

## ⚙️ Ejecutar el proyecto (sin Docker)
```bash
mvn spring-boot:run
```

Luego accede desde:
- Swagger: `http://localhost:8081/swagger-ui.html`
- Consola H2: `http://localhost:8081/h2-console`
