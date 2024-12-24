package com.api.donation_api.service;

import com.api.donation_api.exception.InvalidPostalCodeException;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Address;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
public class PostalCodeService {
    private final RestTemplate restTemplate;

    public PostalCodeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Address findAddressByPostalCode(String postalCode) {
        String url = "https://viacep.com.br/ws/" + postalCode + "/json/";
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        return mapViaCepApiAddress(Objects.requireNonNull(response.getBody()));
    }

    public Address mapViaCepApiAddress(Map<String, Object> addressViaCepApi) {
        if (Objects.equals(addressViaCepApi.get("erro"), "true")){
            throw new InvalidPostalCodeException("CEP inválido!");
        }
        return Address
                .builder()
                .postalCode((String) addressViaCepApi.get("cep"))
                .street((String) addressViaCepApi.get("logradouro"))
                .city((String) addressViaCepApi.get("localidade"))
                .neighborhood((String) addressViaCepApi.get("bairro"))
                .state((String) addressViaCepApi.get("estado"))
                .build();
    }
}
