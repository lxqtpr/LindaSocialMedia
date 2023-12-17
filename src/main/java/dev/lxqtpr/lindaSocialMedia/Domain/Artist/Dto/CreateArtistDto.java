package dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Data
public class CreateArtistDto {
    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    Long countryId;

    MultipartFile portrait;

    List<Long> picturesId;
}
