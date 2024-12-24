package com.api.donation_api.validations;

import com.api.donation_api.dto.addressRequestDTO;
import com.api.donation_api.model.Address;
import com.api.donation_api.repository.AddressRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class NewAddressValidator {
    private final AddressRepository addressRepository;

    public NewAddressValidator(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public void validate(addressRequestDTO addressRequest){
        String postalCode = addressRequest.getPostalCode();
        Number number = addressRequest.getNumber();
        List<Address> addressPostalCode = addressRepository.findAllByPostalCode(postalCode);
        if (number == null){
            throw new RuntimeException("Número do endereço não informado");
        }
        boolean existeEnderecoComNumero = addressPostalCode.stream()
                .anyMatch(adress -> Objects.equals(adress.getNumber(), number));
        if(existeEnderecoComNumero) throw new RuntimeException("Existe um endereço cadastrado com o número: " + number + " no cep " + postalCode);
    }
}
