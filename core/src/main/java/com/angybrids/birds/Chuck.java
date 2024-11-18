package com.angybrids.birds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Chuck extends Bird {
    private Sprite image;
    public Chuck(){
        this.image = new Sprite(new Texture("birds/chuck.png"));
        this.image.setScale(0.2f);
    }
    public Chuck(int x, int y){
        this.image = new Sprite(new Texture("birds/chuck.png")) ;
        this.image.setPosition(x, y);
    }
    public Sprite getSprite() {
        return image;
    }
}
