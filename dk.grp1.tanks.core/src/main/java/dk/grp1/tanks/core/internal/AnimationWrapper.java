package dk.grp1.tanks.core.internal;

import dk.grp1.tanks.common.data.Entity;
import dk.grp1.tanks.common.utils.Vector2D;
import javafx.animation.Animation;

public class AnimationWrapper {
    private float stateTime;
    private Vector2D position;
    private String path;
    private int frameCols;
    private int frameRows;
    private Entity origin;
    private float explosionRadius;

    public AnimationWrapper(Vector2D position, String path, Entity origin, int frameCols, int frameRows, float explosionRadius) {
        this.explosionRadius = explosionRadius;
        this.position = position;
        this.path = path;
        this.origin = origin;
        this.frameCols = frameCols;
        this.frameRows = frameRows;
        this.stateTime = 0.0f;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public int getFrameCols() {
        return frameCols;
    }

    public int getFrameRows() {
        return frameRows;
    }

    public float getStateTime() {
        return stateTime;
    }

    public Vector2D getPosition() {
        return position;
    }

    public String getPath() {
        return path;
    }

    public Entity getOrigin() {
        return origin;
    }

    public void updateStateTime(float delta) {
        stateTime += delta;
    }
}
