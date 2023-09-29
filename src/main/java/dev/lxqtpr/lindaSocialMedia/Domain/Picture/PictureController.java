package dev.lxqtpr.lindaSocialMedia.Domain.Picture;

import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.CreatePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.ResponsePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Dto.UpdatePictureDto;
import dev.lxqtpr.lindaSocialMedia.Domain.Picture.Service.PictureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pictures")
public record PictureController(
        PictureService pictureService
) {
    @GetMapping("/{id}")
    public ResponseEntity<ResponsePictureDto> getPictureById(@PathVariable Long id){
        return ResponseEntity.ok(pictureService.getPictureById(id));
    }
    @PostMapping
    public ResponseEntity<ResponsePictureDto> createPicture(
            @Valid @ModelAttribute CreatePictureDto dto
    ){
        return new ResponseEntity<>(
                pictureService.createPicture(dto),
                HttpStatus.CREATED
        );
    }
    @PutMapping
    public ResponseEntity<ResponsePictureDto> updatePicture(
            @Valid @ModelAttribute UpdatePictureDto dto
    ){
        return ResponseEntity.ok(
                pictureService.updatePicture(dto)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePicture(@PathVariable Long id){
        pictureService.deletePicture(id);
        return ResponseEntity.ok("Picture deleted");
    }

}
