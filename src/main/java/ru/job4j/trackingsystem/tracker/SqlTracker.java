package ru.job4j.trackingsystem.tracker;

import ru.job4j.trackingsystem.db.ConfigValues;
import ru.job4j.trackingsystem.model.Item;
import ru.job4j.trackingsystem.model.Store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlTracker implements Store {
    /**
     * Хранит соединение с бд, подключение получает по url, username, password.
     */
    private Connection cn;

    public SqlTracker() {
    }

    public SqlTracker(Connection cn) {
        this.cn = cn;
    }

    /**
     * Метод осуществляет регистрацию и установку драйвера JDBC, соединение с БД (url, username, password)
     *  ConfigValues - вспомогательный класс для доступа к файлу .properties
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Override
    public boolean init() {
        boolean result = false;
        ConfigValues config = new ConfigValues();
        try {
            Class.forName(config.get("driver-class-name"));
            log("Postgresql JDBC Driver зарегестрирован!");
        } catch (ClassNotFoundException e) {
            log("Не получаеться найти JDBC Driver. Убедитесь что зависимость Maven задана правильно");
            e.printStackTrace();
        }
        try {
            cn = DriverManager.getConnection(config.get("url"),
                    config.get("username"), config.get("password"));
            if (cn != null) {
                result = true;
                log("Соединение успешно созданно! Можно добавлять данные");
            } else {
                log("Не удалось установить соединение!");
            }
        } catch (SQLException e) {
            log("Ошибка подключения!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Методо создания item и добавления ее в БД.
     * PreparedStatement - Хранит и выполняет SQL запросы.
     * ResultSet -
     *
     * @param item
     * @return
     */
    @Override
    public Item add(Item item) {
        String insertQueryStatement = "INSERT INTO items(name, description) VALUES (?,?)";
        try (PreparedStatement ps = cn.prepareStatement(insertQueryStatement, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.executeUpdate();
            log(item.getName() + " успешно добавлена");
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getString(1));
                    log("Сгенерированый Id: " + item.getId());
                } else {
                    log("Вставка данных не удалась");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Метод замены item.
     *
     * @param id
     * @param item
     * @return
     */
    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        String query = "UPDATE items SET name = ?, description = ? WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setInt(3, Integer.parseInt(id));
            result = ps.executeUpdate() > 0;
            log("Замена осуществлена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод удаляет одну заявку.
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        String query = "DELETE FROM items WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(id));
            result = ps.executeUpdate() > 0;
            log("Заявка удалена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод получения всех заявок системы.
     * ResultSet - Получает результаты выполнения SQL запросов(достает данные из таблицы БД)
     * @return
     */
    @Override
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        String query = "Select id, name, description FROM items";
        try (PreparedStatement ps = cn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new Item(rs.getString("id"),
                        rs.getString("name"), rs.getString("description")));
            }
            log("Выборка всех заявок завершена, кол-во: " + result.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> result = new ArrayList<>();
        String query = "Select id, name, description FROM items WHERE name = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add(new Item(rs.getString("id"),
                            rs.getString("name"), rs.getString("description")));
                }
                log("Поиск, по имени заявки, завершен");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Item findById(String id) {
        Item result = null;
        String query = "Select * from items WHERE id = ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result = new Item(rs.getString("id"),
                            rs.getString("name"), rs.getString("description"));
                }
                log("Поиск, по имени заявки, завершен");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        this.cn.close();
    }

    /**
     * Simple log utility
      */
    private static void log(String string) {
        System.out.println(string);
    }
}
