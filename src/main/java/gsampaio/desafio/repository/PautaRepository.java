package gsampaio.desafio.repository;

import gsampaio.desafio.model.PautaModel;
import org.springframework.data.repository.Repository;

public interface PautaRepository extends Repository<PautaModel, Integer> {

    public PautaModel findByNome(String nome);

    public PautaModel save(PautaModel pauta);

}
