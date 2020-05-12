package auctionhouse.entities;

import com.fasterxml.jackson.annotation.*;
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

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @Getter @Setter
    private User seller;
}
