package auctionhouse.services;

import auctionhouse.entities.Item;
import auctionhouse.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public boolean create(Item item) {
        if(item.getId() == null || item.getName().isBlank() || item.getPrice() == null) return false;
        itemRepository.save(item);
        return true;
    }
}
