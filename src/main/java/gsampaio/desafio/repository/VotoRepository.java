package gsampaio.desafio.repository;

import gsampaio.desafio.model.Pauta;
import gsampaio.desafio.model.Voto;
import gsampaio.desafio.utils.TipoVoto;
import gsampaio.desafio.utils.VotoPK;
import org.springframework.data.repository.Repository;

public interface VotoRepository extends Repository<Voto, VotoPK> {

  Voto save(Voto voto);

  Integer countByPautaAndTipoVoto(Pauta pauta, TipoVoto tipoVoto);

}
