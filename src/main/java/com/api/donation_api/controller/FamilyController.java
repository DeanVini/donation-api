package com.api.donation_api.controller;

import com.api.donation_api.dto.FamilyRequestDTO;
import com.api.donation_api.dto.PersonRequestDTO;
import com.api.donation_api.dto.ServiceRequestDTO;
import com.api.donation_api.exception.ResourceNotFoundException;
import com.api.donation_api.model.Family;
import com.api.donation_api.service.FamilyService;
import com.api.donation_api.utils.ResponseConstructorUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/family")
public class FamilyController {
    private final FamilyService familyService;

    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("/")
    public ResponseEntity<Object> getAll(){
        List<Family> families = familyService.getAllFamilies();
        return ResponseConstructorUtils.okResponse(families);
    }

    @GetMapping("/{familyId}")
    public ResponseEntity<Object> getById(@PathVariable Long familyId) throws ResourceNotFoundException {
        Family family = familyService.getFamilyById(familyId);
        return ResponseConstructorUtils.okResponse(family);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createFamily(@RequestBody FamilyRequestDTO newFamilyDTO) {
        Family familyCreated = familyService.createFamily(newFamilyDTO);
        return ResponseConstructorUtils.createdResponse(familyCreated);
    }

    @PutMapping("/{familyId}/members")
    public ResponseEntity<Object> putPersonFamily(@PathVariable Long familyId, @RequestBody List<PersonRequestDTO> personRequestDTO) throws ResourceNotFoundException {
        Family family = familyService.putPersonFamily(familyId, personRequestDTO);
        return ResponseConstructorUtils.okResponse(family);
    }

    @PutMapping("/{familyId}/services")
    public ResponseEntity<Object> putServiceFamily(@PathVariable Long familyId, @RequestBody ServiceRequestDTO serviceRequestDTO) throws ResourceNotFoundException {
        Family family = familyService.linkServiceToFamily(familyId, serviceRequestDTO);
        return ResponseConstructorUtils.okResponse(family);
    }
}
