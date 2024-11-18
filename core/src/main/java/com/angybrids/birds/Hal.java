package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Hal extends Bird {
    private Sprite image;

    public Hal(){
        this.image = new Sprite(new Texture("birds/hal.png"));
        this.image.setScale(0.3f);
    }
    public Hal(int x, int y){
        this.image = new Sprite(new Texture("birds/hal.png"));
        this.image.setPosition(x, y);
    }
    public Hal(int x, int y, float scale){
        this.image = new Sprite(new Texture("birds/hal.png"));
        this.image.setPosition(x, y);
        this.image.setScale(scale);
    }
    public Sprite getSprite() {
        return image;
    }
}
