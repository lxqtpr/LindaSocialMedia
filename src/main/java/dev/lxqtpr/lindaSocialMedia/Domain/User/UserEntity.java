package dev.lxqtpr.lindaSocialMedia.Domain.User;

import dev.lxqtpr.lindaSocialMedia.Domain.Comment.CommentEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Role.RoleEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;
    String name;
    String avatar;
    String city;
    String pageCover;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<RoleEntity> roles;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    ArrayList<CommentEntity> comments;

    Boolean isVerified;

    String email;
}
