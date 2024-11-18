package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Terence extends Bird {
    private Sprite image;
    public Terence() {
        this.image = new Sprite(new Texture("birds/terence.png"));
        image.setScale(0.4f);
    }
    public Terence(int x, int y) {
        this.image = new Sprite(new Texture("birds/terence.png"));
        this.image.setPosition(x, y);
    }
    public Terence(int x, int y, float scale){
        this.image = new Sprite(new Texture("birds/terence.png"));
        this.image.setPosition(x, y);
        this.image.setScale(scale);
    }
    public Sprite getSprite() {
        return image;
    }
}
