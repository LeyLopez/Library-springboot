# Biblioteca-springboot

Este es el backend de una aplicación construida con Spring Boot, creada para proporcionar servicios de reservas y prestamos a los clientes de una biblioteca.
Permite la administración de libros, y visualización de reservas y préstamos realizados por los usuarios registrados en la plataforma.
Utiliza tecnologías como Hibernate, Lombok, JPA, Spring Security, PostgreSQL y JWT para la autenticación y autorización.

## Tecnologías Utilizadas

- **Spring Boot** - Framework principal del backend.
- **Hibernate** - Implementación de JPA para el manejo de la base de datos.
- **Lombok** - Reducción de código repetitivo en las entidades por medio de anotaciones.
- **JPA** - API para la gestión de datos.
- **Spring Security** - Seguridad y manejo de autenticación/autorización.
- **PostgreSQL** - Base de datos utilizada.
- **JWT (JSON Web Token)** - Mecanismo de autenticación.

## Requisitos Previos

Antes de ejecutar la aplicación, asegúrate de tener instalado:

- **Java 17**
- **Gradle o Maven**
- **PostgreSQL**
- **Docker (opcional, para contenerización)**

## Instalación y Configuración

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/LeyLopez/Library-springboot.git
   cd tu-repositorio
   ```

2. Configurar la base de datos en `application.properties` o `application.yml`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_de_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

3. Ejecutar la aplicación con Gradle:
   ```bash
   ./gradlew bootRun
   ```
   o con Maven:
   ```bash
   mvn spring-boot:run
   ```

## Endpoints Principales

## Autenticación
### `POST /api/auth/login` - Iniciar sesión y obtener token JWT.

**Ejemplo de petición**
```json
  {
    "username":"Leydis",
    "password":"123456"
  }
```

**Ejemplo de respuesta (200 OK)**
```json
  {
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJMZXlkaXMiLCJpYXQiOjE3NDM0MDMxNTV9.UFxYrRCS2KGDGJAzh6exW9Kl8x18EIHQDli9kZ9gzfw",
    "type": "Bearer",
    "username": "Leydis",
    "roles": [
        "ROLE_USER"
    ]
}
```


---
    
### `POST /api/auth/signup` - Registrar un nuevo usuario a la plataforma.

**Ejemplo de petición**
```json
  {
    "name":"Leidis",
    "lastname": "Lopez",
    "email":"leidislopez@gmail.com",
    "username":"Leidis",
    "password":"12345",
    "kindOfDocument":"Cedula",
    "documentNumber":1234567,
    "dateOfBirth":"2003-09-26",
    "phoneNumber":"123456",
    "address": "Calle 23"
}
```

**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 5,
    "name": "Leidis",
    "lastname": "Lopez",
    "email": "leidislopez@gmail.com",
    "username": "Leidis",
    "kindOfDocument": "Cedula",
    "documentNumber": 1234567,
    "dateOfBirth": "2003-09-26T00:00:00.000+00:00",
    "phoneNumber": "123456",
    "address": "Calle 23"
}
```


---

## Endpoints de la aplicación (Requieren autenticación)

### `GET /api/usuario` - Obtener lista de usuarios.

