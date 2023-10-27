package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Core.Configs.ModelMapperConfig;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TestObject {
    private final ModelMapperConfig modelMapper = new ModelMapperConfig();
    public ModelMapper getModelMapper(){
        return this.modelMapper.modelMapper();
    }
    // OBJECTS INITIATION // OBJECTS INITIATION // OBJECTS INITIATION // OBJECTS INITIATION // OBJECTS INITIATION

    List<ArtistEntity> artistEntityList = new ArrayList<>();
    @Getter
    private CountryEntity Wales;
    List<PictureEntity> emptyPictureEntityList = new ArrayList<PictureEntity>();

    @Getter
    @Setter
    private ArtistEntity artistJoe;

    @Getter
    private MultipartFile defaultUserPng;

    @Getter
    @Setter
    private MultipartFile mockedMinecraftPicture;

    @Getter
    private PictureEntity artistJoePainting = new PictureEntity();

    @Getter
    @Setter
    private UserEntity userEntity;
    // OBJECTS INITIATION // OBJECTS INITIATION // OBJECTS INITIATION // OBJECTS INITIATION // OBJECTS INITIATION

    public TestObject(){
        // OBJECTS INITIALIZATION
//wales - country  |  Joe - artist

        Wales = new CountryEntity();
        Wales.setId(1L);
        Wales.setName("Wales");
        Wales.setImage("WalesFlag");

        artistJoe = new ArtistEntity(
                null,
                "Joe","Walsh",
                Wales,
                "user",
                new ArrayList<PictureEntity>()
    );
// mocked - user img
        String name = "user";
        String originalFileName = "user.png";
        String contentType = "image/png";
        byte[] content;
        try {content = Files.readAllBytes(Path.of("src/test/resources/user.png"));}
        catch (Exception e){throw new RuntimeException(e);}
        defaultUserPng = new MockMultipartFile(name,
                originalFileName, contentType, content);
        defaultUserPng.getOriginalFilename();
// joe - painting

        artistJoePainting.setArtist(artistJoe);
        artistJoePainting.setName("ORANGE");
        artistJoePainting.setImage("NO IMAGE");
        artistJoePainting.setCreatedAtAge(20023);
// mocked - painting img
        byte[] minecraftPicture;
        try {minecraftPicture = Files.readAllBytes(Path.of("src/test/resources/minecraftpicture.jpg"));}
        catch (IOException e) {throw new RuntimeException(e);}

        mockedMinecraftPicture = new MockMultipartFile("minecraftpicture",
                "minecraftpicture.jpg",
                "image/jpg",
                minecraftPicture);
// BRUNO - User entity

        userEntity = new UserEntity();
        userEntity.setPageCover("temperately_not");
        userEntity.setAvatar("temperately_not");

        userEntity.setIsEnabled(true);
        userEntity.setIsAccountNonLocked(true);
        userEntity.setIsCredentialsNonExpired(true);
        userEntity.setIsAccountNonExpired(true);
        userEntity.setIsVerified(true);

        userEntity.setUsername("BRUNO_2005__TEST__OBJECT");
        userEntity.setName("OLD_BRUNO__TEST__OBJECT");
        userEntity.setEmail("bruno__TEST__OBJECT@mail.com");
        userEntity.setCity("Saint-PetersBurg");
        userEntity.setPassword("lxqptr");

        userEntity.setComments(new ArrayList<>());
        userEntity.setRoles(new HashSet<>());
    }
    public CreateArtistDto getArtistJoeCreateDto(){
        CreateArtistDto artistJoeCreateDto = modelMapper.modelMapper().map(artistJoe,CreateArtistDto.class);
        artistJoeCreateDto.setPortrait(defaultUserPng);
        return artistJoeCreateDto;
    }

}
