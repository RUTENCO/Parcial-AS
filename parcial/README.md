# Sistema de Inventario - API REST

Sistema de gestión de inventario desarrollado con Spring Boot que permite consultar y registrar productos en diferentes almacenes.

## 🚀 Características

- ✅ **Punto 1**: Método GET para consultar inventario por almacén
- ✅ **Punto 2**: Método POST para registrar productos con stock inicial
- ✅ **Punto 3**: Base de datos relacional PostgreSQL con entidades Producto y Almacén
- ✅ **Punto 4**: Documentación con OpenAPI/Swagger
- ✅ **Punto 5**: Respuestas en formato JSON
- ✅ **Punto 6**: Versionamiento con Custom Request Header (X-API-VERSION)
- ✅ **Punto 7**: Implementación de HATEOAS
- ✅ **Punto 8**: Evidencias de funcionamiento (ver sección testing)
- ✅ **Punto 9**: Repositorio en GitHub
- ✅ **Punto 10**: Contenedores con Docker

## 🏗️ Arquitectura

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Frontend      │───▶│    Spring Boot   │───▶│   PostgreSQL    │
│   (Swagger UI)  │    │   REST API       │    │   Database      │
└─────────────────┘    └──────────────────┘    └─────────────────┘
```

### Entidades Principales

- **Almacén**: Representa las sedes/ubicaciones físicas
- **Producto**: Catálogo de productos disponibles  
- **Inventario**: Relación entre almacenes y productos con cantidad

## 🔧 Tecnologías

- **Java 17** - Lenguaje de programación
- **Spring Boot 3.2** - Framework principal
- **Spring Data JPA** - Persistencia
- **Spring HATEOAS** - Hipermedia REST
- **PostgreSQL** - Base de datos
- **SpringDoc OpenAPI** - Documentación API
- **Docker & Docker Compose** - Contenedores
- **Maven** - Gestión de dependencias

## 🚀 Ejecución

### Opción 1: Con Docker Compose (Recomendado)

```bash
# Clonar el repositorio
git clone <tu-repo-url>
cd parcial

# Ejecutar con Docker Compose
docker compose up --build

# La API estará disponible en:
# http://localhost:8080
# Swagger UI: http://localhost:8080/swagger-ui.html
```

### Opción 2: Desarrollo Local

```bash
# Iniciar solo la base de datos
docker compose up db

# Ejecutar la aplicación
./mvnw spring-boot:run -Dspring-boot.run.profiles=local

# O usando el IDE con el perfil 'local'
```

## 📋 API Endpoints

### Headers Requeridos
Todas las peticiones requieren el header de versionado:
```
X-API-VERSION: v1
```

### 🏢 Almacenes
- `GET /api/v1/warehouses` - Listar almacenes
- `GET /api/v1/warehouses/{id}` - Obtener almacén por ID
- `POST /api/v1/warehouses` - Crear almacén

### 📦 Productos  
- `GET /api/v1/products` - Listar productos
- `GET /api/v1/products/{id}` - Obtener producto por ID
- `POST /api/v1/products` - Crear producto

### 📊 Inventario
- `GET /api/v1/inventory?warehouseId={id}` - **[PUNTO 1]** Consultar inventario por almacén
- `POST /api/v1/inventory` - **[PUNTO 2]** Registrar stock inicial

## 📝 Ejemplos de Uso

### 1. Consultar Inventario por Almacén

```bash
curl -X GET "http://localhost:8080/api/v1/inventory?warehouseId=1" \
     -H "X-API-VERSION: v1" \
     -H "Content-Type: application/json"
