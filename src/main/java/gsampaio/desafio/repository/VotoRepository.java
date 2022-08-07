package gsampaio.desafio.repository;

import gsampaio.desafio.model.AssociadoModel;
import gsampaio.desafio.model.PautaModel;
import gsampaio.desafio.model.VotoModel;
import gsampaio.desafio.utils.TipoVoto;
import gsampaio.desafio.utils.VotoPK;
import org.springframework.data.repository.Repository;

public interface VotoRepository extends Repository<VotoModel, VotoPK> {

    public VotoModel findByAssociadoAndPauta(AssociadoModel associado, PautaModel pauta);

    public VotoModel save(VotoModel voto);

    public Integer countByPautaAndTipoVoto(PautaModel pauta, TipoVoto tipoVoto);

}
