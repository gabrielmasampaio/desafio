package gsampaio.desafio.controller;

import gsampaio.desafio.model.AssociadoModel;
import gsampaio.desafio.model.PautaModel;
import gsampaio.desafio.model.VotoModel;
import gsampaio.desafio.repository.AssociadoRepository;
import gsampaio.desafio.repository.PautaRepository;
import gsampaio.desafio.repository.VotoRepository;
import gsampaio.desafio.utils.TipoVoto;
import gsampaio.desafio.utils.VotoPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class AssociadoController {
    @Autowired
    private AssociadoRepository associadoRepository;
    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private VotoRepository votoRepository;

    @GetMapping(path = "/api/associado/{cpf}")
    public ResponseEntity getAssociado(@PathVariable("cpf") String cpf){
        validaCpf(cpf);


        if(Objects.isNull(associadoRepository.findByCpf(cpf)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF não encontrado");

        return ResponseEntity.ok().body(associadoRepository.findByCpf(cpf));
    }

    @PostMapping(path = "/api/associado/")
    public ResponseEntity postAssociado(@RequestBody AssociadoModel associado){
        AssociadoModel AssociadoCriado;
        validaCpf(associado.getCpf());

        try{
            AssociadoCriado = associadoRepository.save(associado);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Existem dados inválidos, ou CPF já cadastrado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(AssociadoCriado);
    }

    @PostMapping(path = "/api/associado/voto/{nomePauta}/{cpf}/{tipoVoto}")
    public ResponseEntity votar(@PathVariable("nomePauta") String nomePauta, @PathVariable("cpf") String cpf, @PathVariable("tipoVoto") String tipoVoto){
        validaCpf(cpf);
        PautaModel pauta;
        AssociadoModel associado;
        VotoModel voto = new VotoModel();

        if(tipoVoto.equals("Sim"))
            voto.setTipoVoto(TipoVoto.SIM);
        else if (tipoVoto.equals("Não"))
            voto.setTipoVoto(TipoVoto.NAO);
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Tipo de voto inválido. [Tipos suportados são: 'Sim', 'Não']");


        try {
            pauta = pautaRepository.findByNome(nomePauta);
            if(Objects.isNull(pauta))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Pauta não encontrada");

            associado = associadoRepository.findByCpf(cpf);
            if(Objects.isNull(associado))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Associado não encontrado");

            if(Objects.nonNull(votoRepository.findByAssociadoAndPauta(associado, pauta)))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: você já votou nessa pauta");

            voto.setId(new VotoPK(associado.getId(), pauta.getId()));
            voto.setAssociado(associado);
            voto.setPauta(pauta);
            votoRepository.save(voto);

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Verifique os dados enviados");
        }

        return ResponseEntity.ok("Voto cadastrado com sucesso");

    };

    private ResponseEntity validaCpf(String cpf){
        if(cpf.replaceAll("[^0-9]", "").length() != 11)
            return ResponseEntity.badRequest().body("CPF informado está no formato inválido");
        return null;
    }
}
