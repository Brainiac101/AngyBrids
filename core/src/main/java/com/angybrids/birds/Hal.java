package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.angybrids.level.Level.SCALE_FACTOR;

public class Hal extends Bird {

    public Hal(World world) {
        super(world);
        this.sprite = new Sprite(new Texture("birds/hal.png"));
        this.sprite.setSize(sprite.getWidth() * 0.35f, sprite.getHeight() * 0.35f);
        this.sprite.setPosition(125, 175);
        this.health = 3;
    }
    public Hal(){
        this.sprite = new Sprite(new Texture("birds/hal.png"));
        this.sprite.setScale(0.3f);
    }
    public Hal(int x, int y){
        this.sprite = new Sprite(new Texture("birds/hal.png"));
        this.sprite.setPosition(x, y);
    }
    public Hal(int x, int y, float scale){
        this.sprite = new Sprite(new Texture("birds/hal.png"));
        this.sprite.setPosition(x, y);
        this.sprite.setScale(scale);
    }
    public Body createBody(){
        return super.createBody(0.1f);
    }
}
