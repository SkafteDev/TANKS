package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.utils.PriorityWrapper;

public class WeaponEntityPartPriority {

    /**
     * Returns a PriorityWrapper containing the input type and the priority.
     * The default priority is 100.
     * @param part
     * @return
     */
    public static PriorityWrapper<IEntityPart> getPriorityWrapper(IEntityPart part) {

        int priority = 100;

        if (part.getClass().isInstance(PhysicsPart.class)) {
            priority = 0;
        } else if (part.getClass().isInstance(CollisionPart.class)) {
            priority = 1;
        } else if (part.getClass().isInstance(MovementPart.class)) {
            priority = 10;
        }

        return new PriorityWrapper<>(priority, part);
    }
}
