package dk.grp1.tanks.common.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class WorldTest {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private GameMap gameMap = new GameMap(10,10);
    private World world;

    @Before
    public void setUp() throws Exception {
        world = new World();
        world.setGameMap(gameMap);
    }

    private Entity createEntity(){
        Entity e = new Entity() {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return e;
    }

    private Entity createEntity1(){
        Entity e1 = new Entity() {
            @Override
            public String toString() {
                return super.toString();
            }
        };
        return e1;
    }

    @Test
    public void getGameMap() {
        assertEquals(gameMap, world.getGameMap());
    }

    @Test
    public void setGameMap() {
        assertEquals(this.gameMap, world.getGameMap());
        GameMap map = new GameMap(100,100);
        world.setGameMap(map);
        assertEquals(map, world.getGameMap());
    }

    @Test
    public void addEntity() {
        assertEquals(0, world.getEntities().size());
        Entity e = createEntity();
        world.addEntity(e);
        assertEquals(1, world.getEntities().size());
        assertEquals(e, world.getEntity(e.getID()));
    }

    @Test
    public void removeEntity() {
        Entity e = createEntity();
        world.addEntity(e);
        assertEquals(1, world.getEntities().size());
        assertEquals(e, world.getEntity(e.getID()));
        world.removeEntity(e);
        assertEquals(0, world.getEntities().size());
        assertEquals(null, world.getEntity(e.getID()));
    }

    @Test
    public void removeEntity1() {
        Entity e = createEntity();
        world.addEntity(e);
        assertEquals(1, world.getEntities().size());
        assertEquals(e, world.getEntity(e.getID()));
        world.removeEntity(e.getID());
        assertEquals(0, world.getEntities().size());
        assertEquals(null, world.getEntity(e.getID()));
    }

    @Test
    public void getEntities() {
        List<Entity> es = new ArrayList<>();
        Entity e = createEntity();
        es.add(e);
        Entity e1 = createEntity();
        es.add(e);
        Entity e2 = createEntity();
        es.add(e);
        Entity e3 = createEntity();
        es.add(e);
        Entity e4 = createEntity();
        es.add(e);
        Entity e5 = createEntity();
        es.add(e);
        Entity e6 = createEntity();
        es.add(e);
        Entity e7 = createEntity();
        es.add(e);


        for (Entity entity : es) {
            world.addEntity(entity);
        }

        for (Entity entity : es) {
            assertTrue(world.getEntities().contains(entity));
        }
    }

    @Test
    public void getEntities1() {
        Entity e = createEntity();
        Entity e1 = createEntity1();

        world.addEntity(createEntity());
        world.addEntity(createEntity());
        world.addEntity(createEntity());

        world.addEntity(createEntity1());
        world.addEntity(createEntity1());
        world.addEntity(createEntity1());
        world.addEntity(createEntity1());
        world.addEntity(createEntity1());
        world.addEntity(createEntity1());

        assertEquals(3, world.getEntities(e.getClass()).size());
        assertEquals(6, world.getEntities(e1.getClass()).size());
    }

    @Test
    public void getEntity() {
        Entity e = createEntity();
        world.addEntity(e);
        assertEquals(e, world.getEntity(e.getID()));
    }
}