package com.api.donation_api.controller;

import com.api.donation_api.dto.PersonRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Person;
import com.api.donation_api.service.PersonService;
import com.api.donation_api.utils.ResponseConstructorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAllPeople(){
        List<Person> people = personService.getAllPessoas();
        return ResponseConstructorUtils.okResponse(people);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createPerson(@RequestBody PersonRequestDTO newPersonDTO) throws ResourceNotFoundException {
        Person personCadastrada = personService.createPerson(newPersonDTO);
        return ResponseConstructorUtils.createdResponse(personCadastrada);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Object> getPersonById(@PathVariable Long personId) throws ResourceNotFoundException {
        Person person = personService.getPersonById(personId);
        return ResponseConstructorUtils.okResponse(person);
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<Object> getPeopleByAddress(@PathVariable Long addressId) throws ResourceNotFoundException {
        List<Person> people = personService.getPeopleByAddress(addressId);
        return ResponseConstructorUtils.okResponse(people);
    }

}
