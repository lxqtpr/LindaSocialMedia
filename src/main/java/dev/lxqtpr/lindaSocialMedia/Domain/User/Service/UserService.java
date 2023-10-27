package dev.lxqtpr.lindaSocialMedia.Domain.User.Service;

import dev.lxqtpr.lindaSocialMedia.Auth.Service.UserDetailsManagerImpl;
import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.Role.UserRoleEnum;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Dto.UserDefaultCreationDto;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.shell.standard.ShellOption;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MinioService minioService;
    private final ModelMapper mapper;

    public UserDetails loadUserByUsername(String username) {
        var userOptional = this.userRepository.findUserEntityByUsername(username);
        if (userOptional.isEmpty()) throw new ResourceNotFoundException("User with username='"+username+"' is not found!");

        System.out.println(userOptional.get());

        return userOptional.get();
    }

    public boolean isUserWithUsernameExists(String username) {
        return this.userRepository.findUserEntityByUsername(username).isPresent();
    }

    public UserEntity saveUserEntity(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    public void deleteUserById(Long id) {
        this.minioService.deleteUserBucket(id);
        this.userRepository.deleteById(id);
    }

    public void opUserRights(Long userId) throws ResourceNotFoundException{
        if (userId!=null) {
            UserEntity user = userRepository.findById(userId).get();
            var roles = user.getRoles();
            roles.add(UserRoleEnum.ROlE_ADMIN);
            userRepository.save(user);

        }
        else{
            throw new ResourceNotFoundException ("user id mustn't bee null");

        }

    }
    public void createTestUser(String username,
                               String password)throws ResourceNotFoundException {
        System.out.println("creating user " + username + ";pw: " + password);
        if (userRepository.findUserEntityByUsername(username).isEmpty()) {
            UserDefaultCreationDto user = new UserDefaultCreationDto(username,password);


            this.saveUserEntity(UserEntity.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(Set.of(UserRoleEnum.ROLE_USER))
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .build()
            );


        }
        else{
            throw new ResourceNotFoundException("user already exists");
        }
    }
}
