package dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class UpdateArtistDto {
    @NotNull
    Long id;

    @NotNull
    String firstName;

    @NotNull
    String lastName;


    MultipartFile portrait;
}