**Ejemplo de respuesta (200 OK)**
```json
  [
    {
        "id": 2,
        "name": "Mateo",
        "lastname": "Cantillo",
        "email": "mateo@gmail.com",
        "username": "Mateo",
        "password": "$2a$10$ntd3E2AWuREKOm0.B3veyui96XW6NJqvmqnu/FMFXA6nbiZpDJNha",
        "kindOfDocument": "TARJETA DE IDENTIDAD",
        "documentNumber": 23254345,
        "dateOfBirth": "2021-12-01T00:00:00.000+00:00",
        "phoneNumber": "32423534",
        "address": "Calle 12"
    },
    {
        "id": 3,
        "name": "Leydis",
        "lastname": "Lopez",
        "email": "ley@gmail.com",
        "username": "Leydis",
        "password": "$2a$10$7/R1BTdKIJU0XVFa.mHPEOKB4OMgZzsq5aamMhPRVCC0xqfoqU3PW",
        "kindOfDocument": "CEDULA",
        "documentNumber": 2342342,
        "dateOfBirth": "2003-12-12T00:00:00.000+00:00",
        "phoneNumber": "12345673",
        "address": "Calle 13"
    },
    {
        "id": 4,
        "name": "Angelica",
        "lastname": "Coronel",
        "email": "ange@gmail.com",
        "username": "Ange",
        "password": "12345",
        "kindOfDocument": "CEDULA",
        "documentNumber": 2312435,
        "dateOfBirth": "2002-01-04T00:00:00.000+00:00",
        "phoneNumber": "3456789",
        "address": "Calle 15"
    },
    {
        "id": 5,
        "name": "Leidis",
        "lastname": "Lopez",
        "email": "leidislopez@gmail.com",
        "username": "Leidis",
        "password": "$2a$10$SFyXY4rSstzMbQmCUE57/.AWaEQVgkqVWDJS/m2T9jyt8be321Bl2",
        "kindOfDocument": "Cedula",
        "documentNumber": 1234567,
        "dateOfBirth": "2003-09-26T00:00:00.000+00:00",
        "phoneNumber": "123456",
        "address": "Calle 23"
    }
]
```


---

### `GET /api/libro` -Obtener lista de libros.
**Ejemplo de respuesta (200 OK)**
```json
  [
    {
        "id": 57,
        "title": "Don Quijote de la Mancha",
        "description": "Historia del ingenioso hidalgo",
        "dateOfPublication": "1605-01-16T05:00:00.000+00:00",
        "quantity": 3,
        "author": 2,
        "coverPage": "https://covers.openlibrary.org/b/id/8228691-L.jpg",
        "genre": 1
    },
    {
        "id": 58,
        "title": "1984",
        "description": "Distopía futurista",
        "dateOfPublication": "1949-06-08T05:00:00.000+00:00",
        "quantity": 7,
        "author": 3,
        "coverPage": "https://covers.openlibrary.org/b/id/7222246-L.jpg",
        "genre": 1
    },
    {
        "id": 59,
        "title": "Orgullo y prejuicio",
        "description": "Romance clásico",
        "dateOfPublication": "1813-01-28T05:00:00.000+00:00",
        "quantity": 4,
        "author": 4,
        "coverPage": "https://covers.openlibrary.org/b/id/8231990-L.jpg",
        "genre": 2
    },
    {
        "id": 60,
        "title": "Moby Dick",
        "description": "Aventura marina",
        "dateOfPublication": "1851-10-18T05:00:00.000+00:00",
        "quantity": 6,
        "author": 5,
        "coverPage": "https://covers.openlibrary.org/b/id/7222276-L.jpg",
        "genre": 2
    },
    {
        "id": 61,
        "title": "Crimen y castigo",
        "description": "Reflexión sobre la culpa",
        "dateOfPublication": "1866-01-01T05:00:00.000+00:00",
        "quantity": 5,
        "author": 6,
        "coverPage": "https://covers.openlibrary.org/b/id/8231856-L.jpg",
        "genre": 3
    },
    {
        "id": 62,
        "title": "El principito",
        "description": "Fábula filosófica",
        "dateOfPublication": "1943-04-06T05:00:00.000+00:00",
        "quantity": 9,
        "author": 7,
        "coverPage": "https://covers.openlibrary.org/b/id/8774816-L.jpg",
        "genre": 3
    },
    {
        "id": 63,
        "title": "Los miserables",
        "description": "Relato de redención",
        "dateOfPublication": "1862-01-01T05:00:00.000+00:00",
        "quantity": 3,
        "author": 8,
        "coverPage": "https://covers.openlibrary.org/b/id/8231851-L.jpg",
        "genre": 5
    },
    {
        "id": 64,
        "title": "La odisea",
        "description": "Épica de Homero",
        "dateOfPublication": "0800-01-01T05:00:00.000+00:00",
        "quantity": 5,
        "author": 9,
        "coverPage": "https://covers.openlibrary.org/b/id/8231995-L.jpg",
        "genre": 2
    },
    {
        "id": 65,
        "title": "El nombre de la rosa",
        "description": "Misterio medieval",
        "dateOfPublication": "1980-01-01T05:00:00.000+00:00",
        "quantity": 4,
        "author": 10,
        "coverPage": "https://covers.openlibrary.org/b/id/8231950-L.jpg",
        "genre": 1
    },
    {
        "id": 66,
        "title": "Rayuela",
        "description": "Narrativa experimental",
        "dateOfPublication": "1963-06-28T05:00:00.000+00:00",
        "quantity": 6,
        "author": 11,
        "coverPage": "https://covers.openlibrary.org/b/id/8231980-L.jpg",
        "genre": 2
    }
]
```

