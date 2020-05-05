package auctionhouse.services;

import auctionhouse.entities.User;
import auctionhouse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(User user) {
        if(user.getUsername() == null || user.getUsername().isBlank() || user.getEmail() == null || user.getEmail().isBlank()){
            return false;
        }

        userRepository.save(user);
        return true;
    }
}
