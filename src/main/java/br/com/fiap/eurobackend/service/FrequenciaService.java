package br.com.fiap.eurobackend.service;

import br.com.fiap.eurobackend.dto.FrequenciaDto;
import br.com.fiap.eurobackend.entities.Aula;
import br.com.fiap.eurobackend.entities.Frequencia;
import br.com.fiap.eurobackend.entities.Matricula;
import br.com.fiap.eurobackend.exceptions.RegraDeNegocioException;
import br.com.fiap.eurobackend.exceptions.ResourceNotFoundException;
import br.com.fiap.eurobackend.repositories.AulaRepository;
import br.com.fiap.eurobackend.repositories.FrequenciaRepository;
import br.com.fiap.eurobackend.repositories.MatriculaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FrequenciaService {

    @Autowired
    private FrequenciaRepository frequenciaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Transactional(readOnly = true)
    public List<FrequenciaDto> findAll() {
        return frequenciaRepository.findAll()
                .stream()
                .map(FrequenciaDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public FrequenciaDto findById(Long id) {
        Frequencia frequencia = frequenciaRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Frequência não encontrada. Id: " + id)
        );
        return new FrequenciaDto(frequencia);
    }

    @Transactional(readOnly = true)
    public List<FrequenciaDto> findByAulaId(Long idAula) {
        if (!aulaRepository.existsById(idAula)) {
            throw new ResourceNotFoundException("Aula não encontrada. Id: " + idAula);
        }
        return frequenciaRepository.findByAulaId(idAula)
                .stream()
                .map(FrequenciaDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<FrequenciaDto> findByMatriculaId(Long idMatricula) {
        if (!matriculaRepository.existsById(idMatricula)) {
            throw new ResourceNotFoundException("Matrícula não encontrada. Id: " + idMatricula);
        }
        return frequenciaRepository.findByMatriculaId(idMatricula)
                .stream()
                .map(FrequenciaDto::new)
                .toList();
    }

    @Transactional
    public FrequenciaDto save(FrequenciaDto dto) {
        Aula aula = aulaRepository.findById(dto.getAulaId()).orElseThrow(
                () -> new ResourceNotFoundException("Aula não encontrada. Id: " + dto.getAulaId())
        );
        Matricula matricula = matriculaRepository.findById(dto.getMatriculaId()).orElseThrow(
                () -> new ResourceNotFoundException("Matrícula não encontrada. Id: " + dto.getMatriculaId())
        );

        if (!matricula.getTurma().getId().equals(aula.getTurma().getId())) {
            throw new RegraDeNegocioException(
                    String.format("A matrícula id %d (turma id %d) não pertence à turma da aula id %d (turma id %d).",
                            dto.getMatriculaId(), matricula.getTurma().getId(), dto.getAulaId(), aula.getTurma().getId())
            );
        }

        if (frequenciaRepository.existsByAulaIdAndMatriculaId(dto.getAulaId(), dto.getMatriculaId())) {
            throw new RegraDeNegocioException(
                    String.format("Já existe lançamento de frequência para a aula id %d e matrícula id %d.", dto.getAulaId(), dto.getMatriculaId())
            );
        }

        Frequencia frequencia = new Frequencia();
        copyDtoToEntity(dto, frequencia, aula, matricula);
        frequencia = frequenciaRepository.save(frequencia);
        return new FrequenciaDto(frequencia);
    }

    @Transactional
    public FrequenciaDto update(Long id, FrequenciaDto dto) {
        try {
            Frequencia frequencia = frequenciaRepository.getReferenceById(id);

            Aula aula = aulaRepository.findById(dto.getAulaId()).orElseThrow(
                    () -> new ResourceNotFoundException("Aula não encontrada. Id: " + dto.getAulaId())
            );
            Matricula matricula = matriculaRepository.findById(dto.getMatriculaId()).orElseThrow(
                    () -> new ResourceNotFoundException("Matrícula não encontrada. Id: " + dto.getMatriculaId())
            );

            if (!matricula.getTurma().getId().equals(aula.getTurma().getId())) {
                throw new RegraDeNegocioException(
                        String.format("A matrícula id %d (turma id %d) não pertence à turma da aula id %d (turma id %d).",
                                dto.getMatriculaId(), matricula.getTurma().getId(), dto.getAulaId(), aula.getTurma().getId())
                );
            }

            if (frequenciaRepository.existsByAulaIdAndMatriculaIdAndIdNot(dto.getAulaId(), dto.getMatriculaId(), id)) {
                throw new RegraDeNegocioException(
                        String.format("Já existe lançamento de frequência para a aula id %d e matrícula id %d.", dto.getAulaId(), dto.getMatriculaId())
                );
            }

            copyDtoToEntity(dto, frequencia, aula, matricula);
            frequencia = frequenciaRepository.save(frequencia);
            return new FrequenciaDto(frequencia);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Frequência não encontrada. Id: " + id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!frequenciaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Frequência não encontrada. Id: " + id);
        }
        frequenciaRepository.deleteById(id);
    }

    private void copyDtoToEntity(FrequenciaDto dto, Frequencia entity, Aula aula, Matricula matricula) {
        entity.setAula(aula);
        entity.setMatricula(matricula);
        entity.setSituacao(dto.getSituacao());
    }
}
