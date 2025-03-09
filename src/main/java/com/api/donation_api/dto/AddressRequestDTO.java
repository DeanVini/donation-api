package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AddressRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String street;

    private String postalCode;

    private String neighborhood;

    private String city;

    private String state;

    private String additionalInfo;

    private double latitude;

    private double longitude;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer number;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PersonRequestDTO> people;
}
