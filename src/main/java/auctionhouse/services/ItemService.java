package auctionhouse.services;

import auctionhouse.dto.BidDto;
import auctionhouse.entities.Bid;
import auctionhouse.entities.Item;
import auctionhouse.repositories.ItemRepository;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public boolean create(Item item) {
        if(item.getName() == null || item.getName().isBlank() || item.getPrice() == null || item.getDescription() == null || item.getDescription().isBlank()) return false;
        itemRepository.save(item);
        return true;
    }

    public Item get(int id) {
        if(id == 0) return null;
        Optional<Item> item = itemRepository.findById(id);
        return item.orElse(null);
    }

    public List<Item> getRange(int startRange, int endRange) {
        if(endRange < startRange) return null;
        Iterable<Item> iterableItems = itemRepository.findAll();
        List<Item> itemsList = StreamSupport
                .stream(iterableItems.spliterator(), false)
                .collect(Collectors.toList());
        List<Item> returnItems = new ArrayList<>();
        for (int i = startRange; i <= endRange; i++) {
            if(i >= itemsList.size()) break;
            returnItems.add(itemsList.get(i));
        }

        return returnItems;
    }

    public boolean addBid(int itemId, Bid bid) {
        if(itemId == 0) return false;
        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isEmpty()) return false;
        Item actualItem = item.get();

        if(bid.getBidder() == null) return false;
        for (Bid previousBid : actualItem.getBids()){
            int res = previousBid.getAmount().compareTo(bid.getAmount());
            if(res > 0 || res == 0) return false;
        }

        actualItem.getBids().add(bid);

        itemRepository.save(actualItem);
        return true;
    }


}
