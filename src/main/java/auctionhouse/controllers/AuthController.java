package auctionhouse.controllers;

import auctionhouse.dto.RegisterDto;
import auctionhouse.entities.User;
import auctionhouse.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        User user = new User(registerDto.getUsername(), registerDto.getEmail(), registerDto.getPassword());
        if(userService.create(user)){
            return ResponseEntity.accepted().body("Registration successful!");
        }
        else {
            return ResponseEntity.unprocessableEntity().body("Please check the submission data.");
        }
    }
}
