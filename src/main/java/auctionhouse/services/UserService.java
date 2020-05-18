package auctionhouse.services;

import auctionhouse.entities.User;
import auctionhouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean create(User user) {
        try {
            if(!user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank() ) return false;
            if(userRepository.existsByUsername(user.getUsername())) return false;
            if(userRepository.existsByEmail(user.getEmail())) return false;
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public User get(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }
}
