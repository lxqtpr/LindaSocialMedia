package dev.lxqtpr.lindaSocialMedia.Domain.Country.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateCountryDto {
    @NotBlank
    String name;

    @NotNull
    MultipartFile image;
}
