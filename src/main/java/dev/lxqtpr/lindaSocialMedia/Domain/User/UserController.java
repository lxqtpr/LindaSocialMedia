package dev.lxqtpr.lindaSocialMedia.Domain.User;

import dev.lxqtpr.lindaSocialMedia.Domain.User.Service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public record UserController(
        UserService userService,
        UserRepository userRepository
){

}
