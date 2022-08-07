package gsampaio.desafio.model;

import gsampaio.desafio.utils.SituacaoEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "tb_pauta")
public class Pauta {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @Column(nullable = false, length = 50, unique = true)
  private String nome;

  @Column(nullable = false)
  private String descricao;

  @ManyToOne
  @JoinColumn(name = "autorId", nullable = false)
  private Associado associado;

  @Enumerated(EnumType.STRING)
  @Column(name = "situacao")
  private SituacaoEnum situacaoEnum;
}
