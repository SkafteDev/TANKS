package dk.grp1.tanks.enemy.internal;

public class Action {
    private FirePowerLevel firePowerLevel;
    private AimSetting aim;

    public Action(FirePowerLevel firePowerLevel, AimSetting aim) {

        this.firePowerLevel = firePowerLevel;
        this.aim = aim;
    }

    public FirePowerLevel getFirePowerLevel() {
        return firePowerLevel;
    }

    public AimSetting getAim() {
        return aim;
    }
}
