package ru.job4j.trackingsystem.menu;

import ru.job4j.trackingsystem.io.Input;
import ru.job4j.trackingsystem.model.Item;
import ru.job4j.trackingsystem.model.Store;

/**
 * Класс содержит действие меню - редактировать заявку.
 */
public class EditItem extends BaseAction {

    public EditItem(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Store tracker) {
        String id = input.ask("Введите id заменяемой заявки");
        if (tracker.findById(id) == null) {
            System.out.println("Нет такой заявки!");
        }
        String name = input.ask("Введите имя новой заявки :");
        String desc = input.ask("Введите описание новой заявки :");
        Item item = new Item(name, desc);
        tracker.replace(id, item);
    }
}
