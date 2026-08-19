package br.com.fiap.eurobackend.dto;

import br.com.fiap.eurobackend.entities.Aula;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AulaDto {

    private Long id;

    @NotNull(message = "Data da aula é requerida")
    private LocalDate dataAula;

    @NotBlank(message = "Horário inicial é requerido")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Horário inicial deve estar no formato HH:mm")
    private String horarioInicio;

    @NotBlank(message = "Horário final é requerido")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$", message = "Horário final deve estar no formato HH:mm")
    private String horarioFim;

    @NotNull(message = "ID da turma é requerido")
    private Long turmaId;

    public AulaDto(Aula aula) {
        this.id = aula.getId();
        this.dataAula = aula.getDataAula();
        this.horarioInicio = aula.getHorarioInicio();
        this.horarioFim = aula.getHorarioFim();
        this.turmaId = aula.getTurma() != null ? aula.getTurma().getId() : null;
    }
}
