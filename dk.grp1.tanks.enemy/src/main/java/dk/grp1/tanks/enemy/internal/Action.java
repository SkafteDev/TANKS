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

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Aim:");
        builder.append(aim.getAim());
        builder.append("\n");
        builder.append("FirePower:");
        builder.append(firePowerLevel.getFirepoweer());
        return builder.toString();
    }
}
