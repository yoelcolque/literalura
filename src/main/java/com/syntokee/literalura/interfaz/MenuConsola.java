package com.syntokee.literalura.interfaz;

import com.syntokee.literalura.model.Autor;
import com.syntokee.literalura.model.Libro;
import com.syntokee.literalura.service.AutorService;
import com.syntokee.literalura.service.GutendexService;
import com.syntokee.literalura.service.LibroService;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class MenuConsola {

    private final Scanner scanner = new Scanner(System.in);
    private final GutendexService gutendexService;
    private final LibroService libroService;
    private final AutorService autorService;

    public MenuConsola(GutendexService gutendexService, LibroService libroService, AutorService autorService) {
        this.gutendexService = gutendexService;
        this.libroService = libroService;
        this.autorService = autorService;
    }

    public void mostrar() {
        int opcion;

        do {
            System.out.println("\n===  MENÚ LITERALURA ===");
            System.out.println("1. Buscar y guardar libro por título");
            System.out.println("2. Listar libros guardados");
            System.out.println("3. Listar autores guardados");
            System.out.println("4. Buscar libros por idioma");
            System.out.println("5. Buscar autores vivos en un año");
            System.out.println("6. Estadísticas: cantidad de libros por idioma");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> buscarYGuardarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> buscarLibrosPorIdioma();
                case 5 -> buscarAutoresVivos();
                case 6 -> estadisticasPorIdioma();
                case 0 -> System.out.println("Hasta pronto");
                default -> System.out.println("Opción inválida. Intenta de nuevo.");
            }

        } while (opcion != 0);
    }

    private void buscarYGuardarLibro() {
        System.out.print("Título del libro: ");
        String titulo = scanner.nextLine();

        Optional.ofNullable(gutendexService.buscarLibroPorTitulo(titulo))
                .flatMap(book -> book)
                .map(libroService::guardarLibroDesdeDTO)
                .ifPresentOrElse(
                        libro -> System.out.println("Libro guardado: " + libro.getTitulo()),
                        () -> System.out.println("Libro no encontrado.")
                );
    }

    private void listarLibros() {
        List<Libro> libros = libroService.listarTodos();
        if (libros.isEmpty()) {
            System.out.println(" No hay libros guardados.");
            return;
        }
        libros.forEach(libro ->
                System.out.printf(" %s | Idioma: %s | Descargas: %d | Autor: %s%n",
                        libro.getTitulo(), libro.getIdioma(), libro.getDescargas(), libro.getAutor().getNombre()));
    }

    private void listarAutores() {
        List<Autor> autores = autorService.listarTodos();
        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
            return;
        }
        autores.forEach(a ->
                System.out.printf("%s (%d - %d)%n", a.getNombre(), a.getNacimiento(), a.getFallecimiento()));
    }

    private void buscarLibrosPorIdioma() {
        System.out.print("Idioma (ej: en, es, fr): ");
        String idioma = scanner.nextLine();

        List<Libro> libros = libroService.buscarPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No hay libros en ese idioma.");
            return;
        }
        libros.forEach(libro ->
                System.out.printf("%s | Autor: %s%n", libro.getTitulo(), libro.getAutor().getNombre()));
    }

    private void buscarAutoresVivos() {
        try {
            System.out.print("Año para buscar autores vivos: ");
            int año = Integer.parseInt(scanner.nextLine());

            List<Autor> autores = autorService.listarVivosEnAnio(año);
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos ese año.");
                return;
            }
            autores.forEach(a ->
                    System.out.printf("%s (%d - %d)%n", a.getNombre(), a.getNacimiento(), a.getFallecimiento()));
        } catch (NumberFormatException e) {
            System.out.println("❌ Año inválido.");
        }
    }

    private void estadisticasPorIdioma() {
        System.out.print("Idioma para contar libros (ej: en, es): ");
        String idioma = scanner.nextLine();

        int total = libroService.buscarPorIdioma(idioma).size();
        System.out.printf("Total de libros en idioma '%s': %d%n", idioma, total);
    }
}
