package gsampaio.desafio.repository;

import gsampaio.desafio.model.Associado;
import org.springframework.data.repository.Repository;

public interface AssociadoRepository extends Repository<Associado, Integer> {

    public Associado findByCpf(String cpf);

    public Associado save(Associado associado);

}
