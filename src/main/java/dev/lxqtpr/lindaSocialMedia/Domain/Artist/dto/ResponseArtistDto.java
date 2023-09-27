package dev.lxqtpr.lindaSocialMedia.Domain.Artist.dto;

import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.ArrayList;

@Data
public class ResponseArtistDto {
    @NotBlank
    String fullName;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotBlank
    String birthDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotBlank
    String deathDate;

    @NotBlank
    CountryEntity Country;

    @NotEmpty
    String portrait;

    @NotEmpty
    ArrayList<PictureEntity> pictures;
}
