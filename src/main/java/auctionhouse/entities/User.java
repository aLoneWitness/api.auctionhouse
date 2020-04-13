package auctionhouse.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Integer id;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @OneToMany(mappedBy="seller")
    @Getter @Setter
    private List<Item> inventory;

}
