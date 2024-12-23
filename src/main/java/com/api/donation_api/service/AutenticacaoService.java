package com.api.donation_api.service;

import com.api.donation_api.dto.AuthRequestDTO;
import com.api.donation_api.dto.UsuarioRequestDTO;
import com.api.donation_api.exception.LoginInvalidoException;
import com.api.donation_api.model.Usuario;
import com.api.donation_api.repository.UsuarioRepository;
import com.api.donation_api.validations.NovoUsuarioValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutenticacaoService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final List<NovoUsuarioValidator> validadorNovosUsuarios;

    @Autowired
    private AutenticacaoService(AuthenticationManager authenticationManager,
                                UserDetailsService userDetailsService,
                                JwtService jwtService,
                                PasswordEncoder passwordEncoder,
                                UsuarioRepository usuarioRepository, List<NovoUsuarioValidator> validadorNovosUsuarios) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.validadorNovosUsuarios = validadorNovosUsuarios;
    }

    public String autenticar(@NotNull AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getLogin(), authRequestDTO.getSenha())
            );
        } catch (AuthenticationException e) {
            throw new LoginInvalidoException("Login ou senha inválidos");
        }

        final Usuario usuario = (Usuario) userDetailsService
                .loadUserByUsername(authRequestDTO.getLogin());

        return jwtService.generateToken(usuario);
    }

    public Usuario registrar(@NotNull UsuarioRequestDTO novoUsuarioRequestDTO) {
        validarNovoUsuario(novoUsuarioRequestDTO);

        Usuario usuario = Usuario
                .builder()
                .login(novoUsuarioRequestDTO.getLogin())
                .senha(passwordEncoder.encode(novoUsuarioRequestDTO.getSenha()))
                .email(novoUsuarioRequestDTO.getEmail())
                .nome(novoUsuarioRequestDTO.getNome())
                .cpf(novoUsuarioRequestDTO.getCpf())
                .administrador(false)
                .build();

        return usuarioRepository.save(usuario);
    }

    public void validarNovoUsuario(@NotNull UsuarioRequestDTO novoUsuarioRequestDTO){
        validadorNovosUsuarios.forEach(validador -> validador.validar(novoUsuarioRequestDTO));
    }
}