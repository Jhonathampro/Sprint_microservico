package br.com.fiap.eurobackend.service;

import br.com.fiap.eurobackend.dto.MatriculaDto;
import br.com.fiap.eurobackend.entities.Aluno;
import br.com.fiap.eurobackend.entities.Matricula;
import br.com.fiap.eurobackend.entities.StatusMatricula;
import br.com.fiap.eurobackend.entities.Turma;
import br.com.fiap.eurobackend.exceptions.RegraDeNegocioException;
import br.com.fiap.eurobackend.exceptions.ResourceNotFoundException;
import br.com.fiap.eurobackend.repositories.AlunoRepository;
import br.com.fiap.eurobackend.repositories.MatriculaRepository;
import br.com.fiap.eurobackend.repositories.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional(readOnly = true)
    public List<MatriculaDto> findAll() {
        return matriculaRepository.findAll()
                .stream()
                .map(MatriculaDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public MatriculaDto findById(Long id) {
        Matricula matricula = matriculaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Matrícula não encontrada. Id: " + id)
        );
        return new MatriculaDto(matricula);
    }

    @Transactional(readOnly = true)
    public List<MatriculaDto> findByAlunoId(Long idAluno) {
        if (!alunoRepository.existsById(idAluno)) {
            throw new ResourceNotFoundException("Aluno não encontrado. Id: " + idAluno);
        }
        return matriculaRepository.findByAlunoId(idAluno)
                .stream()
                .map(MatriculaDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaDto> findByTurmaId(Long idTurma) {
        if (!turmaRepository.existsById(idTurma)) {
            throw new ResourceNotFoundException("Turma não encontrada. Id: " + idTurma);
        }
        return matriculaRepository.findByTurmaId(idTurma)
                .stream()
                .map(MatriculaDto::new)
                .toList();
    }

    @Transactional
    public MatriculaDto save(MatriculaDto dto) {
        Aluno aluno = alunoRepository.findById(dto.getAlunoId()).orElseThrow(
                () -> new ResourceNotFoundException("Aluno não encontrado. Id: " + dto.getAlunoId())
        );
        Turma turma = turmaRepository.findById(dto.getTurmaId()).orElseThrow(
                () -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId())
        );

        if (matriculaRepository.existsByAlunoIdAndTurmaId(dto.getAlunoId(), dto.getTurmaId())) {
            throw new RegraDeNegocioException(
                    String.format("Aluno id %d já possui matrícula cadastrada na turma id %d.", dto.getAlunoId(), dto.getTurmaId())
            );
        }

        if (dto.getStatus() == StatusMatricula.ATIVA) {
            long matriculasAtivas = matriculaRepository.countByTurmaIdAndStatus(dto.getTurmaId(), StatusMatricula.ATIVA);
            if (matriculasAtivas >= turma.getVagas()) {
                throw new RegraDeNegocioException(
                        String.format("Turma id %d não possui vagas disponíveis (vagas totais: %d).", dto.getTurmaId(), turma.getVagas())
                );
            }
        }

        Matricula matricula = new Matricula();
        matricula.setDataMatricula(dto.getDataMatricula() != null ? dto.getDataMatricula() : LocalDate.now());
        copyDtoToEntity(dto, matricula, aluno, turma);
        matricula = matriculaRepository.save(matricula);
        return new MatriculaDto(matricula);
    }

    @Transactional
    public MatriculaDto update(Long id, MatriculaDto dto) {
        try {
            Matricula matricula = matriculaRepository.getReferenceById(id);

            Aluno aluno = alunoRepository.findById(dto.getAlunoId()).orElseThrow(
                    () -> new ResourceNotFoundException("Aluno não encontrado. Id: " + dto.getAlunoId())
            );
            Turma turma = turmaRepository.findById(dto.getTurmaId()).orElseThrow(
                    () -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId())
            );

            if (matriculaRepository.existsByAlunoIdAndTurmaIdAndIdNot(dto.getAlunoId(), dto.getTurmaId(), id)) {
                throw new RegraDeNegocioException(
                        String.format("Aluno id %d já possui matrícula cadastrada na turma id %d.", dto.getAlunoId(), dto.getTurmaId())
                );
            }

            if (dto.getStatus() == StatusMatricula.ATIVA && matricula.getStatus() != StatusMatricula.ATIVA) {
                long matriculasAtivas = matriculaRepository.countByTurmaIdAndStatus(dto.getTurmaId(), StatusMatricula.ATIVA);
                if (matriculasAtivas >= turma.getVagas()) {
                    throw new RegraDeNegocioException(
                            String.format("Turma id %d não possui vagas disponíveis (vagas totais: %d).", dto.getTurmaId(), turma.getVagas())
                    );
                }
            }

            copyDtoToEntity(dto, matricula, aluno, turma);
            if (dto.getDataMatricula() != null) {
                matricula.setDataMatricula(dto.getDataMatricula());
            }
            matricula = matriculaRepository.save(matricula);
            return new MatriculaDto(matricula);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Matrícula não encontrada. Id: " + id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!matriculaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Matrícula não encontrada. Id: " + id);
        }
        matriculaRepository.deleteById(id);
    }

    private void copyDtoToEntity(MatriculaDto dto, Matricula entity, Aluno aluno, Turma turma) {
        entity.setStatus(dto.getStatus());
        entity.setAluno(aluno);
        entity.setTurma(turma);
    }
}
