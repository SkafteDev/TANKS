package dk.grp1.tanks.common.eventManager.events;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.Set;

public class AIEvent extends Event {
    private Set<Vector2D> positions;
    public AIEvent(Entity source, Set<Vector2D> positions) {
        super(source);
        this.positions = positions;
    }

    public Set<Vector2D> getPositions() {
        return positions;
    }
}
