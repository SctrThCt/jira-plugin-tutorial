package ru.teamlead.jira.plugins.tutorial.rest;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.activeobjects.test.TestActiveObjects;
import net.java.ao.EntityManager;
import net.java.ao.test.jdbc.NonTransactional;
import net.java.ao.test.junit.ActiveObjectsJUnitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import ru.teamlead.jira.plugins.tutorial.ao.Tutorialitem;

import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(ActiveObjectsJUnitRunner.class)
public class TableRestTest {
    private EntityManager em;

    ActiveObjects ao;
    private TableRest tableRest;

    @Before
    public void setUp() throws Exception {
        assertNotNull(em);
        ao = new TestActiveObjects(em);
        tableRest = new TableRest(ao);
    }

    @Test
    @NonTransactional
    public void createItem() {
        ao.migrate(Tutorialitem.class);
        assertEquals(0, ao.find(Tutorialitem.class).length);
        tableRest.createItem(TestData.getParams());
        Tutorialitem item = ao.get(Tutorialitem.class, 1);
        compareToReference(item);
        ao.flushAll();
        final Tutorialitem[] items = ao.find(Tutorialitem.class);
        assertEquals(1, items.length);
        compareToReference(items[0]);
    }

    @Test
    @NonTransactional
    public void tableItems() {
        ao.migrate(Tutorialitem.class);
        Tutorialitem item = ao.create(Tutorialitem.class);
        item.setTabordern(0);
        item.setCheckbox(TestData.CHECKBOX_1);
        item.setName(TestData.NAME_1);
        item.setSelect("1");
        item.setCreated(new Date());
        item.save();
        String[] output = TestData.getJsonStringArray((String) tableRest.tableItems().getEntity());
        assertEquals(1, output.length);
        assertEquals(TestData.getReferenceMap(), TestData.jsonStringToHashMap(output[0]));
    }

    public static void compareToReference(Tutorialitem item) {
        Map<String, Object> referenceMap = TestData.getReferenceMap();
        assertEquals(item.getID(), referenceMap.get("id"));
        assertEquals(item.getName(), referenceMap.get("name"));
        assertEquals(item.getSelect(), referenceMap.get("select"));
        assertEquals(item.getCheckbox(), referenceMap.get("checkbox"));
    }
}