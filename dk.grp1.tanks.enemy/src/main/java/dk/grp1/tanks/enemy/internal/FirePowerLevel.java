package dk.grp1.tanks.enemy.internal;

public enum FirePowerLevel {
    TEN(10),
    FIFTEEN(15),
    TWENTY(20),
    TWENTYFIVE(25),
    THIRTY(30),
    FORTY(40),
    FIFTY(50),
    SIXTY(60),
    EIGHTY(80),
    HUNDRED(100),
    HUNDREDTWENTY(120),
    HUNDREDEIGHTY(180),
    TWOHUNDREDTWENTY(220),
    MAX(250);

    private float firepoweer;

    FirePowerLevel(float firepoweer){

        this.firepoweer = firepoweer;
    }

    public float getFirepoweer() {
        return firepoweer;
    }
}
