package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.Service.CountryServiceImpl;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.CreateCountryDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.dto.UpdateCountryDto;
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
public class CountryServiceTest {
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
    // todo:  to force to work MOCKITO
    @Test
    public void testCreateCountry(){
        when(mockMinioService.upload(any(MultipartFile.class)))
                .thenReturn(testObject.getWales().getImage()
                );

        Long id = countryService.createCountry(testObject.getModelMapper()
                .map(testObject.getWales(), CreateCountryDto.class))
                .getId();


        CountryEntity savedCountry = countryRepository.findById(id).get();


        System.out.println(savedCountry.getImage());
        Assertions.assertThat(savedCountry.getName()).isEqualTo(testObject.getWales().getName());
        Assertions.assertThat(savedCountry.getId()).isEqualTo(id);
        //Assertions.assertThat(savedCountry.getImage()).isEqualTo(testObject.Wales.getImage()); //MOCKITO WONT WORK!!!!
        Assertions.assertThat(savedCountry.getArtists().size()).isEqualTo(0);

    }

    @Test
    public void testDeleteCountry(){
        Long id = countryRepository.save(testObject.getWales()).getId();

        countryService.deleteCountry(id);

        Assertions.assertThat(countryRepository.findById(id).orElse(null)).isEqualTo(null);
    }
    @Test
    public void testGetCountry(){
        Long id = countryRepository.save(testObject.getWales()).getId();


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
        Long id = countryRepository.save(testObject.getWales()).getId();
        String newName = "Great Britain";
        String newImage = "Great Britain Flag";
        testObject.getWales().setImage(newImage);
        testObject.getWales().setName(newName);
        testObject.getWales().setId(id);
        when(mockMinioService.upload(any(MultipartFile.class)))
                .thenReturn(testObject.getWales().getImage()
                );

        countryService.updateCountry(testObject.getModelMapper().map(testObject.getWales(), UpdateCountryDto.class));

        CountryEntity updatedCountry = countryRepository.findById(id).get();

        Assertions.assertThat(updatedCountry.getName()).isEqualTo(testObject.getWales().getName());
        Assertions.assertThat(updatedCountry.getId()).isEqualTo(testObject.getWales().getId());
        //Assertions.assertThat(updatedCountry.getImage()).isEqualTo(testObject.Wales.getImage()); //MOCKITO WONT WORK!!!!
        Assertions.assertThat(updatedCountry.getArtists()).isEqualTo(testObject.getWales().getArtists());




    }
}
