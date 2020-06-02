package auctionhouse.controllers;

import auctionhouse.dto.ItemDto;
import auctionhouse.dto.RatingDto;
import auctionhouse.dto.UserDto;
import auctionhouse.entities.Item;
import auctionhouse.entities.User;
import auctionhouse.services.UserService;
import org.modelmapper.ModelMapper;
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
    public ResponseEntity<UserDto> self(@AuthenticationPrincipal User user) {
        user.setPassword(null);
        return ResponseEntity.ok(convertToDto(user));
    }

    @GetMapping
    public ResponseEntity<UserDto> get(@RequestParam String username) {
        if(username.isBlank()) { return ResponseEntity.badRequest().build(); }
        User user = userService.get(username);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        user.setPassword(null);
        return ResponseEntity.ok(convertToDto(user));
    }

    @PostMapping(path = "/addrating")
    public ResponseEntity<?> giveRating(@AuthenticationPrincipal User giver, @RequestBody RatingDto ratingDto) {
        if(ratingDto.getUsername().isBlank()) { return ResponseEntity.badRequest().build(); }
        User receiver = userService.get(ratingDto.getUsername());
        if(receiver == null) {
            return ResponseEntity.notFound().build();
        }
        if(userService.giveRatingToUser(giver, receiver, ratingDto.getStars())){
            return ResponseEntity.accepted().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());

        user.getInventory().forEach(item -> {
            ItemDto itemDto = new ItemDto();
            itemDto.setName(item.getName());
            itemDto.setId(item.getId());
            itemDto.setImage(item.getImage());
            itemDto.setDescription(item.getDescription());
            itemDto.setPrice(item.getPrice());

            userDto.getInventory().add(itemDto);
        });

        user.getRatings().forEach(rating -> {
            RatingDto ratingDto = new RatingDto();
            ratingDto.setStars(rating.getStars());

            userDto.getRatings().add(ratingDto);
        });

        return userDto;
    }
}
