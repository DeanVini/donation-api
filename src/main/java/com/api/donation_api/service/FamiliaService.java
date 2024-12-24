package com.api.donation_api.service;

import com.api.donation_api.dto.addressRequestDTO;
import com.api.donation_api.dto.FamiliaRequestDTO;
import com.api.donation_api.dto.PessoaRequestDTO;
import com.api.donation_api.dto.ServicoRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Address;
import com.api.donation_api.model.Family;
import com.api.donation_api.model.Person;
import com.api.donation_api.model.Service;
import com.api.donation_api.repository.FamilyRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class FamiliaService {
    private final FamilyRepository familyRepository;
    private final AddressService addressService;
    private final PessoaService pessoaService;
    private final ServicoService servicoService;

    public FamiliaService(FamilyRepository familyRepository, AddressService addressService, PessoaService pessoaService, ServicoService servicoService) {
        this.familyRepository = familyRepository;
        this.addressService = addressService;
        this.pessoaService = pessoaService;
        this.servicoService = servicoService;
    }

    public List<Family> getAllFamilias(){
        return familyRepository.findAll();
    }

    public Family getFamiliaById(Long id) throws ResourceNotFoundException {
        return familyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Familia não encontrada"));
    }

    public Family cadatrarFamilia(FamiliaRequestDTO novaFamiliaDTO) {
        Family.FamilyBuilder familyBuilder = Family.builder()
                .name(novaFamiliaDTO.getNome());

        Address addressFamilia = null;
        Person liderFamilia = null;
        Set<Person> pessoasFamilia = new HashSet<>();

        if (novaFamiliaDTO.getAddress() != null){
            addressFamilia = processarEnderecoFamilia(novaFamiliaDTO.getAddress());
            familyBuilder.address(addressFamilia);
        }

        if (novaFamiliaDTO.getLider() != null){
            liderFamilia = processarLiderFamilia(novaFamiliaDTO.getLider());
            familyBuilder.leader(liderFamilia);
        }

        if (novaFamiliaDTO.getPessoas() != null){
            pessoasFamilia = processarPessoasFamilia(novaFamiliaDTO.getPessoas(), liderFamilia, addressFamilia);
            familyBuilder.members(pessoasFamilia);
        }

        Family family = familyBuilder.members(pessoasFamilia).build();
        Family savedFamily = familyRepository.save(family);
        pessoaService.atualizarFamiliaPessoas(pessoasFamilia, savedFamily);
        return savedFamily;
    }

    public Family adicionarPessoaFamilia(Long idFamilia , List<PessoaRequestDTO> pessoasDto) throws ResourceNotFoundException {
        Family familia = getFamiliaById(idFamilia);
        Set<Person> personParaAtualizar = processarPessoasFamilia(pessoasDto, null, familia.getAddress());

        for (Person person : personParaAtualizar) {
            person.setFamily(familia);
        }

        pessoaService.atualizarFamiliaPessoas(personParaAtualizar, familia);

        return familyRepository.save(familia);
    }

    private Address processarEnderecoFamilia(addressRequestDTO addressRequestDTO){
        try {
            return addressService.getOrCreateAddress(addressRequestDTO);

        }catch (ResourceNotFoundException e) {
            throw new RuntimeException("Erro ao obter ou criar o endereço da família.", e);
        }
    }

    public Family vincularServicoFamilia(Long idFamilia, ServicoRequestDTO servicoRequestDTO) throws ResourceNotFoundException {
        Family familia = getFamiliaById(idFamilia);

        if (servicoRequestDTO.getId() == null){
            Service serviceCadastrado = servicoService.cadastarServico(servicoRequestDTO);
            familia.getServices().add(serviceCadastrado);
            return familyRepository.save(familia);
        }

        Service service = servicoService.getById(servicoRequestDTO.getId());
        familia.getServices().add(service);
        return familyRepository.save(familia);
    }

    private Person processarLiderFamilia(PessoaRequestDTO liderDto) {
        try {
            return pessoaService.obterOuCriar(liderDto);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Erro ao obter ou criar o líder da família.", e);
        }
    }

    private Set<Person> processarPessoasFamilia(List<PessoaRequestDTO> dtosPessoas, Person lider, Address address) {
        Set<Person> pessoasFamilia = dtosPessoas.stream()
                .map(pessoaDto -> {
                    try {
                        return pessoaService.obterOuCriar(pessoaDto);
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException("Erro ao obter ou criar uma pessoa da família.", e);
                    }
                })
                .collect(Collectors.toSet());
        if (lider != null) {
            pessoasFamilia.add(lider);
        }
        pessoaService.atualizarEnderecoPessoas(pessoasFamilia, address);
        return pessoasFamilia;
    }
}
