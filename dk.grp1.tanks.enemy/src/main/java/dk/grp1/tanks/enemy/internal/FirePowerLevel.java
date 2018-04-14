package dk.grp1.tanks.enemy.internal;

public enum FirePowerLevel {
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100),
    MAX(250);

    private float firepoweer;

    FirePowerLevel(float firepoweer){

        this.firepoweer = firepoweer;
    }

    public float getFirepoweer() {
        return firepoweer;
    }
}
