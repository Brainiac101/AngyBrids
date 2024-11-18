package com.angybrids.blocks;

import com.badlogic.gdx.graphics.Texture;

public class StoneBar extends Bar{
    private Texture stonebar;
    private boolean orientation;
    public StoneBar(boolean orientation) {
        if (orientation) {
            this.stonebar = new Texture("blocks/stone-vertical.png");
        }
        else{
            this.stonebar = new Texture("blocks/stone-horizontal.png");
        }
    }

    public Texture getBar() {
        return stonebar;
    }
}
