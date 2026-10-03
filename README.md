# 🐾 VetTurno

### Backend profesional con Spring Boot — Veterinaria Huellitas

VetTurno es una API REST desarrollada para digitalizar la gestión de citas de la **Veterinaria Huellitas**.

El proyecto permite administrar responsables, mascotas, veterinarios y citas, evitando cruces de horarios para un mismo veterinario.

La API cuenta con persistencia en MySQL, validaciones, manejo global de errores, autenticación mediante JWT y autorización mediante los roles `USER` y `ADMIN`.

---

## 📖 Historia

Doña Marta administra la Veterinaria Huellitas junto con el doctor Andrés y Paula, quien se encarga de la recepción.

Actualmente, la información de los responsables, mascotas y citas se gestiona mediante un cuaderno y conversaciones de WhatsApp. Esto puede provocar errores como:

* Registrar dos citas para el mismo veterinario a la misma hora.
* Escribir incorrectamente los datos de una mascota.
* Perder información de contacto de los responsables.
* Dificultar la consulta de la agenda.

VetTurno busca solucionar estos problemas mediante una API REST organizada y persistente.

---

# 🎯 Alcance del proyecto

## Incluye

* Registro de usuarios.
* Inicio de sesión.
* Autenticación mediante JWT.
* Roles `USER` y `ADMIN`.
* Gestión de propietarios.
* Gestión de mascotas.
* Gestión de veterinarios.
* Gestión de citas.
* Prevención de citas duplicadas para un mismo veterinario y horario.
* Consulta de citas por veterinario.
* Validación de datos.
* Manejo global de errores.
* Persistencia mediante MySQL.
* Documentación mediante Swagger/OpenAPI.

## Fuera del alcance

* Historia clínica.
* Diagnósticos.
* Fórmulas médicas.
* Pagos.
* Facturación.
* Inventario.
* Tienda de mascotas.
* Recordatorios por correo o WhatsApp.
* Aplicación web o móvil.
* Despliegue en la nube.
* Docker como requisito.

---

# 🛠️ Tecnologías

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Spring Security
* JWT
* BCrypt
* Bean Validation
* Swagger / OpenAPI
* Git
* GitHub

---

# 🏗️ Arquitectura

El proyecto utiliza una arquitectura por capas:

```text
src/main/java/com/tallerEvaluativo/VetTurno
│
├── config
│   └── OpenApiConfig
│
├── controller
│   ├── AuthController
│   ├── PropietarioController
│   ├── MascotaController
│   ├── VeterinarioController
│   └── CitaController
│
├── dto
│   ├── AuthResponse
│   ├── LoginRequest
│   ├── RegistroRequest
│   ├── PropietarioRequest
│   ├── PropietarioDTO
│   ├── MascotaRequest
│   ├── MascotaDTO
│   ├── VeterinarioDTO
│   ├── CitaRequest
│   ├── CitaDTO
│   └── ApiError
│
├── exception
│   └── GlobalExceptionHandler
│
├── model
│   ├── Usuario
│   ├── Rol
│   ├── Propietario
│   ├── Mascota
│   ├── Veterinario
│   └── Cita
│
├── repository
│   ├── UsuarioRepository
│   ├── PropietarioRepository
│   ├── MascotaRepository
│   ├── VeterinarioRepository
│   └── CitaRepository
│
├── security
│   ├── JwtService
│   ├── JwtAuthFilter
│   ├── UsuarioDetailsService
│   └── SecurityConfig
│
└── service
    ├── AuthService
    ├── PropietarioService
    ├── MascotaService
    ├── VeterinarioService
    └── CitaService
```

### Responsabilidad de las capas

**Controller:** recibe las peticiones HTTP y devuelve las respuestas.

**Service:** contiene la lógica de negocio.

**Repository:** comunica la aplicación con la base de datos mediante Spring Data JPA.

**DTO:** define los datos que entran y salen de la API sin exponer directamente las entidades.

**Security:** gestiona autenticación, JWT y autorización por roles.

**Exception:** centraliza el manejo de errores.

---

# 🗄️ Modelo de datos

