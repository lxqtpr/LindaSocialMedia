package dev.lxqtpr.lindaSocialMedia.Core.Services;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ImageUploadException;
import dev.lxqtpr.lindaSocialMedia.Core.Properties.MinioProperties;
import io.minio.*;
import io.minio.messages.Item;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {

        private final MinioClient minioClient;
        private final MinioProperties minioProperties;
        public String upload(MultipartFile image) {
            return uploadInBucket(image,generateFileName(image),minioProperties.getBucket());
        }
    private String uploadInBucket(MultipartFile image, String fileName, String bucketName) {
        try {
            createBucket(bucketName);
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
        if (image.isEmpty() || image.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name.");
        }
        InputStream inputStream;
        try {
            inputStream = image.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
        saveImage(inputStream, fileName,bucketName);
        return fileName;
    }

    public String uploadInUserBucket(MultipartFile image, Long userId) {
            return uploadInBucket(image,generateFileName(image),"user-bucket"+userId);
        }

    @SneakyThrows
    public void deleteFromBucket(String filename, String bucketName) {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(filename)
                        .build()
        );
    }
    @SneakyThrows
    public void deleteFile(String filename) {
        deleteFromBucket(filename,minioProperties.getBucket());
    }

    @SneakyThrows
    public void deleteFileFromUserBucket(String filename, Long userId) {
        deleteFromBucket(filename,"user-bucket"+userId);
    }
    @SneakyThrows
    public void deleteUserBucket(Long userId){
        ListObjectsArgs bucketName = ListObjectsArgs.builder()
                .bucket("user-bucket"+userId)
                .build();

        Iterable<Result<Item>> results = minioClient.listObjects(bucketName);
        for (Result<Item> result : results) {
            deleteFileFromUserBucket(result.get().objectName(),userId);
        }
        deleteBucket("user-bucket"+userId);
    }
    @SneakyThrows
    public void deleteBucket(String bucketName) {
        if (minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build())) {
            minioClient.removeBucket(RemoveBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
        }
    }
    @SneakyThrows
    private void createBucket(String bucketName) {
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
    }

    // NEED TO BE PUBLIC, SORRY!
    private String generateFileName(final MultipartFile file) {
            String extension = getExtension(file);
            return UUID.randomUUID() + "." + extension;
        }

        private String getExtension(final MultipartFile file) {
            return file.getOriginalFilename()
                    .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        }

        @SneakyThrows
        private void saveImage(final InputStream inputStream,
                               final String fileName,
                               String bucketName) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                    .stream(inputStream, inputStream.available(), -1)
                    .bucket(bucketName)
                    .object(fileName)
                    .build());
        }

    @SneakyThrows
    private void saveImage(final InputStream inputStream,
                           final String fileName) {
        saveImage(inputStream,fileName,minioProperties.getBucket());
    }
    @SneakyThrows
    private void saveUserImage(final InputStream inputStream,
                           final String fileName,
                               Long userId) {
        saveImage(inputStream,fileName,"user-bucket"+userId);
    }
}