package com.angybrids.blocks;
//
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;
//
//public class Block {
//    private Texture texture;
//    public Texture getBlock() {
//        return texture;
//    }
//}
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Block {
    public Vector2 position;
    public float width, height;
    public String type;  // e.g., "wood", "stone", "glass"
    public Sprite sp;
    public Body body;
    public int hp;

    public Block(Vector2 position, float width, float height, String type) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.type = type;
        this.sp = new Sprite(new Texture("blocks/" + type + ".png"));
        sp.setPosition(position.x, position.y);
        sp.setSize(width, height);
        if(type.equals("wood")){
            this.hp = 2;
        }
        else if(type.equals("glass")){
            this.hp = 1;
        }
        else if(type.equals("stone")){
            this.hp = 3;
        }

    }


}
