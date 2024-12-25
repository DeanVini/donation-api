package com.api.donation_api.service;

import com.api.donation_api.dto.AddressRequestDTO;
import com.api.donation_api.dto.PersonRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Address;
import com.api.donation_api.model.Family;
import com.api.donation_api.model.Person;
import com.api.donation_api.repository.AddressRepository;
import com.api.donation_api.repository.PersonRepository;
import com.api.donation_api.utils.CustomBeanUtils;
import com.api.donation_api.validations.NewPersonValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final List<NewPersonValidator> newPersonValidator;
    private final AddressService addressService;


    @Autowired
    public PersonService(PersonRepository personRepository, AddressRepository addressRepository, List<NewPersonValidator> newPersonValidator, AddressService addressService) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.newPersonValidator = newPersonValidator;
        this.addressService = addressService;
    }

    public List<Person> getAllPessoas(){
        return personRepository.findAll();
    }

    public Person createPerson(@NotNull PersonRequestDTO newPersonRequest) throws ResourceNotFoundException {
        validateNewPerson(newPersonRequest);

        Person.PersonBuilder personBuilder = Person.builder()
                .name(newPersonRequest.getName())
                .cpf(newPersonRequest.getCpf())
                .telephone(newPersonRequest.getTelephone())
                .dateOfBirth(newPersonRequest.getDateOfBirth());

        if (newPersonRequest.getAddress() != null) {
            AddressRequestDTO addressRequestDTO = newPersonRequest.getAddress();
            if (addressRequestDTO.getId() != null){
                Address address = addressService.getAddressById(addressRequestDTO.getId());
                personBuilder.address(address);
            }
            else{
                Address novoAddress = addressService.createAddress(addressRequestDTO);
                personBuilder.address(novoAddress);
            }
        }

        Person person = personBuilder.build();

        return personRepository.save(person);
    }

    public Person updatePerson(Long id, PersonRequestDTO personRequestDTO) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id));

        CustomBeanUtils.copyNonNullProperties(personRequestDTO, person);
        return personRepository.save(person);
    }

    public List<Person> getPeopleByAddress(Long addressId) throws ResourceNotFoundException {
        Optional<Address> enderecoOptional = addressRepository.findById(addressId);
        if (enderecoOptional.isPresent()){
            Address address = enderecoOptional.get();
            return new ArrayList<>(address.getPeople());
        }

        throw new ResourceNotFoundException("Endereço não encontrado com o id " + addressId);
    }

    public Person getPersonById(Long pessoaId) throws ResourceNotFoundException {
        return personRepository.findById(pessoaId)
                .orElseThrow(()->new ResourceNotFoundException("Pessoa não encontrada."));
    }

    public Person getOrCreate(PersonRequestDTO personRequestDTO) throws ResourceNotFoundException {
        if (personRequestDTO.getId() != null){
            return getPersonById(personRequestDTO.getId());
        }
        return createPerson(personRequestDTO);
    }

    public void updatePeopleAddress(Set<Person> people, Address address){
        for(Person person : people){
            person.setAddress(address);
            personRepository.save(person);
        }
    }

    public void updatePeopleFamily(Set<Person> people, Family family){
        for(Person person : people){
            person.setFamily(family);
            personRepository.saveAndFlush(person);
        }
    }

    public void validateNewPerson(@NotNull PersonRequestDTO personRequestDTO){
        newPersonValidator.forEach(validator -> validator.validate(personRequestDTO));
    }
}
