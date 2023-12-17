package dev.lxqtpr.lindaSocialMedia.Domain.Picture;

import dev.lxqtpr.lindaSocialMedia.Domain.Artist.ArtistEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PictureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    ArtistEntity artist;

    String name;

    String image;

    Integer createdAtAge;
}
