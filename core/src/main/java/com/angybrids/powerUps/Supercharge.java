package com.angybrids.powerUps;

import com.badlogic.gdx.graphics.Texture;

public class Supercharge {
    private Texture image;
    public Supercharge(){
        this.image = new Texture("powerUp/supercharge.png");
    }
    public Texture getImage() {
        return image;
    }
}
