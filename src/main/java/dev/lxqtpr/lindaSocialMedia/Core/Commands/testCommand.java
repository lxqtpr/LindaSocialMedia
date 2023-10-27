package dev.lxqtpr.lindaSocialMedia.Core.Commands;

import dev.lxqtpr.lindaSocialMedia.Core.Configs.ModelMapperConfig;
import dev.lxqtpr.lindaSocialMedia.Domain.Role.UserRoleEnum;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Dto.UserDefaultCreationDto;
import dev.lxqtpr.lindaSocialMedia.Domain.User.Service.UserService;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserController;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserEntity;
import dev.lxqtpr.lindaSocialMedia.Domain.User.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

@ShellComponent
public class testCommand {
    @Autowired
    UserService userService;
    private final ModelMapperConfig modelMapper = new ModelMapperConfig();

    @ShellMethod(key = "OP", value="give OP rules for user")
    public void opUserRights(@ShellOption() Long userId){
        userService.opUserRights(userId);
    }

    @ShellMethod(key="createTestUser", value="creating test User")
    public void createTestUser(@ShellOption String username,
                               @ShellOption String password) {

        userService.createTestUser(username,password);
        System.out.println("Creation ended");
    }

}
