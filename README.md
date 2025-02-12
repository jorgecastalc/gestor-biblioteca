## 📖 Gestor de Biblioteca - Arquitectura y Funcionamiento
### 🏛 Arquitectura Hexagonal

El flujo general de la aplicación sigue la siguiente estructura:

#### *Infraestructura*

- Controladores REST: Reciben las peticiones HTTP y las pasan a los servicios.
- Repositorios JPA: Encapsulan el acceso a la base de datos y se utilizan por los repositorios de dominio.
- Entidades JPA: Representan los datos de la base de datos.
- Mappers: Transforman entre entidades JPA, modelos de dominio y DTOs de entrada/salida.

#### *Aplicación* 

- Contiene la lógica de negocio principal.
- Implementa validaciones y reglas de negocio antes de acceder a la base de datos.
- Se comunica con los repositorios del dominio.

#### *Dominio*

- Contiene los modelos de dominio (libro, usuario y prestamo).
- Define las interfaces de repositorios que deben implementarse en la capa de infraestructura.

El flujo de una operación típica (por ejemplo obtener un préstamo) es:

```sh
Controller -> Service -> Repository (Domain) -> RepositoryImpl (Infraestructura) -> JPA Repository -> Base de Datos
```

### ⚙ Gestión de Excepciones
He manejado las excepciones usando excepciones personalizadas en cada módulo. 

 - LibroException 
 - UsuarioException
 - PrestamoException

Cada excepción incluye:

 - Código de error HTTP (400, 404, etc.).
 - Mensaje descriptivo del problema.
 - Logs para depuración.

### 🔧 Principales Validaciones

 - Libro: No puede guardarse sin título, autor o ISBN. No puede eliminarse si tiene préstamos activos.
 - Usuario: No puede guardarse sin nombre, email o teléfono. No puede eliminarse si tiene préstamos activos.
 - Préstamo:
    + El libro y el usuario deben existir antes de crearse.
    + Un libro no puede estar en más de un préstamo activo.
    + La fecha de devolución no puede ser anterior a la fecha de préstamo.

## 🔧 Configuración y Ejecución

### 1️⃣ Clonar el repositorio

```sh
git clone https://github.com/jorgecastalc/gestor-biblioteca.git
cd gestor-biblioteca
```

### 2️⃣ Construir el proyecto con Maven

```sh
mvn clean install
```

### 3️⃣ Ejecutar la aplicación

```sh
mvn spring-boot:run
```

## 🛠 Testing

Para ejecutar las pruebas unitarias:

```sh
mvn test
```

## 📌 Endpoints principales

### 📖 **Libros**

| Método | Endpoint       | Descripción                      |
| ------ | -------------- | -------------------------------- |
| GET    | `/libros`      | Obtener todos los libros         |
| GET    | `/libros/{id}` | Obtener un libro por ID          |
| POST   | `/libros`      | Crear un nuevo libro             |
| PUT    | `/libros/{id}` | Actualizar un libro completo     |
| PATCH  | `/libros/{id}` | Actualizar parcialmente un libro |
| DELETE | `/libros/{id}` | Eliminar un libro                |

### 👤 **Usuarios**

| Método | Endpoint         | Descripción                        |
| ------ | ---------------- | ---------------------------------- |
| GET    | `/usuarios`      | Obtener todos los usuarios         |
| GET    | `/usuarios/{id}` | Obtener un usuario por ID          |
| POST   | `/usuarios`      | Crear un nuevo usuario             |
| PUT    | `/usuarios/{id}` | Actualizar un usuario completo     |
| PATCH  | `/usuarios/{id}` | Actualizar parcialmente un usuario |
| DELETE | `/usuarios/{id}` | Eliminar un usuario                |

### 🔄 **Préstamos**

| Método | Endpoint          | Descripción                         |
| ------ | ----------------- | ----------------------------------- |
| GET    | `/prestamos`      | Obtener todos los préstamos         |
| GET    | `/prestamos/{id}` | Obtener un préstamo por ID          |
| POST   | `/prestamos`      | Crear un nuevo préstamo             |
| PUT    | `/prestamos/{id}` | Actualizar un préstamo completo     |
| PATCH  | `/prestamos/{id}` | Actualizar parcialmente un préstamo |
| DELETE | `/prestamos/{id}` | Eliminar un préstamo                |

## 🔬 Ejemplos de Request Body para pruebas en Postman

### 📚 **Libros**

**POST /libros**
```json
{
  "titulo": "El Hobbit",
  "autor": "J.R.R. Tolkien",
  "isbn": "978-0345339683",
  "fechaPublicacion": "1937-09-21"
}
```

### 👤 **Usuarios**

**PUT /usuarios/1**
```json
{
  "nombre": "Carlos Sánchez",
  "email": "carlos.sanchez@example.com",
  "telefono": "123456789",
  "fechaRegistro": "2022-01-15"
}
```

### ♻ **Préstamos**

**PATCH /prestamos/2**
```json
{
  "fechaPrestamo": "2025-02-10",
  "fechaDevolucion": "2025-03-10"
}
```
