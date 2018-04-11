package dk.grp1.tanks.weapon.gravitybomb.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.events.SoundEvent;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.utils.Vector2D;

public class GravityBombWeapon implements IWeapon {
        private final String name = "Gravity Bomb";
        private final String description = "Lobs a miniature black hole";
        private final String iconPath = "gravitybomb.png";
        private final String texturePath = "gravitybomb.png";
        private final String explosionTexturePath = "blackHoleSheet.png";
        private final String shootSoundPath = "GravityBombSound.mp3";
        private final String explosionSoundPath = "GravityExplosion.mp3";
        private final int explosionTextureFrameRows = 4;
        private final int explosionTextureFrameCols = 4;

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
            GravityBomb gravityBomb = new GravityBomb();

            CannonPart cannonPart = actor.getPart(CannonPart.class);
            Vector2D cannonCentre = cannonPart.getMuzzleFaceCentre();
            gravityBomb.add(new PositionPart(cannonCentre.getX(),cannonCentre.getY(), cannonPart.getDirection()));
            Vector2D accelerationVector = cannonPart.getDirectionVector();
            accelerationVector.multiplyWithConstant(firePower);
            gravityBomb.add(new MovementPart(accelerationVector, 10000));
            gravityBomb.add(new ShapePart());
            gravityBomb.add(new CirclePart(cannonCentre.getX(),cannonCentre.getY(),7));
            gravityBomb.add(new PhysicsPart(30, -75f));
            gravityBomb.add(new GravityBombCollisionPart(true, 0));
            gravityBomb.add(new DamagePart(50,10));
            gravityBomb.add(new TexturePart(this.texturePath));
            gravityBomb.add(new ExplosionTexturePart(explosionTextureFrameCols,explosionTextureFrameRows,explosionTexturePath));
            SoundPart sounds = new SoundPart(shootSoundPath, explosionSoundPath);
            gravityBomb.add(sounds);
            gameData.getEventManager().addEvent(new SoundEvent(gravityBomb, sounds.getShootSoundPath()));
            world.addEntity(gravityBomb);
        }
}
