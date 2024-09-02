package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaSpecificationRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);
}
