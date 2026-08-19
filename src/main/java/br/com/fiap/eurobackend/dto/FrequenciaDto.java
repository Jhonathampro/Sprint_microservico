package br.com.fiap.eurobackend.dto;

import br.com.fiap.eurobackend.entities.Frequencia;
import br.com.fiap.eurobackend.entities.SituacaoFrequencia;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FrequenciaDto {

    private Long id;

    @NotNull(message = "ID da aula é requerido")
    private Long aulaId;

    @NotNull(message = "ID da matrícula é requerido")
    private Long matriculaId;

    @NotNull(message = "Situação é requerida")
    private SituacaoFrequencia situacao;

    public FrequenciaDto(Frequencia frequencia) {
        this.id = frequencia.getId();
        this.aulaId = frequencia.getAula() != null ? frequencia.getAula().getId() : null;
        this.matriculaId = frequencia.getMatricula() != null ? frequencia.getMatricula().getId() : null;
        this.situacao = frequencia.getSituacao();
    }
}
