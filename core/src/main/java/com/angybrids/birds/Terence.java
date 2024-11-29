package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Terence extends Bird {
    public Terence() {
        this.sprite = new Sprite(new Texture("birds/terence.png"));
        sprite.setScale(0.4f);
    }

    public Terence(World world) {
        super(world);
        this.sprite = new Sprite(new Texture("birds/terence.png"));
        this.sprite.setSize(this.sprite.getWidth() * 0.4f, this.sprite.getHeight() * 0.4f);
        this.sprite.setPosition(125,175);
        this.health = 4;
    }

    public Terence(int x, int y) {
        this.sprite = new Sprite(new Texture("birds/terence.png"));
        this.sprite.setPosition(x, y);
    }

    public Terence(int x, int y, float scale) {
        this.sprite = new Sprite(new Texture("birds/terence.png"));
        this.sprite.setPosition(x, y);
        this.sprite.setScale(scale);
    }
    public Body createBody(){
        return super.createBody(0.075f);
    }
}
