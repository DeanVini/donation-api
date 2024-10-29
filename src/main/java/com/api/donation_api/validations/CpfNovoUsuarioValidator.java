package com.api.donation_api.validations;

import com.api.donation_api.dto.NovoUsuarioRequestDTO;
import com.api.donation_api.exception.CpfInvalidoException;
import com.api.donation_api.repository.UsuarioRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class CpfNovoUsuarioValidator implements NovoUsuarioValidator{
    private final UsuarioRepository usuarioRepository;

    public CpfNovoUsuarioValidator(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void validar(@NotNull NovoUsuarioRequestDTO novoUsuarioRequestDTO){
        String cpf = novoUsuarioRequestDTO.getCpf();

        if (!CpfValidator.isCpfValido(cpf)) {
            throw new CpfInvalidoException("O CPF informado não é válido!");
        }

        if (usuarioRepository.existsByCpf(cpf)) {
            throw new CpfInvalidoException("O CPF informado já foi cadastrado!");
        }
    }
}
