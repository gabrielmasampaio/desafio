package gsampaio.desafio.repository;

import gsampaio.desafio.model.Associado;
import org.springframework.data.repository.Repository;

public interface AssociadoRepository extends Repository<Associado, Integer> {

  Associado findByCpf(String cpf);

  Associado save(Associado associado);

}
