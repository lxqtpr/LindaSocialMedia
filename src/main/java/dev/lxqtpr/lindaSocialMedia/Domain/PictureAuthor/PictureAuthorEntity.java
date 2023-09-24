package dev.lxqtpr.lindaSocialMedia.Domain.PictureAuthor;

import dev.lxqtpr.lindaSocialMedia.Domain.Country.CountryEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PictureAuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String firstName;
    String lastName;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date birthDay;

    @DateTimeFormat(fallbackPatterns = "dd/MM/yyyy")
    private Date deathdate;

    @ManyToOne
    @JoinColumn(name = "country_id")
    CountryEntity country;

    String portrait;

    @CollectionTable(name = "author_files")
    @ElementCollection(fetch = FetchType.EAGER)
    ArrayList<String> pictures;
}
