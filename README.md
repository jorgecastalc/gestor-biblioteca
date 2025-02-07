## 🔧 Configuración y Ejecución

### 1️⃣ Clonar el repositorio

```sh
git clone https://github.com/tu-usuario/gestor-biblioteca.git
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

**POST /usuarios**
```json
{
  "nombre": "Carlos Sánchez",
  "email": "carlos.sanchez@example.com",
  "telefono": "123456789",
  "fechaRegistro": "2022-01-15"
}
```

### ♻ **Préstamos**

**POST /prestamos**
```json
{
  "libroId": 3,
  "usuarioId": 1,
  "fechaPrestamo": "2025-02-10",
  "fechaDevolucion": "2025-03-10"
}
```
