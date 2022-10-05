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

        var category = new Category(3);
        category.setName("value");
        System.out.println(category.getName());
        System.out.println(category);
        System.out.println(category.hashCode());

        var role = Role.of()
                .id(1)
                .name("ADMIN")
                .accessBy("create")
                .accessBy("update")
                .accessBy("read")
                .accessBy("delete")
                .build();
        System.out.println(role);

        var permission = Permission.of()
                .id(5)
                .name("User")
                .rules("one")
                .rules("two")
                .rules("three")
                .build();
        System.out.println(permission);
    }
}
