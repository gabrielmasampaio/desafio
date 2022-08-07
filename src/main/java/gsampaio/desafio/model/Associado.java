package gsampaio.desafio.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tb_associado")
public class Associado {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  @NotBlank
  @Column(nullable = false, length = 50)
  private String nome;

  @NotBlank
  @Column(nullable = false, length = 11, unique = true)
  private String cpf;
}
