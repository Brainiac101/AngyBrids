package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;

public class StoneHorizontal extends Block {
    private Texture stoneBar;
    public StoneHorizontal() {
        this.stoneBar = new Texture("blocks/stone-horizontal.png");
    }
    public Texture getBlock() {
        return stoneBar;
    }
}
