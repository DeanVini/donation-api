package com.api.donation_api.service;

import com.api.donation_api.dto.AddressRequestDTO;
import com.api.donation_api.dto.FamilyRequestDTO;
import com.api.donation_api.dto.PersonRequestDTO;
import com.api.donation_api.dto.ServiceRequestDTO;
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
public class FamilyService {
    private final FamilyRepository familyRepository;
    private final AddressService addressService;
    private final PersonService personService;
    private final ServicoService servicoService;

    public FamilyService(FamilyRepository familyRepository, AddressService addressService, PersonService personService, ServicoService servicoService) {
        this.familyRepository = familyRepository;
        this.addressService = addressService;
        this.personService = personService;
        this.servicoService = servicoService;
    }

    public List<Family> getAllFamilies(){
        return familyRepository.findAll();
    }

    public Family getFamilyById(Long id) throws ResourceNotFoundException {
        return familyRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Familia não encontrada"));
    }

    public Family createFamily(FamilyRequestDTO newFamilyDTO) {
        Family.FamilyBuilder familyBuilder = Family.builder()
                .name(newFamilyDTO.getName());

        Address addressFamilia = null;
        Person familyLeader = null;
        Set<Person> familyPeople = new HashSet<>();

        if (newFamilyDTO.getAddress() != null){
            addressFamilia = processFamilyAddress(newFamilyDTO.getAddress());
            familyBuilder.address(addressFamilia);
        }

        if (newFamilyDTO.getLeader() != null){
            familyLeader = processFamilyLeader(newFamilyDTO.getLeader());
            familyBuilder.leader(familyLeader);
        }

        if (newFamilyDTO.getPeople() != null){
            familyPeople = processFamilyMembers(newFamilyDTO.getPeople(), familyLeader, addressFamilia);
            familyBuilder.members(familyPeople);
        }

        Family family = familyBuilder.members(familyPeople).build();
        Family savedFamily = familyRepository.save(family);
        personService.updatePeopleFamily(familyPeople, savedFamily);
        return savedFamily;
    }

    public Family putPersonFamily(Long familyId , List<PersonRequestDTO> personDTO) throws ResourceNotFoundException {
        Family family = getFamilyById(familyId);
        Set<Person> peopleToUpdate = processFamilyMembers(personDTO, null, family.getAddress());

        for (Person person : peopleToUpdate) {
            person.setFamily(family);
        }

        personService.updatePeopleFamily(peopleToUpdate, family);

        return familyRepository.save(family);
    }

    private Address processFamilyAddress(AddressRequestDTO addressRequestDTO){
        try {
            return addressService.getOrCreateAddress(addressRequestDTO);

        }catch (ResourceNotFoundException e) {
            throw new RuntimeException("Erro ao obter ou criar o endereço da família.", e);
        }
    }

    public Family linkServiceToFamily(Long familyId, ServiceRequestDTO serviceRequestDTO) throws ResourceNotFoundException {
        Family family = getFamilyById(familyId);

        if (serviceRequestDTO.getId() == null){
            Service createdService = servicoService.cadastarServico(serviceRequestDTO);
            family.getServices().add(createdService);
            return familyRepository.save(family);
        }

        Service service = servicoService.getById(serviceRequestDTO.getId());
        family.getServices().add(service);
        return familyRepository.save(family);
    }

    private Person processFamilyLeader(PersonRequestDTO leaderDTO) {
        try {
            return personService.getOrCreate(leaderDTO);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException("Erro ao obter ou criar o líder da família.", e);
        }
    }

    private Set<Person> processFamilyMembers(List<PersonRequestDTO> peopleDTO, Person leader, Address address) {
        Set<Person> familyPeople = peopleDTO.stream()
                .map(personDTO -> {
                    try {
                        return personService.getOrCreate(personDTO);
                    } catch (ResourceNotFoundException e) {
                        throw new RuntimeException("Erro ao obter ou criar uma pessoa da família.", e);
                    }
                })
                .collect(Collectors.toSet());
        if (leader != null) {
            familyPeople.add(leader);
        }
        personService.updatePeopleAddress(familyPeople, address);
        return familyPeople;
    }
}
