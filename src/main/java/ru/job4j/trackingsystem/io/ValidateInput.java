package ru.job4j.trackingsystem.io;

import java.util.List;

/**
 * Класс выполняет проверку ввода данных пользователем.
 */
public class ValidateInput implements Input {

    /**
     * Полученая система ввода.
     */
    private final Input input;

    public ValidateInput(Input input) {
        this.input = input;
    }

    @Override
    public String ask(String question) {
        return this.input.ask(question);
    }

    @Override
    public int ask(String question, List<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Пожайлуста введите значения из диапозона меню.");
            }  catch (NumberFormatException nfe) {
                System.out.println("Пожайлуста введите корректные даные еще раз.");
            }
        } while (invalid);
        return value;
    }
}

