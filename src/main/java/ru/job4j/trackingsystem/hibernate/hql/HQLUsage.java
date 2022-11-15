package ru.job4j.trackingsystem.hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

/**
 * 1. SessionFactory - конфигуратор
 * создания пулов, загрузка кешей,
 * проверка моделей.
 * 2. Session - CRUD операции с БД.
 */
public class HQLUsage {
    public static void main(String[] args) {
        /**
         * Получение SessionFactory.
         * configure() читает hibernate.cfg.xml
         */
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try (SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory()) {
            Session session = sf.openSession();
            /* working with session */
            /* 1. SELECT */
            /* Показать все обьекты связаные с классом Item. */
            Query query = session.createQuery("from Item");
            for (Object st : query.list()) {
                System.out.println(st);
            }
            HQLUsage.unique(session);
            HQLUsage.findById(session, 39);
            HQLUsage.update(session, 39);
            var item = new Item();
            HQLUsage.insert(session);
            Query query1 = session.createQuery("from Item");
            for (Object st : query1.list()) {
                System.out.println(st);
            }
            session.close();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * Деомнстрация выборки по условию where
     * as - псевдоним, краткая запись Item
     * @param session
     */
    public static void unique(Session session) {
        Query<Item> query = session.createQuery(
                "from Item as i where i.id = 41", Item.class);
        System.out.println(query.uniqueResult());
    }

    /**
     * Пример с получением параметров, извне,
     * по их имени. Вместо знака "?" пишем "fId".
     * Главное что би имя в запросе (fId) и имя при
     * установлении параметра совподали.
     * @param session
     * @param id
     */
    public static void findById(Session session, int id) {
        Query<Item> query = session.createQuery(
                "from Item as i where i.id = :fId", Item.class);
        query.setParameter("fId", id);
        System.out.println(query.uniqueResult());
    }

    /**
     * Операция изменения данных.
     * Меняем значение поля name
     * в обьекте с id равным
     * значению переданому в параметре.
     * @param session
     * @param id
     */
    public static void update(Session session, int id) {
        try {
            session.beginTransaction();
            session.createQuery(
                    "UPDATE Item SET name = :fName WHERE id = :fId")
                    .setParameter("fName", "new name")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Удаление обьекта Item
     * где переданный id равен
     * соответсвующему :fId
     * переданому в параметре.
     * @param session
     * @param id
     */
    public static void delete(Session session, int id) {
        try {
            session.beginTransaction();
            session.createQuery(
                    "DELETE Item WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    /**
     * Опреация вставки значения в таблицу
     * запрос подомен SQL запросу.
     * @param session
     */
    public static void insert(Session session) {
        try {
            session.beginTransaction();
            session.createNativeQuery(
                    "INSERT INTO items (name) VALUES (:fName)")
                    .setParameter("fName", "new name")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }
}
