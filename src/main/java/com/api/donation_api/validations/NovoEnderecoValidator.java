package com.api.donation_api.validations;

import com.api.donation_api.dto.EnderecoRequestDTO;
import com.api.donation_api.model.Endereco;
import com.api.donation_api.repository.EnderecoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class NovoEnderecoValidator {
    private final EnderecoRepository enderecoRepository;

    public NovoEnderecoValidator(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public void validar(EnderecoRequestDTO enderecoRequest){
        String cep = enderecoRequest.getCep();
        Number numero = enderecoRequest.getNumero();
        List<Endereco> enderecosCep = enderecoRepository.findAllByCep(cep);
        if (numero == null){
            throw new RuntimeException("Número do endereço não informado");
        }
        boolean existeEnderecoComNumero = enderecosCep.stream()
                .anyMatch(endereco -> Objects.equals(endereco.getNumero(), numero));
        if(existeEnderecoComNumero) throw new RuntimeException("Existe um endereço cadastrado com o número: " + numero + " no cep " + cep);
    }
}
