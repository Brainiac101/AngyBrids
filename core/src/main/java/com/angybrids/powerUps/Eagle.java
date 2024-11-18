package com.angybrids.powerUps;

import com.badlogic.gdx.graphics.Texture;

public class Eagle {
    private Texture image;

    public Eagle() {
        this.image = new Texture("powerUp/eagle.png");
    }

    public Texture getImage() {
        return image;
    }
}
