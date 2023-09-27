package dev.lxqtpr.lindaSocialMedia.Domain.Country.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryRepository;
import org.springframework.stereotype.Service;

@Service
public record CountryServiceImpl(
        CountryRepository countryRepository
) implements CountryService {
    @Override
    public CountryEntity getCountryById(Long id) {
        return countryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country not found."));
    }

    @Override
    public Iterable<CountryEntity> getAllCountries() {
        return countryRepository.findAll();
    }
}
