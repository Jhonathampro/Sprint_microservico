package br.com.fiap.eurobackend.dto;

import br.com.fiap.eurobackend.entities.Professor;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfessorDto {

    private Long id;

    @NotBlank(message = "Nome é requerido")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "CPF é requerido")
    @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
    private String cpf;

    @NotBlank(message = "E-mail é requerido")
    @Email(message = "E-mail deve ser válido")
    @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
    private String email;

    public ProfessorDto(Professor professor) {
        this.id = professor.getId();
        this.nome = professor.getNome();
        this.cpf = professor.getCpf();
        this.email = professor.getEmail();
    }
}
