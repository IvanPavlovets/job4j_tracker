package ru.job4j.trackingsystem.hibernate;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
}
