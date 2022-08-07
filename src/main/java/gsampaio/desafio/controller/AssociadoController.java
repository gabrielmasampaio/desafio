package gsampaio.desafio.controller;

import gsampaio.desafio.model.Associado;
import gsampaio.desafio.service.AssociadoService;
import gsampaio.desafio.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssociadoController {

    @Autowired
    AssociadoService associadoService;

    @Autowired
    VotoService votoService;

    @GetMapping(path = "/api/associado/{cpf}")
    public ResponseEntity buscaAssociado(@PathVariable("cpf") String cpf){

        try {
            associadoService.buscaAssociado(cpf);
        } catch (RuntimeException e){
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok().body(associadoService.buscaAssociado(cpf));
    }

    @PostMapping(path = "/api/associado/")
    public ResponseEntity criarAssociado(@RequestBody Associado associado){
        Associado associadoCriado;
        try{
            associadoCriado = associadoService.criaAssociado(associado);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(associadoCriado);
    }

    @PostMapping(path = "/api/associado/voto/{nomePauta}/{cpf}/{tipoVoto}")
    public ResponseEntity votar(@PathVariable("nomePauta") String nomePauta, @PathVariable("cpf") String cpf, @PathVariable("tipoVoto") String tipoVoto){

        try {
            votoService.cadastraVoto(nomePauta, cpf, tipoVoto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok("Voto cadastrado com sucesso");
    }
}
