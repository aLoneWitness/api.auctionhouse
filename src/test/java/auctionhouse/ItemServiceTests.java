package auctionhouse;

import auctionhouse.entities.Item;
import auctionhouse.repositories.ItemRepository;
import auctionhouse.services.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DataJpaTest
@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTests {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Test
    public void testCreateWithCorrectParams() {
        Item item = new Item();
        item.setDescription("Good game");
        item.setName("Mario Odyssey");
        item.setImage("https://s.s-bol.com/imgbase0/imagebase3/large/FC/0/3/6/6/9200000073666630.jpg");
        item.setPrice(new BigDecimal("49.99"));

        boolean successState = itemService.create(item);

        assertTrue(successState);
    }

    @Test
    public void testCreateWithBlankName() {
        Item item = new Item();
        item.setName("");
        item.setDescription("Good game");
        item.setImage("https://s.s-bol.com/imgbase0/imagebase3/large/FC/0/3/6/6/9200000073666630.jpg");
        item.setPrice(new BigDecimal("49.99"));

        boolean successState = itemService.create(item);

        assertFalse(successState);
    }

    @Test
    public void testCreateWithNoName() {
        Item item = new Item();
        item.setDescription("Good game");
        item.setImage("https://s.s-bol.com/imgbase0/imagebase3/large/FC/0/3/6/6/9200000073666630.jpg");
        item.setPrice(new BigDecimal("49.99"));

        boolean successState = itemService.create(item);

        assertFalse(successState);
    }

    @Test
    public void testCreateWithBlankDescription() {
        Item item = new Item();
        item.setDescription("");
        item.setName("Mario Odyssey");
        item.setImage("https://s.s-bol.com/imgbase0/imagebase3/large/FC/0/3/6/6/9200000073666630.jpg");
        item.setPrice(new BigDecimal("49.99"));

        boolean successState = itemService.create(item);

        assertFalse(successState);
    }

    @Test
    public void testCreateWithNoDescription() {
        Item item = new Item();
        item.setName("Mario Odyssey");
        item.setImage("https://s.s-bol.com/imgbase0/imagebase3/large/FC/0/3/6/6/9200000073666630.jpg");
        item.setPrice(new BigDecimal("49.99"));

        boolean successState = itemService.create(item);

        assertFalse(successState);
    }

    @Test
    public void testCreateWithNoPrice() {
        Item item = new Item();
        item.setDescription("Good game");
        item.setName("Mario Odyssey");
        item.setImage("https://s.s-bol.com/imgbase0/imagebase3/large/FC/0/3/6/6/9200000073666630.jpg");

        boolean successState = itemService.create(item);

        assertFalse(successState);
    }
}
