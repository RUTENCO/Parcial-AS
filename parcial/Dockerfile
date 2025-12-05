# Dockerfile
FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
# Copia el archivo .jar que genera Maven al contenedor
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]