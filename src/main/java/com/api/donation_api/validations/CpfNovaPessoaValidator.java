package com.api.donation_api.validations;

import com.api.donation_api.dto.PessoaRequestDTO;
import com.api.donation_api.exception.CpfInvalidoException;
import com.api.donation_api.repository.PessoaRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CpfNovaPessoaValidator implements NovaPessoaValidator {
    private final PessoaRepository pessoaRepository;

    public CpfNovaPessoaValidator(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public void validar(@NotNull PessoaRequestDTO pessoaRequestDTO){
        String cpf = pessoaRequestDTO.getCpf();

        if(!CpfValidator.isCpfValido(cpf)){
            throw new CpfInvalidoException("O CPF informado não é válido!");
        }

        if (pessoaRepository.existsByCpf(cpf)){
            throw new CpfInvalidoException("O CPF informado já foi cadastrado!");
        }
    }
}
