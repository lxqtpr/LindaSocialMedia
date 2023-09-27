package dev.lxqtpr.lindaSocialMedia.Domain.Country.dto;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ResponseCountryDto {
    @NotBlank
    String name;

    @NotBlank
    String image;

    @NotBlank
    ArrayList<ArtistEntity> authors;
}
