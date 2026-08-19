package br.com.fiap.eurobackend.repositories;

import br.com.fiap.eurobackend.entities.Aula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    boolean existsByTurmaIdAndDataAula(Long turmaId, LocalDate dataAula);

    boolean existsByTurmaIdAndDataAulaAndIdNot(Long turmaId, LocalDate dataAula, Long id);

    List<Aula> findByTurmaId(Long turmaId);
}
