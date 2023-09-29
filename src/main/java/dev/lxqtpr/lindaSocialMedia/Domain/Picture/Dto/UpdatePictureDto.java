package dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdatePictureDto {
    @NotNull
    Long id;

    Long artistId;

    @NotNull
    String name;

    @NotNull
    MultipartFile image;

    @NotNull
    Integer createdAtAge;
}
