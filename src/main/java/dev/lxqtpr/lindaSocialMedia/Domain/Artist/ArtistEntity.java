package dev.lxqtpr.lindaSocialMedia.Domain.Artist;

import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ArtistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstName;
    String lastName;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    Date birthdate;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    Date deathdate;

    @ManyToOne
    @JoinColumn(name = "country_id")
    CountryEntity country;

    String portrait;

    @OneToMany(mappedBy = "artist")
    ArrayList<PictureEntity> pictures;
}
