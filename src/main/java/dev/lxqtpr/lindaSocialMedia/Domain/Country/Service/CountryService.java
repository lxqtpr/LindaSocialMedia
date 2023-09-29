package dev.lxqtpr.lindaSocialMedia.Domain.Country.Service;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.CreateCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.ResponseCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.UpdateCountryDto;
import java.util.List;
public interface CountryService {
    ResponseCountryDto getCountryById(Long id);
    List<ResponseCountryDto> getAllCountries();

    ResponseCountryDto createCountry(CreateCountryDto dto);

    ResponseCountryDto updateCountry(UpdateCountryDto dto);

    void deleteCountry(Long id);

    Iterable<ResponseArtistDto> getArtistsByCountryName(String countryName);
}
