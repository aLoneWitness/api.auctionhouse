package auctionhouse.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.StringIdGenerator.class,
//        property="id")
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @JsonIgnore
    private Integer id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private BigDecimal price;

    @Getter @Setter
    private String image;

    @Getter @Setter
    private String description;

    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    @Getter @Setter
    @OneToMany(fetch = FetchType.EAGER)
    private List<Bid> bids = new ArrayList<>();

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Getter @Setter
    private User seller;
}
