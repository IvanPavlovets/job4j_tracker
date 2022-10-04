package ru.job4j.trackingsystem.io;

import java.util.List;
import java.util.Scanner;

/**
 * Класс для работы с пользователем, реализует интерфейс Input.
 */
public class ConsoleInput implements Input {
    /**
     * Класс Scanner принимает поток ввода (System.in) с консоли,
     * с помощью метода получения полной строки nextLine.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * метод печатает вопрос а потом получает данные
     * из консоли введеные пользователем.
     */
    @Override
    public String ask(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    /**
     * В перегруженом методе происходит преобразование возвращеного значения,
     * с типом String из метода ask в тип int. Затем происходит сравнение
     * этого значения с допустимым диапозоном значений ключа.
     * @param question - вопрос системы ввода/вывода.
     * @param range - диапозон, входит ли значение в данный ключ.
     * @return int
     */
    @Override
    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value : range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Вышли из диапозона меню");
        }
        return key;
    }

}
