package dev.lxqtpr.lindaSocialMedia.Domain.Country;

import dev.lxqtpr.lindaSocialMedia.Domain.PictureAuthor.PictureAuthorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    String image;

    @OneToMany(mappedBy = "country")
    ArrayList<PictureAuthorEntity> authors;
}
