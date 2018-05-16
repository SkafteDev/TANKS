package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.data.parts.IEntityPart;

import java.util.*;


public abstract class Entity {
    private final UUID ID = UUID.randomUUID();
    private Map<Class, IEntityPart> parts = new HashMap<>();

    /**
     * Adds a part to an entity
     * @param part
     */
    public void add(IEntityPart part) {
        parts.put(part.getClass(), part);
    }

    /**
     * Removes a part from an entity
     * @param partClass
     */
    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    /**
     * Gets a part of a specific type from the entity
     * @param partClass
     * @param <E>
     * @return
     */
    public <E extends IEntityPart> E getPart(Class partClass) {

        for (IEntityPart part : parts.values()) {
            if (partClass.isInstance(part)) {

                return (E) part;
            }

        }

        return null;
    }

    /**
     * Gets all parts
     * @return
     */
    public Collection<IEntityPart> getParts() {
        return parts.values();
    }

    /**
     * Gets the id of the entity
     * @return
     */
    public String getID() {
        return ID.toString();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
