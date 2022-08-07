package gsampaio.desafio.repository;

import gsampaio.desafio.model.Pauta;
import org.springframework.data.repository.Repository;

public interface PautaRepository extends Repository<Pauta, Integer> {

  Pauta findByNome(String nome);

  Pauta save(Pauta pauta);

}
