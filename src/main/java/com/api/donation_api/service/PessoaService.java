package com.api.donation_api.service;

import com.api.donation_api.dto.EnderecoRequestDTO;
import com.api.donation_api.dto.PessoaRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Endereco;
import com.api.donation_api.model.Familia;
import com.api.donation_api.model.Pessoa;
import com.api.donation_api.repository.EnderecoRepository;
import com.api.donation_api.repository.PessoaRepository;
import com.api.donation_api.utils.BeanPersonalizadoUtils;
import com.api.donation_api.validations.NovaPessoaValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PessoaService {
    private final PessoaRepository pessoaRepository;
    private final EnderecoRepository enderecoRepository;
    private final List<NovaPessoaValidator> validadoresNovaPessoa;
    private final EnderecoService enderecoService;


    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository, List<NovaPessoaValidator> validadoresNovaPessoa, EnderecoService enderecoService) {
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
        this.validadoresNovaPessoa = validadoresNovaPessoa;
        this.enderecoService = enderecoService;
    }

    public List<Pessoa> getAllPessoas(){
        return pessoaRepository.findAll();
    }

    public Pessoa cadastrarPessoa(@NotNull PessoaRequestDTO novaPessoaRequest) throws ResourceNotFoundException {
        validarNovaPessoa(novaPessoaRequest);

        Pessoa.PessoaBuilder pessoaBuilder = Pessoa.builder()
                .nome(novaPessoaRequest.getNome())
                .cpf(novaPessoaRequest.getCpf())
                .telefone(novaPessoaRequest.getTelefone())
                .dataNascimento(novaPessoaRequest.getDataNascimento());

        if (novaPessoaRequest.getEndereco() != null) {
            EnderecoRequestDTO enderecoRequestDTO = novaPessoaRequest.getEndereco();
            if (enderecoRequestDTO.getId() != null){
                Endereco endereco = enderecoService.getEnderecoById(enderecoRequestDTO.getId());
                pessoaBuilder.endereco(endereco);
            }
            else{
                Endereco novoEndereco = enderecoService.cadastrarEndereco(enderecoRequestDTO);
                pessoaBuilder.endereco(novoEndereco);
            }
        }

        Pessoa pessoa = pessoaBuilder.build();

        return pessoaRepository.save(pessoa);
    }

    public Pessoa atualizarPessoa(Long id, PessoaRequestDTO pessoaRequestDTO) throws ResourceNotFoundException {
        Pessoa pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id));

        BeanPersonalizadoUtils.copiarPropriedadesNaoNulas(pessoaRequestDTO, pessoa);
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

    public Pessoa getPessoaById(Long pessoaId) throws ResourceNotFoundException {
        return pessoaRepository.findById(pessoaId)
                .orElseThrow(()->new ResourceNotFoundException("Pessoa não encontrada."));
    }

    public Pessoa obterOuCriar(PessoaRequestDTO pessoaRequestDTO) throws ResourceNotFoundException {
        if (pessoaRequestDTO.getId() != null){
            return getPessoaById(pessoaRequestDTO.getId());
        }
        return cadastrarPessoa(pessoaRequestDTO);
    }

    public void atualizarEnderecoPessoas(Set<Pessoa> pessoas, Endereco endereco){
        for(Pessoa pessoa : pessoas){
            pessoa.setEndereco(endereco);
            pessoaRepository.save(pessoa);
        }
    }

    public void atualizarFamiliaPessoas(Set<Pessoa> pessoas, Familia familia){
        for(Pessoa pessoa : pessoas){
            pessoa.setFamilia(familia);
            pessoaRepository.saveAndFlush(pessoa);
        }
    }

    public void validarNovaPessoa(@NotNull PessoaRequestDTO pessoaRequestDTO){
        validadoresNovaPessoa.forEach(validador -> validador.validar(pessoaRequestDTO));
    }
}
