package auctionhouse.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class ItemDto {
    @Getter
    @Setter
    private String name;

    @Getter @Setter
    private BigDecimal price;

    @Getter @Setter
    private String image;
}
