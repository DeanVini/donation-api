package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Person;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface PersonRepository extends JpaSpecificationRepository<Person, Long> {
    Optional<Person> findByCpf(String cpf);

    @Override
    @NotNull
    Optional<Person> findById(@NotNull Long id);

    Boolean existsByCpf(@NotNull String cpf);
}
