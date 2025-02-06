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

## 🛠 Testing

Para ejecutar las pruebas unitarias:

```sh
mvn test
```
