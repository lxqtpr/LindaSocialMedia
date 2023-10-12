package dev.lxqtpr.lindaSocialMedia;

import dev.lxqtpr.lindaSocialMedia.Auth.Service.UserDetailsManagerImpl;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Dto.UserDefaultCreationDto;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import jakarta.persistence.NonUniqueResultException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
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
        var userDto = new UserDefaultCreationDto("lusha_bumych1ovm", "password1pazhiloy");
        manager.createUser(userDto);
        var user = userRepository.findUserEntityByUsername(userDto.getUsername()).get();

        assert userDto.getUsername().equals(user.getUsername());
    }

    @Test
    void testAnotherUserWithSameUsernameCantBeCreated() {
        var userDto = new UserDefaultCreationDto("lusha_bumych1ovm", "password1pazhiloy");
        var user = userRepository.save(UserEntity.builder()
                        .username(userDto.getUsername())
                        .email(userDto.getUsername().concat("@gmail.com"))
                        .password(userDto.getPassword())
                .build());

        // same username
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(UserEntity.builder().username(user.getUsername()).build());
        });

        // not same username but same email
        Assertions.assertThrows(JpaSystemException.class, () -> {
            userRepository.save(UserEntity.builder()
                            .username(userDto.getUsername().concat("83vmoii4"))
                            .email(userDto.getUsername().concat("@gmail.com"))
                            .password(userDto.getPassword())
                    .build());
        });
    }
}
