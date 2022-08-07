package gsampaio.desafio.model;

import gsampaio.desafio.utils.TipoVoto;
import gsampaio.desafio.utils.VotoPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity(name = "tb_voto")
public class VotoModel {

    @EmbeddedId
    private VotoPK id;

    @ManyToOne
    @JoinColumn(name = "associadoId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private AssociadoModel associado;

    @ManyToOne
    @JoinColumn(name = "pautaId", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private PautaModel pauta;

    @Column
    private TipoVoto tipoVoto;

}
