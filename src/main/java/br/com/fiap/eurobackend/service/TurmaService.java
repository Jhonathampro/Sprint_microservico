package br.com.fiap.eurobackend.service;

import br.com.fiap.eurobackend.dto.TurmaDto;
import br.com.fiap.eurobackend.entities.Professor;
import br.com.fiap.eurobackend.entities.Turma;
import br.com.fiap.eurobackend.exceptions.ResourceNotFoundException;
import br.com.fiap.eurobackend.repositories.ProfessorRepository;
import br.com.fiap.eurobackend.repositories.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public List<TurmaDto> findAll() {
        return turmaRepository.findAll()
                .stream()
                .map(TurmaDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public TurmaDto findById(Long id) {
        Turma turma = turmaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Turma não encontrada. Id: " + id)
        );
        return new TurmaDto(turma);
    }

    @Transactional
    public TurmaDto save(TurmaDto dto) {
        Professor professor = professorRepository.findById(dto.getProfessorId()).orElseThrow(
                () -> new ResourceNotFoundException("Professor não encontrado. Id: " + dto.getProfessorId())
        );

        Turma turma = new Turma();
        copyDtoToEntity(dto, turma, professor);
        turma = turmaRepository.save(turma);
        return new TurmaDto(turma);
    }

    @Transactional
    public TurmaDto update(Long id, TurmaDto dto) {
        try {
            Turma turma = turmaRepository.getReferenceById(id);
            Professor professor = professorRepository.findById(dto.getProfessorId()).orElseThrow(
                    () -> new ResourceNotFoundException("Professor não encontrado. Id: " + dto.getProfessorId())
            );

            copyDtoToEntity(dto, turma, professor);
            turma = turmaRepository.save(turma);
            return new TurmaDto(turma);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Turma não encontrada. Id: " + id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!turmaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Turma não encontrada. Id: " + id);
        }
        turmaRepository.deleteById(id);
    }

    private void copyDtoToEntity(TurmaDto dto, Turma entity, Professor professor) {
        entity.setNome(dto.getNome());
        entity.setTurno(dto.getTurno());
        entity.setVagas(dto.getVagas());
        entity.setProfessor(professor);
    }
}
