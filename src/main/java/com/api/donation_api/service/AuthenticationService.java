package com.api.donation_api.service;

import com.api.donation_api.dto.AuthRequestDTO;
import com.api.donation_api.dto.UsuarioRequestDTO;
import com.api.donation_api.exception.InvalidLoginException;
import com.api.donation_api.model.User;
import com.api.donation_api.repository.UsuarioRepository;
import com.api.donation_api.validations.NewUserValidator;
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
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final List<NewUserValidator> newUserValidators;

    @Autowired
    private AuthenticationService(AuthenticationManager authenticationManager,
                                  UserDetailsService userDetailsService,
                                  JwtService jwtService,
                                  PasswordEncoder passwordEncoder,
                                  UsuarioRepository usuarioRepository,
                                  List<NewUserValidator> newUserValidators) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.newUserValidators = newUserValidators;
    }

    public String authenticate(@NotNull AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getLogin(), authRequestDTO.getSenha())
            );
        } catch (AuthenticationException e) {
            throw new InvalidLoginException("Invalid login or password");
        }

        final User user = (User) userDetailsService
                .loadUserByUsername(authRequestDTO.getLogin());

        return jwtService.generateToken(user);
    }

    public User register(@NotNull UsuarioRequestDTO newUserRequestDTO) {
        validateNewUser(newUserRequestDTO);

        User user = User
                .builder()
                .userName(newUserRequestDTO.getLogin())
                .password(passwordEncoder.encode(newUserRequestDTO.getSenha()))
                .email(newUserRequestDTO.getEmail())
                .name(newUserRequestDTO.getNome())
                .cpf(newUserRequestDTO.getCpf())
                .admin(false)
                .build();

        return usuarioRepository.save(user);
    }

    public void validateNewUser(@NotNull UsuarioRequestDTO newUserRequestDTO) {
        newUserValidators.forEach(validator -> validator.validate(newUserRequestDTO));
    }
}
