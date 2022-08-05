package gsampaio.desafio.repository;

import gsampaio.desafio.model.AssociadoModel;
import org.springframework.data.repository.Repository;

public interface AssociadoRepository extends Repository<AssociadoModel, Integer> {

    public AssociadoModel findByCpf(String cpf);

    public AssociadoModel save(AssociadoModel associado);

}
