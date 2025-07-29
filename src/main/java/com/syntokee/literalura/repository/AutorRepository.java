package com.syntokee.literalura.repository;

import com.syntokee.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreIgnoreCase(String nombre);

    List<Autor> findByNacimientoLessThanEqualAndFallecimientoGreaterThanEqual(Integer nacimiento, Integer fallecimiento);
}
