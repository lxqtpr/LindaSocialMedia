package dev.lxqtpr.lindaSocialMedia.UnitTest;


import dev.lxqtpr.lindaSocialMedia.Core.Properties.MinioProperties;
import dev.lxqtpr.lindaSocialMedia.Core.Services.MinioService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Service.UserService;
import io.minio.*;
import io.minio.errors.*;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Transactional
@SpringBootTest
public class MinioServiceTest {


    @Autowired
    MinioService minioService;
    @Autowired
    UserService userService;
    TestObject testObject;
    @Autowired
    MinioServiceTest(MinioClient minioClient, MinioProperties minioProperties){
        this.minioProperties = minioProperties;
        this.minioClient = minioClient;
    }
    private final MinioClient minioClient;

    private final MinioProperties minioProperties;


    @BeforeEach
    public void before(){
        testObject = new TestObject();
    }

    @SneakyThrows
    @Test
    public void testUploadInUserBucket(){
        var user = testObject.getUserEntity();
        user.setId(1L);
        String bucketName = "user-bucket"+user.getId();
        String fileName = minioService.uploadInUserBucket(testObject.getDefaultUserPng(),
                user.getId());

        boolean bucketFound = minioClient.bucketExists(
                BucketExistsArgs
                        .builder()
                        .bucket("user-bucket"+user.getId())
                        .build());


        boolean imageFound = false;
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName).build());
            imageFound = true;
        }catch (Exception e){
           throw (e);
        }


        Assertions.assertThat(bucketFound).isTrue();
        Assertions.assertThat(imageFound).isTrue();


        minioService.deleteFileFromUserBucket(fileName,user.getId());
    }
    @SneakyThrows
    @Test
    public void testDeleteBucketWithUser() throws IOException {
        var user = testObject.getUserEntity();
        Long userId = userService.saveUserEntity(user).getId();
        InputStream inputStream = testObject.getDefaultUserPng().getInputStream();
        String filename = testObject.getDefaultUserPng().getOriginalFilename();
        String bucketName = "user-bucket"+userId;
        boolean found = minioClient.bucketExists(
                BucketExistsArgs
                        .builder()
                        .bucket(bucketName)
                        .build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }

        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream,inputStream.available(),-1)
                .bucket(bucketName)
                .object(filename)
                .build());

        userService.deleteUserById(userId);

        found = minioClient.bucketExists(
                BucketExistsArgs
                        .builder()
                        .bucket(bucketName)
                        .build());

        Assertions.assertThat(found).isFalse();


    }
}
