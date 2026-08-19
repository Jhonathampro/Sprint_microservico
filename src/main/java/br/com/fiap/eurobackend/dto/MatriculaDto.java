package br.com.fiap.eurobackend.dto;

import br.com.fiap.eurobackend.entities.Matricula;
import br.com.fiap.eurobackend.entities.StatusMatricula;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MatriculaDto {

    private Long id;

    private LocalDate dataMatricula;

    @NotNull(message = "Status da matrícula é requerido")
    private StatusMatricula status;

    @NotNull(message = "ID do aluno é requerido")
    private Long alunoId;

    @NotNull(message = "ID da turma é requerido")
    private Long turmaId;

    public MatriculaDto(Matricula matricula) {
        this.id = matricula.getId();
        this.dataMatricula = matricula.getDataMatricula();
        this.status = matricula.getStatus();
        this.alunoId = matricula.getAluno() != null ? matricula.getAluno().getId() : null;
        this.turmaId = matricula.getTurma() != null ? matricula.getTurma().getId() : null;
    }
}
