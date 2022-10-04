package ru.job4j.trackingsystem.model;

import lombok.Data;

@Data
public class ItemData {
    private String id;

    private String name;

    private String description;

    private long create;
}
