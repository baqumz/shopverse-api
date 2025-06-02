# Usa una imagen ligera de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo .jar generado por Maven al contenedor
COPY target/shopverse-api-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto 8080 para que se pueda acceder a la aplicación
EXPOSE 8080

# Comando que se ejecuta al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]