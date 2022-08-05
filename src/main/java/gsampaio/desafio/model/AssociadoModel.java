package gsampaio.desafio.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Constraint;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Entity(name = "tb_associado")
public class AssociadoModel {

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
