package auctionhouse.controllers;


import auctionhouse.dto.BidDto;
import auctionhouse.dto.ItemDto;
import auctionhouse.dto.UserDto;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("items")
@CrossOrigin
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
    public ResponseEntity<ItemDto> get(@RequestParam int id) {
        Item item = itemService.get(id);
        if(item == null) {
            throw new IllegalArgumentException();
        }

        item.getSeller().setInventory(null);
        item.setBids(item.getBids());
        return ResponseEntity.ok().body(convertToDto(item));
    }

    @GetMapping(path = "/getrange",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ItemDto>> getRange(@RequestParam int startRange, @RequestParam int endRange) {
        List<Item> items = itemService.getRange(startRange, endRange);
        if(items == null) {
            throw new IllegalArgumentException();
        }
        List<ItemDto> itemDtos = new ArrayList<>();
        for (Item item: items) {
            itemDtos.add(convertToDto(item));
        }



        return ResponseEntity.ok().body(itemDtos);
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
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setImage(item.getImage());
        itemDto.setDescription(item.getDescription());
        itemDto.setName(item.getName());
        itemDto.setPrice(item.getPrice());

        item.getBids().forEach((bid -> {
            BidDto bidDto = new BidDto();
            bidDto.setId(bid.getId());
            bidDto.setAmount(bid.getAmount());
            bidDto.setUsername(bid.getBidder().getUsername());
            itemDto.getBids().add(bidDto);
        }));

        UserDto userDto = new UserDto();
        userDto.setEmail(item.getSeller().getEmail());
        userDto.setId(item.getSeller().getId());
        userDto.setUsername(item.getSeller().getUsername());
        itemDto.setSeller(userDto);

        return itemDto;
    }
}
