package dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto;

import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.ResponseCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.ResponsePictureWithoutArtist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.*;

@Data
public class ResponseArtistDto {
    @NotBlank
    Long id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    ResponseCountryDto country;

    @NotEmpty
    String portrait;

    @NotEmpty
    List<ResponsePictureWithoutArtist> pictures;
}
