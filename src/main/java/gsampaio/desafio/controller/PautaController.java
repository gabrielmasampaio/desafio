package gsampaio.desafio.controller;

import gsampaio.desafio.model.AssociadoModel;
import gsampaio.desafio.model.PautaModel;
import gsampaio.desafio.repository.AssociadoRepository;
import gsampaio.desafio.repository.PautaRepository;
import gsampaio.desafio.utils.SituacaoEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/*
    TODO: Adicionar tempo de votação na requisição e na lógica
    TODO: Contabilizar os votos
    TODO: ADICIONAR SWAGGER
    TODO: Refatorar controllers (adicionar service?)
    ** EXTRA **
    TODO: - VINCULAR CHAMADA DE API HEROKUAPP
 */
@RestController
public class PautaController {

    @Autowired
    private PautaRepository pautaRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    @PostMapping(path = "/api/pauta/{cpf}")
    public ResponseEntity postPauta(@PathVariable("cpf") String cpf, @RequestBody PautaModel pauta){
        PautaModel pautaCriada;
        pauta.setSituacaoEnum(SituacaoEnum.CRIADO);
        AssociadoModel associado = associadoRepository.findByCpf(cpf);
        pauta.setAssociado(associado);
        pautaCriada = pautaRepository.save(pauta);
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaCriada);
    }

    @PutMapping("/api/pauta/{nome}")
    public ResponseEntity iniciaVotacao(@PathVariable("nome") String nomePauta){
        PautaModel pauta;

        try {
            pauta = pautaRepository.findByNome(nomePauta);
            if(Objects.isNull(pauta))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Pauta não encontrada");
            pauta.setSituacaoEnum(SituacaoEnum.VOTACAO_ABERTA);
            pautaRepository.save(pauta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Verifique os dados enviados");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Pauta '" + pauta.getNome() + "' alterada com sucesso. Situação: " + pauta.getSituacaoEnum());
    }

    @GetMapping("/api/pauta/contabilizar/{nome}")
    public ResponseEntity contabilizaVotacao(@PathVariable("nome") String nomePauta){
        PautaModel pauta;

        try {
            pauta = pautaRepository.findByNome(nomePauta);
            if(Objects.isNull(pauta))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Pauta não encontrada");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Verifique os dados enviados");
        }

        return null;
    }
}
