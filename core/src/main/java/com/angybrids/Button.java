package com.angybrids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Button {
    private Texture texture;
    private Sprite buttonSprite;

    public Button(Texture texture, int x, int y, float scale) {
        this.texture = texture;
        buttonSprite = new Sprite(texture);
        buttonSprite.setPosition(x, y);
        buttonSprite.setScale(scale);
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getButtonSprite() {
        return buttonSprite;
    }
}