---

### `GET /api/reserva` -Obtener lista de reservas.

**Ejemplo de respuesta (200 OK)**
```json
  [
    {
        "id": 51,
        "reservationDate": "2024-06-12T05:00:00.000+00:00",
        "reservationEndDate": "2024-06-22T05:00:00.000+00:00",
        "statusChangeDate": "2024-06-20T05:00:00.000+00:00",
        "user": 3,
        "book": 60,
        "status": "CANCELADO"
    },
    {
        "id": 74,
        "reservationDate": "2025-07-15T05:00:00.000+00:00",
        "reservationEndDate": "2025-07-25T05:00:00.000+00:00",
        "statusChangeDate": "2025-07-23T05:00:00.000+00:00",
        "user": 3,
        "book": 67,
        "status": "ENTREGADO"
    }
]
```


---

### `GET /api/prestamo` -Obtener lista de préstamos.
**Ejemplo de respuesta (200 OK)**
```json
  [
    {
        "id": 140,
        "loanDate": "2024-05-03T05:00:00.000+00:00",
        "devolutionDate": "2024-05-13T05:00:00.000+00:00",
        "statusChangeDate": "2024-05-11T05:00:00.000+00:00",
        "user": 3,
        "book": 58,
        "status": "ENTREGADO"
    },
    {
        "id": 141,
        "loanDate": "2024-06-08T05:00:00.000+00:00",
        "devolutionDate": "2024-06-18T05:00:00.000+00:00",
        "statusChangeDate": "2024-06-15T05:00:00.000+00:00",
        "user": 3,
        "book": 59,
        "status": "ENTREGADO"
    }
]
```


---
### `GET /api/libro/{id}` -Obtener libro por ID.
**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 58,
    "title": "1984",
    "description": "Distopía futurista",
    "dateOfPublication": "1949-06-08T05:00:00.000+00:00",
    "quantity": 7,
    "author": 3,
    "coverPage": "https://covers.openlibrary.org/b/id/7222246-L.jpg",
    "genre": 1
}
```


---
### `GET /api/usuario/{id}` -Obtener usuario por ID.
**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 3,
    "name": "Leydis",
    "lastname": "Lopez",
    "email": "ley@gmail.com",
    "username": "Leydis",
    "password": "$2a$10$7/R1BTdKIJU0XVFa.mHPEOKB4OMgZzsq5aamMhPRVCC0xqfoqU3PW",
    "kindOfDocument": "CEDULA",
    "documentNumber": 2342342,
    "dateOfBirth": "2003-12-12T00:00:00.000+00:00",
    "phoneNumber": "12345678",
    "address": "Calle 13"
}
```


---
### `GET /api/reserva/{id}` -Obtener reserva por ID.
**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 89,
    "reservationDate": "2026-02-04T05:00:00.000+00:00",
    "reservationEndDate": "2026-02-14T05:00:00.000+00:00",
    "statusChangeDate": "2026-02-12T05:00:00.000+00:00",
    "user": 3,
    "book": 62,
    "status": "ENTREGADO"
}
```


---
### `GET /api/prestamo/{id}` -Obtener prestamo por ID.
**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 140,
    "loanDate": "2024-05-03T05:00:00.000+00:00",
    "devolutionDate": "2024-05-13T05:00:00.000+00:00",
    "statusChangeDate": "2024-05-11T05:00:00.000+00:00",
    "user": 3,
    "book": 58,
    "status": "ENTREGADO"
}
```


---
### `GET /api/autor/{id}` -Obtener autor por ID.
**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 1,
    "name": "Gabriel",
    "lastname": "García Márquez",
    "dateOfBirth": "1927-03-06T05:00:00.000+00:00"
}
```


---
### `GET /api/genero/{id}` -Obtener genero de un libro por ID del genero.

```json
  {
    "id": 1,
    "name": "Realismo Mágico"
}
```


---

### `POST /api/genero` -Agregar genero a la biblioteca.

**Ejemplo de petición**
```json
  {
    "id":1,
    "name":"terror"
}
```

**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 1,
    "name": "terror"
}
```
---


