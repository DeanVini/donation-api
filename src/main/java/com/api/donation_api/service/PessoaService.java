package com.api.donation_api.service;

import com.api.donation_api.dto.PessoaDTO;
import com.api.donation_api.exception.CpfInvalidoException;
import com.api.donation_api.model.Pessoa;
import com.api.donation_api.repository.PessoaRepository;
import com.api.donation_api.validations.CpfValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa cadastrarPessoa(@NotNull PessoaDTO pessoaDTO){
        if (!CpfValidator.isCpfValido(pessoaDTO.getCpf()) ){
            throw new CpfInvalidoException("O cpf informado não é válido!");
        }

        Pessoa pessoa = Pessoa
                .builder()
                .nome(pessoaDTO.getNome())
                .cpf(pessoaDTO.getCpf())
                .telefone(pessoaDTO.getTelefone())
                .dataNascimento(pessoaDTO.getDataNascimento())
                .build();
        return pessoaRepository.save(pessoa);
    }

    public Optional<Pessoa> getPessoaById(Long pessoaId){
        return pessoaRepository.findById(pessoaId);
    }
}
