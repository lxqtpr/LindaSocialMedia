package dev.lxqtpr.lindaSocialMedia.Domain.Country.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.CreateCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.ResponseCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.UpdateCountryDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public record CountryServiceImpl(
        CountryRepository countryRepository,
        ModelMapper mapper,
        MinioService minioService,
        ArtistRepository artistRepository
) implements CountryService {
    @Override
    public ResponseCountryDto getCountryById(Long id) {
        var country = countryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
        return mapper.map(country, ResponseCountryDto.class);
    }

    @Override
    public List<ResponseCountryDto> getAllCountries() {
        var countries = countryRepository.findAll();
        return countries.stream()
                .map(country -> mapper.map(country, ResponseCountryDto.class))
                .toList();
    }

    @Override
    public ResponseCountryDto createCountry(CreateCountryDto dto) {
        var countryEntity = mapper.map(dto, CountryEntity.class);
        var filePath = minioService.upload(dto.getImage());
        countryEntity.setArtists(new ArrayList<>());
        countryEntity.setImage(filePath);
        return mapper.map(countryRepository.save(countryEntity), ResponseCountryDto.class);
    }

    @Override
    public ResponseCountryDto updateCountry(UpdateCountryDto dto) {
        var country = mapper.map(dto, CountryEntity.class);
        if(dto.getImage() != null){
            var newFileName = minioService.upload(dto.getImage());
            var oldCountry = countryRepository
                    .findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
            minioService.deleteFile(oldCountry.getImage());
            country.setImage(newFileName);
        }
        country.setName(dto.getName());
        if(dto.getArtistsId() != null) {
            country.setArtists(artistRepository.findAllById(dto.getArtistsId()));
        }
        return mapper.map(countryRepository.save(country), ResponseCountryDto.class);
    }

    @Override
    public void deleteCountry(Long id) {
        var country = countryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found"));
        minioService.deleteFile(country.getImage());
        countryRepository.deleteById(id);
    }

    @Override
    public Iterable<ResponseArtistDto> getArtistsByCountryName(String countryName) {
        var country = countryRepository
                .findByName(countryName)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
        return country.getArtists()
                .stream()
                .map(author -> mapper.map(author, ResponseArtistDto.class))
                .toList();
    }
}
