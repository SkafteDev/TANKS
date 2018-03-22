package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.utils.Vector2D;

import java.util.List;

/**
 * A GameMapFunction is part of what makes up the game map. the game map consists of potentially several functions defining the map structure
 * The functions covers from the startX value to the endX value of the map.
 */
public interface IGameMapFunction extends Comparable {
    /**
     * Get the StartX value. Which is the value from where the function starts
     * @return float StartX
     */
    float getStartX();

    /**
     * Get the Y value based on the given xValue. The Y value is calculated based on the implemented function
     * @param xValue float xValue
     * @return float YValue
     */
    float getYValue(float xValue);

    /**
     * Get the EndX value. Which is the value from where the function ends
     * @return float EndX
     */
    float getEndX();

    /**
     * Get the Y values based on the given xValues. The Y values are calculated based on the implemented function
     * @param xValues List of floats
     * @return List of floats containing yValues. Matching the index of x values.
     */
    List<Float> getYValues(List<Float> xValues);

    /**
     * Checks if the given x value is within this functions range
     * @param x
     * @return
     */
    boolean isWithin(float x);

    void setEndX(float value);

    void setStartX(float value);

    /**
     * Checks whether or not the function exists ONLY within the given range.
     * @param startX the start value for the given range
     * @param endX the end value for the given range
     * @return True/false
     */
    boolean existsOnlyWithinRange(float startX, float endX);

    /**
     * Split the function into two new functions that have two new ranges. This is used if a function needs to be split to incorporate a circle in between. Other uses may be for map generation
     * @param rangeOneStartX
     * @param rangeOneEndX
     * @param rangeTwoStartX
     * @param rangeTwoEndX
     * @return Returns two new IGameMapFunctions with identical implementation to the parent, only the range have changed. If added to the map. Remember to remove parent function.
     */
    List<IGameMapFunction> splitInTwoWithNewRanges(float rangeOneStartX, float rangeOneEndX, float rangeTwoStartX, float rangeTwoEndX);
}
