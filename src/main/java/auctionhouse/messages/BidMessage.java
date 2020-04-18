package auctionhouse.messages;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class BidMessage {
    @Getter @Setter
    private String from;
    @Getter @Setter
    private BigDecimal amount;
}
