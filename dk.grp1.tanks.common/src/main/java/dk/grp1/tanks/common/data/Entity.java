package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.data.parts.IEntityPart;

import java.util.*;

/**
 * Created by danie on 12-03-2018.
 */
public abstract class Entity {
    private final UUID ID = UUID.randomUUID();
    private Map<Class, IEntityPart> parts = new HashMap<>();

    public void add(IEntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends IEntityPart> E getPart(Class partClass) {

        for (IEntityPart part : parts.values()) {
            if (partClass.isInstance(part)) {

                return (E) part;
            }

        }

        return null;
    }

    public Collection<IEntityPart> getParts() {
        return parts.values();
    }

    public String getID() {
        return ID.toString();
    }
}
