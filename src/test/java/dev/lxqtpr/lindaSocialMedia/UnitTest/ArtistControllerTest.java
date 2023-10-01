package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistController;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ArtistControllerTest {
    // INITIALIZATION PART
    TestObject testObject;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private ArtistController artistController;
    // INITIALIZATION PART
    @BeforeEach
    public void beforeEach(){
        testObject = new TestObject();
    }

    @Test
    public void testCreate(){
        countryRepository.save(testObject.Wales);
        artistController.createArtist(testObject.getArtistJoeCreateDto());

        System.out.println(artistRepository.findAll());

    }

}