VetTurno utiliza cinco entidades principales:

```text
Usuario
   │
   └── rol: USER / ADMIN


Propietario
   │
   └── 1 ─────── N ─── Mascota
                         │
                         │
                         └── 1 ─────── N ─── Cita ─── N : 1 ─── Veterinario
```

### Propietario

Representa a la persona responsable de una o varias mascotas.

Campos:

* `id`
* `nombre`
* `telefono`
* `email`

### Mascota

Representa al paciente de la veterinaria.

Campos:

* `id`
* `nombre`
* `especie`
* `raza`
* `propietario`

Una mascota pertenece a un propietario.

### Veterinario

Representa al profesional que atiende las citas.

Campos:

* `id`
* `nombre`
* `especialidad`

### Cita

Representa una reserva.

Campos:

* `id`
* `fechaHora`
* `motivo`
* `mascota`
* `veterinario`

### Usuario

Representa a la persona que utiliza la API.

Campos:

* `id`
* `email`
* `password`
* `rol`

La contraseña se almacena utilizando BCrypt.

---

# ⚙️ Configuración

## Requisitos

Antes de ejecutar el proyecto se necesita:

* Java 17
* Maven
* MySQL
* Git

---

## 🗄️ Base de datos MySQL

Crear la base de datos:

```sql
CREATE DATABASE vetturno;
```

Luego configurar las propiedades de conexión en:

```text
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vetturno_db
spring.datasource.username=SU_USUARIO
spring.datasource.password=SU_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

stripe.secret-key=${STRIPE_SECRET_KEY}
stripe.webhook-secret=${STRIPE_WEBHOOK_SECRET:whsec_pendiente}

stripe.success-url=${STRIPE_SUCCESS_URL:http://localhost:8080/api/pagos/success}
stripe.cancel-url=${STRIPE_CANCEL_URL:http://localhost:8080/api/pagos/cancel}

jwt.secret=SU_SECRETO_JWT
jwt.expiration=86400000


```

> No publicar contraseñas, secretos JWT ni otras credenciales reales en GitHub.

---

# ▶️ Cómo ejecutar

Clonar el repositorio:

```bash
git clone URL_DEL_REPOSITORIO
```

Entrar al proyecto:

```bash
cd VetTurno
```

Ejecutar con Maven:

### Windows

```bash
.\mvnw.cmd spring-boot:run
```

### macOS/Linux

```bash
./mvnw spring-boot:run
```

La aplicación se ejecutará en:

```text
http://localhost:8080
```

---

# 📚 Swagger / OpenAPI

La API está documentada mediante Swagger.

Una vez iniciada la aplicación se puede acceder a:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger permite consultar y ejecutar los endpoints directamente desde el navegador.

Para los endpoints protegidos se debe utilizar el botón:

```text
Authorize
```

e introducir el JWT utilizando el formato:

```text
Bearer TU_TOKEN
```

---

# 🔐 Seguridad

VetTurno utiliza JWT para proteger los endpoints.

## Rutas públicas

```text
POST /api/auth/register
POST /api/auth/login
```

## USER y ADMIN

Pueden:

```text
POST /api/propietarios
GET  /api/propietarios

POST /api/mascotas
GET  /api/mascotas

POST /api/citas
GET  /api/citas
GET  /api/citas/veterinario/{id}

GET /api/veterinarios
```

## Solo ADMIN

Puede registrar veterinarios:

```text
POST /api/veterinarios
```

Si un usuario con rol `USER` intenta registrar un veterinario, la API responde:

```text
403 Forbidden
```

---

# 👤 Registro

Endpoint:

```http
POST /api/auth/register
```

Ejemplo:

```json
{
  "email": "paula@gmail.com",
  "password": "12345678"
}
```

El registro crea automáticamente un usuario con:

```text
rol = USER
```

El cliente no puede convertirse en `ADMIN` enviando un rol diferente.

Respuesta:

```json
{
  "token": "JWT",
  "tipo": "Bearer",
  "email": "paula@gmail.com",
  "rol": "USER"
}
```

---

