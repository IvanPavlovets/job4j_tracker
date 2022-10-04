package ru.job4j.trackingsystem.io;

import java.util.List;

/**
 * Интерфейс системы ввода/вывода трекинговой системы,
 * описывает методы для работы с пользователем.
 */
public interface Input {
    /**
     * Метод возвращает ключ для действия системы.
     * @param question
     * @return
     */
    String ask(String question);

    /**
     * Перегруженый метод ask.
     * @param question - вопрос системы ввода/вывода.
     * @param range - диапозон, входит ли значение в данный ключ.
     * @return - ключ.
     */
    int ask(String question, List<Integer> range);
}


