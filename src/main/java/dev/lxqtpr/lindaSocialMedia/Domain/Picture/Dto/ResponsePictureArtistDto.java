package dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class ResponsePictureArtistDto {
    @NotBlank
    Long id;

    @NotBlank
    String firstName;

    @NotNull
    Integer createdAtAge;

    @NotBlank
    String lastName;
}
