package dev.lxqtpr.lindaSocialMedia.Auth;

import dev.lxqtpr.lindaSocialMedia.Domain.Role.UserRoleEnum;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Service.UserService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserDetailsManagerImpl implements UserDetailsManager {
    private final UserService userService;

    // for a default user creation (for example, after successful completed registration)
    @Override
    public void createUser(UserDetails user) {
        if (!this.userExists(user.getUsername())) {
            this.userService.saveUserEntity(
                    UserEntity.builder()
                            .name(user.getUsername())
                            .password(user.getPassword())
                            .roles(Set.of(UserRoleEnum.ROLE_USER))
                            .isEnabled(true)
                            .isAccountNonExpired(true)
                            .isAccountNonLocked(true)
                            .isCredentialsNonExpired(true)
                            .build()
            );
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        var userEntity = this.userService.loadUserByUsername(user.getUsername());
        userEntity.setIsAccountNonExpired(user.isAccountNonExpired());
        userEntity.setIsAccountNonLocked(user.isAccountNonLocked());
        userEntity.setIsCredentialsNonExpired(user.isCredentialsNonExpired());
        userEntity.setIsEnabled(user.isEnabled());
        this.userService.saveUserEntity(userEntity);
    }

    @Override
    public void deleteUser(String username) {
        var userEntity = this.userService.loadUserByUsername(username);
        this.userService.deleteUserById(userEntity.getId());
    }

    @Deprecated
    @Override
    public void changePassword(String oldPassword, String newPassword) {
    }

    @Override
    public boolean userExists(String username) {
        return this.userService.isUserWithUsernameExists(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userService.loadUserByUsername(username);
    }
}
