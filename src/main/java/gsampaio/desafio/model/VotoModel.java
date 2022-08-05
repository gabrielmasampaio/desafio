package gsampaio.desafio.model;

import gsampaio.desafio.utils.TipoVoto;
import gsampaio.desafio.utils.VotoId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity(name = "tb_voto")
public class VotoModel {

    @EmbeddedId
    private VotoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("associadoId")
    private AssociadoModel associado;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pautaId")
    private PautaModel pauta;

    private TipoVoto tipoVoto;

}
