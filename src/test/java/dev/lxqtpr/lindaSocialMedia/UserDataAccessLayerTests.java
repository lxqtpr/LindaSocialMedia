package dev.lxqtpr.lindaSocialMedia;

import dev.lxqtpr.lindaSocialMedia.Auth.Service.UserDetailsManagerImpl;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserDefaultCreationDto;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LindaSocialMediaApplication.class)
@Transactional
public class UserDataAccessLayerTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsManagerImpl manager;

    @Test
    void testUserWithUsernameExistsWithRightUsername() {
        var userDto = new UserDefaultCreationDto("lusha_bumych", "password1pazhiloy");
        manager.createUser(userDto);
        var user = userRepository.findUserEntityByUsername(userDto.getUsername()).get();

        assert userDto.getUsername().equals(user.getUsername());
    }
}
