package dev.lxqtpr.lindaSocialMedia.Domain.File.Service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String upload(final MultipartFile image);
}
