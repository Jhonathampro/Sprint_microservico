package br.com.fiap.eurobackend.repositories;

import br.com.fiap.eurobackend.entities.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    List<Turma> findByProfessorId(Long professorId);
}
