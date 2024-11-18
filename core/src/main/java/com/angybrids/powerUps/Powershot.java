package com.angybrids.powerUps;

import com.badlogic.gdx.graphics.Texture;

public class Powershot {
    private Texture image;
    public Powershot() {
        this.image = new Texture("powerUp/powershot.png");
    }
    public Texture getImage() {
        return image;
    }
}
