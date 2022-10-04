package ru.job4j.trackingsystem.menu;

public abstract class BaseAction implements UserAction {
    private final int key;
    private final String name;

    protected BaseAction(final int key, final String name) {
        this.key = key;
        this.name = name;
    }

    @Override
    public int key() {
        return this.key;
    }

    /**
     * сообщает пользователю о том что данный пункт меню делает.
     * @return - строка формируеться по шаблону
     * принимающиму в качестве значений передоваемые данные
     * для сложения 2х строк
     */
    @Override
    public String info() {
        return String.format("%s. %s", this.key, this.name);
    }

}
