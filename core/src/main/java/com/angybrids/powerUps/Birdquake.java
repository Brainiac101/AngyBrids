package com.angybrids.powerUps;

import com.badlogic.gdx.graphics.Texture;

public class Birdquake {
    private Texture image;
    public Birdquake(){
        this.image = new Texture("powerUp/birdquake.png");
    }
    public Texture getImage() {
        return image;
    }
}
