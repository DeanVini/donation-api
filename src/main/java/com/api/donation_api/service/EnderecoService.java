package com.api.donation_api.service;

import com.api.donation_api.dto.NovoEnderecoRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Endereco;
import com.api.donation_api.repository.EnderecoRepository;
import com.api.donation_api.utils.BeanPersonalizadoUtils;
import com.api.donation_api.validations.NovoEnderecoValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final NovoEnderecoValidator novoEndereco;

    public EnderecoService(EnderecoRepository enderecoRepository, NovoEnderecoValidator novoEndereco) {
        this.enderecoRepository = enderecoRepository;
        this.novoEndereco = novoEndereco;
    }

    public List<Endereco> getAllEnderecos(){
        return enderecoRepository.findAll();
    }

    public Endereco cadastrarEndereco(@NotNull NovoEnderecoRequestDTO novoEnderecoRequest) {
        novoEndereco.validar(novoEnderecoRequest);

        Endereco endereco = Endereco
                .builder()
                .cep(novoEnderecoRequest.getCep())
                .logradouro(novoEnderecoRequest.getLogradouro())
                .bairro(novoEnderecoRequest.getBairro())
                .municipio(novoEnderecoRequest.getMunicipio())
                .estado(novoEnderecoRequest.getEstado())
                .complemento(novoEnderecoRequest.getComplemento())
                .numero(novoEnderecoRequest.getNumero())
                .build();
        return enderecoRepository.save(endereco);
    }
//    @TODO: quando tiver a generalização dos builds
//    public Endereco atualizarEndereco(@NotNull )

    public Endereco atualizarEndereco(Long id, NovoEnderecoRequestDTO enderecoDTO) throws ResourceNotFoundException {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + id));
        BeanPersonalizadoUtils.copiarPropriedadesNaoNulas(enderecoDTO, endereco);
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> getEnderecosByCep(String cep){
        return enderecoRepository.findAllByCep(cep);
    }

    public Optional<Endereco> getEnderecoById(@NotNull Long idEndereco){
        return enderecoRepository.findById(idEndereco);
    }
}
