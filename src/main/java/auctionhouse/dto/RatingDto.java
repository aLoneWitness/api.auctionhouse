package auctionhouse.dto;

import lombok.Getter;
import lombok.Setter;

public class RatingDto {
    @Setter @Getter
    private int stars;

    @Setter @Getter
    private String username;
}
