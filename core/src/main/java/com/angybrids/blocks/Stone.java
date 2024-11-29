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


package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Stone extends Block {
    public Stone(){
        this.sp = new Sprite(new Texture("blocks/stone.png"));
    }

    public Stone(World world) {
        super(world);
        this.sp = new Sprite(new Texture("blocks/stone.png"));
        this.sp.setPosition(125, 175);
        this.hp = 1;
    }

    public Stone(int x, int y) {
        this.sp = new Sprite(new Texture("blocks/stone.png"));
        this.sp.setPosition(x, y);
    }

    public Stone(World world, float x, float y) {
        super(world);
        this.position = new Vector2(x, y);
        this.sp = new Sprite(new Texture("blocks/stone.png"));
        this.sp.setPosition(x, y);
        this.hp = 1;
    }

    public Body createBody(){
        return super.createBody(0.3f);
    }
}
