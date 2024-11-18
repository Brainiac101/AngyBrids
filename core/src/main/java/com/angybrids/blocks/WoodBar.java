package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;

public class WoodBar extends Bar {
    private Texture woodbar;
    private boolean orientation;
    public WoodBar(boolean orientation) {
        if (orientation) {
            this.woodbar = new Texture("blocks/wood-vertical.png");
        }
        else{
            this.woodbar = new Texture("blocks/wood-horizontal.png");
        }
    }
    public Texture getBar() {
        return woodbar;
    }
}
