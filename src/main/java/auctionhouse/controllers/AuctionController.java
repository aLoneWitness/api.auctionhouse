package auctionhouse.controllers;

import auctionhouse.entities.Bid;
import auctionhouse.entities.User;
import auctionhouse.messages.BidMessage;
import auctionhouse.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class AuctionController {
    // TODO: Implement proper websocket security
    @SendTo("/topic/auction/{itemId}")
    public BidMessage bid(@DestinationVariable int itemId, BidMessage newBid) {
        return newBid;
    }

    @MessageExceptionHandler
    public String handleException(IllegalArgumentException ex) {
        return "Caught error: " + ex.getMessage();
    }
}
