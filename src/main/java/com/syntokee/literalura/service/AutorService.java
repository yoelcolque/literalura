package com.syntokee.literalura.service;

import com.syntokee.literalura.model.Autor;
import com.syntokee.literalura.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {

    private final AutorRepository autorRepo;

    public AutorService(AutorRepository autorRepo) {
        this.autorRepo = autorRepo;
    }

    public Autor guardarSiNoExiste(Autor autor) {
        return autorRepo.findByNombreIgnoreCase(autor.getNombre())
                .orElseGet(() -> autorRepo.save(autor));
    }

    public Optional<Autor> buscarPorNombre(String nombre) {
        return autorRepo.findByNombreIgnoreCase(nombre);
    }

    public Autor guardarNuevo(Autor autor) {
        return autorRepo.save(autor);
    }

    public List<Autor> listarTodos() {
        return autorRepo.findAll();
    }

    public List<Autor> listarVivosEnAnio(int anio) {
        return autorRepo.findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(anio, anio);
    }
}
