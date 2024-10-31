package ru.job4j.trackingsystem.start;

import ru.job4j.trackingsystem.io.ConsoleInput;
import ru.job4j.trackingsystem.io.Input;
import ru.job4j.trackingsystem.io.ValidateInput;
import ru.job4j.trackingsystem.menu.MenuTracker;
import ru.job4j.trackingsystem.model.Store;
import ru.job4j.trackingsystem.tracker.SqlTracker;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс с системой ввода/вывода для работы с пользовательм.
 */
public class StartUI {
    /**
     * Получение данных от пользователя.
     */
    private final Input input;

    /**
     * Хранилище заявок.
     */
    private final Store tracker;

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, Store tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * В методе происходи выбор пункта меню пользователем,
     * в зависимсоти от выбронового пун2кта меню выполнение
     * метода идет по соответсвующему направлению ветвления.
     * Метод fillActions() заполняет данные.
     * Метод show() каждый раз показывает меню.
     * В методе select() каждый раз спрашиваем пункт меню
     * который хочет выбрать пользователь.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, tracker);
        menu.fillActions();
        do {
            menu.show();
            menu.select(input.ask("Выбирите ключ: ", menu.getActionsNumbers()));
        } while (!"y".equals(this.input.ask("Exit?(y):")));
    }

    /**
     * Переменные окружения используется для запуска liquibase,
     * но не используется в самом приложении.
     * Нам нужно добавить эту возможность так же.
     */
    private static String loadSysEnvIfNullThenConfig(String sysEnv, String key, Properties config) {
        String value = System.getenv(sysEnv);
        if (value == null) {
            value = config.getProperty(key);
        }
        return value;
    }

    /**
     * Docker сервисы
     */
    private static Connection loadConnection() throws ClassNotFoundException, SQLException {
        var config = new Properties();
        try (InputStream in = StartUI.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            config.load(in);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        String url = loadSysEnvIfNullThenConfig("JDBC_URL", "url", config);
        String username = loadSysEnvIfNullThenConfig("JDBC_USERNAME", "username", config);
        String password = loadSysEnvIfNullThenConfig("JDBC_PASSWORD", "password", config);
        String driver = loadSysEnvIfNullThenConfig("JDBC_DRIVER", "driver-class-name", config);
        System.out.println("url=" + url);
        Class.forName(driver);
        return DriverManager.getConnection(
                url, username, password
        );
    }

    /**
     * Запускт программы.
     * Вариант запуска с помощью MemeTracker
     *     public static void main(String[] args) {
     *         Input console = new ValidateInput(new ConsoleInput());
     *         Store tracker = new MemTracker();
     *         StartUI startUI = new StartUI(console, tracker);
     *         startUI.init();
     *     }
     * @param args
     */
    public static void main(String[] args) {
        Input validate = new ValidateInput(new ConsoleInput());
        try (Connection connection = loadConnection();
            Store tracker = new SqlTracker()) {
            tracker.init();
            StartUI startUI = new StartUI(validate, tracker);
            startUI.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

