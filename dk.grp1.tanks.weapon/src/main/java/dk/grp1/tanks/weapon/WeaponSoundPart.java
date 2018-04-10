package dk.grp1.tanks.weapon;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.IEntityPart;

public class WeaponSoundPart implements IEntityPart {

    private String shootSoundPath;
    private String onHitSoundPath;

    public WeaponSoundPart(String shootSoundPath, String onHitSoundPath){

        this.shootSoundPath = shootSoundPath;
        this.onHitSoundPath = onHitSoundPath;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }

    public String getShootSoundPath() {
        return shootSoundPath;
    }



    public String getOnHitSoundPath() {
        return onHitSoundPath;
    }


}
