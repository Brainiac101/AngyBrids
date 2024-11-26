package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.angybrids.level.Level.SCALE_FACTOR;

public class Blue extends Bird {

    public Blue(){
        this.sprite = new Sprite(new Texture("birds/blue.png"));
        this.sprite.setScale(0.3f);
    }

    public Blue(World world) {
        super(world);
        this.sprite = new Sprite(new Texture("birds/blue.png"));
        this.sprite.setSize(sprite.getWidth() * 0.3f, sprite.getHeight() * 0.3f);
        this.sprite.setPosition(125, 175);
//        this.health = 1;
    }

    public Blue(int x, int y) {
        this.sprite = new Sprite(new Texture("birds/blue.png"));
        this.sprite.setPosition(x, y);
    }

    public Blue(World world, int x, int y) {
        super(world);
        this.sprite = new Sprite(new Texture("birds/blue.png"));
        this.sprite.setPosition(x, y);
    }

    public Body createBody(){
        return super.createBody(0.3f);
    }
}
