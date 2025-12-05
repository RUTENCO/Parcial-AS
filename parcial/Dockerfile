# Build stage
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /workspace/app

# Copy maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline -B

# Copy source code and build
COPY src src
RUN ./mvnw clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp

# Create user for security
RUN addgroup -g 1001 spring && adduser -u 1001 -G spring -s /bin/sh -D spring

# Copy jar from build stage
COPY --from=build /workspace/app/target/*.jar app.jar

USER spring:spring

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]