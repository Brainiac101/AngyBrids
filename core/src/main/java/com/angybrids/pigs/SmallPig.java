//package com.angybrids.pigs;
//
//import com.angybrids.birds.Bird;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.physics.box2d.Body;
//import com.badlogic.gdx.physics.box2d.World;
//
//public class SmallPig {
//    private Sprite image;
//    public SmallPig(int x, int y){
//        image = new Sprite(new Texture("pigs/pig.png"));
//        image.setPosition(x, y);
//        image.setScale(0.07f);
//    }
//    public Sprite getSprite() {
//        return image;
//    }
//}


package com.angybrids.pigs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class SmallPig extends Pig {

    public SmallPig(){
        texture = new Texture("pigs/pig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setScale(0.3f);
    }

    public SmallPig(World world) {
        super(world);
        texture = new Texture("pigs/pig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(sprite.getWidth() * 0.3f, sprite.getHeight() * 0.3f);
        this.sprite.setPosition(125, 175);
        this.health = 1;
    }

    public SmallPig(int x, int y) {
        texture = new Texture("pigs/pig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
    }

    public SmallPig(World world, int x, int y) {
        super(world);
        texture = new Texture("pigs/pig.png");
        this.sprite = new Sprite(texture);
        this.sprite.setSize(sprite.getWidth() * 0.07f, sprite.getHeight() * 0.07f);
        this.sprite.setPosition(x, y);
        this.health = 1;
    }

    public Body createBody(){
        return super.createBody(0.3f);
    }
}
