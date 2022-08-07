package gsampaio.desafio.repository;

import gsampaio.desafio.model.PautaModel;
import gsampaio.desafio.model.VotoModel;
import org.springframework.data.repository.Repository;

public interface PautaRepository extends Repository<PautaModel, Integer> {

    public PautaModel findByNome(String nome);

    public PautaModel save(PautaModel pauta);

}
