package dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePictureDto {
    @NotNull
    Long artistId;
    @NotNull
    Integer createdAtAge;
    @NotNull
    MultipartFile image;

    @NotNull
    String name;
}
