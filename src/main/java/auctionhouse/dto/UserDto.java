package auctionhouse.dto;

import auctionhouse.entities.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserDto {
    @Getter @Setter
    private String name;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private List<Item> inventory;
}
