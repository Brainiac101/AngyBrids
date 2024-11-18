package com.angybrids.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Pig {
    private Sprite image;
    public Pig(int x, int y){
        image = new Sprite(new Texture("pigs/pig.png"));
        image.setPosition(x, y);
        image.setScale(0.07f);
    }
    public Sprite getSprite() {
        return image;
    }
}
