package dev.lxqtpr.lindaSocialMedia.Domain.File;

import dev.lxqtpr.lindaSocialMedia.Domain.File.Service.FileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public record FileController(
        FileService fileService
) {
}
