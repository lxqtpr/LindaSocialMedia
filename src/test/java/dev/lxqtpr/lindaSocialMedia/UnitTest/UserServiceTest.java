package dev.lxqtpr.lindaSocialMedia.UnitTest;

import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Service.UserService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
public class UserServiceTest {

    TestObject testObject;
    ModelMapper mapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    MinioService minioService;
    UserService userService;
    @BeforeEach
    public void before(){
        testObject = new TestObject();
        mapper = testObject.getModelMapper();
        userService = new UserService(userRepository,minioService,mapper);
    }

    @Test
    public void testLoadUserByUsername(){
        UserEntity savedEntity = userRepository.save(testObject.getUserEntity());

        String namePng = minioService.uploadInUserBucket(testObject.getDefaultUserPng(),savedEntity.getId());
        testObject.getUserEntity().setAvatar(namePng);
        testObject.getUserEntity().setPageCover(namePng);
        testObject.getUserEntity().setId(savedEntity.getId());

        userRepository.save(testObject.getUserEntity());

        var loadedUser = userService.loadUserByUsername(savedEntity.getUsername());

        Assertions.assertThat(loadedUser.getUsername()).isEqualTo(testObject.getUserEntity().getUsername());
        Assertions.assertThat(loadedUser.getAuthorities().size()).isEqualTo(testObject.getUserEntity().getAuthorities().size());
        Assertions.assertThat(loadedUser.getPassword()).isEqualTo(testObject.getUserEntity().getPassword());




        minioService.deleteFileFromUserBucket(namePng, savedEntity.getId());
    }
    @Test
    public void testIsUserWithUsernameExists(){
        UserEntity savedEntity = userRepository.save(testObject.getUserEntity());

        String namePng = minioService.uploadInUserBucket(testObject.getDefaultUserPng(),savedEntity.getId());
        testObject.getUserEntity().setAvatar(namePng);
        testObject.getUserEntity().setPageCover(namePng);
        testObject.getUserEntity().setId(savedEntity.getId());

        userRepository.save(testObject.getUserEntity());

        var loadedUser = userService.isUserWithUsernameExists(savedEntity.getUsername());

        Assertions.assertThat(loadedUser).isEqualTo(true);




        minioService.deleteFileFromUserBucket(namePng,savedEntity.getId());
    }
    @Test
    public void testSaveUser(){
        Long loadedUserId = userService.saveUserEntity(testObject.getUserEntity()).getId();

        String namePng = minioService.uploadInUserBucket(testObject.getDefaultUserPng(),loadedUserId);
        testObject.getUserEntity().setAvatar(namePng);
        testObject.getUserEntity().setPageCover(namePng);
        testObject.getUserEntity().setId(loadedUserId);

        userService.saveUserEntity(testObject.getUserEntity());

        var loadedUser = userRepository.findById(loadedUserId).get();

        Assertions.assertThat(loadedUser.getName()).isEqualTo(testObject.getUserEntity().getName());
        Assertions.assertThat(loadedUser.getPassword()).isEqualTo(testObject.getUserEntity().getPassword());
        Assertions.assertThat(loadedUser.getUsername()).isEqualTo(testObject.getUserEntity().getUsername());
        Assertions.assertThat(loadedUser.getAvatar()).isEqualTo(testObject.getUserEntity().getAvatar());
        Assertions.assertThat(loadedUser.getPageCover()).isEqualTo(testObject.getUserEntity().getPageCover());

        minioService.deleteFileFromUserBucket(namePng,loadedUserId);
    }
    @Test
    public void testDeleteUsername() {

        Long savedEntityId = userRepository.save(testObject.getUserEntity()).getId();

        String namePng = minioService.uploadInUserBucket(testObject.getDefaultUserPng(),savedEntityId);

        testObject.getUserEntity().setAvatar(namePng);
        testObject.getUserEntity().setPageCover(namePng);
        testObject.getUserEntity().setId(savedEntityId);

        userRepository.save(testObject.getUserEntity());


        userService.deleteUserById(savedEntityId);

        Assertions.assertThat(userRepository.findById(savedEntityId).orElse(null)).isEqualTo(null);


        minioService.deleteFileFromUserBucket(namePng,savedEntityId);


    }
}
