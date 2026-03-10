# Employee API — Invex

REST API para la gestión de empleados desarrollada con **Spring Boot 4**, **Java**, **MySQL** y arquitectura hexagonal.

---

## Stack tecnológico

| Tecnología | Versión |
|---|---|
| Java | 21+ |
| Spring Boot | 4.0.3 |
| MySQL | 8.x |
| JPA / Hibernate | (incluido en Spring Boot) |
| Lombok | (incluido en Spring Boot) |
| SpringDoc OpenAPI | 3.0.2 |
| JUnit | 5 |
| Mockito | (incluido en Spring Boot Test) |

---

## Requisitos previos

- Java 21 o superior instalado
- Maven 3.9+ instalado
- MySQL 8.x corriendo en `localhost:3306`
- Base de datos `invex` creada

---

## Configuración de base de datos

Ejecutar en MySQL:

```sql
CREATE DATABASE IF NOT EXISTS invex;
CREATE USER 'invex'@'localhost' IDENTIFIED BY 'P@@ssw00rd_2k26';
GRANT ALL PRIVILEGES ON invex.* TO 'invex'@'localhost';
FLUSH PRIVILEGES;
```

La tabla `employee` se crea automáticamente al arrancar la aplicación (`spring.jpa.hibernate-ddl-auto=update`).

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
cd employee
```

### 2. Compilar el proyecto

```bash
mvn clean install
```

### 3. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La API quedará disponible en:
```
http://localhost:9191/invex
```

---

## Documentación interactiva (Swagger UI)

Con la aplicación corriendo, acceder a:

```
http://localhost:9191/invex/swagger-ui/index.html
```

---

## Endpoints disponibles

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/v1/employees` | Obtener todos los empleados |
| GET | `/api/v1/employees/{id}` | Obtener empleado por ID |
| GET | `/api/v1/employees/search?name=XXXX` | Buscar empleados por nombre |
| POST | `/api/v1/employees` | Crear uno o varios empleados |
| PUT | `/api/v1/employees/{id}` | Actualizar empleado por ID |
| DELETE | `/api/v1/employees/{id}` | Eliminar empleado por ID |

**URL base completa:** `http://localhost:9191/invex/api/v1/employees`

---

## Ejemplos de uso

### GET todos los empleados
```bash
curl -X GET http://localhost:9191/invex/api/v1/employees
```

### GET empleado por ID
```bash
curl -X GET http://localhost:9191/invex/api/v1/employees/1
```

### GET buscar por nombre
```bash
curl -X GET "http://localhost:9191/invex/api/v1/employees/search?name=JOSÉ"
```

### POST crear empleados
```bash
curl -X POST http://localhost:9191/invex/api/v1/employees \
  -H "Content-Type: application/json" \
  -d '[{
    "firstName": "JOSÉ",
    "secondName": null,
    "lastNameMatern": "SÁNCHEZ",
    "lastNamePatern": "SILVA",
    "age": 52,
    "gender": "H",
    "birthdate": "25-12-1974",
    "position": "PROGRAMADOR SENIOR BACKEND JAVA",
    "status": true
  }]'
```

### PUT actualizar empleado
```bash
curl -X PUT http://localhost:9191/invex/api/v1/employees/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "JOSÉ",
    "lastNameMatern": "SÁNCHEZ",
    "lastNamePatern": "SILVA",
    "age": 53,
    "gender": "H",
    "birthdate": "25-12-1974",
    "position": "ARQUITECTO DE SOFTWARE",
    "status": true
  }'
```

### DELETE eliminar empleado
```bash
curl -X DELETE http://localhost:9191/invex/api/v1/employees/1
```

---

## Pruebas unitarias

Las pruebas cubren `EmployeeServiceI` y `EmployeeControllerI` con **JUnit 5** y **Mockito**.

### Ejecutar pruebas

```bash
mvn test
```

### Ejecutar con reporte de cobertura

```bash
mvn test jacoco:report
```

El reporte se genera en: `target/site/jacoco/index.html`

### Casos de prueba incluidos

**EmployeeServiceITest**
- `readAll` — lista exitosa / excepción de repositorio
- `readById` — ID existente / ID no encontrado
- `create` — guardado exitoso
- `updateById` — actualización exitosa / ID no encontrado
- `deleteById` — eliminación exitosa / ID no encontrado
- `readByName` — búsqueda exitosa / excepción de repositorio

**EmployeeControllerITest**
- `readAll` — HTTP 200 / HTTP 500
- `readById` — HTTP 200 / HTTP 400 (ID nulo o ≤ 0)
- `create` — HTTP 200 / HTTP 400 (lista nula o vacía)
- `updateById` — HTTP 200 / HTTP 400 (ID nulo)
- `deleteById` — HTTP 200 / HTTP 400 (ID nulo)
- `readByName` — HTTP 200 / HTTP 400 (nombre nulo o vacío)

---

## Estructura del proyecto

```
src/
└── main/
│   └── java/invex/employee/api/
│       ├── application/usecase/
│       │   ├── EmployeeService.java         # Interfaz del servicio
│       │   └── EmployeeServiceI.java        # Implementación
│       ├── domain/
│       │   ├── model/
│       │   │   └── EmployeeEntity.java      # Entidad JPA
│       │   └── ports/output/
│       │       └── EmployeeRepository.java  # Repositorio JPA
│       └── infrastructure/adapters/input/
│           ├── EmployeeController.java      # Interfaz del controller
│           └── EmployeeControllerI.java     # Implementación
└── test/
    └── java/invex/employee/api/
        ├── application/usecase/
        │   └── EmployeeServiceITest.java
        └── infrastructure/adapters/input/
            └── EmployeeControllerITest.java
```

---

## Logs

Los logs se generan en consola y en archivo:

```
logs/employee-api.log
```

Configuración en `application.properties`:
- Nivel general: `INFO`
- Nivel del proyecto: `DEBUG`
- Rotación: máximo 10MB por archivo, 30 días de historial
