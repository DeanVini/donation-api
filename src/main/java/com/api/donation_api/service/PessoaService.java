package com.api.donation_api.service;

import com.api.donation_api.dto.NovaPessoaRequestDTO;
import com.api.donation_api.exception.CpfInvalidoException;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Endereco;
import com.api.donation_api.model.Pessoa;
import com.api.donation_api.repository.EnderecoRepository;
import com.api.donation_api.repository.PessoaRepository;
import com.api.donation_api.validations.CpfValidator;
import com.api.donation_api.validations.NovaPessoaValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final List<NovaPessoaValidator> validadoresNovaPessoa;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository, List<NovaPessoaValidator> validadoresNovaPessoa) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
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

//        if (novaPessoaRequest.getEndereco() != null) {
//            Endereco endereco = enderecoRepository.save(novaPessoaRequest.getEndereco());
//            pessoaBuilder.endereco(endereco);
//        }

        Pessoa pessoa = pessoaBuilder.build();

        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> getPessoasByEndereco(Long idEndereco) throws ResourceNotFoundException {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(idEndereco);
        if (enderecoOptional.isPresent()){
            Endereco endereco = enderecoOptional.get();
            return new ArrayList<>(endereco.getPessoas());
        }

        throw new ResourceNotFoundException("Endereço não encontrado com o id " + idEndereco);
    }

    public Optional<Pessoa> getPessoaById(Long pessoaId){
        return pessoaRepository.findById(pessoaId);
    }

    public void validarNovaPessoa(@NotNull NovaPessoaRequestDTO novaPessoaRequestDTO){
        validadoresNovaPessoa.forEach(validador -> validador.validar(novaPessoaRequestDTO));
    }
}
