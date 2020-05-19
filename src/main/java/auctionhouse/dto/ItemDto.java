package auctionhouse.dto;

import auctionhouse.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class ItemDto {
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private BigDecimal price;

    @Getter @Setter
    private String image;

    @Getter @Setter
    private String description;

    @Getter @Setter
    private UserDto seller;
}
