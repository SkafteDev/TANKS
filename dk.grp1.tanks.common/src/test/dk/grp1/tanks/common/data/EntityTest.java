package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.data.parts.IEntityPart;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class EntityTest {

    @Test
    public void add() {
        // Initialize parts that is to get added to the Entity
        List<IEntityPart> parts = new ArrayList<>();
        parts.add(getNewDefaultPart());
        parts.add(getNewDefaultPart2());
        parts.add(getNewDefaultPart3());
        parts.add(getNewDefaultPart4());
        parts.add(getNewDefaultPart5());

        Entity entity = getNewDefaultEntityWithParts(parts);

        // Assert that all the parts have been added
        assertTrue(entity.getParts().size() == 5);

        // Assert that it is in fact the correct parts that was added.
        for (IEntityPart part : entity.getParts()) {
            assertTrue(parts.contains(part));
        }

    }

    @Test
    public void remove() {
        Entity entity = getNewDefaultEntityWithParts();
        IEntityPart[] parts = entity.getParts().toArray(new IEntityPart[0]);

        int size = parts.length;

        for (int i = 0; i < size; i++) {
            IEntityPart partToRemove = parts[i];
            entity.remove(partToRemove.getClass()); // Remove part from entity
            assertFalse(entity.getParts().contains(partToRemove)); // Test if the part was actually removed.
        }
    }

    @Test
    public void getPart() {
        Entity entity = getNewDefaultEntityWithParts();
        IEntityPart[] parts = entity.getParts().toArray(new IEntityPart[0]); // Just the Java-way to convert to an array.

        int size = parts.length;

        for (int i = 0; i < size; i++) {
            IEntityPart partOne = parts[i];
            IEntityPart partTwo = entity.getPart(partOne.getClass());
            assertEquals(partOne, partTwo);
        }
    }

    @Test
    public void getParts() {
        Entity entity = getNewDefaultEntityWithParts();
        assertTrue(entity.getParts().size() == 5);

        Entity entity2 = getNewDefaultEntity();
        assertTrue(entity2.getParts().size() == 0);
    }

    @Test
    public void getID() {
        Entity entity = getNewDefaultEntity();
        boolean testPassed;

        try {
            // This will throw an illegal argument, if the ID is malformed
            UUID uuid = UUID.fromString(entity.getID());
            testPassed = true;
        } catch (IllegalArgumentException ex) {
            testPassed = false;
        }

        assertTrue(testPassed);
    }

    //region Anonymous classes used for testing only
    private static Entity getNewDefaultEntity() {
        return new Entity() {
            @Override
            public void add(IEntityPart part) {
                super.add(part);
            }

            @Override
            public void remove(Class partClass) {
                super.remove(partClass);
            }

            @Override
            public <E extends IEntityPart> E getPart(Class partClass) {
                return super.getPart(partClass);
            }

            @Override
            public Collection<IEntityPart> getParts() {
                return super.getParts();
            }

            @Override
            public String getID() {
                return super.getID();
            }
        };
    }

    private static IEntityPart getNewDefaultPart() {
        return new IEntityPart() {
            @Override
            public void processPart(Entity entity, GameData gameData, World world) {
                System.out.println(this.getClass());
            }
        };
    }

    private static IEntityPart getNewDefaultPart2() {
        return new IEntityPart() {
            @Override
            public void processPart(Entity entity, GameData gameData, World world) {
                System.out.println(this.getClass());
            }
        };
    }

    private static IEntityPart getNewDefaultPart3() {
        return new IEntityPart() {
            @Override
            public void processPart(Entity entity, GameData gameData, World world) {
                System.out.println(this.getClass());
            }
        };
    }

    private static IEntityPart getNewDefaultPart4() {
        return new IEntityPart() {
            @Override
            public void processPart(Entity entity, GameData gameData, World world) {
                System.out.println(this.getClass());
            }
        };
    }

    private static IEntityPart getNewDefaultPart5() {
        return new IEntityPart() {
            @Override
            public void processPart(Entity entity, GameData gameData, World world) {
                System.out.println(this.getClass());
            }
        };
    }

    private static Entity getNewDefaultEntityWithParts() {
        Entity entity = getNewDefaultEntity();

        // Initialize parts that is to get added to the Entity
        List<IEntityPart> parts = new ArrayList<>();
        parts.add(getNewDefaultPart());
        parts.add(getNewDefaultPart2());
        parts.add(getNewDefaultPart3());
        parts.add(getNewDefaultPart4());
        parts.add(getNewDefaultPart5());

        // Add the parts
        for (IEntityPart part : parts) {
            entity.add(part);
        }

        return entity;
    }

    private static Entity getNewDefaultEntityWithParts(List<IEntityPart> parts) {
        Entity entity = getNewDefaultEntity();

        // Add the parts
        for (IEntityPart part : parts) {
            entity.add(part);
        }

        return entity;
    }
    //endregion
}