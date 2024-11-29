package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Wood extends Block {
    public Wood(){
        this.sp = new Sprite(new Texture("blocks/wood.png"));
    }

    public Wood(World world) {
        super(world);
        this.sp = new Sprite(new Texture("blocks/wood.png"));
        this.sp.setPosition(125, 175);
        this.hp = 1;
    }

    public Wood(int x, int y) {
        this.sp = new Sprite(new Texture("blocks/wood.png"));
        this.sp.setPosition(x, y);
    }

    public Wood(World world, float x, float y) {
        super(world);
        this.position = new Vector2(x, y);
        this.sp = new Sprite(new Texture("blocks/wood.png"));
        this.sp.setPosition(x, y);
        this.hp = 1;
    }

    public Body createBody(){
        return super.createBody(0.2f);
    }
}
