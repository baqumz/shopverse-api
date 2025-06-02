# 🛍️ ShopVerse API

ShopVerse API es una API REST sencilla desarrollada con Spring Boot y base de datos H2, organizada con una arquitectura por capas clara y profesional.

## 📌 Tecnologías usadas

- Java 17 + Spring Boot
- Spring Data JPA
- Base de datos embebida H2
- Maven

## 🗂️ Estructura del proyecto

```
com.technova.shopverseapi
├── controller     → Manejo de solicitudes HTTP (REST)
├── service        → Lógica de negocio y validaciones
│   └── impl       → Implementaciones de servicios
├── dto            → Data Transfer Objects (DTOs)
├── model          → Entidades JPA
└── repository     → Acceso a base de datos
```

## 🚀 Funcionalidades actuales

- CRUD completo de productos y categorías.
- Separación de responsabilidades por capas.
- Manejo profesional de respuestas HTTP con ResponseEntity.
- Relaciones entre entidades con JPA:
    - ManyToOne (Producto → Categoría)
    - OneToMany (Categoría → Productos)
- Uso de DTOs para respuestas optimizadas y controladas.
- Endpoints adicionales para consultas avanzadas:
    - Obtener productos junto a su categoría.
    - Obtener productos por categoría específica.
    - Obtener detalles completos de una categoría y sus productos.

## 🌐 Endpoints nuevos

- Obtener productos con datos de categoría incluidos (DTO):

```
GET /api/products/dto
```

- Obtener productos por categoría específica:

```
GET /api/products/by-category/{categoryId}
```

- Obtener detalles de categoría con nombres de productos asociados (DTO):

```
GET /api/categories/{id}/details
```

## 🔧 Cómo ejecutar el proyecto

Desde la consola, ejecutar:

```bash
mvn spring-boot:run
```

Luego acceder desde:

```
http://localhost:8081/h2-console (Consola H2)
http://localhost:8081/api/products (Productos)
http://localhost:8081/api/categories (Categorías)
```
