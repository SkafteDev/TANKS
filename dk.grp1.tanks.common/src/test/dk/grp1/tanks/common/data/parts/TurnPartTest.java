package dk.grp1.tanks.common.data.parts;


import org.junit.Test;

import static org.junit.Assert.*;

public class TurnPartTest {

    @Test
    public void processPart() {
    }

    @Test
    public void testSetMyTurnAndisMyTurn() {
        TurnPart turnPart = new TurnPart();

        assertTrue(turnPart.isMyTurn() == false);
        turnPart.setMyTurn(true); // Set a flag for ending turn

        assertTrue(turnPart.isMyTurn() == true);
    }


}