package dk.grp1.tanks.weapon.HomingMissile.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.weapon.HomingMissile.internal.AI.*;

import java.util.List;

public class HomingMissileWeapon implements IWeapon {

    private final String name = "Homing Missile";
    private final String description = "Fires a single big shot";
    private final String iconPath = "missile.png";
    private final String shootSoundPath= "boom.mp3";
    private final String texturePath = "missile.png";
    private final String explosionTexturePath = "explosion.png";
    private final int explosionTextureFrameRows = 6;
    private final int explosionTextureFrameCols = 8;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public void shoot(Entity actor, GameData gameData, float firePower, World world) {
        HomingMissile wep = new HomingMissile();

        CannonPart cannonPart = actor.getPart(CannonPart.class);
        Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
        wep.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
        Vector2D accelerationVector = cannonPart.getDirectionVector();
        accelerationVector.multiplyWithConstant(0);
        wep.add(new ShapePart());
        wep.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),2));
        wep.add(new PhysicsPart(30, -0f));
        wep.add(new CollisionPart(false,0));
        wep.add(new DamagePart(20,10));
        wep.add(new TexturePart(this.texturePath));
        wep.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
        SoundPart sounds = new SoundPart("boom.mp3","boom.mp3");
        wep.add(sounds);
        gameData.getEventManager().addEvent(new SoundEvent(wep,sounds.getShootSoundPath()));

        State initialState = new State(world.getGameMap(),wep);
        IGoalSelector goalSelector = new GoalSelector(world,gameData,actor,wep);
        State goalState = goalSelector.calculateGoalState();
        System.out.println("GOAL STATE FOUND " + goalState.getEntityPosition());
        ITreeSearch ai = new AStar(initialState,goalState);
        List<Vector2D> path = ai.searchPoints();
        System.out.println("ASTAR COMPLETE");
        System.out.println(path.toString());
        wep.add(new HomingMovementPart(path));

        world.addEntity(wep);
    }
}
