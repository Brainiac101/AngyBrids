package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Matilda extends Bird {
    private Sprite image;
    public Matilda(){
        this.image = new Sprite(new Texture("birds/matilda.png"));
        this.image.setScale(0.3f);
    }
    public Matilda(int x, int y){
        this.image = new Sprite(new Texture("birds/matilda.png"));
        this.image.setPosition(x, y);
    }
    public Matilda(int x, int y, float scale){
        this.image = new Sprite(new Texture("birds/matilda.png"));
        this.image.setPosition(x, y);
        this.image.setScale(scale);
    }
    public Sprite getSprite() {
        return image;
    }
}
