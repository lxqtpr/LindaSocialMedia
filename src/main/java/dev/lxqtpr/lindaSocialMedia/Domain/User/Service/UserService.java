package dev.lxqtpr.lindaSocialMedia.Domain.User.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ResourceNotFoundException;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final ModelMapper mapper;

    public UserEntity loadUserByUsername(String username) {
        return this.userRepository.findUserEntityByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User with username='"+username+"' is not found!"));
    }

    public boolean isUserWithUsernameExists(String username) {
        return this.userRepository.findUserEntityByUsername(username).isPresent();
    }
}
