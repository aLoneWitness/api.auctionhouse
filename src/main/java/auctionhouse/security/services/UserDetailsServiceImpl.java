package auctionhouse.security.services;

import auctionhouse.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository applicationUserRepository) {
        this.userRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<auctionhouse.entities.User> applicationUser = userRepository.findByUsername(username);
        if (applicationUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        auctionhouse.entities.User user = applicationUser.get();
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }
}
