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

    public List<Pessoa> getAllPessoas(){
        return pessoaRepository.findAll();
    }

    public Pessoa cadastrarPessoa(@NotNull NovaPessoaRequestDTO novaPessoaRequest) {
        validarNovaPessoa(novaPessoaRequest);

        Pessoa.PessoaBuilder pessoaBuilder = Pessoa.builder()
                .nome(novaPessoaRequest.getNome())
                .cpf(novaPessoaRequest.getCpf())
                .telefone(novaPessoaRequest.getTelefone())
                .dataNascimento(novaPessoaRequest.getDataNascimento());

        if (novaPessoaRequest.getEndereco() != null) {
            pessoaBuilder.endereco(novaPessoaRequest.getEndereco());
        }

        Pessoa pessoa = pessoaBuilder.build();

        return pessoaRepository.save(pessoa);
    }


    public Optional<Pessoa> getPessoaById(Long pessoaId){
        return pessoaRepository.findById(pessoaId);
    }

    public void validarNovaPessoa(@NotNull NovaPessoaRequestDTO novaPessoaRequestDTO){
        validadoresNovaPessoa.forEach(validador -> validador.validar(novaPessoaRequestDTO));
    }
}
