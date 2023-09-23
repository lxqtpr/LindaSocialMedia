package dev.lxqtpr.lindaSocialMedia.Configs;

import dev.lxqtpr.lindaSocialMedia.Properties.MinioProperties;
import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;

public record MinioConfig(MinioProperties minioProperties) {
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(minioProperties.getUrl())
                .credentials(minioProperties().getAccessKey(), minioProperties().getSecretKey())
                .build();
    }
}
