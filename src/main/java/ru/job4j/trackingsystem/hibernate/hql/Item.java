package ru.job4j.trackingsystem.hibernate.hql;

import lombok.Data;
import ru.job4j.trackingsystem.hibernate.toone.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "items")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "participates",
            joinColumns = { @JoinColumn(name = "item_id")},
            inverseJoinColumns = { @JoinColumn(name = "user_id")}
            )
    private List<User> participates;

}
