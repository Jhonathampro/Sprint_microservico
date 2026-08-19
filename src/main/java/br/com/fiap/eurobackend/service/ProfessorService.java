package br.com.fiap.eurobackend.service;

import br.com.fiap.eurobackend.dto.ProfessorDto;
import br.com.fiap.eurobackend.entities.Professor;
import br.com.fiap.eurobackend.exceptions.RegraDeNegocioException;
import br.com.fiap.eurobackend.exceptions.ResourceNotFoundException;
import br.com.fiap.eurobackend.repositories.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    @Transactional(readOnly = true)
    public List<ProfessorDto> findAll() {
        return professorRepository.findAll()
                .stream()
                .map(ProfessorDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfessorDto findById(Long id) {
        Professor professor = professorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Professor não encontrado. Id: " + id)
        );
        return new ProfessorDto(professor);
    }

    @Transactional
    public ProfessorDto save(ProfessorDto dto) {
        if (professorRepository.existsByCpf(dto.getCpf())) {
            throw new RegraDeNegocioException("Já existe um professor cadastrado com o CPF informado: " + dto.getCpf());
        }
        if (professorRepository.existsByEmail(dto.getEmail())) {
            throw new RegraDeNegocioException("Já existe um professor cadastrado com o e-mail informado: " + dto.getEmail());
        }

        Professor professor = new Professor();
        copyDtoToEntity(dto, professor);
        professor = professorRepository.save(professor);
        return new ProfessorDto(professor);
    }

    @Transactional
    public ProfessorDto update(Long id, ProfessorDto dto) {
        try {
            Professor professor = professorRepository.getReferenceById(id);

            if (professorRepository.existsByCpfAndIdNot(dto.getCpf(), id)) {
                throw new RegraDeNegocioException("Já existe um professor cadastrado com o CPF informado: " + dto.getCpf());
            }
            if (professorRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
                throw new RegraDeNegocioException("Já existe um professor cadastrado com o e-mail informado: " + dto.getEmail());
            }

            copyDtoToEntity(dto, professor);
            professor = professorRepository.save(professor);
            return new ProfessorDto(professor);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Professor não encontrado. Id: " + id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!professorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Professor não encontrado. Id: " + id);
        }
        professorRepository.deleteById(id);
    }

    private void copyDtoToEntity(ProfessorDto dto, Professor entity) {
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
    }
}
