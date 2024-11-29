package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

import static com.angybrids.level.Level.SCALE_FACTOR;

public abstract class Block implements Serializable {
    public Vector2 position;
    public float width, height;
    public String type;
    public Sprite sp;
    public Body body;
    public int hp;
    private World world;

    public Block(){}

    public Block(World w){
        this.world = w;
    }
    public abstract Body createBody();

    public Block(Vector2 position, float width, float height, String type) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.type = type;
        this.sp = new Sprite(new Texture("blocks/" + type + ".png"));
        sp.setPosition(position.x, position.y);
        sp.setSize(width, height);
        if(type.equals("wood")){
            this.hp = 2;
        }
        else if(type.equals("glass")){
            this.hp = 1;
        }
        else if(type.equals("stone")){
            this.hp = 3;
        }
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body createBody(float d){
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(sp.getX() / SCALE_FACTOR, sp.getY() / SCALE_FACTOR);
        // Create the physics body
        body = world.createBody(bd);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(sp.getWidth() / 2 / SCALE_FACTOR, sp.getHeight() / 2 / SCALE_FACTOR);
        body.createFixture(shape, d);
        body.setUserData(sp);
        shape.dispose();
        return body;
    }


}
