package auctionhouse.dto;

import auctionhouse.entities.Bid;
import auctionhouse.entities.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.StringIdGenerator.class,
//        property="id")
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
    private List<BidDto> bids = new ArrayList<>();

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Getter @Setter
    private UserDto seller;
}
