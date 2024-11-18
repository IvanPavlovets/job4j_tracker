package ru.job4j.trackingsystem.menu;

import ru.job4j.trackingsystem.io.Input;
import ru.job4j.trackingsystem.model.Item;
import ru.job4j.trackingsystem.model.Store;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс содержит действие меню - найти заявку по имени.
 */
class FindItemByName extends BaseAction {

    protected FindItemByName(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Store tracker) {
        String name = input.ask("Введите имя заявки");
        for (Item item : tracker.findByName(name)) {
            System.out.println("Имя: " + item.getName() + " Описание: " + item.getDescription());
        }
    }
}

/**
 * Класс содержит действия меню - добавить новую заявку, показать все заявки.
 */
public class MenuTracker {

    /**
     * Переменная хранит экземпляр системы ввода/вывода.
     */
    private Input input;

    /**
     * Переменная хранит экземпляр класса хранилища
     * и всех утилитных методов трекинговой системы.
     */
    private Store tracker;

    /**
     * Указатель позиции. Что бы добовлять события в систему.
     */
    private int position = 0;


    /**
     * массив содержит все действия системы.
     */
    private List<UserAction> actions = new ArrayList<>();

    public final List<Integer> getActionsNumbers() {
        ArrayList<Integer> ranges = new ArrayList<>();
        for (int i = 0; i < actions.size(); i++) {
            ranges.add(i);
        }
        return ranges;
    }

    public MenuTracker(Input input, Store tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Выводит информацию про событие.
     * Метод инициализирует внутренее событие.
     * Создаем экземпляр внутренего класса
     * и инициализируем его передаными обьектами.
     */
    public void fillActions() {
        this.actions.add(position++, this.new AddItem(0, "=== Добавить новую заявку ==="));
        this.actions.add(position++, new ShowItems(1, "=== Показать все заявки системы ==="));
        this.actions.add(position++, new EditItem(2, "=== Редактировать заявку ==="));
        this.actions.add(position++, new DeleteItem(3, "=== Удалить заявку ==="));
        this.actions.add(position++, new FindItemById(4, "=== Найти заявку по id ==="));
        this.actions.add(position++, new FindItemByName(5, "=== Найти заявку по имени ==="));
    }

    /**
     * Метод принимает событие и добовляет его в хранилище системы.
     * @param action - добовляемое событие - действие в трекинговойсистеме.
     */
    public void addAction(UserAction action) {
        this.actions.set(position++, action);
    }

    /**
     * Метод выполняет действия которые выбрал пользователь.
     * Возвращает действие которое храниться в массиве.
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Метод печатает меню.
     */
    public void show() {
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(action.info());
            }
        }
    }

    /**
     * Внутрений класс определяет внтутренюю логику действия меню по добовлению заявки.
     */
    private class AddItem extends BaseAction {

        public AddItem(int key, String name) {
            super(key, name);
        }

        /**
         * Выполняет действие по добовлению заявки.
         * @param input - обьект интерфейса ввода.
         * @param tracker - базовый класс всех утилитных методов действий трекинговой системы.
         */
        @Override
        public void execute(Input input, Store tracker) {
            String name = input.ask("Введите имя новой заявки :");
            String desc = input.ask("Введите описание новой заявки :");
            tracker.add(new Item(name, desc));
        }
    }

    /**
     * Внутрений класс содержит внтутренюю логику меню по удалению заявки.
     */
    public static class DeleteItem extends BaseAction {

        protected DeleteItem(int key, String name) {
            super(key, name);
        }

        /**
         * Выполняет действие по удалению заявки.
         * @param input - обьект интерфейса ввода.
         * @param tracker - базовый класс всех утилитных методов действий трекинговой системы.
         */
        @Override
        public void execute(Input input, Store tracker) {
            String id = input.ask("Введите id заявки");
            System.out.println("Заявка с именем: " + tracker.findById(id).getName() + " удалена!");
            tracker.delete(id);
        }
    }

    /**
     * Внутрений класс действия меню по показу всех заявок.
     */
    private static class ShowItems extends BaseAction {

        protected ShowItems(int key, String name) {
            super(key, name);
        }

        /**
         * Выполняет действие по показу всех заявок.
         * @param input - обьект интерфейса ввода.
         * @param tracker - базовый класс всех утилитных методов действий трекинговой системы.
         */
        @Override
        public void execute(Input input, Store tracker) {
            for (Item item : tracker.findAll()) {
                System.out.println(String.format("%s. %s. %s", item.getName(), item.getDescription(), item.getId()));
            }
        }
    }

    /**
     * Статический Внутрений класс реализует действия меню по поиску заявки по id.
     */
    public static class FindItemById extends BaseAction {

        protected FindItemById(int key, String name) {
            super(key, name);
        }

        /**
         * Выполняет действие по поиску заявки по id.
         * @param input - обьект интерфейса ввода.
         * @param tracker - базовый класс всех утилитных методов действий трекинговой системы.
         */
        @Override
        public void execute(Input input, Store tracker) {
            String id = input.ask("Введите id заявки");
            System.out.println("Найденная заявка: " + tracker.findById(id).getName());
        }
    }

}
