package com.api.donation_api.service;

import com.api.donation_api.dto.EnderecoRequestDTO;
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

    public Endereco cadastrarEndereco(@NotNull EnderecoRequestDTO novoEnderecoRequest) {
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

    public Endereco atualizarEndereco(Long id, EnderecoRequestDTO enderecoDTO) throws ResourceNotFoundException {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + id));
        BeanPersonalizadoUtils.copiarPropriedadesNaoNulas(enderecoDTO, endereco);
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> getEnderecosByCep(String cep){
        return enderecoRepository.findAllByCep(cep);
    }

    public Endereco getEnderecoById(@NotNull Long idEndereco) throws ResourceNotFoundException {
        Endereco endereco = enderecoRepository.findById(idEndereco)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        return endereco;
    }

    public Endereco obterOuCriar(EnderecoRequestDTO enderecoRequestDTO) throws ResourceNotFoundException {
        if (enderecoRequestDTO.getId() != null){
            return getEnderecoById(enderecoRequestDTO.getId());
        }
        return cadastrarEndereco(enderecoRequestDTO);
    }
}
