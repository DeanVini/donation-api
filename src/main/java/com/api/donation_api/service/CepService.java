package com.api.donation_api.service;

import com.api.donation_api.exception.CepInvalidoException;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Endereco;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class CepService {
    private final RestTemplate restTemplate;

    public CepService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Endereco buscarEnderecoPorCep(String cep) throws ResourceNotFoundException {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        return mapearEnderecoViaCep(Objects.requireNonNull(response.getBody()));
    }

    public Endereco mapearEnderecoViaCep(Map<String, Object> enderecoViaCep) throws ResourceNotFoundException {
        if (Objects.equals((String) enderecoViaCep.get("erro"), "true")){
            throw new CepInvalidoException("CEP inválido!");
        }
        return Endereco
                .builder()
                .cep((String) enderecoViaCep.get("cep"))
                .logradouro((String) enderecoViaCep.get("logradouro"))
                .municipio((String) enderecoViaCep.get("localidade"))
                .bairro((String) enderecoViaCep.get("bairro"))
                .estado((String) enderecoViaCep.get("estado"))
                .build();
    }
}
