package br.com.fiap.eurobackend.repositories;

import br.com.fiap.eurobackend.entities.Matricula;
import br.com.fiap.eurobackend.entities.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    boolean existsByAlunoIdAndTurmaId(Long alunoId, Long turmaId);

    boolean existsByAlunoIdAndTurmaIdAndIdNot(Long alunoId, Long turmaId, Long id);

    List<Matricula> findByAlunoId(Long alunoId);

    List<Matricula> findByTurmaId(Long turmaId);

    long countByTurmaIdAndStatus(Long turmaId, StatusMatricula status);
}
