package gsampaio.desafio.controller;

import gsampaio.desafio.model.Pauta;
import gsampaio.desafio.service.PautaService;
import gsampaio.desafio.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PautaController {

  @Autowired
  private PautaService pautaService;

  @Autowired
  private VotoService votoService;

  @PostMapping(path = "/api/pauta/{cpf}")
  public ResponseEntity criarPauta(@PathVariable("cpf") String cpf, @RequestBody Pauta pauta) {
    Pauta pautaCriada;
    try {
      pautaCriada = pautaService.criarPauta(cpf, pauta);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(pautaCriada);
  }

  @PutMapping("/api/pauta/{nome}")
  public ResponseEntity iniciarVotacao(@PathVariable("nome") String nomePauta, @Param("tempoSessao") Integer tempoSessao) {
    Pauta pauta;
    try {
      pauta = pautaService.iniciarVotacao(nomePauta, tempoSessao);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    return ResponseEntity.status(HttpStatus.OK).body("Pauta '" + pauta.getNome() + "' alterada com sucesso. Situação: " + pauta.getSituacaoEnum());
  }

  @GetMapping("/api/pauta/contabilizar/{nome}")
  public ResponseEntity contabilizaVotacao(@PathVariable("nome") String nomePauta) {
    Pauta pauta;

    try {
      pauta = votoService.contabilizaVotacao(nomePauta);
    } catch (RuntimeException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    return ResponseEntity.ok(pauta);
  }
}
