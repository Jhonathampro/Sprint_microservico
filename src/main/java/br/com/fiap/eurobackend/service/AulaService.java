package br.com.fiap.eurobackend.service;

import br.com.fiap.eurobackend.dto.AulaDto;
import br.com.fiap.eurobackend.entities.Aula;
import br.com.fiap.eurobackend.entities.Turma;
import br.com.fiap.eurobackend.exceptions.RegraDeNegocioException;
import br.com.fiap.eurobackend.exceptions.ResourceNotFoundException;
import br.com.fiap.eurobackend.repositories.AulaRepository;
import br.com.fiap.eurobackend.repositories.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Transactional(readOnly = true)
    public List<AulaDto> findAll() {
        return aulaRepository.findAll()
                .stream()
                .map(AulaDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AulaDto findById(Long id) {
        Aula aula = aulaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Aula não encontrada. Id: " + id)
        );
        return new AulaDto(aula);
    }

    @Transactional
    public AulaDto save(AulaDto dto) {
        Turma turma = turmaRepository.findById(dto.getTurmaId()).orElseThrow(
                () -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId())
        );

        if (aulaRepository.existsByTurmaIdAndDataAula(dto.getTurmaId(), dto.getDataAula())) {
            throw new RegraDeNegocioException(
                    String.format("Já existe uma aula cadastrada para a turma id %d na data %s.", dto.getTurmaId(), dto.getDataAula())
            );
        }

        Aula aula = new Aula();
        copyDtoToEntity(dto, aula, turma);
        aula = aulaRepository.save(aula);
        return new AulaDto(aula);
    }

    @Transactional
    public AulaDto update(Long id, AulaDto dto) {
        try {
            Aula aula = aulaRepository.getReferenceById(id);
            Turma turma = turmaRepository.findById(dto.getTurmaId()).orElseThrow(
                    () -> new ResourceNotFoundException("Turma não encontrada. Id: " + dto.getTurmaId())
            );

            if (aulaRepository.existsByTurmaIdAndDataAulaAndIdNot(dto.getTurmaId(), dto.getDataAula(), id)) {
                throw new RegraDeNegocioException(
                        String.format("Já existe uma aula cadastrada para a turma id %d na data %s.", dto.getTurmaId(), dto.getDataAula())
                );
            }

            copyDtoToEntity(dto, aula, turma);
            aula = aulaRepository.save(aula);
            return new AulaDto(aula);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Aula não encontrada. Id: " + id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!aulaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aula não encontrada. Id: " + id);
        }
        aulaRepository.deleteById(id);
    }

    private void copyDtoToEntity(AulaDto dto, Aula entity, Turma turma) {
        entity.setDataAula(dto.getDataAula());
        entity.setHorarioInicio(dto.getHorarioInicio());
        entity.setHorarioFim(dto.getHorarioFim());
        entity.setTurma(turma);
    }
}
