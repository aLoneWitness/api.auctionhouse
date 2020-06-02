package auctionhouse.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Rating {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    @JsonIgnore
    private Integer Id;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User from;

    @Getter @Setter
    private Integer stars;
}
