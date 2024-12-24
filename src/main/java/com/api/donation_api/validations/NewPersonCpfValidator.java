package com.api.donation_api.validations;

import com.api.donation_api.dto.PessoaRequestDTO;
import com.api.donation_api.exception.InvalidCpfException;
import com.api.donation_api.repository.PersonRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class NewPersonCpfValidator implements NewPersonValidator {
    private final PersonRepository personRepository;

    public NewPersonCpfValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public void validate(@NotNull PessoaRequestDTO personRequestDTO){
        String cpf = personRequestDTO.getCpf();

        if(!CpfValidator.isCpfValido(cpf)){
            throw new InvalidCpfException("O CPF informado não é válido!");
        }

        if (personRepository.existsByCpf(cpf)){
            throw new InvalidCpfException("O CPF informado já foi cadastrado!");
        }
    }
}
