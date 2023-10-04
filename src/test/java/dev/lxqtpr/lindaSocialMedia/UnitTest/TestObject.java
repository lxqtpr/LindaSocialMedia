package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Core.Configs.ModelMapperConfig;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureEntity;
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
import java.util.List;

public class TestObject {
    private final ModelMapperConfig modelMapper = new ModelMapperConfig();
    public ModelMapper getModelMapper(){
        return this.modelMapper.modelMapper();
    }
    // ARTIST JOE OBJECT // ARTIST JOE OBJECT // ARTIST JOE OBJECT // ARTIST JOE OBJECT

    List<ArtistEntity> artistEntityList = new ArrayList<>();
    @Getter
    private CountryEntity Wales;
    List<PictureEntity> emptyPictureEntityList = new ArrayList<PictureEntity>();
    @Getter
    @Setter
    private ArtistEntity artistJoe;
    private MultipartFile mockedMultipartFile;

    @Getter
    @Setter
    private MultipartFile mockedMinecraftPicture;

    @Getter
    private PictureEntity artistJoePainting = new PictureEntity();

    public TestObject(){

        // ARTIST JOE INIT // ARTIST JOE INIT // ARTIST JOE INIT // ARTIST JOE INIT
        Wales = new CountryEntity();
        Wales.setId(1L);
        Wales.setName("Wales");
        Wales.setImage("WalesFlag");

        artistJoe = new ArtistEntity(
                null,
                "Joe","Walsh",
                Wales,
                "avatar",
                new ArrayList<PictureEntity>()
    );
        String name = "avatar";
        String originalFileName = "avatar.png";
        String contentType = "image/png";
        byte[] content = null;
        mockedMultipartFile = new MockMultipartFile(name,
                originalFileName, contentType, content);
        mockedMultipartFile.getOriginalFilename();

        artistJoePainting.setArtist(artistJoe);
        artistJoePainting.setName("ORANGE");
        artistJoePainting.setImage("NO IMAGE");
        artistJoePainting.setCreatedAtAge(20023);
        byte[] minecraftPicture;
        try {minecraftPicture = Files.readAllBytes(Path.of("src/test/resources/minecraftpicture.jpg"));}
        catch (IOException e) {throw new RuntimeException(e);}

        mockedMinecraftPicture = new MockMultipartFile("minecraftpicture",
                "minecraftpicture.jpg",
                "image/jpg",
                minecraftPicture);

    }
    public CreateArtistDto getArtistJoeCreateDto(){
        CreateArtistDto artistJoeCreateDto = modelMapper.modelMapper().map(artistJoe,CreateArtistDto.class);
        artistJoeCreateDto.setPortrait(mockedMultipartFile);
        return artistJoeCreateDto;
    }

}
