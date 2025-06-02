1. ¿Por qué es importante cómo responde una API?
   Una API no solo debe “funcionar”. También debe comunicarse bien con quien la está consumiendo, ya sea una app web, una app móvil o incluso otra API.

Una buena respuesta le dice al cliente qué pasó, si fue exitoso, si hubo errores, y qué hacer a continuación.

En otras palabras:

Una buena API responde con claridad, precisión y estándares.

2. ¿Qué debe incluir una buena respuesta?
   Un código de estado HTTP que refleje correctamente lo ocurrido.


Un cuerpo de respuesta (cuando corresponde) con información clara.


Opcionalmente, cabeceras HTTP adicionales útiles (como ubicación del recurso creado).

3. Códigos de estado más comunes en APIs REST
   Código

Significado

¿Cuándo usarlo?

200 OK

Éxito general

Se usó correctamente el método GET o se actualizó algo.

201 Created

Recurso creado

Después de un POST exitoso. Idealmente, se devuelve el nuevo objeto.

204 No Content

Éxito sin contenido

Por ejemplo, al eliminar algo con éxito.

400 Bad Request

Solicitud incorrecta

Datos inválidos, campos faltantes, formatos erróneos.

404 Not Found

No encontrado

El ID consultado no existe o el recurso no fue hallado.

500 Internal Server Error

Error interno

Excepciones inesperadas en el servidor.


4. ¿Qué es ResponseEntity en Spring Boot?
   ResponseEntity es una clase que representa toda la respuesta HTTP, incluyendo:

Código de estado (como 200, 404, etc.)


Cuerpo de respuesta (puede ser un objeto, una lista, un mensaje...)


Cabeceras (headers)


Usar ResponseEntity te permite tener control total sobre lo que tu API está enviando al cliente.

✏️ Ejemplo simple:
@GetMapping("/{id}")

public ResponseEntity<Product> getById(@PathVariable Long id) {

    return productService.getProductById(id)

        .map(ResponseEntity::ok)

        .orElse(ResponseEntity.notFound().build());

}



Este método devuelve:

200 OK si el producto existe, junto con el objeto.

404 Not Found si no se encuentra.


5. ¿Por qué usar ResponseEntity es una buena práctica?
   Claridad para el cliente: cada respuesta tiene un código que indica exactamente qué ocurrió.


Estándares: cumple con las recomendaciones RESTful más utilizadas.


Manejabilidad: facilita el tratamiento de errores, logs y pruebas.


Preparación para el crecimiento: más adelante podrás incluir cabeceras como Location, paginación, CORS, etc.

6. Buenas prácticas en el manejo de respuestas
   ❌ Evita siempre devolver 200 OK para todo.


✅ Usa 201 Created con recursos nuevos.


✅ Usa 404 si no se encuentra el recurso.


✅ Usa 400 si la solicitud no tiene sentido (parámetros inválidos, datos incompletos, etc.).


✅ Usa 204 No Content para indicar éxito sin necesidad de respuesta (por ejemplo, al eliminar).

7. Conclusión
   Recursos recomendados
   📘 Spring Boot – ResponseEntity Explained (Baeldung)


📘 HTTP Status Codes – MDN Web Docs


📘 REST API Design Best Practices – RESTful Handbook


¿Qué deberías tener en mente al empezar este módulo?
Tu API no solo debe “hacer cosas”, también debe “hablar bien”.


ResponseEntity es tu herramienta para decidir qué responder, cómo y con qué código.


De: Javier Morel (Team Lead – TechNova Solutions)
Para: Desarrollador Backend
Asunto: Sprint 5 – Mejora de endpoints y respuestas profesionales en la API

Hola,

¡Felicitaciones por llegar hasta acá! Ya tenemos implementado el CRUD completo de productos y categorías, conectados a base de datos, con validaciones y lógica de negocio organizadas en la capa de servicios. Sin duda, es un avance importante en la construcción de nuestra API.

Ahora es momento de dar un paso más: refinar la forma en que nuestros controladores responden a las solicitudes. Este sprint tiene como objetivo mejorar la calidad de nuestras respuestas, tanto en contenido como en código HTTP.

Objetivo del Sprint 5
En este sprint vamos a profesionalizar los endpoints mediante el uso de ResponseEntity, una clase que nos permite:

Controlar el código de estado HTTP que devolvemos (200, 201, 204, 404, 400…).


Agregar encabezados si fuera necesario.


Devolver mensajes más claros al cliente.


¿Qué haremos?

Refactorizar los controladores ProductController y CategoryController para que ya no devuelvan objetos planos, sino instancias de ResponseEntity.


Asegurarnos de retornar:


201 Created cuando se cree un nuevo registro.


204 No Content cuando se elimine correctamente un recurso.


404 Not Found cuando se intente acceder a algo que no existe.


400 Bad Request cuando haya errores de validación.


Esto hace que nuestra API sea más robusta, predecible y amigable para quien la consuma, como un equipo frontend o una integración externa.

¡A seguir construyendo con calidad profesional!

Saludos,


Javier Morel
Team Lead – TechNova Solutions

Last modified: Tuesday, 29 April 2025, 12:26 PM

