package auctionhouse.dto;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class BidDto {
    @Getter
    @Setter
    private Integer Id;

    @Getter @Setter
    private BigDecimal amount;
}
