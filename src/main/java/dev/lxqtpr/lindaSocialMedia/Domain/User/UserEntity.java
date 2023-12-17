package dev.lxqtpr.lindaSocialMedia.Domain.User;

import dev.lxqtpr.lindaSocialMedia.Domain.Comment.CommentEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.Role.UserRoleEnum;
import dev.lxqtpr.lindaSocialMedia.Domain.Chat.ChatEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString(of = {"id", "username"})
@EqualsAndHashCode(of = {"id", "username"})
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // todo: SHOULD BE UNIQUE!!!
    @Column(name = "username")
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String city;
    private String pageCover;
    private String email;

    @ManyToMany
    @JoinTable(
            name = "rooms_members",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id"))
    Set<ChatEntity> rooms;

    @ElementCollection
    private Set<UserRoleEnum> roles;
    private Boolean isVerified;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<CommentEntity> comments;

    // for user details functionality
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public UserEntity(Long id, String username, String password, String name, String avatar,
                      String city, String pageCover, String email, Set<UserRoleEnum> roles, Boolean isVerified,
                      Boolean isAccountNonExpired, Boolean isAccountNonLocked, Boolean isCredentialsNonExpired, Boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
        this.city = city;
        this.pageCover = pageCover;
        this.email = email;
        this.roles = roles;
        this.isVerified = isVerified;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }
}
