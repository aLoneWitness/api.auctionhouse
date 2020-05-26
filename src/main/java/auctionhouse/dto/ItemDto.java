package auctionhouse.dto;

import auctionhouse.entities.User;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property="id")
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

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Getter @Setter
    private UserDto seller;
}
