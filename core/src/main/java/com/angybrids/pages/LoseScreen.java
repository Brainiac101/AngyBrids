package com.angybrids.pages;


import com.angybrids.Button;
import com.angybrids.Main;
import com.angybrids.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.angybrids.Level;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class LoseScreen implements Screen, Serializable {
    private Texture background;
    private Texture retryImage;
    private Texture mapImage;
    private Texture failTitle;
    private int level;
    private List<Sprite> selectedBirds;
    final Main game;
    private FitViewport viewport;

    public LoseScreen(int level, Main game, List<Sprite> selectedBirds) {
        viewport = new FitViewport(1280, 720);
        this.game = game;
        this.level = level;
        if (level % 3 == 1) {
            this.background = new Texture("levelAssets/bg3.png");
        } else if (level % 3 == 0) {
            this.background = new Texture("levelAssets/bg2.png");
        } else {
            this.background = new Texture("levelAssets/bg1.png");
        }
        this.selectedBirds=selectedBirds;
        retryImage = new Texture("icons/retryIcon.png");
        mapImage = new Texture("icons/mapIcon.png");
        failTitle = new Texture("failTitle.png");
    }
    public LoseScreen(int level,Main game) {
        viewport = new FitViewport(1280, 720);
        this.game = game;
        this.level = level;
        retryImage = new Texture("icons/retryIcon.png");
        mapImage = new Texture("icons/mapIcon.png");
        failTitle = new Texture("failTitle.png");
        this.background = new Texture("levelAssets/bg1.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        Button retryButton = new Button(retryImage, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2, 1.6f);
        Button mapButton = new Button(mapImage, Gdx.graphics.getWidth() / 2 + 75, Gdx.graphics.getHeight() / 2, 1.6f);

        game.batch.begin();
        game.batch.draw(background, 0, 0, width, height);
        retryButton.getButtonSprite().draw(game.batch);
        mapButton.getButtonSprite().draw(game.batch);
        game.batch.draw(failTitle, Gdx.graphics.getWidth() / 2 - 125, Gdx.graphics.getHeight() / 2 + 100);
        game.batch.end();

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if((mapButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
         || (retryButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        else
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        if (Gdx.input.justTouched()) {
            if (retryButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                game.setScreen(new Level(level,game));
//                try {
//                    this.dispose();
//                    game.setScreen(new Level(level, game,selectedBirds));
//                } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
//                         IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
            } else if (mapButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                this.dispose();
                game.setScreen(new Map(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        failTitle.dispose();
    }
}
