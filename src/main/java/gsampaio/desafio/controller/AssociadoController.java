package gsampaio.desafio.controller;

import gsampaio.desafio.model.AssociadoModel;
import gsampaio.desafio.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class AssociadoController {
    @Autowired
    private AssociadoRepository associadoRepository;

    @GetMapping(path = "/api/associado/{cpf}")
    public ResponseEntity getAssociado(@PathVariable("cpf") String cpf){
        if(!validaCpf(cpf))
            return ResponseEntity.badRequest().body("CPF informado está no formato inválido");

        if(Objects.isNull(associadoRepository.findByCpf(cpf)))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CPF não encontrado");

        return ResponseEntity.ok().body(associadoRepository.findByCpf(cpf));
    }

    @PostMapping(path = "/api/associado/")
    public ResponseEntity postAssociado(@RequestBody AssociadoModel associado){
        AssociadoModel AssociadoCriado;
        if(!validaCpf(associado.getCpf()))
            return ResponseEntity.badRequest().body("CPF informado está no formato inválido");
        try{
            AssociadoCriado = associadoRepository.save(associado);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Existem dados inválidos, ou CPF já cadastrado");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(AssociadoCriado);
    }

    private boolean validaCpf(String cpf){
        cpf = cpf.replaceAll("[^0-9]", ""); //Makes CPF numeric only
        return cpf.length() == 11;
    }
}
