package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;

public class WoodBlock extends Block{
    private Texture woodBlock;
    public WoodBlock() {
        this.woodBlock = new Texture("blocks/woodBlock.png");
    }

    public Texture getBlock() {
        return woodBlock;
    }
}
