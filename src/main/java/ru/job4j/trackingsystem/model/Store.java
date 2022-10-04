package ru.job4j.trackingsystem.model;

import java.sql.SQLException;
import java.util.List;

public interface Store extends AutoCloseable {
    boolean init() throws ClassNotFoundException, SQLException;
    Item add(Item item);
    boolean replace(String id, Item item);
    boolean delete(String id);
    List<Item> findAll();
    List<Item> findByName(String key);
    Item findById(String id);
}
