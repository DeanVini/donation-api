package com.api.donation_api.service;

import com.api.donation_api.dto.NovaFamiliaDTO;
import com.api.donation_api.model.Familia;
import com.api.donation_api.repository.FamiliaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FamiliaService {
    private final FamiliaRepository familiaRepository;
    private final EnderecoService enderecoService;
    private final PessoaService pessoaService;

    public FamiliaService(FamiliaRepository familiaRepository, EnderecoService enderecoService, PessoaService pessoaService) {
        this.familiaRepository = familiaRepository;
        this.enderecoService = enderecoService;
        this.pessoaService = pessoaService;
    }

    public List<Familia> getAllFamilias(){
        return familiaRepository.findAll();
    }

    public Optional<Familia> getFamiliaById(Long id){
        return familiaRepository.findById(id);
    }

//    public Familia cadatrarFamilia(NovaFamiliaDTO novaFamiliaDTO){
//        Familia familia = Familia.builder()
//                .nome(novaFamiliaDTO.getNome());
//
//        if (novaFamiliaDTO.getLider()){
//            familia.lider
//        }
//
//    }
}
