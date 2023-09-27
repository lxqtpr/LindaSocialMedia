package dev.lxqtpr.lindaSocialMedia.Domain.Artist;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Service.ArtistService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artists")
public record ArtistController(
        ArtistService artistService
) {

    @GetMapping("/{id}")
    public ResponseEntity<ArtistEntity> getArtistById(@Valid @PathVariable Long id){
        return ResponseEntity.
                ok(artistService.getArtistById(id));
    }
    @GetMapping()
    public ResponseEntity<Iterable<ArtistEntity>> getAllArtists(){
        return ResponseEntity
                .ok(artistService.getAllArtist());
    }
    @PostMapping
    public ResponseEntity<ResponseArtistDto> createArtist(
            @Valid @RequestBody CreateArtistDto dto,
            @Valid @RequestBody CountryEntity country
    ){
        return new ResponseEntity<>(
                artistService.createArtist(dto, country),
                HttpStatus.CREATED
        );
    }
}
