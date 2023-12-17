package dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ResponsePictureWithoutArtist {
    @NotBlank
    Long id;

    @NotBlank
    String image;

    @NotBlank
    String name;

    @NotNull
    Integer createdAtAge;
}
