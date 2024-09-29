package com.api.donation_api.service;

import com.api.donation_api.dto.AuthRequestDTO;
import com.api.donation_api.dto.NovoUsuarioRequestDTO;
import com.api.donation_api.model.Usuario;
import com.api.donation_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private AutenticacaoService(AuthenticationManager authenticationManager,
                                UserDetailsService userDetailsService,
                                JwtService jwtService,
                                PasswordEncoder passwordEncoder,
                                UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public String autenticar(AuthRequestDTO authRequestDTO) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDTO.getLogin(), authRequestDTO.getSenha())
            );
        } catch (AuthenticationException e) {
            throw new Exception("Login ou senha inválidos", e);
        }

        final Usuario usuario = (Usuario) userDetailsService
                .loadUserByUsername(authRequestDTO.getLogin());

        return jwtService.generateToken(usuario);
    }

    public Usuario registrar(NovoUsuarioRequestDTO novoUsuarioRequestDTO) {
        System.out.println(novoUsuarioRequestDTO);
        Usuario usuario = Usuario
                .builder()
                .login(novoUsuarioRequestDTO.getLogin())
                .senha(passwordEncoder.encode(novoUsuarioRequestDTO.getSenha()))
                .build();

        return usuarioRepository.save(usuario);
    }
}