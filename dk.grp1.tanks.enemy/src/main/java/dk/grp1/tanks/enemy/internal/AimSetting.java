package dk.grp1.tanks.enemy.internal;

public enum AimSetting {

    RIGHT(3.14f * 0 / 8f),
    RIGHTSLIGHTUP(3.14f * 1 /8f),
    RIGHTFORTYFIVE(3.14f * 2 /8f),
    RIGHTMUCHUP(3.14f * 3/8f),
    UP(3.14f *4/8f),
    LEFTMUCHUP(3.14f * 5 /8f),
    LEFTFORTYFIVE(3.14f * 6/8f),
    LEFTSLIGHTUP(3.14f * 7 /8f),
    LEFT(3.14f * 8 / 8f);

    private float aim;

    AimSetting(float aim){

        this.aim = aim;
    }

    public float getAim() {
        return aim;
    }
}
