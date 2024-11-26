package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.angybrids.level.Level.SCALE_FACTOR;

public class Matilda extends Bird {
    public Matilda(World world) {
        super(world);
        this.sprite = new Sprite(new Texture("birds/matilda.png"));
        this.sprite.setSize(sprite.getWidth() * 0.3f, sprite.getHeight() * 0.3f);
        this.sprite.setPosition(125,175);
        this.health = 3;
    }
    public Matilda(){
        this.sprite = new Sprite(new Texture("birds/matilda.png"));
        this.sprite.setScale(0.3f);
    }
    public Matilda(int x, int y){
        this.sprite = new Sprite(new Texture("birds/matilda.png"));
        this.sprite.setPosition(x, y);
    }
    public Matilda(int x, int y, float scale){
        this.sprite = new Sprite(new Texture("birds/matilda.png"));
        this.sprite.setPosition(x, y);
        this.sprite.setScale(scale);
    }
    public Body createBody(){
        return super.createBody(0.09f);
    }
}
