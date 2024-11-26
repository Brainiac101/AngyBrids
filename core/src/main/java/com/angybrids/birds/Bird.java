package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import static com.angybrids.level.Level.SCALE_FACTOR;

public abstract class Bird {
    protected Sprite sprite;
    protected Body body;
    protected Texture texture;
    protected int health;
    protected BodyDef bd;
    protected World world;

    public Bird() {
    }

    public Bird(World world) {
        this.world = world;
        bd = new BodyDef();
    }

    public Bird(World world, int x, int y) {
        this.world = world;
        bd = new BodyDef();
    }

    public Bird(World world, int x, int y, float scale) {
        this.world = world;
        bd = new BodyDef();
    }

    public Body createBody(float d) {
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set((sprite.getX() + sprite.getWidth() / 2) / SCALE_FACTOR, (sprite.getY() + sprite.getHeight() / 2) / SCALE_FACTOR);
        body = world.createBody(bd);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sprite.getWidth() / 2 / SCALE_FACTOR, sprite.getHeight() / 2 / SCALE_FACTOR);
        body.createFixture(shape, d);
        body.setUserData(sprite);
        shape.dispose();
        return body;
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
}
