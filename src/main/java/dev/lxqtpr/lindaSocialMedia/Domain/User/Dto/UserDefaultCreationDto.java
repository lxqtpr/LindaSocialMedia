package dev.lxqtpr.lindaSocialMedia.Domain.User.Dto;

import dev.lxqtpr.lindaSocialMedia.Domain.Role.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.checkerframework.common.value.qual.MinLen;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class UserDefaultCreationDto implements UserDetails {
    @NotBlank
    @Length(min = 5, max = 30)
    private final String username;
    @NotBlank
    @Length(min = 8, max = 40)
    private final String password;

    @Deprecated
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(UserRoleEnum.ROLE_USER);
    }

    @Deprecated
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Deprecated
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Deprecated
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Deprecated
    @Override
    public boolean isEnabled() {
        return true;
    }
}
