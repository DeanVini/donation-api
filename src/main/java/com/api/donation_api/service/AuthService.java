package com.api.donation_api.service;

import com.api.donation_api.dto.AuthRequestDTO;
import com.api.donation_api.dto.UserRequestDTO;
import com.api.donation_api.exception.InvalidLoginException;
import com.api.donation_api.model.User;
import com.api.donation_api.repository.UserRepository;
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
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final List<NewUserValidator> newUserValidators;

    @Autowired
    private AuthService(AuthenticationManager authenticationManager,
                        UserDetailsService userDetailsService,
                        JwtService jwtService,
                        PasswordEncoder passwordEncoder,
                        UserRepository userRepository,
                        List<NewUserValidator> newUserValidators) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.newUserValidators = newUserValidators;
    }

    public String authenticate(@NotNull AuthRequestDTO authRequestDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new InvalidLoginException("Invalid login or password");
        }

        final User user = (User) userDetailsService
                .loadUserByUsername(authRequestDTO.getUsername());

        return jwtService.generateToken(user);
    }

    public User register(@NotNull UserRequestDTO newUserRequestDTO) {
        validateNewUser(newUserRequestDTO);

        User user = User
                .builder()
                .username(newUserRequestDTO.getUsername())
                .password(passwordEncoder.encode(newUserRequestDTO.getPassword()))
                .email(newUserRequestDTO.getEmail())
                .name(newUserRequestDTO.getName())
                .cpf(newUserRequestDTO.getCpf())
                .admin(false)
                .build();

        return userRepository.save(user);
    }

    public void validateNewUser(@NotNull UserRequestDTO newUserRequestDTO) {
        newUserValidators.forEach(validator -> validator.validate(newUserRequestDTO));
    }
}
