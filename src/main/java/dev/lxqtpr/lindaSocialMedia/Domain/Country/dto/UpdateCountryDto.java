package dev.lxqtpr.lindaSocialMedia.Domain.Country.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class UpdateCountryDto {

    @NotNull
    Long id;

    @NotBlank
    String name;

    MultipartFile image;

    List<Long> artistsId;
}
