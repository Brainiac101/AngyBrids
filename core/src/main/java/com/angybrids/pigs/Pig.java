package com.angybrids.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

import static com.angybrids.level.Level.SCALE_FACTOR;


public abstract class Pig implements Serializable {
    protected Sprite sprite;
    protected Body body;
    protected Texture texture;
    protected int health;
    protected World world;

    public Pig() {
    }

    public Pig(World world) {
        this.world = world;
    }

    public Pig(World world, int x, int y) {
        this.world = world;
    }

    public Pig(World world, int x, int y, float scale) {
        this.world = world;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public abstract Body createBody();

    public Body createBody(float d) {
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set((sprite.getX() + sprite.getWidth() / 2) / SCALE_FACTOR, (sprite.getY() + sprite.getHeight() / 2) / SCALE_FACTOR);
        body = world.createBody(bd);
//        CircleShape shape = new CircleShape();
        PolygonShape shape = new PolygonShape();
//        shape.setRadius(sprite.getWidth() / 2/SCALE_FACTOR);
        shape.setAsBox(sprite.getWidth() / 2 / SCALE_FACTOR, sprite.getHeight() / 2 / SCALE_FACTOR);
        body.createFixture(shape, d);
        body.setUserData(sprite);
        shape.dispose();
        return body;
    }

    public void dispose(){
        this.texture.dispose();
    }
}
