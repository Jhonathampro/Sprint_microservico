package br.com.fiap.eurobackend.entities;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "frequencia", uniqueConstraints = {
        @UniqueConstraint(name = "uk_frequencia", columnNames = {"id_aula", "id_matricula"})
})
public class Frequencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_frequencia")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_aula", nullable = false)
    private Aula aula;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao", nullable = false, length = 20)
    private SituacaoFrequencia situacao;
}
