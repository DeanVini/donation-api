package com.api.donation_api.controller;

import com.api.donation_api.dto.NovaPessoaRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Pessoa;
import com.api.donation_api.service.PessoaService;
import com.api.donation_api.utils.ConstrutorResposta;
import com.api.donation_api.view.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {
    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllPessoas(){
        List<Pessoa> pessoas= pessoaService.getAllPessoas();
        return ConstrutorResposta.respostaOk(pessoas);
    }

    @PostMapping("/")
    public ResponseEntity<Object> cadastrarPessoa(@RequestBody NovaPessoaRequestDTO novaPessoaRequestDTO){
        Pessoa pessoaCadastrada = pessoaService.cadastrarPessoa(novaPessoaRequestDTO);
        return ConstrutorResposta.respostaCreated(pessoaCadastrada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable Long id){
        Optional<Pessoa> pessoa = pessoaService.getPessoaById(id);
        return ConstrutorResposta.respostaOk(pessoa);
    }

    @GetMapping("/endereco/{idEndereco}")
    @JsonView(Views.PessoaResumo.class)
    public ResponseEntity<Object> getPessoasByEndereco(@PathVariable Long idEndereco) throws ResourceNotFoundException {
        List<Pessoa> pessoas = pessoaService.getPessoasByEndereco(idEndereco);
        return ConstrutorResposta.respostaOk(pessoas);
    }

}
