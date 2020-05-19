package auctionhouse.controllers;

import auctionhouse.entities.User;
import auctionhouse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/me")
    public ResponseEntity<User> self(@AuthenticationPrincipal User user) {
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<User> get(@RequestParam String username) {
        if(username.isBlank()) { return ResponseEntity.badRequest().build(); }
        User user = userService.get(username);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
