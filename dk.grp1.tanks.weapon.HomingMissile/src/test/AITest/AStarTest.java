package AITest;

import dk.grp1.tanks.common.data.*;
import dk.grp1.tanks.common.data.parts.*;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.weapon.HomingMissile.internal.AI.*;
import dk.grp1.tanks.weapon.HomingMissile.internal.HomingMissile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AStarTest {

    private World world;
    private GameData gameData;
    private GameMap gameMap;
    private Entity shooter;
    private Entity target;
    private Entity homingMissile;

    @Before
    public void setUp() throws Exception {
        world = new World();
        gameData = new GameData();
        shooter = new TestEntity();
        target = new TestEntity();

        shooter.add(new PositionPart(25,55,0));
        shooter.add(new CirclePart(25,55,5));

        target.add(new PositionPart(300,55,0));
        target.add(new CirclePart(300,55,5));

        world.addEntity(shooter);
        world.addEntity(target);

        gameMap = new GameMap(800,600);
        IGameMapFunction testGameMapFunction = new TestGameMapFunction();
        gameMap.addGameMapFunction(testGameMapFunction);
        world.setGameMap(gameMap);

        homingMissile = new HomingMissile();
        homingMissile.add(new PositionPart(25,55,0));
        homingMissile.add(new ShapePart());
        homingMissile.add(new CirclePart(25,55,2));
        homingMissile.add(new PhysicsPart(30, -0f));
        homingMissile.add(new CollisionPart(false,0));
        homingMissile.add(new DamagePart(3f,10));
    }

    @Test
    public void GoalSelectionTest() {
        IGoalSelector goalSelector = new GoalSelector(world,gameData,shooter,homingMissile);
        State goalState = goalSelector.calculateGoalState();
        PositionPart positionPart = target.getPart(PositionPart.class);

        Assert.assertEquals(positionPart.getX(),goalState.getEntityPosition().getX(), 8f);
        Assert.assertEquals(positionPart.getY(),goalState.getEntityPosition().getY(), 3f);
    }

    @Test
    public void AStarTest() {
        State initialState = new State(gameMap,homingMissile);
        IGoalSelector goalSelector = new GoalSelector(world,gameData,shooter,homingMissile);
        State goalState = goalSelector.calculateGoalState();
        ITreeSearch astar = new AStar(initialState,goalState);
        List<Vector2D> path = astar.searchPoints();
        Assert.assertFalse(path.isEmpty());
        float expectedX = 291;
        for (Vector2D vector2D : path) {
            Assert.assertEquals(expectedX,vector2D.getX(),0.5f);
            expectedX -= 2f;
        }
    }


    @Test
    public void aStarStartAtTop(){
        homingMissile = new HomingMissile();
        homingMissile.add(new PositionPart(250,450,0));
        homingMissile.add(new ShapePart());
        homingMissile.add(new CirclePart(250,450,2));
        homingMissile.add(new PhysicsPart(30, -0f));
        homingMissile.add(new CollisionPart(false,0));
        homingMissile.add(new DamagePart(3f,10));

        State initialState = new State(gameMap,homingMissile);
        IGoalSelector goalSelector = new GoalSelector(world,gameData,shooter,homingMissile);
        State goalState = goalSelector.calculateGoalState();
        ITreeSearch astar = new AStar(initialState,goalState);
        List<Vector2D> path = astar.searchPoints();
        System.out.println(path);
    }

    @Test
    public void aStarSin(){
        gameMap = new GameMap(800,600);
        IGameMapFunction testGameMapFunction = new SinGameMapTest(100f,(1/66f),0,0,0,800);
        gameMap.addGameMapFunction(testGameMapFunction);
        world.setGameMap(gameMap);
        homingMissile = new HomingMissile();
        homingMissile.add(new PositionPart(100,105,0));
        homingMissile.add(new ShapePart());
        homingMissile.add(new CirclePart(100,105,2));
        homingMissile.add(new PhysicsPart(30, -0f));
        homingMissile.add(new CollisionPart(false,0));
        homingMissile.add(new DamagePart(3f,10));
        world.removeEntity(shooter);
        world.removeEntity(target);
        shooter = new TestEntity();
        target = new TestEntity();

        shooter.add(new PositionPart(100,105,0));
        shooter.add(new CirclePart(100,105,5));

        target.add(new PositionPart(590,47,0));
        target.add(new CirclePart(590,47,5));

        world.addEntity(shooter);
        world.addEntity(target);
        State initialState = new State(gameMap,homingMissile);
        IGoalSelector goalSelector = new GoalSelector(world,gameData,shooter,homingMissile);
        State goalState = goalSelector.calculateGoalState();
        ITreeSearch astar = new AStar(initialState,goalState);
        List<Vector2D> path = astar.searchPoints();
        System.out.println(path);
    }

    private class TestEntity extends Entity {
    }
}