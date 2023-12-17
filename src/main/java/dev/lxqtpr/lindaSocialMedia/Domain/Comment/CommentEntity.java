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
@ToString(of = {"id", "text"})
@EqualsAndHashCode(of = {"id", "text"})
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    private String text;

    @ManyToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<UserEntity> likes;
}
