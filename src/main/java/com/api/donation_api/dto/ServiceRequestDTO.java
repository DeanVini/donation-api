package com.api.donation_api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;

    private String type;

    private String description;

    private Boolean available;

    public String getDescription(){
        return description != null ? description : "";
    }
}
