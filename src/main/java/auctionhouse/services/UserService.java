package auctionhouse.services;

import auctionhouse.entities.Rating;
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
            if(user.getUsername().isBlank() || user.getPassword().isBlank() || user.getEmail().isBlank() ) return false;
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

    public boolean giveRatingToUser(User giver, User receiver, int stars) {
        if(giver.getId().equals(receiver.getId())) return false;
        if(stars < 1 || stars > 5) return false;
//        for (Rating rating: receiver.getRatings()) {
//            if(rating.getFrom().getId().equals(receiver.getId()) || rating.getFrom().getId().equals(giver.getId())){
//                return false;
//            }
//        }
        

        Rating rating = new Rating();
        rating.setFrom(giver);
        rating.setStars(stars);
        receiver.getRatings().add(rating);

        if(receiver.getRatings().size() > 10) {
            int total = 0;
            for (Rating receivedRating: receiver.getRatings()) {
                total = total + receivedRating.getStars();
            }
            if(total / receiver.getRatings().size() < 2) {
                userRepository.deleteById(receiver.getId());
                System.out.println("Deleted account " + receiver.getUsername());
                return true;
            }
        }

        userRepository.save(receiver);
        return true;
    }
    
}
