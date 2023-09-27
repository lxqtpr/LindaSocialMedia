package dev.lxqtpr.lindaSocialMedia.Domain.Country.Service;

import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;

public interface CountryService {
    CountryEntity getCountryById(Long id);
    Iterable<CountryEntity> getAllCountries();

}
