package dk.grp1.tanks.weapon.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.MovementPart;
import dk.grp1.tanks.common.events.Event;
import dk.grp1.tanks.common.events.ShootingEvent;
import dk.grp1.tanks.common.services.IEntityProcessingService;

public class WeaponProcessingSystem implements IEntityProcessingService {

    private WeaponFactory wepFac = new WeaponFactory();

    @Override
    public void process(World world, GameData gameData) {

        for (Event ev: gameData.getEvents(ShootingEvent.class)
             ) {
            world.addEntity(wepFac.create(ev,gameData));
            gameData.removeEvent(ev);


        }

        for (Entity bullet : world.getEntities(Bullet.class)
                ) {
            MovementPart movePart =  bullet.getPart(MovementPart.class);
            movePart.processPart(bullet, gameData);

        }
    }
}
