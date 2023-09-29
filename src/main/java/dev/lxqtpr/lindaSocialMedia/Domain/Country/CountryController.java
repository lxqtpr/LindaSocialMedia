package dev.lxqtpr.lindaSocialMedia.Domain.Country;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.Service.CountryService;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.CreateCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.ResponseCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.UpdateCountryDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/countries")
public record CountryController(
        CountryService countryService
) {

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCountryDto> getCountryById(@PathVariable Long id){
        return ResponseEntity.ok(countryService.getCountryById(id));
    }
    @GetMapping
    public ResponseEntity<Iterable<ResponseCountryDto>> getAllCountries(){
        return ResponseEntity.ok(countryService.getAllCountries());
    }
    @GetMapping("/artists/{countryName}")
    public ResponseEntity<Iterable<ResponseArtistDto>> getArtistsByCountryName(
            @PathVariable String countryName
    ){
        return ResponseEntity.ok(countryService.getArtistsByCountryName(countryName));
    }
    @PostMapping
    public ResponseEntity<ResponseCountryDto> createCountry(
            @Valid @ModelAttribute CreateCountryDto dto
    ){
        return new ResponseEntity<>(
                countryService.createCountry(dto),
                HttpStatus.CREATED
        );
    }
    @PutMapping
    public ResponseEntity<ResponseCountryDto> updateCountry(
            @Valid @ModelAttribute UpdateCountryDto dto
    ){
        return ResponseEntity.ok(
                countryService.updateCountry(dto)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id){
       countryService.deleteCountry(id);
        return ResponseEntity.ok("Country deleted");
    }
}
