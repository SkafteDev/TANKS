package dk.grp1.tanks.weapon.HomingMissile.internal.AI;

import dk.grp1.tanks.common.data.GameData;
import dk.grp1.tanks.common.eventManager.events.AIEvent;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.HashSet;
import java.util.Set;

public class Consumer {

    private Set<Node> shared;
    private GameData gameData;
    private Thread t;

    public Consumer(GameData gameData, Set<Node> sharedRessource) {
        this.gameData = gameData;
        this.shared = sharedRessource;
    }

    public void startConsuming(){
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean bool = true;
                while(bool){
                    Set<Vector2D> vector2DS = new HashSet<>();
                    for (Node node : shared) {
                        vector2DS.add(node.getState().getEntityPosition());
                    }
                    gameData.getEventManager().addEvent(new AIEvent(null,vector2DS));
                   try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }

            }
        });
        t.start();
    }
    public void stopConsuming(){
        Set<Vector2D> vector2DS2 = new HashSet<>();
        for (Node node : shared) {
            vector2DS2.add(node.getState().getEntityPosition());
        }
        gameData.getEventManager().addEvent(new AIEvent(null,vector2DS2));
        t.stop();
    }
}
