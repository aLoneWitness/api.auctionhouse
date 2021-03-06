package auctionhouse.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.StringIdGenerator.class,
//        property="id")
@Entity
public class User {
    public User(){

    }

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    @JsonIgnore
    private Integer id;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String password;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Getter @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Item> inventory = new ArrayList<>();

    @Getter @Setter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();
}
