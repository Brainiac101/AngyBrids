package com.angybrids.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Crazy extends Pig {
    public Crazy(){
    texture = new Texture("pigs/crazyPig.png");
    this.sprite = new Sprite(texture);
}

    public Crazy(World world) {
        super(world);
        texture = new Texture("pigs/crazyPig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(sprite.getWidth(), sprite.getHeight());
        this.sprite.setPosition(125, 175);
        this.health = 3;
    }

    public Crazy(int x, int y) {
        texture = new Texture("pigs/crazyPig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
    }

    public Crazy(World world, int x, int y) {
        super(world);
        texture = new Texture("pigs/crazyPig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(sprite.getWidth(), sprite.getHeight());
        this.sprite.setPosition(x, y);
        this.health = 3;
    }

    public Body createBody(){
        return super.createBody(0.3f);
    }
}
