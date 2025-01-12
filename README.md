# Desafío Foro Hub Alura Latam

## Desarrollado por: Raúl Humberto Díaz Fernández

# Desafío Foro Alura

Este es el código fuente del desafío del Foro Alura, desarrollado con **Spring Boot** y configurado para manejar seguridad, validación de datos, integración con bases de datos, y generación de documentación OpenAPI.

---

## **Tecnologías utilizadas**
- **Java 17**
- **Spring Boot 3.4.1**
- **Maven**
- **MySQL**
- **Flyway** (migraciones de base de datos)
- **Spring Security**
- **JWT (JSON Web Tokens)** para autenticación
- **Lombok** para simplificar el código
- **OpenAPI** para la documentación de la API

---

## **Características**
- Gestión de usuarios y roles con **Spring Security**.
- Autenticación basada en **JWT**.
- Documentación interactiva generada automáticamente con **Swagger UI**.
- Migraciones de base de datos con **Flyway**.
- Entorno de desarrollo mejorado con **Spring Boot DevTools**.
- Validación de datos con **Spring Boot Starter Validation**.

---

## **Configuración del proyecto**
### **Prerrequisitos**
- Java 17 o superior instalado.
- MySQL instalado y configurado.
- Maven instalado.

## Documentación de la API
La documentación interactiva de la API está disponible en:

http://localhost:8080/swagger-ui.html


### **Clonar el proyecto**
```bash
git clone https://github.com/tu-usuario/desafio-foro-alura.git
cd desafio-foro-alura

Configuración de la base de datos
Crea una base de datos en MySQL:
sql
Copiar código
CREATE DATABASE forohub;

Configura las credenciales de acceso en el archivo application.properties o application.yml:
properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost/forohub
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contrasena

...

## **Estructura de la base de datos**
El proyecto utiliza las siguientes tablas para modelar los datos:

### **Tablas principales**
1. **Cursos** (`cursos`)
   - **id**: Identificador único del curso.
   - **nombre**: Nombre del curso.
   - **categoria**: Categoría a la que pertenece el curso.

2. **Usuarios** (`usuarios`)
   - **id**: Identificador único del usuario.
   - **nombre**: Nombre del usuario.
   - **email**: Correo electrónico único.
   - **contrasena**: Contraseña encriptada del usuario.

3. **Perfiles** (`perfiles`)
   - **id**: Identificador único del perfil.
   - **nombre**: Nombre del perfil (e.g., "Administrador", "Estudiante").

4. **Tópicos** (`topicos`)
   - **id**: Identificador único del tópico.
   - **titulo**: Título del tópico.
   - **mensaje**: Mensaje inicial del tópico.
   - **fechacreacion**: Fecha y hora de creación.
   - **status**: Estado del tópico (`ABIERTO`, `CERRADO`, `PENDIENTE`).
   - **autor_id**: Referencia al autor del tópico (relación con `usuarios`).
   - **curso_id**: Referencia al curso asociado (relación con `cursos`).

5. **Respuestas** (`respuestas`)
   - **id**: Identificador único de la respuesta.
   - **mensaje**: Contenido de la respuesta.
   - **topico_id**: Referencia al tópico asociado (relación con `topicos`).
   - **fechacreacion**: Fecha y hora de creación.
   - **autor_id**: Referencia al autor de la respuesta (relación con `usuarios`).
   - **solucion**: Indica si es la solución aceptada para el tópico.

### **Relaciones**
1. **Usuarios y Perfiles** (`usuarios_perfiles`)
   - Tabla intermedia para una relación **N:N** entre usuarios y perfiles.
   - Contiene:
     - **usuario_id**: Referencia al usuario.
     - **perfil_id**: Referencia al perfil.

### **Integridad referencial**
- Todas las relaciones están configuradas con `ON DELETE CASCADE` para garantizar que los datos relacionados se eliminen automáticamente si un registro principal es eliminado.

---



