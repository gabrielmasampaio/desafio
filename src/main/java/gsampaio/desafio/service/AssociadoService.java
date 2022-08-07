package gsampaio.desafio.service;

import gsampaio.desafio.model.Associado;
import gsampaio.desafio.repository.AssociadoRepository;
import gsampaio.desafio.utils.Validacoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    public Associado buscaAssociado(String cpf){
        Validacoes.cpfValido(cpf);
        if(Objects.isNull(associadoRepository.findByCpf(cpf)))
            throw new RuntimeException("Associado não encontrado para o cpf informado");

        return associadoRepository.findByCpf(cpf);
    }

    public Associado criaAssociado(Associado associado) {
        validaAssociado(associado);
        Associado associadoCriado;

        try {
            associadoCriado = associadoRepository.save(associado);
        } catch (Exception e) {
            throw new RuntimeException("Erro: cpf já cadastrado");
        }

        return associadoCriado;
    }

    public void validaAssociado(Associado associado){
        Validacoes.cpfValido(associado.getCpf());
        if(associado.getNome().isEmpty())
            throw new RuntimeException("Erro: nome do associado não pode estar em branco");
    }
}
