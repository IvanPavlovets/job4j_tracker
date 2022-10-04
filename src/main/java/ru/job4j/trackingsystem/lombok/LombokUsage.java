package ru.job4j.trackingsystem.lombok;

import ru.job4j.trackingsystem.model.ItemData;

public class LombokUsage {
    public static void main(String[] args) {
        var bird = new BirdData();
        bird.setAge(1);
        System.out.println(bird);

        var item = new ItemData();
        item.setId("1");
        System.out.println(item);

    }
}
