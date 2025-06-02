# 🛍️ ShopVerse API

ShopVerse API es una API REST sencilla desarrollada con Spring Boot y base de datos H2, organizada con una arquitectura por capas clara y profesional.

## 📌 Tecnologías usadas

- Java 17 + Spring Boot (3.5.0 - LTS)
- Spring Data JPA
- Base de datos embebida H2
- Hibernate Validator (validaciones automáticas)
- Spring Security (autenticación básica y autorización)
- Spring Batch (procesamiento automatizado desde CSV)
- Maven

## 🗂️ Estructura del proyecto

```
com.technova.shopverseapi
├── config         → Configuraciones adicionales (seguridad)
├── controller     → Manejo de solicitudes HTTP (REST)
├── service        → Lógica de negocio y validaciones
│   └── impl       → Implementaciones de servicios
├── dto            → Data Transfer Objects (DTOs)
├── model          → Entidades JPA con validaciones
├── repository     → Acceso a base de datos
├── exception      → Manejo global de excepciones
└── batch          → Procesamiento automatizado desde archivos CSV (Spring Batch)
    ├── BatchConfig.java           → Configuración de Jobs Batch
    ├── BatchJobRunner.java        → Ejecución automática de Jobs
    └── model                      → Modelos auxiliares para lectura CSV
```

## 🚀 Funcionalidades actuales

- CRUD completo de productos y categorías.
- Separación de responsabilidades por capas.
- Manejo profesional de respuestas HTTP con ResponseEntity.
- Relaciones entre entidades con JPA:
  - ManyToOne (Producto → Categoría)
  - OneToMany (Categoría → Productos)
- Uso de DTOs para respuestas optimizadas y controladas.
- Validaciones automáticas con Bean Validation (`@NotBlank`, `@Size`, `@Min`, etc.).
- Manejo global de errores mediante `@ControllerAdvice` con mensajes personalizados.
- Resolución del problema de recursividad en relaciones mediante anotaciones Jackson (`@JsonManagedReference`, `@JsonBackReference`).

## 🔒 Seguridad implementada

La API ahora está protegida mediante autenticación básica (Basic Auth) y roles claramente definidos:

| Usuario | Contraseña | Rol   | Permisos                                |
|---------|------------|-------|-----------------------------------------|
| admin   | admin123   | ADMIN | Leer, crear, editar y eliminar recursos |
| user    | user123    | USER  | Solo lectura de recursos                |

### Ejemplo de acceso protegido con cURL:

- Acceso válido con usuario ADMIN:

```bash
curl -u admin:admin123 http://localhost:8081/api/products
```

- Acceso restringido para creación con usuario USER (403 Forbidden):

```bash
curl -u user:user123 -X POST http://localhost:8081/api/products \
-H "Content-Type: application/json" \
-d '{"name":"Producto Test","description":"Descripción","price":10,"category":{"id":1}}'
```

## 📂 Procesamiento automatizado con Spring Batch

El proyecto incluye un procesamiento automatizado mediante Spring Batch para importar productos y categorías desde archivos CSV ubicados en `src/main/resources/data`.

**Archivos CSV de ejemplo:**

- `categories.csv`
- `products.csv`

### Ejecución automática al inicio

Al iniciar la aplicación, el proceso Batch carga automáticamente los datos desde los CSV hacia la base de datos (H2).

**Consultas de verificación desde la consola H2:**

```sql
SELECT * FROM category;
SELECT * FROM product;
SELECT * FROM BATCH_JOB_EXECUTION;
```

## 🌐 Endpoints adicionales

- Obtener productos con datos de categoría incluidos (DTO):

```
GET /api/products/dto
```

- Obtener productos por categoría específica:

```
GET /api/products/by-category/{categoryId}
```

- Obtener detalles de una categoría con nombres de productos asociados (DTO):

```
GET /api/categories/{id}/details
```

## ✅ Validaciones implementadas

Las entidades tienen validaciones integradas para evitar datos inválidos:

- **Category**:
  - El nombre no puede estar vacío (`@NotBlank`)
  - La descripción debe tener al menos 10 caracteres (`@Size(min=10)`)

- **Product**:
  - El nombre no puede estar vacío (`@NotBlank`)
  - La descripción no puede estar vacía (`@NotBlank`)
  - El precio es obligatorio y debe ser positivo (`@NotNull`, `@Min(1)`)
  - La categoría es obligatoria (`@NotNull`)

## 🚨 Manejo global de errores

La aplicación incluye una clase global `GlobalExceptionHandler` que captura automáticamente errores de validación y excepciones personalizadas, devolviendo mensajes específicos y amigables al cliente.

### Ejemplo de respuesta de validación:

```json
{
  "name": "El nombre del producto no puede estar vacío",
  "description": "La descripción no puede estar vacía",
  "price": "El precio debe ser mayor a 0",
  "category": "La categoría es obligatoria"
}
```

## 🔧 Cómo ejecutar el proyecto

Desde la consola, ejecutar:

```bash
mvn spring-boot:run
```

Luego acceder desde el navegador o cliente HTTP (como Postman):

- Consola H2:  
  [http://localhost:8081/h2-console](http://localhost:8081/h2-console)

- Productos (requiere autenticación):  
  [http://localhost:8081/api/products](http://localhost:8081/api/products)

- Categorías (requiere autenticación):  
  [http://localhost:8081/api/categories](http://localhost:8081/api/categories)

¡Todo listo para desplegar y continuar desarrollando ShopVerse de manera profesional! 🚀
