package auctionhouse.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    @OneToMany
    private List<Bid> bids;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User seller;
}
