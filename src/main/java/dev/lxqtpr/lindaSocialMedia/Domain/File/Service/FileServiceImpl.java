package dev.lxqtpr.lindaSocialMedia.Domain.File.Service;

import dev.lxqtpr.lindaSocialMedia.Core.Exception.ImageUploadException;
import dev.lxqtpr.lindaSocialMedia.Core.Properties.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
public record FileServiceImpl(
        MinioClient minioClient,
        MinioProperties minioProperties
) implements FileService {
    @Override
    public String upload(MultipartFile image) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
        if (image.isEmpty() || image.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name.");
        }
        String fileName = generateFileName(image);
        InputStream inputStream;
        try {
            inputStream = image.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
        saveImage(inputStream, fileName);
        return fileName;
    }
    @SneakyThrows
    private void createBucket(){
        boolean isBucketExist = minioClient.bucketExists(
                BucketExistsArgs
                        .builder()
                        .bucket(minioProperties.getBucket())
                        .build()
        );
        if (!isBucketExist){
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(minioProperties().getBucket())
                            .build());
        }
    }
    private String generateFileName(final MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }
    private String getExtension(final MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }
    @SneakyThrows
    private void saveImage(final InputStream inputStream, final String fileName){
        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .stream(inputStream, inputStream.available(), -1)
                        .bucket(minioProperties.getBucket())
                        .object(fileName)
                        .build());
    }
}