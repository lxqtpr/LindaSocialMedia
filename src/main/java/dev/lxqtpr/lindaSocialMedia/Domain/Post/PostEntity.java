package dev.lxqtpr.lindaSocialMedia.Domain.Post;

import dev.lxqtpr.lindaSocialMedia.Domain.Comment.CommentEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    UserEntity author;

    String text;

    @CollectionTable(name = "post_files")
    @ElementCollection(fetch = FetchType.EAGER)
    ArrayList<String> files;

    @OneToMany(mappedBy = "post")
    ArrayList<CommentEntity> comments;
}
