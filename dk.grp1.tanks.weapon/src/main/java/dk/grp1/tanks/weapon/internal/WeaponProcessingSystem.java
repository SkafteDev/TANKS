package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.services.IEntityProcessingService;
import dk.grp1.tanks.common.utils.PriorityWrapperComparator;
import dk.grp1.tanks.common.utils.PriorityWrapper;
import dk.grp1.tanks.weapon.Projectile;

import java.util.*;

public class WeaponProcessingSystem implements IEntityProcessingService {


    @Override
    public void process(World world, GameData gameData) {

        for (Entity bullet : world.getEntities(Projectile.class)) {

            // creates a list of parts with priority
            List<PriorityWrapper<IEntityPart>> partPriorities = new ArrayList<>();
            for (IEntityPart part : bullet.getParts()) {
                partPriorities.add(WeaponEntityPartPriority.getPriorityWrapper(part));
            }

            //sort list
            Collections.sort(partPriorities, new PriorityWrapperComparator());

            // process parts in  correct order
            for (PriorityWrapper<IEntityPart> part : partPriorities) {
                part.getType().processPart(bullet, gameData, world);
            }
        }
    }
}
