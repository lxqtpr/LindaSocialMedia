package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Core.Configs.ModelMapperConfig;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Artist.Dto.CreateArtistDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestObject {
    private final ModelMapperConfig modelMapper = new ModelMapperConfig();
    // ARTIST JOE OBJECT // ARTIST JOE OBJECT // ARTIST JOE OBJECT // ARTIST JOE OBJECT

    List<ArtistEntity> artistEntityList = new ArrayList<>();
    CountryEntity Wales;
    List<PictureEntity> emptyPictureEntityList = new ArrayList<PictureEntity>();
    private final ArtistEntity artistJoe;
    private MultipartFile mockedMultipartFile;

    public TestObject(){

        // ARTIST JOE INIT // ARTIST JOE INIT // ARTIST JOE INIT // ARTIST JOE INIT
        Wales = new CountryEntity();
        Wales.setId(1L);
        Wales.setName("Wales");
        Wales.setImage("WalesFlag");

        artistJoe = new ArtistEntity(
                1L,
                "Joe","Walsh",
                Wales,
                "avatar",
                null
    );
        Path path = Paths.get("C:\\Windows\\workspace\\LinuxSHIT\\lindaSocialMedia\\src\\test\\resources\\avatar.png");
        String name = "avatar.png";
        String originalFileName = "avatar.png";
        String contentType = "image/png";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }
        mockedMultipartFile = new MockMultipartFile(name,
                originalFileName, contentType, content);
        mockedMultipartFile.getOriginalFilename();
    }
    public CreateArtistDto getArtistJoeCreateDto(){
        CreateArtistDto artistJoeCreateDto = modelMapper.modelMapper().map(artistJoe,CreateArtistDto.class);
        artistJoeCreateDto.setPortrait(mockedMultipartFile);
        return artistJoeCreateDto;
    }

}
