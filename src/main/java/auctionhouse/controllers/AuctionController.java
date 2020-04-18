package auctionhouse.controllers;

import auctionhouse.messages.BidMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AuctionController {

    @MessageMapping("/auction")
    @SendTo("/topic/auction/bids")
    public BidMessage bid(BidMessage newBid) throws Exception {
        BidMessage bidMessage = new BidMessage();
        bidMessage.setFrom(newBid.getFrom());
        bidMessage.setAmount(newBid.getAmount());
        return bidMessage;
    }
}
