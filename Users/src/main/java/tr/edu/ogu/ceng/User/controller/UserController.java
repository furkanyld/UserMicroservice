package tr.edu.ogu.ceng.User.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tr.edu.ogu.ceng.User.entity.User;
import tr.edu.ogu.ceng.User.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService usersService;

    @GetMapping("/name/{name}")
    public ResponseEntity<Optional<User>> getUserByUsername(@PathVariable String name) {
        Optional<User> user = usersService.getByUsername(name);
        return ResponseEntity.ok(user);
    }
}
