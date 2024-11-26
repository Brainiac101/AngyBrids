package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Chuck extends Bird {
    public Chuck(World world) {
        super(world);
        this.sprite = new Sprite(new Texture("birds/chuck.png"));
        this.sprite.setSize(sprite.getWidth() * 0.25f, sprite.getHeight() * 0.25f);
        this.sprite.setPosition(125, 175);
        this.health = 3;
    }
    public Chuck(){
        this.sprite = new Sprite(new Texture("birds/chuck.png"));
        this.sprite.setScale(0.2f);
    }
    public Chuck(int x, int y){
        this.sprite = new Sprite(new Texture("birds/chuck.png")) ;
        this.sprite.setPosition(x, y);
    }
    public Body createBody(){
        return super.createBody(0.15f);
    }
}
