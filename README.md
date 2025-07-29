<h1 align="center"> LiterAlura </h1>

<p align="center">
  Catálogo de libros con interacción por consola, integración con la API Gutendex y persistencia en PostgreSQL.
</p>

---

##  Índice

- [Descripción del proyecto](#-descripción-del-proyecto)
- [ Funcionalidades del proyecto](#-funcionalidades-del-proyecto)
- [ Acceso al proyecto](#-acceso-al-proyecto)
- [️ Abre y ejecuta el proyecto](#-abre-y-ejecuta-el-proyecto)
- [ Tecnologías utilizadas](#-tecnologías-utilizadas)


---

## Descripción del proyecto

**LiterAlura** es una aplicación Java con Spring Boot que permite consultar información de libros y autores desde la API pública de Gutendex. El usuario puede interactuar mediante un menú en consola, realizar búsquedas por título, idioma, y consultar estadísticas. Todos los libros y autores pueden ser almacenados en una base de datos PostgreSQL.

Este proyecto fue desarrollado como parte del programa **Oracle Next Education (ONE)**.

---

## Funcionalidades del proyecto

-  Buscar y guardar un libro por título desde la API.
-  Listar todos los libros almacenados.
-  Filtrar libros por idioma.
-  Listar todos los autores registrados.
- ️Consultar autores vivos en un año determinado.
-  Mostrar la cantidad total de libros por idioma.

---

## Abre y ejecuta el proyecto

1. Clona el repositorio:
```bash
git clone https://github.com/tu_usuario/literalura.git
```
---
### Asegúrate de tener:

    Java 17+

    Maven 4+

    PostgreSQL 16+ con una base llamada literalura


---
### Configura tu application.properties:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

---
### Ejecuta el proyecto:

./mvnw spring-boot:run

Tecnologías utilizadas

    Java 17

    Spring Boot 3.2.3

    Spring Data JPA

    PostgreSQL

    Jackson (JSON mapping)

    Gutendex API


---
### Personas desarrolladoras
<img src="https://avatars.githubusercontent.com/u/0000000?v=4" width=115><br><sub>[yoelcolque]</sub>

### Link Trello

https://trello.com/b/5QYPtTeR/literalura-challenge-java
