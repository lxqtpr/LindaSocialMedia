package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistController;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.ResponseArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Service.ArtistServiceImpl;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Service.PictureService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class ArtistServiceTest {
    // INITIALIZATION PART
    TestObject testObject;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    PictureService pictureService;
    private ArtistServiceImpl artistService;
    MinioService mockMinioService = Mockito.mock(MinioService.class);
    // INITIALIZATION PART
    @BeforeEach
    public void beforeEach(){
        testObject = new TestObject();
        artistService = new ArtistServiceImpl(
                        testObject.getModelMapper(),
                        artistRepository,
                        mockMinioService,
                        countryRepository,
                        pictureRepository,
                        pictureService);
    }

    @Test
    public void testCreate(){
        when(mockMinioService.upload(any(MultipartFile.class)))
                .thenReturn(
                        testObject.getArtistJoeCreateDto().getPortrait().getName()
                );
        countryRepository.save(testObject.getWales());
        Long id = artistService.createArtist(testObject.getArtistJoeCreateDto()).getId();
        ArtistEntity savedArtist = artistRepository.findById(id).get();
        Assertions.assertThat(savedArtist.getFirstName()).isEqualTo(testObject.getArtistJoe().getFirstName());
        Assertions.assertThat(savedArtist.getPortrait()).isEqualTo(testObject.getArtistJoe().getPortrait());
        Assertions.assertThat(savedArtist.getLastName()).isEqualTo(testObject.getArtistJoe().getLastName());
        Assertions.assertThat(savedArtist.getId()).isEqualTo(id);
        Assertions.assertThat(savedArtist.getPictures()).isEqualTo(null);


    }

    @Test
    public void testGetId(){
        countryRepository.save(testObject.getWales());
        Long id = artistRepository.save(testObject.getArtistJoe()).getId();
        ResponseArtistDto getSavedArtist = artistService.getArtistById(id);
        assert getSavedArtist != null;
        Assertions.assertThat(getSavedArtist.getFirstName()).isEqualTo(testObject.getArtistJoe().getFirstName());
        Assertions.assertThat(getSavedArtist.getPortrait()).isEqualTo(testObject.getArtistJoe().getPortrait());
        Assertions.assertThat(getSavedArtist.getLastName()).isEqualTo(testObject.getArtistJoe().getLastName());
        Assertions.assertThat(getSavedArtist.getId()).isEqualTo(id);
        Assertions.assertThat(getSavedArtist.getPictures()).isEqualTo(testObject.getArtistJoe().getPictures());

    }

    @Test
    public void testDeleteId(){
        countryRepository.save(testObject.getWales());
        Long id = artistRepository.save(testObject.getArtistJoe()).getId();

        artistService.deleteArtist(id);

        Assertions.assertThat(artistRepository.findById(id).orElse(null)).isEqualTo(null);
    }
}
