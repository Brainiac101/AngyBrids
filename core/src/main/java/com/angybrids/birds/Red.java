package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Red  extends Bird {
    public Red() {
        this.sprite = new Sprite(new Texture("birds/red.png"));
        this.sprite.setScale(0.2f);
    }
    public Red(World world) {
        super(world);
        this.sprite = new Sprite(new Texture("birds/red.png"));
        this.sprite.setSize(sprite.getWidth() * 0.2f, sprite.getHeight() * 0.2f);
        sprite.setPosition(125, 175);
        this.health = 2;
    }
    public Red(int x, int y){
        this.sprite = new Sprite(new Texture("birds/red.png"));
        this.sprite.setPosition(x, y);
    }
    public Body createBody(){
        return super.createBody(0.2f);
    }
}
