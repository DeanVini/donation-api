package com.api.donation_api.service;

import com.api.donation_api.dto.NovaPessoaRequestDTO;
import com.api.donation_api.exception.CpfInvalidoException;
import com.api.donation_api.model.Pessoa;
import com.api.donation_api.repository.PessoaRepository;
import com.api.donation_api.validations.CpfValidator;
import com.api.donation_api.validations.NovaPessoaValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final List<NovaPessoaValidator> validadoresNovaPessoa;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, List<NovaPessoaValidator> validadoresNovaPessoa) {
        this.pessoaRepository = pessoaRepository;
        this.validadoresNovaPessoa = validadoresNovaPessoa;
    }

    public Pessoa cadastrarPessoa(@NotNull NovaPessoaRequestDTO novaPessoaRequestDTO){
        validarNovaPessoa(novaPessoaRequestDTO);

        Pessoa pessoa = Pessoa
                .builder()
                .nome(novaPessoaRequestDTO.getNome())
                .cpf(novaPessoaRequestDTO.getCpf())
                .telefone(novaPessoaRequestDTO.getTelefone())
                .dataNascimento(novaPessoaRequestDTO.getDataNascimento())
                .build();

        return pessoaRepository.save(pessoa);
    }


    public Optional<Pessoa> getPessoaById(Long pessoaId){
        return pessoaRepository.findById(pessoaId);
    }

    public void validarNovaPessoa(@NotNull NovaPessoaRequestDTO novaPessoaRequestDTO){
        validadoresNovaPessoa.forEach(validador -> validador.validar(novaPessoaRequestDTO));
    }
}
