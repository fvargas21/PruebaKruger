# Kevaluacion - Sistema de Gestión de Capacitaciones

Sistema RESTful desarrollado con Spring Boot para gestionar capacitaciones internas de empleados, incluyendo cursos, sesiones y inscripciones.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Ejecución Local](#ejecución-local)
- [Ejecución con Docker](#ejecución-con-docker)
- [API Documentation](#api-documentation)
- [Datos de Prueba](#datos-de-prueba)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Endpoints Principales](#endpoints-principales)

## 🚀 Características

- ✅ Gestión completa de empleados
- ✅ Administración de cursos de capacitación
- ✅ Programación de sesiones de entrenamiento
- ✅ Sistema de inscripciones con validaciones
- ✅ Calificaciones y seguimiento de progreso
- ✅ Validaciones por grupos (Create/Update)
- ✅ Documentación automática con Swagger/OpenAPI
- ✅ Base de datos H2 embebida para desarrollo
- ✅ Arquitectura de servicios con interfaces
- ✅ Manejo global de excepciones
- ✅ Constantes centralizadas
- ✅ Datos de prueba pre-cargados

## 🛠️ Tecnologías

- **Java 17**
- **Spring Boot 3.3.3**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **H2 Database** (embebida)
- **MapStruct** (mapeo de DTOs)
- **Lombok** (reducción de boilerplate)
- **OpenAPI/Swagger** (documentación)
- **Maven** (gestión de dependencias)
- **Docker** (containerización)

## 📋 Requisitos Previos

### Para ejecución local:
- Java 17 o superior
- Maven 3.6+ (opcional, se incluye wrapper)
- Git

### Para ejecución con Docker:
- Docker
- Docker Compose (opcional)

## 💾 Instalación y Configuración

### 1. Clonar el Repositorio

```bash
git clone <repository-url>
cd kevaluacion
```

### 2. Verificar Java (Linux/Mac)

```bash
java -version
```

### 2. Verificar Java (Windows)

```cmd
java -version
```

Debe mostrar Java 17 o superior.

## 🏃‍♂️ Ejecución Local

### Opción 1: Usando Maven Wrapper (Recomendado)

#### Linux/Mac:
```bash
# Dar permisos de ejecución al wrapper
chmod +x mvnw

# Compilar el proyecto
./mvnw clean compile

# Ejecutar la aplicación
./mvnw spring-boot:run

# Ejecutar con perfil de desarrollo específico
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Windows:
```cmd
# Compilar el proyecto
mvnw.cmd clean compile

# Ejecutar la aplicación
mvnw.cmd spring-boot:run

# Ejecutar con perfil de desarrollo específico
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

### Opción 2: Usando Maven (si está instalado)

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn spring-boot:run

# Ejecutar con perfil de desarrollo
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Opción 3: Ejecutar JAR

```bash
# Compilar y empaquetar
./mvnw clean package

# Ejecutar el JAR generado
java -jar target/kevaluacion-0.0.1-SNAPSHOT.jar

# Ejecutar con perfil de desarrollo
java -jar target/kevaluacion-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

La aplicación estará disponible en: **http://localhost:8080**

## 📋 Configuración de Perfiles

El proyecto incluye configuración para desarrollo optimizada:

### 🔧 Perfil de Desarrollo (`dev`)
- Base de datos H2 en memoria con datos de prueba
- Logging detallado y debugging habilitado
- Consola H2 habilitada
- Swagger UI habilitado
- Actuator con todos los endpoints expuestos
- Recarga automática con DevTools
- Configuración optimizada para desarrollo

**Activar**: `--spring.profiles.active=dev` (opcional)

## 🐳 Ejecución con Docker

### 1. Crear el Dockerfile

El Dockerfile está incluido en el proyecto (ver archivo `Dockerfile` en la raíz).

### 2. Construir la imagen

```bash
docker build -t kevaluacion:latest .
```

### 3. Ejecutar el contenedor

```bash
docker run -p 8080:8080 kevaluacion:latest
```

### 4. Usando Docker Compose (Opcional)

```bash
docker-compose up -d
```

La aplicación estará disponible en: **http://localhost:8080**

El swagger estará disponible en: **http://localhost:8080/kevaluacion/swagger-ui.html**

## 📚 API Documentation

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva de la API:

- **Swagger UI**: http://localhost:8080/kevaluacion/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **Consola H2**: http://localhost:8080/h2-console

### Configuración H2 Console:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: *(vacío)*

## 🎯 Datos de Prueba

La aplicación carga automáticamente datos de prueba al iniciar. Estos incluyen:

### 👥 Empleados Pre-cargados:
| ID | Nombre | Email |
|----|--------|-------|
| 1 | Juan Pérez | juan.perez@kevaluacion.com |
| 2 | María García | maria.garcia@kevaluacion.com |
| 3 | Carlos López | carlos.lopez@kevaluacion.com |

### 📚 Cursos Pre-cargados:
| ID | Nombre | Descripción | Capacidad Máxima |
|----|--------|-------------|------------------|
| 1 | Spring Boot Fundamentals | Learn the basics of Spring Boot framework | 20 |
| 2 | Microservices Architecture | Design and implement microservices with Spring Cloud | 15 |
| 3 | Database Design | Relational database design principles and best practices | 25 |

### 📅 Sesiones Pre-cargadas:
- **Sesión 1**: Spring Boot Fundamentals (7 días desde hoy) - Capacidad: 15
- **Sesión 2**: Spring Boot Fundamentals (21 días desde hoy) - Capacidad: 20
- **Sesión 3**: Microservices Architecture (14 días desde hoy) - Capacidad: 10
- **Sesión 4**: Database Design (28 días desde hoy) - Capacidad: 25

### 📝 Inscripciones Pre-cargadas:
- Juan Pérez inscrito en Spring Boot Fundamentals (Sesión 1) - Calificación: 8.5
- María García inscrita en Spring Boot Fundamentals (Sesión 1) - Calificación: 9.0
- Juan Pérez inscrito en Microservices Architecture (Sesión 3) - Sin calificación
- Carlos López inscrito en Spring Boot Fundamentals (Sesión 2) - Sin calificación

## 🏗️ Estructura del Proyecto

```
src/main/java/sasf/net/kevaluacion/
├── config/
│   └── DataLoader.java              # Carga de datos de prueba
├── controller/
│   ├── EmployeeController.java      # REST endpoints para empleados
│   ├── CourseController.java        # REST endpoints para cursos y sesiones
│   └── EnrollmentController.java    # REST endpoints para inscripciones
├── dto/
│   ├── employee/                    # DTOs para empleados
│   ├── course/                      # DTOs para cursos
│   ├── session/                     # DTOs para sesiones
│   └── enrollment/                  # DTOs para inscripciones
├── entity/
│   ├── Employee.java               # Entidad empleado
│   ├── Course.java                 # Entidad curso
│   ├── Session.java                # Entidad sesión
│   └── Enrollment.java             # Entidad inscripción
├── exception/
│   ├── BusinessException.java       # Excepciones de negocio
│   ├── ResourceNotFoundException.java
│   ├── ErrorResponse.java          # Respuesta de error estándar
│   └── GlobalExceptionHandler.java # Manejo global de excepciones
├── mapper/
│   ├── EmployeeMapper.java         # Mapeo entity-DTO empleados
│   ├── CourseMapper.java           # Mapeo entity-DTO cursos
│   ├── SessionMapper.java          # Mapeo entity-DTO sesiones
│   └── EnrollmentMapper.java       # Mapeo entity-DTO inscripciones
├── repository/
│   ├── EmployeeRepository.java     # Repositorio empleados
│   ├── CourseRepository.java       # Repositorio cursos
│   ├── SessionRepository.java      # Repositorio sesiones
│   └── EnrollmentRepository.java   # Repositorio inscripciones
├── service/
│   ├── EmployeeService.java        # Interfaz servicio empleados
│   ├── CourseService.java          # Interfaz servicio cursos
│   ├── SessionService.java         # Interfaz servicio sesiones
│   ├── EnrollmentService.java      # Interfaz servicio inscripciones
│   └── impl/
│       ├── EmployeeServiceImpl.java    # Implementación empleados
│       ├── CourseServiceImpl.java      # Implementación cursos
│       ├── SessionServiceImpl.java     # Implementación sesiones
│       └── EnrollmentServiceImpl.java  # Implementación inscripciones
├── util/
│   └── Constants.java              # Constantes centralizadas
├── validation/
│   └── ValidationGroups.java       # Grupos de validación
└── KevaluacionApplication.java     # Clase principal
```

## 🔗 Endpoints Principales

### Empleados
- `GET /kevaluacion/employees` - Listar todos los empleados
- `POST /kevaluacion/employees` - Crear empleado
- `GET /kevaluacion/employees/{id}` - Obtener empleado por ID
- `PUT /kevaluacion/employees/{id}` - Actualizar empleado

### Cursos
- `GET /kevaluacion/courses` - Listar todos los cursos con sesiones
- `POST /kevaluacion/courses` - Crear curso
- `GET /kevaluacion/courses/{id}` - Obtener curso por ID
- `PUT /kevaluacion/courses/{id}` - Actualizar curso

### Sesiones
- `POST /kevaluacion/courses/{courseId}/sessions` - Crear sesión para un curso
- `GET /kevaluacion/courses/{courseId}/sessions` - Listar sesiones de un curso
- `PUT /kevaluacion/sessions/{sessionId}` - Actualizar sesión

### Inscripciones
- `POST /kevaluacion/sessions/{sessionId}/enrollments` - Inscribir empleado en sesión
- `GET /kevaluacion/employees/{employeeId}/enrollments` - Obtener inscripciones de empleado
- `GET /kevaluacion/sessions/{sessionId}/enrollments` - Obtener inscripciones de sesión
- `PUT /kevaluacion/enrollments/{enrollmentId}/grade` - Actualizar calificación

## 🚨 Solución de Problemas

### Error de Java Version
```bash
Error: Could not find or load main class
```
**Solución**: Verificar que Java 17+ esté instalado y configurado en PATH.

### Error de Puerto en Uso
```bash
Port 8080 was already in use
```
**Solución**: Cambiar el puerto en `application.properties`:
```properties
server.port=8081
```

### Problemas de Memoria
```bash
OutOfMemoryError
```
**Solución**: Aumentar memoria de la JVM:
```bash
java -Xmx1g -jar target/kevaluacion-0.0.1-SNAPSHOT.jar
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 📞 Soporte

Para reportar bugs o solicitar funcionalidades, por favor abre un issue en el repositorio de GitHub.

---

**Desarrollado con ❤️ usando Spring Boot**
