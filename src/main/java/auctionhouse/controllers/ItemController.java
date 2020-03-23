package auctionhouse.controllers;


import auctionhouse.entities.Item;
import auctionhouse.entities.User;
import auctionhouse.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    String create(@RequestBody Item item) {
        itemRepository.save(item);

        return "Added";
    }

    @GetMapping()
    String test(){
        return "Test";
    }
}
