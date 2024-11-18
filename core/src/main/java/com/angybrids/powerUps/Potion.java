package com.angybrids.powerUps;

import com.badlogic.gdx.graphics.Texture;

public class Potion {
    private Texture image;
    public Potion(){
        this.image = new Texture("powerUp/potion.png");
    }
    public Texture getImage() {
        return image;
    }
}
