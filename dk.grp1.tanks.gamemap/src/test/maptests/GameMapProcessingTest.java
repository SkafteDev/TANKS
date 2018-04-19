package maptests;

import dk.grp1.tanks.common.data.*;
import dk.grp1.tanks.common.eventManager.events.Event;
import dk.grp1.tanks.common.eventManager.events.MapDestructionEvent;
import dk.grp1.tanks.common.services.IGamePluginService;
import dk.grp1.tanks.common.utils.Vector2D;
import dk.grp1.tanks.gamemap.internal.GameMapLinear;
import dk.grp1.tanks.gamemap.internal.GameMapProcessing;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GameMapProcessingTest {

    @Test
    public void testMapProcessing(){
        World world = new World();
        GameData gd = new GameData();
        GameMap map = new GameMap(100,50);
        List<IGameMapFunction> funcs = new ArrayList<>();
        IGameMapFunction func = new GameMapLinear(0,19,0,100);
        funcs.add(func);
        map.setGameMapFunctions(funcs);
        world.setGameMap(map);
        GameMapProcessing processor = new GameMapProcessing(gd, world);
        Event event = new MapDestructionEvent(new Entity() {
        }, new Vector2D(50,20),20);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
        processor.processEvent(event);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() > 1);
    }


    @Test
    public void testMapProcessingFlatMapExplosionExactlyOnTheLine(){
        World world = new World();
        GameData gd = new GameData();
        GameMap map = new GameMap(100,50);
        List<IGameMapFunction> funcs = new ArrayList<>();
        IGameMapFunction func = new GameMapLinear(0,20,0,100);
        funcs.add(func);
        map.setGameMapFunctions(funcs);
        world.setGameMap(map);
        GameMapProcessing processor = new GameMapProcessing(gd, world);
        Event event = new MapDestructionEvent(new Entity() {
        }, new Vector2D(50,20),20);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
        processor.processEvent(event);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() > 1);
    }

    @Test
    public void testMapProcessingSteepMapExplosionExactlyOnTheLine(){
        World world = new World();
        GameData gd = new GameData();
        GameMap map = new GameMap(100,50);
        List<IGameMapFunction> funcs = new ArrayList<>();
        IGameMapFunction func = new GameMapLinear(0,20,0,49.99f);
        funcs.add(func);
        IGameMapFunction func2 = new GameMapLinear(1000, 49.99f, 50.01f,func);
        funcs.add(func2);
        IGameMapFunction func3 = new GameMapLinear(0,50.01f,100,func2);
        funcs.add(func3);
        map.setGameMapFunctions(funcs);
        world.setGameMap(map);
        GameMapProcessing processor = new GameMapProcessing(gd, world);
        Event event = new MapDestructionEvent(new Entity() {
        }, new Vector2D(50,30),10);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 3);
        processor.processEvent(event);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() > 3);
    }

    @Test
    public void testMapProcessingBigExplosion(){
        World world = new World();
        GameData gd = new GameData();
        GameMap map = new GameMap(100,50);
        List<IGameMapFunction> funcs = new ArrayList<>();
        IGameMapFunction func = new GameMapLinear(0,20,0,100);
        funcs.add(func);
        map.setGameMapFunctions(funcs);
        world.setGameMap(map);
        GameMapProcessing processor = new GameMapProcessing(gd, world);
        Event event = new MapDestructionEvent(new Entity() {
        }, new Vector2D(50,20),50);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
        processor.processEvent(event);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
    }

    @Test
    public void testMapProcessingNoIntersect(){
        World world = new World();
        GameData gd = new GameData();
        GameMap map = new GameMap(100,50);
        List<IGameMapFunction> funcs = new ArrayList<>();
        IGameMapFunction func = new GameMapLinear(0,20,0,100);
        funcs.add(func);
        map.setGameMapFunctions(funcs);
        world.setGameMap(map);
        GameMapProcessing processor = new GameMapProcessing(gd, world);
        Event event = new MapDestructionEvent(new Entity() {
        }, new Vector2D(50,80),20);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
        processor.processEvent(event);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
    }

    @Test
    public void testMapProcessingAlmostIntersect(){
        World world = new World();
        GameData gd = new GameData();
        GameMap map = new GameMap(100,50);
        List<IGameMapFunction> funcs = new ArrayList<>();
        IGameMapFunction func = new GameMapLinear(0,20,0,100);
        funcs.add(func);
        map.setGameMapFunctions(funcs);
        world.setGameMap(map);
        GameMapProcessing processor = new GameMapProcessing(gd, world);
        Event event = new MapDestructionEvent(new Entity() {
        }, new Vector2D(50,30),9.79f);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
        processor.processEvent(event);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
    }

    @Test
    public void testMapProcessingBarelyIntersect(){
        World world = new World();
        GameData gd = new GameData();
        GameMap map = new GameMap(100,50);
        List<IGameMapFunction> funcs = new ArrayList<>();
        IGameMapFunction func = new GameMapLinear(0,20,0,100);
        funcs.add(func);
        map.setGameMapFunctions(funcs);
        world.setGameMap(map);
        GameMapProcessing processor = new GameMapProcessing(gd, world);
        Event event = new MapDestructionEvent(new Entity() {
        }, new Vector2D(50,30),10);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() == 1);
        processor.processEvent(event);
        Assert.assertTrue(world.getGameMap().getGameMapFunctions().size() > 1);
    }


}
