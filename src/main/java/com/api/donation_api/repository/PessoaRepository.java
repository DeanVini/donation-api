package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Pessoa;

import java.util.Optional;

public interface PessoaRepository extends JpaSpecificationRepository<Pessoa, Long> {
    Optional<Pessoa> findByCpf(String cpf);
    Optional<Pessoa> findById(Long id);
}
