package com.api.donation_api.validations;

import com.api.donation_api.dto.NovaPessoaRequestDTO;
import com.api.donation_api.exception.CpfInvalidoException;
import com.api.donation_api.repository.PessoaRepository;
import org.jetbrains.annotations.NotNull;

public class CpfNovaPessoaValidator implements NovaPessoaValidator {
    private final PessoaRepository pessoaRepository;

    public CpfNovaPessoaValidator(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public void validar(@NotNull NovaPessoaRequestDTO novaPessoaRequestDTO){
        String cpf = novaPessoaRequestDTO.getCpf();

        if(!CpfValidator.isCpfValido(cpf)){
            throw new CpfInvalidoException("O CPF informado não é válido!");
        }

        if (pessoaRepository.existsByCpf(cpf)){
            throw new CpfInvalidoException("O CPF informado já foi cadastrado!");
        }
    }
}
