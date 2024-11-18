package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Bomb extends Bird{
    private Sprite image;

    public Bomb() {
        this.image = new Sprite(new Texture("birds/bomb.png"));
        image.setScale(0.25f);
    }

    public Bomb(int x, int y){
        this.image = new Sprite(new Texture("birds/bomb.png"));
        this.image.setPosition(x, y);
    }
    public Sprite getSprite() {
        return image;
    }
}