# 🔑 Login

Endpoint:

```http
POST /api/auth/login
```

Ejemplo:

```json
{
  "email": "paula@gmail.com",
  "password": "12345678"
}
```

La API verifica las credenciales y devuelve un JWT vigente.

---

# 👨‍👩‍👧 Propietarios

## Crear propietario

```http
POST /api/propietarios
```

Ejemplo:

```json
{
  "nombre": "Juan Barthelemy",
  "telefono": "50946902019",
  "email": "juan@gmail.com"
}
```

Respuesta esperada:

```text
201 Created
```

## Listar propietarios

```http
GET /api/propietarios
```

Respuesta:

```text
200 OK
```

---

# 🐶 Mascotas

## Crear mascota

```http
POST /api/mascotas
```

Ejemplo:

```json
{
  "nombre": "Luna",
  "especie": "Gato",
  "raza": "Siamés",
  "propietarioId": 1
}
```

Respuesta:

```text
201 Created
```

Ejemplo:

```json
{
  "id": 1,
  "nombre": "Luna",
  "especie": "Gato",
  "raza": "Siamés",
  "propietarioId": 1,
  "propietarioNombre": "Juan Barthelemy"
}
```

---

# 🩺 Veterinarios

## Crear veterinario

Solo ADMIN:

```http
POST /api/veterinarios
```

Ejemplo:

```json
{
  "nombre": "Carlos Martínez",
  "especialidad": "Medicina General Veterinaria"
}
```

Respuesta:

```text
201 Created
```

## Listar veterinarios

```http
GET /api/veterinarios
```

Respuesta:

```text
200 OK
```

---

# 📅 Citas

## Crear cita

```http
POST /api/citas
```

Ejemplo:

```json
{
  "fechaHora": "2026-10-01T10:00:00",
  "motivo": "Consulta general",
  "mascotaId": 1,
  "veterinarioId": 1
}
```

Respuesta:

```text
201 Created
```

La mascota y el veterinario deben existir.

La fecha debe ser futura.

No se permite registrar dos citas para el mismo veterinario en la misma fecha y hora.

---

# 🔎 Consultar citas

## Todas las citas

```http
GET /api/citas
```

## Citas de un veterinario

```http
GET /api/citas/veterinario/{id}
```

Ejemplo:

```http
GET /api/citas/veterinario/1
```

Devuelve únicamente las citas asociadas al veterinario cuyo `id` es `1`.

---

# ❌ Manejo de errores

La API utiliza un formato común para los errores:

```json
{
  "status": 400,
  "mensaje": "Datos inválidos",
  "errores": {},
  "timestamp": "2026-09-30T12:00:00"
}
```

## Estados utilizados

| Estado | Significado                                   |
| ------ | --------------------------------------------- |
| `200`  | Consulta o autenticación correcta             |
| `201`  | Recurso creado                                |
| `400`  | Datos inválidos o regla de negocio incumplida |
| `401`  | Autenticación requerida o inválida            |
| `403`  | Usuario autenticado sin permisos              |
| `500`  | Error interno                                 |

Las excepciones son gestionadas mediante un manejador global para evitar mostrar información interna de la aplicación.

---

# 🧪 Matriz de pruebas manuales

| #  | Escenario                                         | Resultado esperado |
| -- | ------------------------------------------------- | ------------------ |
| 1  | La aplicación inicia con MySQL disponible         | Servidor activo    |
| 2  | Registro válido de Paula                          | `200` y token      |
| 3  | Registro con email inválido y clave corta         | `400`              |
| 4  | Login con credenciales válidas                    | `200` y JWT        |
| 5  | GET `/api/citas` sin token                        | Acceso rechazado   |
| 6  | POST `/api/veterinarios` con USER                 | `403`              |
| 7  | POST `/api/veterinarios` con ADMIN                | `201`              |
| 8  | Crear propietario                                 | `201`              |
| 9  | Crear mascota con propietario existente           | `201`              |
| 10 | Crear mascota con propietario inexistente         | `400`              |
| 11 | Crear cita futura válida                          | `201`              |
| 12 | Crear cita con fecha pasada                       | `400`              |
| 13 | Crear segunda cita en mismo horario y veterinario | `400`              |
| 14 | Filtrar citas por veterinario                     | `200`              |
| 15 | Reiniciar aplicación y consultar datos            | Datos persistentes |

