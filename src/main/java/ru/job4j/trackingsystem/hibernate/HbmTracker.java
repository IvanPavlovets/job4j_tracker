package ru.job4j.trackingsystem.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(Integer id, Item item) {
        boolean rsl = false;
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery(
                    "UPDATE Item as i SET i.name = :fName, i.description = :fDescription"
                            + " where id = :fId")
                    .setParameter("fName", item.getName())
                    .setParameter("fDescription", item.getDescription())
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public boolean delete(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery(
                "DELETE Item WHERE id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return false;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> itemList = session.createQuery(
                "from ru.job4j.trackingsystem.hibernate.Item").list();
        session.getTransaction().commit();
        session.close();
        return itemList;
    }

    @Override
    public List<Item> findByName(String key) {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> itemList = session.createQuery(
                "from Item where name = :key").list();
        session.getTransaction().commit();
        session.close();
        return null;
    }

    @Override
    public Item findById(Integer id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