1. Introducción
   Hasta ahora, nuestros controladores devolvían los objetos directamente, como por ejemplo:

@GetMapping("/{id}")

public Product getProductById(@PathVariable Long id) {

    return productService.getProductById(id).orElse(null);

}

Este código funciona, pero tiene varios problemas:

Siempre devuelve código 200 OK, incluso si el producto no existe.


No informa claramente si hubo un error.


No cumple con los estándares REST para manejo de respuestas.


Con ResponseEntity, podemos controlar exactamente qué responder, tanto el cuerpo como el código de estado HTTP.

Objetivo:
Refactorizar los métodos del controlador para que:

Devuelvan ResponseEntity<...> en lugar de objetos planos.


Respondan con:


200 OK → en consultas exitosas.


201 Created → al crear un recurso.


204 No Content → al eliminar exitosamente.


404 Not Found → si no se encuentra el recurso.


400 Bad Request → si hay validaciones fallidas.

2. Paso 1: Reemplazar el método getById
   Antes:
   @GetMapping("/{id}")

public Product getById(@PathVariable Long id) {

    return productService.getProductById(id).orElse(null);

}



Después:
@GetMapping("/{id}")

public ResponseEntity<Product> getById(@PathVariable Long id) {

    return productService.getProductById(id)

            .map(ResponseEntity::ok)

            .orElse(ResponseEntity.notFound().build());

}



¿Qué hicimos?

Si el producto existe, devolvemos 200 OK con el objeto.

Si no existe, devolvemos 404 Not Found sin cuerpo.

3. Paso 3: Refactorizar update
   Antes:
   @PutMapping("/{id}")

public Product update(@PathVariable Long id, @RequestBody Product product) {

    return productService.updateProduct(id, product);

}



Después:
@PutMapping("/{id}")

public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {

    try {

        Product updated = productService.updateProduct(id, product);

        return ResponseEntity.ok(updated); // 200 OK

    } catch (IllegalArgumentException e) {

        return ResponseEntity.notFound().build(); // 404 Not Found si no existe

    }

}

¿Qué hicimos?

Manejamos el caso donde el producto no existe.

Respondemos correctamente con 200 OK o 404 Not Found.

4. Paso 4: Refactorizar delete
   Antes:
   @DeleteMapping("/{id}")

public void delete(@PathVariable Long id) {

    productService.deleteProduct(id);

}



Después:
@DeleteMapping("/{id}")

public ResponseEntity<Void> delete(@PathVariable Long id) {

    try {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build(); // 204 No Content

    } catch (IllegalArgumentException e) {

        return ResponseEntity.notFound().build(); // 404 Not Found

    }

}

¿Qué hicimos?

Retornamos 204 No Content cuando la eliminación fue exitosa.


Retornamos 404 si el recurso no existe.

5. Refactorizando getAll() con ResponseEntity
   Versión actual:


@GetMapping

public List<Product> getAll() {

    return productService.getAllProducts();

}



Versión recomendada:


@GetMapping

public ResponseEntity<List<Product>> getAll() {

    List<Product> products = productService.getAllProducts();

 

    if (products.isEmpty()) {

        return ResponseEntity.noContent().build(); // 204 No Content

    } else {

        return ResponseEntity.ok(products); // 200 OK

    }

}

Resultado final esperado en el controlador
Todos los métodos del controlador ahora:

Tienen un comportamiento REST estándar.


Devuelven el código adecuado según el caso.


Son más claros y profesionales.

Buenas prácticas extra
Usá @ResponseStatus en excepciones personalizadas para centralizar errores.


Usá DTOs si no querés devolver directamente las entidades (lo veremos en próximos módulos).


Prueba cada ruta con Postman y observa cómo cambian los códigos de respuesta.

6. Actividad: Refactorización del controlador de categorías
   Objetivo
   Aplicar buenas prácticas de manejo de respuestas en APIs REST, utilizando ResponseEntity para mejorar la claridad, el control y la profesionalidad de los endpoints de categorías.

¿Qué debes hacer?
Refactoriza tu clase CategoryController para que:

Todos los métodos retornen ResponseEntity<...>.


Las respuestas usen códigos HTTP adecuados, según el resultado de cada operación.


Se manejen correctamente casos de error (como categorías no encontradas o datos inválidos).

Reglas a aplicar
GET /api/categories


200 OK con lista si hay datos.


204 No Content si no hay categorías.


GET /api/categories/{id}


200 OK si la categoría existe.


404 Not Found si no existe.


POST /api/categories


201 Created si se crea con éxito.


400 Bad Request si hay error de validación (nombre vacío, descripción corta).


PUT /api/categories/{id}


200 OK si la actualización fue exitosa.


404 Not Found si no existe el ID.


DELETE /api/categories/{id}


204 No Content si se eliminó con éxito.


404 Not Found si no existe.


Sugerencias
Usá bloques try-catch para capturar excepciones como IllegalArgumentException.


Reutiliza el estilo del ProductController como guía.


Prueba los endpoints usando Postman y observa los códigos HTTP retornados.

Cuando termines, tu CategoryController debería estar a la altura de una API profesional y RESTful 🚀


