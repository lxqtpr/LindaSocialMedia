package dev.lxqtpr.lindaSocialMedia.Domain.Artist;

import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.PictureEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
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

    @ManyToOne()
    @JoinColumn(name = "country_id")
    CountryEntity country;

    String portrait;

    @OneToMany(mappedBy = "artist")
    List<PictureEntity> pictures;
}
