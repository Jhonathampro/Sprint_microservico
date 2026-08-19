package br.com.fiap.eurobackend.dto;

import br.com.fiap.eurobackend.entities.Turma;
import br.com.fiap.eurobackend.entities.Turno;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaDto {

    private Long id;

    @NotBlank(message = "Nome é requerido")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull(message = "Turno é requerido")
    private Turno turno;

    @NotNull(message = "Vagas é requerido")
    @Positive(message = "Vagas deve ser maior que zero")
    private Integer vagas;

    @NotNull(message = "ID do professor é requerido")
    private Long professorId;

    public TurmaDto(Turma turma) {
        this.id = turma.getId();
        this.nome = turma.getNome();
        this.turno = turma.getTurno();
        this.vagas = turma.getVagas();
        this.professorId = turma.getProfessor() != null ? turma.getProfessor().getId() : null;
    }
}
