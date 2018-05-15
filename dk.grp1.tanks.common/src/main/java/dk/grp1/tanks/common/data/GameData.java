package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.eventManager.EventManager;
import dk.grp1.tanks.common.services.IRoundService;
import dk.grp1.tanks.common.services.IWeapon;
import dk.grp1.tanks.common.services.IWeaponListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danie on 12-03-2018.
 */
public class GameData {

    private int GAMEWIDTH = 800;
    private int GAMEHEIGHT = 600;

    private float delta;
    private Boolean shouldEndTurn = false;
    private final GameKeys keys = new GameKeys();
//    private List<Event> events = new CopyOnWriteArrayList<>();
    private List<IWeapon> gameWeapons = new ArrayList<>();
    private List<IWeaponListener> weaponListeners = new ArrayList<>();
    private EventManager eventManager = new EventManager();
    private IRoundService turnManager;

    public GameData(int gameWidth, int gameHeight){
        this.GAMEWIDTH = gameWidth;
        this.GAMEHEIGHT = gameHeight;
    }

    public GameData(){
        this.GAMEWIDTH = 800;
        this.GAMEHEIGHT = 600;
    }

    public Boolean shouldEndTurn() {
        return shouldEndTurn;
    }

    public void setShouldEndTurn(Boolean shouldEndTurn) {
        this.shouldEndTurn = shouldEndTurn;
    }

    public GameKeys getKeys() {
        return keys;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getDelta() {
        return delta;
    }

    public void setGameWidth(int width) {
        this.GAMEWIDTH = width;
    }

    public float getGameWidth() {
        return GAMEWIDTH;
    }

    public float getGameHeight() {
        return GAMEHEIGHT;
    }

    public IRoundService getTurnManager() {
        return turnManager;
    }

    public void setTurnManager(IRoundService turnManager) {
        this.turnManager = turnManager;
    }

    public void setGameHeight(int height) {
        this.GAMEHEIGHT = height;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public void addWeapon(IWeapon weapon) {
        if (weapon != null) {
            this.gameWeapons.add(weapon);
            for (IWeaponListener listener : weaponListeners) {
                listener.weaponAdded(weapon, this);
            }

        }
    }

    public void removeWeapon(IWeapon weapon) {
        if (weapon != null) {
            this.gameWeapons.remove(weapon);
            for (IWeaponListener listener : weaponListeners) {
                listener.weaponRemoved(weapon, this);
            }

        }
    }

    public void addWeaponListener(IWeaponListener listener) {
        this.weaponListeners.add(listener);
    }

    public void removeWeponListener(IWeaponListener listener) {
        this.weaponListeners.remove(listener);
    }

    public List<IWeapon> getWeapons() {
        return this.gameWeapons;
    }
}


