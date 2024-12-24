package com.api.donation_api.service;

import com.api.donation_api.dto.addressRequestDTO;
import com.api.donation_api.dto.PessoaRequestDTO;
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
public class PessoaService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;
    private final List<NewPersonValidator> validadoresNovaPessoa;
    private final AddressService addressService;


    @Autowired
    public PessoaService(PersonRepository personRepository, AddressRepository addressRepository, List<NewPersonValidator> validadoresNovaPessoa, AddressService addressService) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
        this.validadoresNovaPessoa = validadoresNovaPessoa;
        this.addressService = addressService;
    }

    public List<Person> getAllPessoas(){
        return personRepository.findAll();
    }

    public Person createPerson(@NotNull PessoaRequestDTO newPersonRequest) throws ResourceNotFoundException {
        validarNovaPessoa(newPersonRequest);

        Person.PersonBuilder personBuilder = Person.builder()
                .name(newPersonRequest.getNome())
                .cpf(newPersonRequest.getCpf())
                .telephone(newPersonRequest.getTelefone())
                .dateOfBirth(newPersonRequest.getDataNascimento());

        if (newPersonRequest.getAddress() != null) {
            addressRequestDTO addressRequestDTO = newPersonRequest.getAddress();
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

    public Person atualizarPessoa(Long id, PessoaRequestDTO pessoaRequestDTO) throws ResourceNotFoundException {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada com o ID: " + id));

        CustomBeanUtils.copyNonNullProperties(pessoaRequestDTO, person);
        return personRepository.save(person);
    }

    public List<Person> getPessoasByEndereco(Long idEndereco) throws ResourceNotFoundException {
        Optional<Address> enderecoOptional = addressRepository.findById(idEndereco);
        if (enderecoOptional.isPresent()){
            Address address = enderecoOptional.get();
            return new ArrayList<>(address.getPeople());
        }

        throw new ResourceNotFoundException("Endereço não encontrado com o id " + idEndereco);
    }

    public Person getPessoaById(Long pessoaId) throws ResourceNotFoundException {
        return personRepository.findById(pessoaId)
                .orElseThrow(()->new ResourceNotFoundException("Pessoa não encontrada."));
    }

    public Person obterOuCriar(PessoaRequestDTO pessoaRequestDTO) throws ResourceNotFoundException {
        if (pessoaRequestDTO.getId() != null){
            return getPessoaById(pessoaRequestDTO.getId());
        }
        return createPerson(pessoaRequestDTO);
    }

    public void atualizarEnderecoPessoas(Set<Person> people, Address address){
        for(Person person : people){
            person.setAddress(address);
            personRepository.save(person);
        }
    }

    public void atualizarFamiliaPessoas(Set<Person> people, Family familia){
        for(Person person : people){
            person.setFamily(familia);
            personRepository.saveAndFlush(person);
        }
    }

    public void validarNovaPessoa(@NotNull PessoaRequestDTO pessoaRequestDTO){
        validadoresNovaPessoa.forEach(validador -> validador.validate(pessoaRequestDTO));
    }
}
