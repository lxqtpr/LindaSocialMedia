package dev.lxqtpr.lindaSocialMedia.Domain.User.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
        this.userRepository.deleteById(id);
    }
}
