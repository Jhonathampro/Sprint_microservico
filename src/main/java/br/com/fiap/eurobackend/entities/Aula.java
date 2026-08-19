package br.com.fiap.eurobackend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "aula", uniqueConstraints = {
        @UniqueConstraint(name = "uk_aula", columnNames = {"data_aula", "id_turma"})
})
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aula")
    private Long id;

    @Column(name = "data_aula", nullable = false)
    private LocalDate dataAula;

    @Column(name = "horario_inicio", nullable = false, length = 5)
    private String horarioInicio;

    @Column(name = "horario_fim", nullable = false, length = 5)
    private String horarioFim;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_turma", nullable = false)
    private Turma turma;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Frequencia> frequencias = new ArrayList<>();
}
