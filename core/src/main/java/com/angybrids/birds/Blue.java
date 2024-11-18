package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Blue extends Bird {
    private Sprite image;

    public Blue() {
        this.image = new Sprite(new Texture("birds/blue.png"));
        this.image.setScale(0.3f);
    }

    public Blue(int x, int y) {
        this.image = new Sprite(new Texture("birds/blue.png"));
        this.image.setPosition(x, y);
    }
    public Sprite getSprite() {
        return image;
    }
}
