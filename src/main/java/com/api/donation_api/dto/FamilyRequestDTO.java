package com.api.donation_api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FamilyRequestDTO {
    private String name;
    private AddressRequestDTO address;
    private PersonRequestDTO leader;
    private List<PersonRequestDTO> people;
}
