package gsampaio.desafio.model;

import gsampaio.desafio.utils.SituacaoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Entity(name = "tb_pauta")
public class PautaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank(message = "Nome da pauta é obrigatório")
    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "autorId", nullable = false)
    private AssociadoModel associado;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoEnum situacaoEnum;


}
