package dev.lxqtpr.lindaSocialMedia.Domain.Picture.Service;

import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.CreatePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.ResponsePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.ResponsePictureWithoutArtist;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.UpdatePictureDto;

import java.util.List;

public interface PictureService {
    ResponsePictureDto createPicture(CreatePictureDto dto);
    ResponsePictureDto getPictureById(Long id);
    void deletePicture(Long id);
    ResponsePictureDto updatePicture(UpdatePictureDto dto);
}
