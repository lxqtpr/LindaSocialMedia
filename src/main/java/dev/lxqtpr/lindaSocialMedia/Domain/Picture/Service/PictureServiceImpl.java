package dev.lxqtpr.lindaSocialMedia.Domain.Picture.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.ResponseCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.CreatePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.ResponsePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.ResponsePictureWithoutArtist;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.UpdatePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record PictureServiceImpl(
        MinioService minioService,
        PictureRepository pictureRepository,
        ModelMapper mapper,
        ArtistRepository artistRepository
) implements PictureService {
    @Override
    public ResponsePictureDto createPicture(CreatePictureDto dto) {
        var artist = artistRepository
                .findById(dto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        var fileName = minioService.upload(dto.getImage());
        var picture = mapper.map(dto, PictureEntity.class);
        picture.setArtist(artist);
        picture.setImage(fileName);
        return mapper.map(
                pictureRepository.save(picture),
                ResponsePictureDto.class
        );
    }

    @Override
    public ResponsePictureDto getPictureById(Long id) {
        var picture = pictureRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Picture not found"));
        return mapper.map(picture, ResponsePictureDto.class);
    }
    @Override
    public void deletePicture(Long id) {
        var picture = pictureRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Picture not found"));
        minioService.deleteFile(picture.getImage());
        pictureRepository.deleteById(id);
    }

    @Override
    public ResponsePictureDto updatePicture(UpdatePictureDto dto) {
        var picture = mapper.map(dto, PictureEntity.class);
        if (dto.getImage() != null){
            var fileName = minioService.upload(dto.getImage());
            var oldPicture = pictureRepository
                    .findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Picture not found"));
            minioService.deleteFile(oldPicture.getImage());
            picture.setImage(fileName);
        }
        picture.setName(dto.getName());
        if(dto.getArtistId() != null ){
            var artist = artistRepository
                    .findById(dto.getArtistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
            picture.setArtist(artist);
        }
        return mapper.map(pictureRepository.save(picture), ResponsePictureDto.class);
    }
}
