package dev.lxqtpr.lindaSocialMedia.Domain.Artist.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.UpdateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.ResponseCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ArtistServiceImpl(
        ModelMapper mapper,
        ArtistRepository artistRepository,
        MinioService fileService,
        CountryRepository countryRepository,
        PictureRepository pictureRepository,
        PictureService pictureService
) implements ArtistService {

    @Override
    public ResponseArtistDto getArtistById(Long id) {
        var artist = artistRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found."));
        return mapper.map(artist, ResponseArtistDto.class);
    }

    @Override
    public List<ResponseArtistDto> getAllArtist() {
        return artistRepository
                .findAll()
                .stream().map(artis -> mapper.map(artis,ResponseArtistDto.class))
                .toList();
    }

    @Override
    public ResponseArtistDto createArtist(CreateArtistDto dto) {
        var country = countryRepository
                .findById(dto.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));
        var artist = mapper.map(dto, ArtistEntity.class);
        if (dto.getPicturesId() != null){
            artist.setPictures(pictureRepository.findAllById(dto.getPicturesId()));
        }
        var fileName = fileService.upload(dto.getPortrait());
        artist.setCountry(country);
        artist.setPortrait(fileName);
        artistRepository.save(artist);
        var res = mapper.map(artist, ResponseArtistDto.class);
        res.setCountry(mapper.map(country, ResponseCountryDto.class));
        return res;
    }

    @Override
    public ResponseArtistDto updateArtist(UpdateArtistDto dto) {
        var artist = mapper.map(dto, ArtistEntity.class);
        if (dto.getPortrait() != null){
            var oldArtist = artistRepository.findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
            fileService.deleteFile(oldArtist.getPortrait());
            var portraitFileName = fileService.upload(dto.getPortrait());
            artist.setPortrait(portraitFileName);
        }
        return mapper.map(artistRepository.save(artist), ResponseArtistDto.class);
    }

    @Override
    public void deleteArtist(Long id) {
        var artist = artistRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Artist not found"));
        artist.getPictures().forEach(picture -> pictureService.deletePicture(picture.getId()));
        artistRepository.deleteById(id);
    }
}
