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
        if(position == null){
            throw new IllegalArgumentException("Position Vector is null");
        }
        if(path == null){
            throw new IllegalArgumentException("Path is null");
        }
        if(origin == null){
            throw new IllegalArgumentException("Origin entity is null");
        }
        if(frameCols < 0){
            throw new IllegalArgumentException("Frame Cols must be positive");
        }
        if(frameRows < 0){
            throw new IllegalArgumentException("Frame Rows must be positive");
        }
        if(explosionRadius < 0){
            throw new IllegalArgumentException("Explosion Radius must be positive");
        }

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
