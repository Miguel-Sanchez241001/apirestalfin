# AlfinApiRest

## Descripción General

AlfinApiRest es una API desarrollada en **Java 17** utilizando el framework **Spring Boot** y la programación reactiva con **WebFlux**. El sistema permite realizar conversiones de tipo de cambio entre monedas, con persistencia de datos mediante **H2** y autenticación basada en **JWT**.

## Requisitos Previos

1. **Java 17**
   - Asegúrese de tener Java 17 instalado y configurado en su sistema. Puede verificarlo con:
     ```bash
     java -version
     ```
2. **Apache Maven**
   - Utilice Maven para la gestión de dependencias y compilación del proyecto.

## Tecnologías y Dependencias Clave

- **Spring Boot 3.4.1**
   - WebFlux para programación reactiva.
   - Seguridad con Spring Security.
- **JWT**: Para autenticación segura.
- **H2 Database**: Base de datos en disco persistente configurada con R2DBC para programación reactiva.
- **SpringDoc**: Generación automática de documentación OpenAPI/Swagger.

## Configuración del Proyecto

### Archivo `application.yml`

```yaml
spring:
  main:
    web-application-type: reactive
  r2dbc:
    url: r2dbc:h2:file:///./db_trxCambio
  sql:
    init:
      mode: always
      schema-locations: classpath:schema.sql
  logging:
    level:
      root: INFO
      pe.com.alfin: INFO
  application:
    name: appCambios
server:
  port: 8080

jwt:
  secret: aEIu9S7cvZUnPJWezau3rKUCxj4BLtpCVzhVSyam93prJOxofs7688P0OD5tmTIsLL6u7G9HpXvT
  expiration:
    days: 0
    hours: 0
    minutes: 15
    seconds: 0
springdoc:
  api-docs:
    path: /v3/swagger-ui.html
  swagger-ui:
    path: /v3/api-docs
```

### Base de Datos

La conexión a la base de datos se realiza mediante R2DBC. Para revisar los registros almacenados:

1. Acceda al archivo `db_trxCambio` creado en el directorio del proyecto.
2. Abra la consola de H2 en `http://localhost:8080/h2-console`.
   - **URL**: `jdbc:h2:file:./db_trxCambio`


Nota: Debido a la naturaleza reactiva de R2DBC, no se recomienda conectarse directamente a la base de datos una vez que el sistema esté en producción.

## Ejecución del Proyecto

1. Clone el repositorio:
   ```bash
   git clone <url-del-repositorio>
   ```
2. Compile y ejecute la aplicación con Maven:
   ```bash
   mvn spring-boot:run
   ```
3. La API estará disponible en:
   ```
   http://localhost:8080
   ```

## Documentación de la API

Swagger UI se encuentra habilitado para explorar los endpoints disponibles:

```plaintext
http://localhost:8080/v3/swagger-ui.html
```

## Endpoints Principales

### 1. Registro de Usuario (`/auth/signUp`)

**Método**: POST

**Descripción**: Permite registrar un nuevo usuario.

**Request Ejemplo:**
```json
{
  "nombre": "Juan",
  "apellidos": "Pérez García",
  "telefono": "0123456789",
  "celular": "987654321",
  "username": "juan.perez@example.com",
  "password": "securePassword123",
  "rolId": 1
}
```

**Nota**: El valor de `rolId` debe corresponder al rol asignado al usuario.

### 2. Inicio de Sesión (`/auth/login`)

**Método**: POST

**Descripción**: Permite obtener un token JWT utilizando las credenciales del usuario registrado.

**Response Ejemplo:**
```json
{
  "access_token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzdHJpbmdld2V3ZXdld2V3Iiwicm9sZXMiOlt7ImF1dGhvcml0eSI6IkFkbWluaXN0cmFkb3IifV0sImlhdCI6MTczNzIyNzA3MywiZXhwIjoxNzM3MjI3OTczfQ.LbcUTqrHAyy9Xfp1cOpT_cdddVlCq9Kv_IPQbMw6KSQx9K_60hysV8L_Qe3E5MDF"
}
```

**Nota**: El token JWT debe incluirse en el encabezado `Authorization` de todas las solicitudes posteriores:
```plaintext
Authorization: Bearer <token>
```

### 3. Auditoría (`/api/auditoria`)

**Método**: GET

**Descripción**: Recupera todas las entradas de auditoría registradas en el sistema.

**Requiere Token**: Sí

### 4. Tipo de Cambio (`/api/tipocambio`)

- **Buscar Tipo de Cambio** (`GET`):
   - Busca el tipo de cambio entre dos monedas proporcionadas.

- **Crear Tipo de Cambio** (`POST`):
   - Crea un nuevo tipo de cambio.

- **Modificar Tipo de Cambio** (`PUT`):
   - Modifica un tipo de cambio existente.

- **Realizar Tipo de Cambio** (`POST /doit`):
   - Calcula el tipo de cambio para un monto proporcionado entre dos monedas.

## Arquitectura de la Solución

### Diagrama de Arquitectura

![img_1.png](img_1.png)

### Pruebas Postman

![img_2.png](img_2.png)

![img_3.png](img_3.png)