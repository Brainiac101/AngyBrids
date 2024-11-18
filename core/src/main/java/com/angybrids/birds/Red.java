package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Red  extends Bird {
    private Sprite image;
    public Red() {
        this.image = new Sprite(new Texture("birds/red.png"));
        this.image.setScale(0.2f);
    }
    public Red(int x, int y){
        this.image = new Sprite(new Texture("birds/red.png"));
        this.image.setPosition(x, y);
    }
    public Sprite getSprite() {
        return image;
    }
}
