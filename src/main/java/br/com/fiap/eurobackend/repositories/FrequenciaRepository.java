package br.com.fiap.eurobackend.repositories;

import br.com.fiap.eurobackend.entities.Frequencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {

    boolean existsByAulaIdAndMatriculaId(Long aulaId, Long matriculaId);

    boolean existsByAulaIdAndMatriculaIdAndIdNot(Long aulaId, Long matriculaId, Long id);

    List<Frequencia> findByAulaId(Long aulaId);

    List<Frequencia> findByMatriculaId(Long matriculaId);
}
