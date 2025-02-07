package com.biblioteca.gestor_biblioteca.infrastructure.dataloader;

import com.biblioteca.gestor_biblioteca.modules.libro.application.services.LibroService;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import com.biblioteca.gestor_biblioteca.modules.prestamo.application.services.PrestamoService;
import com.biblioteca.gestor_biblioteca.modules.prestamo.domain.model.Prestamo;
import com.biblioteca.gestor_biblioteca.modules.usuario.application.services.UsuarioService;
import com.biblioteca.gestor_biblioteca.modules.usuario.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final LibroService libroService;
    private final UsuarioService usuarioService;
    private final PrestamoService prestamoService;

    @Override
    public void run(String... args) throws Exception {
        if (libroService.obtenerLibros().isEmpty()) {
            libroService.guardarLibro(new Libro(null, "1984", "George Orwell", "9780451524935",
                    LocalDate.of(1949, 6, 8)));
            libroService.guardarLibro(
                    new Libro(null, "Brave New World", "Aldous Huxley", "9780060850524",
                            LocalDate.of(1932, 8, 1)));
            libroService.guardarLibro(
                    new Libro(null, "Fahrenheit 451", "Ray Bradbury", "9781451673319",
                            LocalDate.of(1953, 10, 19)));
            libroService.guardarLibro(
                    new Libro(null, "The Great Gatsby", "F. Scott Fitzgerald", "9780743273565",
                            LocalDate.of(1925, 4, 10)));
            libroService.guardarLibro(
                    new Libro(null, "Moby Dick", "Herman Melville", "9781503280786",
                            LocalDate.of(1851, 11, 14)));
        }
        if (usuarioService.obtenerUsuarios().isEmpty()) {
            usuarioService.guardarUsuario(new Usuario(null, "Juan Pérez", "juan.perez@example.com",
                    "123456789", LocalDate.of(2020, 1, 15)));
            usuarioService.guardarUsuario(
                    new Usuario(null, "María López", "maria.lopez@example.com",
                            "987654321", LocalDate.of(2021, 3, 22)));
            usuarioService.guardarUsuario(
                    new Usuario(null, "Carlos García", "carlos.garcia@example.com",
                            "555123456", LocalDate.of(2019, 7, 9)));
            usuarioService.guardarUsuario(new Usuario(null, "Ana Torres", "ana.torres@example.com",
                    "444987654", LocalDate.of(2022, 5, 30)));
            usuarioService.guardarUsuario(
                    new Usuario(null, "Luis Fernández", "luis.fernandez@example.com",
                            "333222111", LocalDate.of(2018, 11, 3)));
        }
        List<Libro> libros = libroService.obtenerLibros();
        List<Usuario> usuarios = usuarioService.obtenerUsuarios();

        // Cargar préstamos si no existen
        if (prestamoService.obtenerPrestamos()
                .isEmpty() && !libros.isEmpty() && !usuarios.isEmpty()) {
            prestamoService.guardarPrestamo(new Prestamo(null, libros.get(0), usuarios.get(0),
                    LocalDate.of(2025, 2, 1), LocalDate.of(2025, 3, 1)));

            prestamoService.guardarPrestamo(new Prestamo(null, libros.get(1), usuarios.get(1),
                    LocalDate.of(2025, 1, 15), LocalDate.of(2025, 2, 15)));

            prestamoService.guardarPrestamo(new Prestamo(null, libros.get(2), usuarios.get(2),
                    LocalDate.of(2025, 1, 25), LocalDate.of(2025, 2, 25)));

            prestamoService.guardarPrestamo(new Prestamo(null, libros.get(3), usuarios.get(3),
                    LocalDate.of(2025, 2, 5), LocalDate.of(2025, 3, 5)));

            prestamoService.guardarPrestamo(new Prestamo(null, libros.get(4), usuarios.get(4),
                    LocalDate.of(2025, 1, 30), LocalDate.of(2025, 2, 28)));
        }
    }
}
