package dk.grp1.tanks.enemy.internal;

public enum AimSetting {
    LEFT(3.14f * 3 / 4f),
    RIGHT(3.14f / 4f);

    private float aim;

    AimSetting(float aim){

        this.aim = aim;
    }

    public float getAim() {
        return aim;
    }
}
