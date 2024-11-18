package com.angybrids.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class King {
    private Sprite image;

    public King(int x, int y){
        image = new Sprite(new Texture("pigs/kingpig.png"));
        image.setPosition(x, y);
        image.setScale(0.18f);
    }
    public Sprite getSprite() {
        return image;
    }
}
