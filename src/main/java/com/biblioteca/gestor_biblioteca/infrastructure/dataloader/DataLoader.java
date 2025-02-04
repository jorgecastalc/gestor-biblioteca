package com.biblioteca.gestor_biblioteca.infrastructure.dataloader;

import com.biblioteca.gestor_biblioteca.modules.libro.application.services.LibroService;
import com.biblioteca.gestor_biblioteca.modules.libro.domain.model.Libro;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final LibroService libroService;

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
    }
}
