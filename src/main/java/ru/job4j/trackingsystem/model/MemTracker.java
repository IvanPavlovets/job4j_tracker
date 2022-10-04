package ru.job4j.trackingsystem.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Класс - являеться хранилищем заявок
 * и осуществляет основные операции взоимодеяствия с заявками Item.
 */
public class MemTracker implements Store {
    /**
     * хранилище заявок.
     */
    private List<Item> items = new ArrayList<>();

    /**
     * указатель ячейки добавляемой заявки.
     */
    private int position = 0;

    private static final Random RN = new Random();

    @Override
    public boolean init() throws ClassNotFoundException, SQLException {
        return false;
    }

    /**
     * Метод добавления заявки в хранилище.
     * При добавлении генерируеться случайный id.
     *
     * @param item - добовляемая заявка.
     * @return - обьект заявки добавляемый в хранилище.
     */
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(this.position++, item);
        return item;
    }

    /**
     * Метод редактирования заявки.
     * Заменяет ячейку в коллекции item
     * найденую по id.
     * Вновь вставленой заявке присваеваеться новый id если еще не создан.
     *
     * @param id      - аргумент сравниваеться с id элементов массива items.
     * @param newItem - новый обьект массива items который замещает найденый id.
     */
    public boolean replace(String id, Item newItem) {
        for (int i = 0; i != this.position; i++) {
            if (items.get(i) != null && items.get(i).getId().equals(id)) {
                if (newItem.getId() == null) {
                    newItem.setId(this.generateId());
                }
                items.set(i, newItem);
            }
        }
        return false;
    }

    /**
     * Метод удаления заявки.
     * Удаление происходит по индексу совпавшего элемента по id.
     *
     * @param id - аргумент сравниваеться с id элементов коллекции items.
     */
    public boolean delete(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                this.items.remove(i);
            }
        }
        return false;
    }

    /**
     * Метод возвращает список всех заявок в списке result.
     * Возвращаемое количество заявок соответсвует
     * текущему положению указателя добавленых элементов.
     *
     * @return - список всех заявок.
     */
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        for (Item item : this.items) {
            result.add(item);
        }
        return result;
    }

    /**
     * Метод возвращает список заявок по имени.
     * Проверяет в цикле все элементы массива items,
     * сравнивая name с аргументом метода String key.
     * Элементы, у которых совпадает name,
     * копирует в result.
     *
     * @param key - аргумент сравниваеться с именами
     *            элементов массива items.
     * @return - массив совпавших элементов.
     */
    public List<Item> findByName(String key) {
        ArrayList<Item> result = new ArrayList<>();
        for (Item item : this.items) {
            if (item != null && item.getName().equals(key)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Метод возвращает обьект заявки по id.
     * Проверяет в цикле все элементы массива items
     * сравнивая id элемента с аргументом String id.
     *
     * @param id - аргумент сравниваеться с id элементов массива items.
     * @return - совпавший по id обьект item.
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : this.items) {
            if (item != null && item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }

    /**
     * Метод генерации id для новой заявки.
     * Сумма из сгенерированного случайного числа
     * и текущего времени в милисекундах
     * переводиться в тип String.
     *
     * @return - уникальный ключ.
     */
    private String generateId() {
        return String.valueOf(System.currentTimeMillis() + RN.nextInt());
    }

    @Override
    public void close() throws Exception {

    }
}

