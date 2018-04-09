package dk.grp1.tanks.common.services;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.data.World;

public interface IRoundEndService {

    /**
     * The round is over if no more than one entity with a TurnPart exists
     * @param world
     * @return
     */
    boolean isRoundOver(World world);

    /**
     * Gets the winner of the round.
     * if no winner exists (if the game is still running, or the game was a draw), null is returned.
     * @param world
     * @return
     */
    Entity getRoundWinner(World world);
}
