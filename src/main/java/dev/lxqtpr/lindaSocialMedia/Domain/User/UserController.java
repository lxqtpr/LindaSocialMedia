package dev.lxqtpr.lindaSocialMedia.Domain.User;

import dev.lxqtpr.lindaSocialMedia.Authentication.Service.UserDetailsManagerImpl;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Service.UserService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.dto.UserDefaultCreationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public record UserController(
        UserService userService,
        UserDetailsManagerImpl detailsManager) {

    // for tests
    @PostMapping("/create")
    public ResponseEntity<String> createUser(UserDefaultCreationDto userDto) {
        if (detailsManager().userExists(userDto.getUsername()))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("User with username='"+userDto.getUsername()+"' is already exists!");

        detailsManager().createUser(userDto);

        return ResponseEntity.status(HttpStatus.OK).body("User created!");
    }
}
