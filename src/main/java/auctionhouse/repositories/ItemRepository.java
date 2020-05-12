package auctionhouse.repositories;

import auctionhouse.entities.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item, Integer> {
}