```

**Respuesta:**
```json
{
  "_embedded": {
    "inventoryDTOList": [
      {
        "id": 1,
        "almacen": {
          "id": 1,
          "nombre": "Sede Central",
          "direccion": null,
          "ciudad": "Bogotá"
        },
        "producto": {
          "id": 1,
          "nombre": "Laptop Dell",
          "descripcion": null,
          "precio": 2500.00
        },
        "cantidad": 10,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/v1/inventory?warehouseId=1"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/v1/inventory?warehouseId=1"
    }
  }
}
```

### 2. Registrar Stock Inicial

```bash
curl -X POST "http://localhost:8080/api/v1/inventory" \
     -H "X-API-VERSION: v1" \
     -H "Content-Type: application/json" \
     -d '{
       "warehouseId": 1,
       "productId": 2,
       "cantidad": 50
     }'
```

**Respuesta:**
```json
{
  "id": 3,
  "almacen": {
    "id": 1,
    "nombre": "Sede Central",
    "direccion": null,
    "ciudad": "Bogotá"
  },
  "producto": {
    "id": 2,
    "nombre": "Mouse Logitech",
    "descripcion": null,
    "precio": 50.00
  },
  "cantidad": 50,
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/v1/inventory"
    },
    "ver-inventario": {
      "href": "http://localhost:8080/api/v1/inventory?warehouseId=1"
    }
  }
}
```

## 🗃️ Base de Datos

La base de datos se inicializa automáticamente con datos de prueba:

```sql
-- Almacenes
INSERT INTO almacenes (nombre, ciudad) VALUES
('Sede Central', 'Bogotá'),
('Sede Norte', 'Medellín');

-- Productos  
INSERT INTO productos (nombre, precio) VALUES
('Laptop Dell', 2500.00),
('Mouse Logitech', 50.00);

-- Inventario inicial
INSERT INTO inventario (almacen_id, producto_id, cantidad) VALUES 
(1, 1, 10),  -- Sede Central: 10 Laptops
(2, 2, 20);  -- Sede Norte: 20 Mouses
```

## 📚 Documentación API

La documentación interactiva está disponible en:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

## 🧪 Testing

### Pruebas con Postman/Thunder Client

Importar la colección desde la documentación de Swagger o usar los ejemplos curl proporcionados.

### Pruebas Funcionales

1. **Verificar versionamiento**: Hacer petición sin header `X-API-VERSION` (debe fallar)
2. **Consultar inventario**: GET con `warehouseId=1` (debe retornar productos)
3. **Registrar stock**: POST con datos válidos (debe crear/actualizar inventario)
4. **HATEOAS**: Verificar que las respuestas incluyan enlaces `_links`
5. **Errores**: Probar con IDs inexistentes (debe retornar 404)

### Health Check

```bash
curl http://localhost:8080/actuator/health
```

## 🔒 Consideraciones de Seguridad

- Variables de entorno para credenciales de BD
- Usuario no-root en contenedor Docker
- Validación de entrada en controllers
- Manejo centralizado de excepciones

## 📂 Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/udea/parcial/
│   │   ├── config/          # Configuración (OpenAPI, Interceptores)
│   │   ├── controller/      # Controladores REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── entity/         # Entidades JPA
│   │   ├── exception/      # Manejo de excepciones
│   │   ├── mapper/         # Mappers DTO ↔ Entity
│   │   ├── repository/     # Repositorios JPA
│   │   └── service/        # Lógica de negocio
│   └── resources/
│       ├── application.properties      # Configuración producción
│       └── application-local.properties # Configuración local
├── db-init/
│   └── init.sql           # Script inicialización BD
├── docker-compose.yaml    # Orquestación contenedores
└── Dockerfile            # Imagen de la aplicación
```

## 🤝 Contribución

1. Fork el proyecto
2. Crear branch: `git checkout -b feature/nueva-funcionalidad`
3. Commit: `git commit -m 'Agregar nueva funcionalidad'`
4. Push: `git push origin feature/nueva-funcionalidad`
5. Pull Request

## 📄 Licencia

Este proyecto es para fines académicos - Universidad de Antioquia.

---

**Desarrollado con ❤️ para el Parcial 2 de Arquitectura de Software**
