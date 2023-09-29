package dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResponsePictureDto {
    @NotBlank
    Long id;

    @NotBlank
    String image;

    @NotBlank
    String name;

    @NotBlank
    ResponsePictureArtistDto artist;

    @NotNull
    Integer createdAtAge;
}
