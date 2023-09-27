package dev.lxqtpr.lindaSocialMedia.Domain.Country.dto;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Data
public class CreateCountryDto {
    @NotBlank
    String name;

    @NotBlank
    MultipartFile image;

    @NotBlank
    ArrayList<ArtistEntity> authors;
}
