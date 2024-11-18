package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;

public class WoodVertical extends Block {
    private Texture woodVertical;
    public WoodVertical() {
        woodVertical = new Texture("blocks/wood-vertical.png");
    }
    public Texture getBlock() {
        return woodVertical;
    }
}
