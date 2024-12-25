package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PersonRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String name;

    private String cpf;

    private String telephone;

    private LocalDate dateOfBirth;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AddressRequestDTO address;
}