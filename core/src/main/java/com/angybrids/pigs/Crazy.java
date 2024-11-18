package com.angybrids.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Crazy {
    private Sprite image;
    public Crazy(int x, int y){
        image = new Sprite(new Texture("pigs/crazyPig.png"));
        image.setPosition(x, y);
        image.setScale(1f);
    }
    public Sprite getSprite() {
        return image;
    }
}
