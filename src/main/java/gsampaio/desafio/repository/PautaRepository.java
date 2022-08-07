package gsampaio.desafio.repository;

import gsampaio.desafio.model.Pauta;
import org.springframework.data.repository.Repository;

public interface PautaRepository extends Repository<Pauta, Integer> {

    public Pauta findByNome(String nome);

    public Pauta save(Pauta pauta);

}
