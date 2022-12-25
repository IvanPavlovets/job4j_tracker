package ru.job4j.trackingsystem.hibernate.hql;

import lombok.Data;
import lombok.ToString;
import ru.job4j.trackingsystem.hibernate.toone.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
@Data
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "item_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
            )
    private List<User> participates = new ArrayList<>();

}
