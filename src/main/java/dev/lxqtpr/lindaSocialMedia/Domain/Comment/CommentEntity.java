package dev.lxqtpr.lindaSocialMedia.Domain.Comment;

import dev.lxqtpr.lindaSocialMedia.Domain.Post.PostEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    UserEntity author;

    @ManyToOne
    @JoinColumn(name = "post_id")
    PostEntity post;

    String text;

    private Long likesQuantity;
}
