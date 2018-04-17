package dk.grp1.tanks.common.data.parts;

import dk.grp1.tanks.common.data.GameData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TurnPartTest {


    @Test
    public void setCurrentTurnNumber() {
        int turnNumber = 5;
        TurnPart.setCurrentTurnNumber(turnNumber);
        assertEquals(TurnPart.getCurrentTurnNumber(), turnNumber);
    }

    @Test
    public void getCurrentTurnNumber() {
        int turnNumber = -1;
        TurnPart.setCurrentTurnNumber(turnNumber);
        assertEquals(TurnPart.getCurrentTurnNumber(), turnNumber);
    }

    @Test
    public void resetPart() {

        // Add 1st
        int turnNumber = 5;
        TurnPart.setCurrentTurnNumber(turnNumber);
        assertEquals(TurnPart.getCurrentTurnNumber(), turnNumber);

        // Add 2nd
        turnNumber = 10;
        TurnPart.setCurrentTurnNumber(turnNumber);
        assertEquals(TurnPart.getCurrentTurnNumber(), turnNumber);

        // Add 3rd
        turnNumber = 14;
        TurnPart.setCurrentTurnNumber(turnNumber);
        assertEquals(TurnPart.getCurrentTurnNumber(), turnNumber);

        TurnPart.resetPart();

        assertEquals(TurnPart.getCurrentTurnNumber(), 0);
        assertEquals(TurnPart.getTurnPartCount(), 0);
    }

    @Test
    public void processPart() {
    }

    @Test
    public void isMyTurn() {
        TurnPart turnPart1 = new TurnPart();
        TurnPart.setCurrentTurnNumber(turnPart1.getMyTurnNumber()); // Set the turn to be the first TurnPart that was created

        assertTrue(turnPart1.isMyTurn()); // Test for this condtion

        // Create two more TurnPart, which should increase the turn part count, but not shift turn
        TurnPart turnPart2 = new TurnPart();
        TurnPart turnPart3 = new TurnPart();

        assertTrue(turnPart1.isMyTurn()); // This should still hold

        TurnPart.setCurrentTurnNumber(turnPart3.getMyTurnNumber());
        assertTrue(turnPart3.isMyTurn()); // Turn should have shifted
    }

    @Test
    public void getTurnTimeRemaining() {
        TurnPart turnPart = new TurnPart();
        GameData gameData = new GameData();
        gameData.setDelta(2.5f);

        float expectedTurnTimeRemaining = turnPart.getTurnDuration() - gameData.getDelta();
        TurnPart.setCurrentTurnNumber(turnPart.getMyTurnNumber()); // Make it the turn parts' turn, or time won't be processed.
        turnPart.processPart(null, gameData, null);

        assertEquals(expectedTurnTimeRemaining, turnPart.getTurnTimeRemaining(), 0.01f);
    }

    @Test
    public void getMyTurnNumber() {

        int n = 20;
        List<TurnPart> turnPartList = new ArrayList<>();

        // Create a list of turn parts
        for (int i = 0; i < n ; i++) {
            turnPartList.add(new TurnPart());
        }

        // Traverse the list backwards (why not?) and check that the turn number has been given correctly in sequence
        // 0, 1, 2, 3, 4, ..., n-1
        for (int i = n-1; i >= 0 ; i--) {
            int myTurnNumber = turnPartList.get(i).getMyTurnNumber();
            assertTrue(myTurnNumber == i);
        }

    }

    @Test
    public void endMyTurn() {
        TurnPart turnPart = new TurnPart();

        turnPart.endMyTurn(); // Set a flag for ending turn

        assertTrue(turnPart.getEndMyTurn() == true);
    }
}