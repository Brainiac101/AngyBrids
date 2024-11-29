package com.angybrids.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class King extends Pig {
    public King() {
        texture = new Texture("pigs/kingpig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setScale(0.18f);
    }

    public King(World world) {
        super(world);

        texture = new Texture("pigs/kingpig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(sprite.getWidth() * 0.18f, sprite.getHeight() * 0.18f);
        this.sprite.setPosition(125, 175);
        this.health = 5;
    }

    public King(int x, int y) {
        texture = new Texture("pigs/kingpig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
    }

    public King(World world, float x, float y) {
        super(world);
        position = new Vector2(x, y);
        texture = new Texture("pigs/kingpig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(sprite.getWidth() * 0.18f, sprite.getHeight() * 0.18f);
        this.sprite.setPosition(x, y);
        this.health = 5;
    }

    public Body createBody() {
        return super.createBody(0.3f);
    }
}
