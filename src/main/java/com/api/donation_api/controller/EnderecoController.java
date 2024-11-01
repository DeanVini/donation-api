package com.api.donation_api.controller;

import com.api.donation_api.dto.NovoEnderecoRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Endereco;
import com.api.donation_api.service.CepService;
import com.api.donation_api.service.EnderecoService;
import com.api.donation_api.utils.ConstrutorResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
    private final EnderecoService enderecoService;
    private final CepService cepService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService, CepService cepService) {
        this.enderecoService = enderecoService;
        this.cepService = cepService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllEnderecos(){
        List<Endereco> enderecos= enderecoService.getAllEnderecos();
        return ConstrutorResposta.respostaOk(enderecos);
    }

    @PostMapping("/")
    public ResponseEntity<Object> cadastrarEndereco(@RequestBody NovoEnderecoRequestDTO novoEnderecoRequest) throws ResourceNotFoundException {
        Endereco enderecoCriado = enderecoService.cadastrarEndereco(novoEnderecoRequest);
        return ConstrutorResposta.respostaCreated(enderecoCriado);
    }

    @PutMapping("/{idEndereco}")
    public ResponseEntity<Object> atualizarEndereco(@PathVariable Long idEndereco, @RequestBody NovoEnderecoRequestDTO novoEnderecoDTO) throws ResourceNotFoundException {
        Endereco enderecoAtualizado = enderecoService.atualizarEndereco(idEndereco, novoEnderecoDTO);
        return ConstrutorResposta.respostaOk(enderecoAtualizado);
    }

    @GetMapping("/viaCep/{cep}")
    public ResponseEntity<Object> getEnderecoViaCep(@PathVariable String cep) throws ResourceNotFoundException {
        Endereco endereco = cepService.buscarEnderecoPorCep(cep);
        return ConstrutorResposta.respostaOk(endereco);
    }

    @GetMapping("/{idEndereco}")
    public ResponseEntity<Object> getEnderecoById(@PathVariable Long idEndereco){
        Optional<Endereco> endereco = enderecoService.getEnderecoById(idEndereco);
        return ConstrutorResposta.respostaOk(endereco);
    }
}
