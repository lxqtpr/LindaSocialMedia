package dev.lxqtpr.lindaSocialMedia.Domain.Artist.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.File.Service.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public record ArtistServiceImpl(
        ModelMapper mapper,
        ArtistRepository artistRepository,
        FileService fileService
) implements ArtistService {

    @Override
    public ArtistEntity getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Artist not found."));
    }

    @Override
    public Iterable<ArtistEntity> getAllArtist() {
        return artistRepository.findAll();
    }

    @Override
    public ResponseArtistDto createArtist(CreateArtistDto dto, CountryEntity country) {
        var artist = mapper.map(dto, ArtistEntity.class);

        return mapper.map(artistRepository.save(artist), ResponseArtistDto.class);
    }


}
