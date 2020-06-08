package auctionhouse;

import auctionhouse.entities.User;
import auctionhouse.repositories.UserRepository;
import auctionhouse.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAddRatingToSelf() {
        User giver = new User();
        User receiver = new User();
        giver.setId(1);
        receiver.setId(1);

        boolean isAdded = userService.giveRatingToUser(giver, receiver, 3);

        assertFalse(isAdded);
    }

    @Test
    public void testAddRatingToOther() {
        User giver = new User();
        User receiver = new User();
        giver.setId(1);
        receiver.setId(2);

        boolean isAdded = userService.giveRatingToUser(giver, receiver, 3);

        assertTrue(isAdded);
    }

    @Test
    public void testAddDoubleRating() {
        User giver = new User();
        giver.setId(1);

        User receiver = new User();
        receiver.setId(2);

        userService.giveRatingToUser(giver, receiver, 3);
        boolean isDouble = userService.giveRatingToUser(giver, receiver, 2);

        assertFalse(isDouble);
    }

    @Test
    public void testNegativeRatingDeletionScheme() {
        List<User> givers = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            User giver = new User();
            giver.setId(i);
            givers.add(giver);
        }

        User receiver = new User();
        receiver.setId(999);

        for (User giver: givers) {
            userService.giveRatingToUser(giver, receiver, 1);
        }
    }

//    @Test
//    public void testRemoveAccountAfterTooMuchNegativeRating() {
//        List<User> givers = new ArrayList<>();
//        for (int i = 0; i < 30; i++){
//            User giver = new User();
//            giver.setId(i);
//            givers.add(giver);
//        }
//
//        User receiver = new User();
//        receiver.setId(999);
//
//        for (User giver: givers) {
//            userService.giveRatingToUser(giver, receiver, 1);
//        }
//
//
//    }
//
//    @Test
//    public void testKeepAccountAfterManyNormalRatings() {
//        List<User> givers = new ArrayList<>();
//        for (int i = 0; i < 30; i++){
//            User giver = new User();
//            giver.setId(i);
//            givers.add(giver);
//        }
//
//        User receiver = new User();
//        receiver.setUsername("BigUser");
//        receiver.setEmail("ffdas@gmail.com");
//        receiver.setPassword("fdasfsda");
//
//        Mockito.when(userRepository.save(receiver)).thenReturn(receiver);
//        userRepository.save(receiver);
//
//        for (User giver: givers) {
//            userService.giveRatingToUser(giver, receiver, 3);
//        }
//
//        Mockito.when()
//        List<User> users = userRepository.findAll();
//
//        assertFalse(true);
//    }
}