---

# 🔄 Flujo recomendado para probar VetTurno

Seguir este orden:

```text
1. Iniciar MySQL
        ↓
2. Iniciar VetTurno
        ↓
3. Registrar usuario
        ↓
4. Login
        ↓
5. Copiar JWT
        ↓
6. Swagger → Authorize
        ↓
7. Crear propietario
        ↓
8. Crear mascota
        ↓
9. Crear veterinario con ADMIN
        ↓
10. Crear cita
        ↓
11. Consultar citas
        ↓
12. Filtrar por veterinario
        ↓
13. Probar fecha pasada
        ↓
14. Probar horario duplicado
        ↓
15. Reiniciar aplicación
        ↓
16. Verificar persistencia
```

---

# 🔒 Roles

VetTurno utiliza dos roles:

### USER

Usuario de recepción.

Puede registrar:

* Propietarios.
* Mascotas.
* Citas.

También puede consultar veterinarios y citas.

### ADMIN

Tiene las mismas capacidades que USER y además puede registrar veterinarios.

```text
USER
 ├── Propietarios
 ├── Mascotas
 ├── Citas
 └── Consultas

ADMIN
 ├── Propietarios
 ├── Mascotas
 ├── Citas
 ├── Consultas
 └── Veterinarios
```

---

# 🧠 Decisiones técnicas

### ¿Por qué utilizar DTO?

Los DTO permiten controlar exactamente qué información entra y sale de la API.

Por ejemplo, `MascotaDTO` devuelve:

```json
{
  "id": 1,
  "nombre": "Luna",
  "especie": "Gato",
  "raza": "Siamés",
  "propietarioId": 1,
  "propietarioNombre": "Juan Barthelemy"
}
```

En lugar de devolver toda la entidad `Propietario` y sus posibles relaciones.

Esto evita ciclos JSON y mantiene las respuestas más simples.

### ¿Por qué la regla de horario está en CitaService?

Porque es una regla de negocio.

El controller recibe la petición, pero el service decide si la cita puede registrarse.

Antes de guardar se comprueba:

1. Que la mascota exista.
2. Que el veterinario exista.
3. Que la fecha sea futura.
4. Que no exista otra cita del mismo veterinario en la misma fecha y hora.

---

# 🤖 Uso de IA

Durante el desarrollo se utilizó inteligencia artificial como herramienta de apoyo para:

* Comprender conceptos de Spring Boot.
* Revisar la arquitectura por capas.
* Diagnosticar errores.
* Comprender DTO y relaciones JPA.
* Revisar configuraciones de seguridad.
* Analizar validaciones y manejo de errores.

Las sugerencias fueron revisadas y probadas dentro del proyecto antes de incorporarse.

---

# 📦 Construcción del proyecto

Para generar el archivo JAR:

### Windows

```bash
.\mvnw.cmd clean package -DskipTests
```

### macOS/Linux

```bash
./mvnw clean package -DskipTests
```

El archivo generado estará dentro de:

```text
target/
```

---

# 📌 Estado del proyecto

VetTurno implementa el MVP solicitado para la gestión de responsables, mascotas, veterinarios y citas de la Veterinaria Huellitas.

El proyecto incluye:

* Arquitectura por capas.
* JPA/Hibernate.
* MySQL.
* DTO.
* REST.
* JWT.
* BCrypt.
* Roles USER/ADMIN.
* Validaciones.
* Manejo global de errores.
* Swagger/OpenAPI.
* Persistencia de datos.
* Prevención de cruces de horarios.

---

# 👨‍💻 Autor

**Jean-René Barthélémy **

Proyecto realizado como parte del:

**Java AI Engineer — Módulo 3**

**Taller evaluativo: Backend profesional con Spring Boot**

Proyecto: **VetTurno — Veterinaria Huellitas**
