package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.CreatePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.ResponsePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.UpdatePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureRepository;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Service.PictureService;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Service.PictureServiceImpl;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@SpringBootTest
@Transactional
public class PictureServiceTest {
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    MinioService minioService;
    TestObject testObject;
    ModelMapper modelMapper;
    @Autowired
    PictureRepository pictureRepository;
    PictureServiceImpl pictureService;
    @BeforeEach
    public void BeforeEach(){
        testObject = new TestObject();
        modelMapper = testObject.getModelMapper();
        pictureService = new PictureServiceImpl(
                minioService,
                pictureRepository,
                modelMapper,
                artistRepository
        );

        countryRepository.save(testObject.getWales());
        ArtistEntity artistJoe = artistRepository.save(testObject.getArtistJoe());
        testObject.setArtistJoe(artistJoe);
    }

    @Test
    public void testGetPicture(){
        var savedEntity = pictureRepository.save(testObject.getArtistJoePainting());

        var returnedEntity = modelMapper.map(pictureService.getPictureById(savedEntity.getId()),PictureEntity.class);

        Assertions.assertThat(returnedEntity.getName()).isEqualTo(savedEntity.getName());
        Assertions.assertThat(returnedEntity.getImage()).isEqualTo(savedEntity.getImage());
        Assertions.assertThat(returnedEntity.getId()).isEqualTo(savedEntity.getId());
        Assertions.assertThat(returnedEntity.getCreatedAtAge()).isEqualTo(savedEntity.getCreatedAtAge());
        Assertions.assertThat(returnedEntity.getArtist().getFirstName()).isEqualTo(savedEntity.getArtist().getFirstName());
        Assertions.assertThat(returnedEntity.getArtist().getLastName()).isEqualTo(savedEntity.getArtist().getLastName());
    }

    @Test
    public void testCreatePicture() {
        PictureEntity requestedToSave = testObject.getArtistJoePainting();
        requestedToSave.setArtist(testObject.getArtistJoe());

        CreatePictureDto createPictureDto = modelMapper.map(requestedToSave, CreatePictureDto.class);
        createPictureDto.setImage(testObject.getMockedMinecraftPicture());

        ResponsePictureDto dtoCreatedPicture = pictureService.createPicture(createPictureDto);
        //dtoCreatedPicture returns FROM SERVICE
        //createPictureDto  sent TO service
        PictureEntity savedPicture = pictureRepository.findById(dtoCreatedPicture.getId()).get();

        Assertions.assertThat(savedPicture.getId()).isEqualTo(dtoCreatedPicture.getId());
        Assertions.assertThat(savedPicture.getCreatedAtAge()).isEqualTo(requestedToSave.getCreatedAtAge());
        Assertions.assertThat(savedPicture.getName()).isEqualTo(requestedToSave.getName());
        Assertions.assertThat(savedPicture.getImage()).isEqualTo(dtoCreatedPicture.getImage());
        Assertions.assertThat(savedPicture.getArtist().getFirstName()).isEqualTo(requestedToSave.getArtist().getFirstName());
        Assertions.assertThat(savedPicture.getArtist().getLastName()).isEqualTo(requestedToSave.getArtist().getLastName());
        Assertions.assertThat(savedPicture.getArtist().getPortrait()).isEqualTo(requestedToSave.getArtist().getPortrait());
        Assertions.assertThat(savedPicture.getArtist().getCountry()).isEqualTo(requestedToSave.getArtist().getCountry());

        minioService.deleteFile(dtoCreatedPicture.getImage());
    }

    @Test
    public void testDeletePicture(){
        Long id = pictureRepository.save(testObject.getArtistJoePainting()).getId();

        pictureService.deletePicture(id);

        Assertions.assertThat(pictureRepository.findById(id).orElse(null)).isEqualTo(null);


    }

    @Test
    public void testUpdatePicture(){
        Long id = pictureRepository.save(testObject.getArtistJoePainting()).getId();

        UpdatePictureDto updatePictureDto = modelMapper.map(pictureRepository.findById(id),UpdatePictureDto.class);
        try {
            updatePictureDto.setImage(new MockMultipartFile(
                    testObject.getMockedMinecraftPicture().getName(),
                    testObject.getMockedMinecraftPicture().getOriginalFilename(),
                    testObject.getMockedMinecraftPicture().getContentType(),
                    testObject.getMockedMinecraftPicture().getBytes()
            ));       } catch (IOException e) { throw new RuntimeException(e);}
        updatePictureDto.setName("NewMinecraftPictureDto");
        updatePictureDto.setCreatedAtAge(20024);
        updatePictureDto.setArtistId(testObject.getArtistJoe().getId());

        String newImage = pictureService.updatePicture(updatePictureDto).getImage();

        var updatedPicture = pictureRepository.findById(id).get();



        Assertions.assertThat(updatedPicture.getArtist()).isEqualTo(testObject.getArtistJoe());
        Assertions.assertThat(updatedPicture.getImage()).isEqualTo(newImage);
        Assertions.assertThat(updatedPicture.getName()).isEqualTo("NewMinecraftPictureDto");
        Assertions.assertThat(updatedPicture.getCreatedAtAge()).isEqualTo(20024);
        Assertions.assertThat(updatedPicture.getId()).isEqualTo(id);



    }




}
