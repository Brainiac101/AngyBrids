package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.angybrids.level.Level.SCALE_FACTOR;

public class Bomb extends Bird {

    public Bomb(World world) {
        super(world);
        this.sprite = new Sprite(new Texture("birds/bomb.png"));
        this.sprite.setSize(this.sprite.getWidth() * 0.25f, this.sprite.getHeight() * 0.25f);
        this.sprite.setPosition(125, 175);
        this.health = 3;
    }

    public Bomb() {
        this.sprite = new Sprite(new Texture("birds/bomb.png"));
        sprite.setScale(0.25f);
    }

    public Bomb(int x, int y) {
        this.sprite = new Sprite(new Texture("birds/bomb.png"));
        this.sprite.setPosition(x, y);
    }

    public Body createBody() {
        return super.createBody(0.1f);
    }
}
