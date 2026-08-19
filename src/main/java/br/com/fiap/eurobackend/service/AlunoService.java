package br.com.fiap.eurobackend.service;


import br.com.fiap.eurobackend.dto.AlunoDto;
import br.com.fiap.eurobackend.entities.Aluno;
import br.com.fiap.eurobackend.exceptions.RegraDeNegocioException;
import br.com.fiap.eurobackend.exceptions.ResourceNotFoundException;
import br.com.fiap.eurobackend.repositories.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Transactional(readOnly = true)
    public List<AlunoDto> findAll() {
        return alunoRepository.findAll()
                .stream()
                .map(AlunoDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlunoDto findById(Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Aluno não encontrado. Id: " + id)
        );
        return new AlunoDto(aluno);
    }

    @Transactional
    public AlunoDto save(AlunoDto dto) {
        if (alunoRepository.existsByCpf(dto.getCpf())) {
            throw new RegraDeNegocioException("Já existe um aluno cadastrado com o CPF informado: " + dto.getCpf());
        }
        if (alunoRepository.existsByEmail(dto.getEmail())) {
            throw new RegraDeNegocioException("Já existe um aluno cadastrado com o e-mail informado: " + dto.getEmail());
        }

        Aluno aluno = new Aluno();
        copyDtoToEntity(dto, aluno);
        aluno = alunoRepository.save(aluno);
        return new AlunoDto(aluno);
    }

    @Transactional
    public AlunoDto update(Long id, AlunoDto dto) {
        try {
            Aluno aluno = alunoRepository.getReferenceById(id);

            if (alunoRepository.existsByCpfAndIdNot(dto.getCpf(), id)) {
                throw new RegraDeNegocioException("Já existe um aluno cadastrado com o CPF informado: " + dto.getCpf());
            }
            if (alunoRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
                throw new RegraDeNegocioException("Já existe um aluno cadastrado com o e-mail informado: " + dto.getEmail());
            }

            copyDtoToEntity(dto, aluno);
            aluno = alunoRepository.save(aluno);
            return new AlunoDto(aluno);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Aluno não encontrado. Id: " + id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aluno não encontrado. Id: " + id);
        }
        alunoRepository.deleteById(id);
    }

    private void copyDtoToEntity(AlunoDto dto, Aluno entity) {
        entity.setNome(dto.getNome());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
    }
}
