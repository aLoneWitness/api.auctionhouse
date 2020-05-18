package auctionhouse.controllers;


import auctionhouse.dto.BidDto;
import auctionhouse.dto.ItemDto;
import auctionhouse.entities.Bid;
import auctionhouse.entities.Item;
import auctionhouse.entities.User;
import auctionhouse.messages.BidMessage;
import auctionhouse.services.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("items")
public class ItemController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ItemService itemService;

    private final ModelMapper modelMapper;


    @Autowired
    public ItemController(SimpMessagingTemplate simpMessagingTemplate, ItemService itemService, ModelMapper modelMapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void create(@AuthenticationPrincipal User user, @RequestBody ItemDto itemDto) {
        Item item = convertToEntity(itemDto);
        item.setSeller(user);
        if(!itemService.create(item)) {
            throw new IllegalArgumentException();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> get(@RequestParam int id) {
        Item item = itemService.get(id);
        if(item == null) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok().body(item);
    }

    @GetMapping(path = "/getrange",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> getRange(@RequestParam int startRange, @RequestParam int endRange) {
        List<Item> items = itemService.getRange(startRange, endRange);
        if(items == null) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok().body(items);
    }

    @PostMapping(path = "/addbid", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> placeBid(@AuthenticationPrincipal User user, @RequestBody BidDto bidDto){
        Bid bid = new Bid();
        bid.setAmount(bidDto.getAmount());
        bid.setBidder(user);

        if(itemService.addBid(bidDto.getItemId(), bid)){
            BidMessage bidMessage = new BidMessage();
            bidMessage.setAmount(bid.getAmount());
            bidMessage.setFrom(user.getUsername());
            this.simpMessagingTemplate.convertAndSend("/topic/auction/" + bidDto.getItemId(), bidMessage);
            return ResponseEntity.accepted().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ IllegalArgumentException.class })
    public void handleIllegalArgumentException(){}

    private Item convertToEntity(ItemDto itemDto) {
        return modelMapper.map(itemDto, Item.class);
    }

    private ItemDto convertToDto(Item item) {
        return modelMapper.map(item, ItemDto.class);
    }
}
