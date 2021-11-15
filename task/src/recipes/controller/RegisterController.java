package recipes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.domain.User;
import recipes.service.UserService;

import javax.validation.Valid;

/**
 * @author dzahbarov
 */

@RestController
@RequestMapping("api/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void register(@Valid @RequestBody User user) {
        User userFromDB = userService.findByEmail(user.getEmail());
        if (userFromDB != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        userService.save(user);
    }

}
