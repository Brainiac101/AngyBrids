package com.angybrids.level;

import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

public class Projectile implements Serializable {
    private final float gravity;
    public Vector2 startVelocity;
    public Vector2 startPoint;

    public Projectile(float gravity) {
        this.gravity = gravity;
        startVelocity = new Vector2();
        startPoint = new Vector2();
    }

    public float getX(float t) {
        return startVelocity.x * t + startPoint.x;
    }

    public float getY(float t) {
        return 0.5f * gravity * t * t + startVelocity.y * t + startPoint.y ;
    }
}
