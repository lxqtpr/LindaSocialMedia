package dev.lxqtpr.lindaSocialMedia.Domain.Artist;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Service.ArtistService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.ResponseArtistDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public record ArtistController(
        ArtistService artistService
) {

    @GetMapping("/{id}")
    public ResponseEntity<ResponseArtistDto> getArtistById(@Valid @PathVariable Long id){
        return ResponseEntity.
                ok(artistService.getArtistById(id));
    }
    @GetMapping()
    public ResponseEntity<List<ResponseArtistDto>> getAllArtists(){
        return ResponseEntity
                .ok(artistService.getAllArtist());
    }
    @PostMapping
    public ResponseEntity<ResponseArtistDto> createArtist(
            @Valid @ModelAttribute CreateArtistDto dto
    ){
        return new ResponseEntity<>(
                artistService.createArtist(dto),
                HttpStatus.CREATED
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Long id){
        artistService.deleteArtist(id);
        return ResponseEntity.ok("Country deleted");
    }
}
