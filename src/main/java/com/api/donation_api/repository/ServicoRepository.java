package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Servico;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface ServicoRepository extends JpaSpecificationRepository<Servico, Long> {
    @Override
    @NotNull
    Optional<Servico> findById(@NotNull Long id);

    List<Servico> findAllByDisponivelTrue();
}
