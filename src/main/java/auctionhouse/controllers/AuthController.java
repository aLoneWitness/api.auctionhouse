package auctionhouse.controllers;

import auctionhouse.dto.RegisterDto;
import auctionhouse.entities.User;
import auctionhouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@EnableAutoConfiguration
@RequestMapping("auth")
@CrossOrigin
public class AuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return ResponseEntity
                    .badRequest()
                    .body("Username already in use!");
        }
        if(userRepository.existsByEmail(registerDto.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body("Email already in use!");
        }

        User user = new User(registerDto.getUsername(), registerDto.getEmail(), bCryptPasswordEncoder.encode(registerDto.getPassword()));
        userRepository.save(user);
        return ResponseEntity.accepted().body("Registration succesfull!");
    }
}
