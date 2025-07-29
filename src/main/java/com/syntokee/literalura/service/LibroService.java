package com.syntokee.literalura.service;

import com.syntokee.literalura.dto.AuthorDTO;
import com.syntokee.literalura.dto.BookDTO;
import com.syntokee.literalura.model.Autor;
import com.syntokee.literalura.model.Libro;
import com.syntokee.literalura.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository libroRepo;
    private final AutorService autorService;

    public LibroService(LibroRepository libroRepo, AutorService autorService) {
        this.libroRepo = libroRepo;
        this.autorService = autorService;
    }

    public Libro guardarLibroDesdeDTO(BookDTO dto) {
        if (dto.getAuthors() == null || dto.getAuthors().isEmpty()) return null;
        if (dto.getLanguages() == null || dto.getLanguages().isEmpty()) return null;

        AuthorDTO autorDTO = dto.getAuthors().get(0);

        Autor autorGuardado = autorService.buscarPorNombre(autorDTO.getName())
                .orElseGet(() -> autorService.guardarNuevo(
                        new Autor(autorDTO.getName(), autorDTO.getBirth_year(), autorDTO.getDeath_year())
                ));

        Libro libro = new Libro(
                dto.getTitle(),
                dto.getLanguages().get(0),
                dto.getDownloadCount(),
                autorGuardado
        );

        return libroRepo.save(libro);
    }

    public List<Libro> listarTodos() {
        return libroRepo.findAll();
    }

    public List<Libro> buscarPorIdioma(String idioma) {
        return libroRepo.findByIdioma(idioma);
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        return libroRepo.findByTituloContainingIgnoreCase(titulo);
    }
}
