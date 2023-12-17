package dev.lxqtpr.lindaSocialMedia.Domain.Artist.Service;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.UpdateArtistDto;

import java.util.List;

public interface ArtistService {
    ResponseArtistDto getArtistById(Long id);
    List<ResponseArtistDto> getAllArtist();
    ResponseArtistDto createArtist(CreateArtistDto dto);

    ResponseArtistDto updateArtist(UpdateArtistDto dto);
    void deleteArtist(Long id);
}
