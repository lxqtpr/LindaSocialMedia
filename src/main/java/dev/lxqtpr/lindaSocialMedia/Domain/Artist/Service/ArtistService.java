package dev.lxqtpr.lindaSocialMedia.Domain.Artist.Service;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;

public interface ArtistService {
    ArtistEntity getArtistById(Long id);
    Iterable<ArtistEntity> getAllArtist();
    ResponseArtistDto createArtist(CreateArtistDto dto, CountryEntity country);
}
