package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.data.World;
import dk.grp1.tanks.common.data.parts.IEntityPart;
import dk.grp1.tanks.common.eventManager.events.SoundEvent;

public class SoundPart implements IEntityPart {

    private String shootSoundPath;
    private String onHitSoundPath;

    public SoundPart(){

    }
    public SoundPart(String shootSoundPath, String onHitSoundPath){

        this.shootSoundPath = shootSoundPath;
        this.onHitSoundPath = onHitSoundPath;
    }

    @Override
    public void processPart(Entity entity, GameData gameData, World world) {

    }

    /**
     * Returns the path to the resource containing the sound of shooting
     * @return
     */
    public String getShootSoundPath() {
        return shootSoundPath;
    }


    /**
     * Returns the path to the resource containing the sound of impact
     * @return
     */
    public String getOnHitSoundPath() {
        return onHitSoundPath;
    }


}
