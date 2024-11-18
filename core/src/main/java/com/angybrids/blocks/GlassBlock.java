package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;

public class GlassBlock extends Block {
    private Texture glassBlock;
    public GlassBlock() {
        this.glassBlock = new Texture("blocks/glassBlock.png");

    }
    public Texture getBlock() {
        return glassBlock;
    }
}
