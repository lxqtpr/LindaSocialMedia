package dev.lxqtpr.lindaSocialMedia.Domain.Country;

import dev.lxqtpr.lindaSocialMedia.Domain.Country.Service.CountryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public record CountryController(
        CountryService countryService
) {
}
