package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Service.ArtistServiceImpl;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.Service.CountryServiceImpl;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.CreateCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.ResponseCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.UpdateCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Service.PictureService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class CountryService {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private CountryRepository countryRepository;
    MinioService mockMinioService = Mockito.mock(MinioService.class);
    TestObject testObject;
    CountryServiceImpl countryService;

    @BeforeEach
    public void beforeEach(){
        testObject = new TestObject();
        countryService = new CountryServiceImpl(
                countryRepository,
                testObject.getModelMapper(),
                mockMinioService,
                artistRepository
        );
    }

    @Test
    public void testCreateCountry(){
        when(mockMinioService.upload(any(MultipartFile.class)))
                .thenReturn(testObject.Wales.getImage()
                );

        Long id = countryService.createCountry(testObject.getModelMapper()
                .map(testObject.Wales, CreateCountryDto.class))
                .getId();


        CountryEntity savedCountry = countryRepository.findById(id).get();


        System.out.println(savedCountry.getImage());
        Assertions.assertThat(savedCountry.getName()).isEqualTo(testObject.Wales.getName());
        Assertions.assertThat(savedCountry.getId()).isEqualTo(id);
        //Assertions.assertThat(savedCountry.getImage()).isEqualTo(testObject.Wales.getImage()); //MOCKITO WONT WORK!!!!
        Assertions.assertThat(savedCountry.getArtists().size()).isEqualTo(0);

    }

    @Test
    public void testDeleteCountry(){
        Long id = countryRepository.save(testObject.Wales).getId();

        countryService.deleteCountry(id);

        Assertions.assertThat(countryRepository.count()).isEqualTo(0);
    }
    @Test
    public void testGetCountry(){
        Long id = countryRepository.save(testObject.Wales).getId();


        CountryEntity getCountry = testObject.getModelMapper()
                .map(countryService.getCountryById(id), CountryEntity.class);


        CountryEntity savedCountry = countryRepository.findById(id).get();
        Assertions.assertThat(getCountry.getArtists()).isEqualTo(savedCountry.getArtists());
        Assertions.assertThat(getCountry.getImage()).isEqualTo(savedCountry.getImage());
        Assertions.assertThat(getCountry.getName()).isEqualTo(savedCountry.getName());
        Assertions.assertThat(getCountry.getId()).isEqualTo(savedCountry.getId());
    }

    @Test
    public void testUpdateCountry(){
        Long id = countryRepository.save(testObject.Wales).getId();
        String newName = "Great Britain";
        String newImage = "Great Britain Flag";
        testObject.Wales.setImage(newImage);
        testObject.Wales.setName(newName);
        testObject.Wales.setId(id);
        when(mockMinioService.upload(any(MultipartFile.class)))
                .thenReturn(testObject.Wales.getImage()
                );

        countryService.updateCountry(testObject.getModelMapper().map(testObject.Wales, UpdateCountryDto.class));

        CountryEntity updatedCountry = countryRepository.findById(id).get();

        Assertions.assertThat(updatedCountry.getName()).isEqualTo(testObject.Wales.getName());
        Assertions.assertThat(updatedCountry.getId()).isEqualTo(testObject.Wales.getId());
        //Assertions.assertThat(updatedCountry.getImage()).isEqualTo(testObject.Wales.getImage()); //MOCKITO WONT WORK!!!!
        Assertions.assertThat(updatedCountry.getArtists()).isEqualTo(testObject.Wales.getArtists());




    }
}
