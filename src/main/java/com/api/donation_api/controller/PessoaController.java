package com.api.donation_api.controller;

import com.api.donation_api.dto.PessoaRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Person;
import com.api.donation_api.service.PessoaService;
import com.api.donation_api.utils.ResponseConstructorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Person> people = pessoaService.getAllPessoas();
        return ResponseConstructorUtils.okResponse(people);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createPerson(@RequestBody PessoaRequestDTO novaPessoaRequestDTO) throws ResourceNotFoundException {
        Person personCadastrada = pessoaService.createPerson(novaPessoaRequestDTO);
        return ResponseConstructorUtils.createdResponse(personCadastrada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPessoaById(@PathVariable Long id) throws ResourceNotFoundException {
        Person person = pessoaService.getPessoaById(id);
        return ResponseConstructorUtils.okResponse(person);
    }

    @GetMapping("/endereco/{idEndereco}")
    public ResponseEntity<Object> getPessoasByEndereco(@PathVariable Long idEndereco) throws ResourceNotFoundException {
        List<Person> people = pessoaService.getPessoasByEndereco(idEndereco);
        return ResponseConstructorUtils.okResponse(people);
    }

}
