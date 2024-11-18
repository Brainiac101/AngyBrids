package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;

public class WoodHorizontal extends Block{
    private Texture glassBlock;
    public WoodHorizontal() {
        this.glassBlock = new Texture("blocks/glassBlock.png");

    }
    public Texture getBlock() {
        return glassBlock;
    }
}
