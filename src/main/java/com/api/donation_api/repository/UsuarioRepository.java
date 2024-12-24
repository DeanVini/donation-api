package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.User;

import java.util.Optional;

public interface UsuarioRepository extends JpaSpecificationRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Boolean existsByCpf(String cpf);
    Boolean existsByEmail(String email);
}