### `POST /api/libro` -Agregar libro a la biblioteca.
**Ejemplo de petición**
```json
  {
    "title":"cien años",
    "description":"macondo",
    "dateOfPublication": "2000-12-07",
    "quantity": 3,
    "coverPage":"andja",
    "author":2
}
```

**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 90,
    "title": "cien años",
    "description": "macondo",
    "dateOfPublication": "2000-12-07T00:00:00.000+00:00",
    "quantity": 3,
    "author": 2,
    "coverPage": "andja",
    "genre": null
}
```


---
### `POST /api/autor` -Agregar autor a la biblioteca.
**Ejemplo de petición**
```json
  {
   "name":"Leidis",
   "lastname":"Lopez",
   "dateOfBirth":"2002-26-09"
}

```

**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 53,
    "name": "Leidis",
    "lastname": "Lopez",
    "dateOfBirth": "2004-02-09T00:00:00.000+00:00"
}
```


---
### `POST /api/prestamo` -Agregar prestamo a la biblioteca.
**Ejemplo de petición**
```json
  {
    "loanDate":"2025-01-12",
    "devolutionDate":"2025-02-12",
    "statusChangeDate":"2025-02-10",
    "user":3,
    "book":58,
    "status":"ENTREGADO"
}
```

**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 187,
    "loanDate": "2025-01-12T00:00:00.000+00:00",
    "devolutionDate": "2025-02-12T00:00:00.000+00:00",
    "statusChangeDate": "2025-02-10T00:00:00.000+00:00",
    "user": 3,
    "book": 58,
    "status": "ENTREGADO"
}
```


---
### `POST /api/reserva` -Agregar reserva a la biblioteca.
**Ejemplo de petición**
```json
  {
    "reservationDate":"2024-12-30",
    "reservationEndDate":"2025-01-30",
    "statusChangeDate": "2024-01-25",
    "user":3,
    "book":58,
    "status":"ENTREGADO"
}
```

**Ejemplo de respuesta (200 OK)**
```json
  {
    "id": 99,
    "reservationDate": "2024-12-30T00:00:00.000+00:00",
    "reservationEndDate": "2025-01-30T00:00:00.000+00:00",
    "statusChangeDate": "2024-01-25T00:00:00.000+00:00",
    "user": 3,
    "book": 58,
    "status": "ENTREGADO"
}
```


---
### `PUT /api/usuario/{id}` -Actualizar un usuario por su ID.
### `PUT /api/libro/{id}` -Actualizar un libro por su ID.
### `PUT /api/genero/{id}` -Actualizar un genero por su ID.
### `PUT /api/autor/{id}` -Actualizar un autor por su ID.
### `PUT /api/reserva/{id}` -Actualizar una reserva por su ID.
### `PUT /api/prestamo/{id}` -Actualizar un préstamo por su ID.
### `DELETE /api/usuario/{id}` -Eliminar un usuario por su ID.
### `DELETE /api/libro/{id}` -Eliminar un libro por su ID.
### `DELETE /api/genero/{id}` -Eliminar un genero por su ID.
### `DELETE /api/autor/{id}` -Eliminar un autor por su ID.
### `DELETE /api/reserva/{id}` -Eliminar una reserva por su ID.
### `DELETE /api/prestamo/{id}` -Eliminar un préstamo por su ID.

## Seguridad y JWT

La aplicación usa Spring Security con JWT para la autenticación. El token debe enviarse en la cabecera de cada petición:
```http
Authorization: Bearer <tu_token_jwt>
```

## Contenerización con Docker

Para ejecutar la aplicación en Docker, utiliza el siguiente `Dockerfile`:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/tu-app.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Construir y ejecutar el contenedor:
```bash
docker build -t mi-aplicacion .
docker run -p 8080:8080 mi-aplicacion
```

## Contribuciones

Si deseas contribuir, por favor abre un *Pull Request* o crea un *Issue* para discutir cambios.


