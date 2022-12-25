package ru.job4j.trackingsystem.hibernate;

import org.junit.Test;
import ru.job4j.trackingsystem.hibernate.hql.HbmTracker;
import ru.job4j.trackingsystem.hibernate.hql.Item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TrackerHbmTest {
    @Test
    public void whenAddNewItemThenTrackerHasSameItem() throws Exception {
        try (var tracker = new HbmTracker()) {
            Item item = new Item();
            item.setName("test1");
            item.setDescription("desc1");
            tracker.add(item);
            Item result = tracker.findById(item.getId());
            assertThat(result.getName(), is(item.getName()));
        }
    }

    @Test
    public void whenReplaceItemThenTrackerHasSameReplaceItem() throws Exception {
        try (var tracker = new HbmTracker()) {
            var item = new Item();
            item.setName("test1");
            item.setDescription("desc1");
            tracker.add(item);
            var replaceItem = new Item();
            item.setName("test2");
            item.setDescription("desc2");
            tracker.replace(item.getId(), replaceItem);
            Item result = tracker.findById(item.getId());
            assertThat(result.getName(), is(replaceItem.getName()));
        }
    }

    @Test
    public void whenDeleteItemThenTrackerHasNotItem() throws Exception {
        try (var tracker = new HbmTracker()) {
            var item = new Item();
            item.setName("t");
            item.setDescription("d");
            tracker.add(item);
            tracker.delete(item.getId());
            int result = tracker.findByName(item.getName()).size();
            assertThat(result, is(0));
        }
    }

    @Test
    public void whenFindAllThenTrackerGetMoreThenZeroItem() throws Exception {
        try (var tracker = new HbmTracker()) {
            var item = new Item();
            item.setName("t");
            item.setDescription("d");
            tracker.add(item);
            boolean result = tracker.findAll().size() > 0;
            assertThat(result, is(true));
        }
    }

    @Test
    public void whenFindByNameThenTrackerFindIt() throws Exception {
        try (var tracker = new HbmTracker()) {
            var item = new Item();
            item.setName("tt");
            item.setDescription("dd");
            tracker.add(item);
            var result = tracker.findByName(item.getName()).get(0);
            assertThat(result.getName(), is(item.getName()));
        }
    }


}
