package auctionhouse.controllers;


import auctionhouse.dto.ItemDto;
import auctionhouse.entities.Item;
import auctionhouse.entities.User;
import auctionhouse.repositories.ItemRepository;
import auctionhouse.services.ItemService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("items")
@CrossOrigin
public class ItemController {

    private final ItemService itemService;

    private final ModelMapper modelMapper;

    @Autowired
    public ItemController(ItemService itemService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.modelMapper = modelMapper;
    }


    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void create(@RequestBody ItemDto itemDto) {

        if(!itemService.create(convertToEntity(itemDto))) {
            throw new IllegalArgumentException();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Item> get(@RequestParam int id) {
        Item item = itemService.get(id);
        if(item == null) {
            throw new IllegalArgumentException();
        }
        return ResponseEntity.ok().body(item);
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
