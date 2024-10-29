package com.api.donation_api.repository;

import com.api.donation_api.interfaces.JpaSpecificationRepository;
import com.api.donation_api.model.Endereco;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaSpecificationRepository<Endereco, Long> {
    public List<Endereco> findAllByCep(String cep);

    public Boolean existsByCep(String cep);
}
