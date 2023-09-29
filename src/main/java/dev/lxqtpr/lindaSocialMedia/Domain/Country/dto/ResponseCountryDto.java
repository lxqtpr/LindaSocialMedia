package dev.lxqtpr.lindaSocialMedia.Domain.Country.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResponseCountryDto {
    @NotBlank
    Long id;

    @NotBlank
    String name;

    @NotBlank
    String image;
}
