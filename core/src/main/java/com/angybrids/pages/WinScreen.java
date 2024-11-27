package com.angybrids.pages;


import com.angybrids.Button;
import com.angybrids.Main;
import com.angybrids.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.angybrids.Level;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class WinScreen implements Screen, Serializable {
    private Texture background;
    private Texture nextImage;
    private Texture mapImage;
    private Texture winImage;
    private int level;
    final Main game;
    private FitViewport viewport;

    public WinScreen(int level, Main game) {
        viewport = new FitViewport(1280, 720);
        this.game = game;
        this.level = level;
        if (level % 3 == 1) {
            this.background = new Texture("levelAssets/bg1.png");
        } else if (level % 3 == 0) {
            this.background = new Texture("levelAssets/bg2.png");
        } else {
            this.background = new Texture("levelAssets/bg3.png");
        }
        nextImage = new Texture("icons/nextIcon.png");
        mapImage = new Texture("icons/mapIcon.png");
        winImage = new Texture("winTitle.png");
    }
//    public WinScreen(Main game) {
//        viewport = new FitViewport(1280, 720);
//        this.game = game;
//        this.level = 0;
//        this.background = new Texture("levelAssets/bg3.png");
//        nextImage = new Texture("icons/nextIcon.png");
//        mapImage = new Texture("icons/mapIcon.png");
//        winImage = new Texture("winTitle.png");
//    }
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

        Button nextButton = new Button(nextImage, Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2, 1.6f);
        Button mapButton = new Button(mapImage, Gdx.graphics.getWidth() / 2 + 75, Gdx.graphics.getHeight() / 2, 1.6f);

        game.batch.begin();
        game.batch.draw(background, 0, 0, width, height);
        nextButton.getButtonSprite().draw(game.batch);
        mapButton.getButtonSprite().draw(game.batch);
        game.batch.draw(winImage, Gdx.graphics.getWidth() / 2 - 125, Gdx.graphics.getHeight() / 2 + 100);
        game.batch.end();

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if(nextButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)
         || mapButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        else Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        if (Gdx.input.justTouched()) {
            if (nextButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                game.setScreen(new Level(level+1,game));
//                try {
//                    this.dispose();
////                    game.setScreen(new Level(level+1, game));
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
    }
}
