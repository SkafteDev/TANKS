package dk.grp1.tanks.common.data;

import dk.grp1.tanks.common.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by danie on 12-03-2018.
 */
public class GameData {

        private final int GAMEWIDTH = 160;
        private final int GAMEHEIGHT = 90;

        private float delta;
        private int displayWidth;
        private int displayHeight;
        private final GameKeys keys = new GameKeys();
        private List<Event> events = new CopyOnWriteArrayList<>();

        public void addEvent(Event e) {
            events.add(e);
        }

        public void removeEvent(Event e) {
            events.remove(e);
        }

        public List<Event> getEvents() {
            return events;
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

        public void setDisplayWidth(int width) {
            this.displayWidth = width;
        }

        public float getGameWidth() { return GAMEWIDTH; }

        public float getGameHeight() {return GAMEHEIGHT;}

//        public int getDisplayWidth() {
//            return displayWidth;
//        }

        public void setDisplayHeight(int height) {
            this.displayHeight = height;
        }

//        public int getDisplayHeight() {
//            return displayHeight;
//        }

        public <E extends Event> List<Event> getEvents(Class<E> type, String sourceID) {
            List<Event> r = new ArrayList();
            for (Event event : events) {
                if (event.getClass().equals(type) && event.getSource().getID().equals(sourceID)) {
                    r.add(event);
                }
            }

            return r;
        }

    public <E extends Event> List<Event> getEvents(Class<E> type) {
        List<Event> r = new ArrayList();
        for (Event event : events) {
            if (event.getClass().equals(type)) {
                r.add(event);
            }
        }

        return r;
    }
    }


