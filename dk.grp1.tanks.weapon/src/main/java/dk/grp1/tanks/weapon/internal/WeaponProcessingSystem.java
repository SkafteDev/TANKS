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

    private WeaponFactory wepFac = new WeaponFactory();

    @Override
    public void process(World world, GameData gameData) {

        for (Entity bullet : world.getEntities(Projectile.class)) {

            List<PriorityWrapper<IEntityPart>> partPriorities = new ArrayList<>();

            for (IEntityPart part : bullet.getParts()) {
                partPriorities.add(WeaponEntityPartPriority.getPriorityWrapper(part));
            }

            Collections.sort(partPriorities, new PriorityWrapperComparator());

            for (PriorityWrapper<IEntityPart> part : partPriorities) {
                part.getType().processPart(bullet, gameData, world);
            }
        }
    }
}
