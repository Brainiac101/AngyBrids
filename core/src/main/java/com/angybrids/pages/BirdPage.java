package com.angybrids.pages;

import com.angybrids.*;
import com.angybrids.birds.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class BirdPage implements Screen, Serializable {
    final Main game;
    private FitViewport viewport;
    private boolean visibility;

    private Texture background;
    private Texture title;

    private Texture mapImage;
    private Texture homeImage;
    private Texture settingImage;
    private Texture shopImage;
    private Texture quitImage;
    private Texture saveImage;

    private Sprite red;
    private Sprite chuck;
    private Sprite bomb;
    private Sprite matilda;
    private Sprite blue;
    private Sprite hal;
    private Sprite terence;

    public BirdPage(Main game) {
        this.game = game;
        this.viewport = new FitViewport(1280, 720);

        this.background = new Texture("birdPage/birdBG.png");
        this.title = new Texture("birdPage/title.png");
        this.mapImage = new Texture("icons/mapIcon.png");
        this.homeImage = new Texture("icons/homeIcon.png");
        this.settingImage = new Texture("icons/settingIcon.png");
        this.shopImage = new Texture("icons/shopIcon.png");
        this.quitImage = new Texture("icons/exitIcon.png");
        this.saveImage = new Texture("icons/saveIcon.png");

        this.red = new Red(200, 320).getSprite();
        this.chuck = new Chuck(450, 320).getSprite();
        this.bomb = new Bomb(700, 320).getSprite();
        this.blue = new Blue(950, 320).getSprite();
        this.terence = new Terence(225, 50, 1.4f).getSprite();
        this.matilda = new Matilda(515, 20, 1f).getSprite();
        this.hal = new Hal(800, 50, 1.5f).getSprite();
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

        Button homeButton = new Button(homeImage, homeImage.getWidth() / 2 + 25,
            Gdx.graphics.getHeight() - homeImage.getHeight() / 2 - 65, 1.6f);
        Button mapButton = new Button(mapImage, mapImage.getWidth() / 2 + 25,
            mapImage.getHeight() / 2, 1.6f);
        Button settingButton = new Button(settingImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 - 65,
            Gdx.graphics.getHeight() - settingImage.getHeight() / 2 - 60, 0.7f);
        Button shopButton = new Button(shopImage, Gdx.graphics.getWidth() - (shopImage.getWidth() / 2) - 75,
            shopImage.getHeight() / 2, 1.5f);
        Button quitButton = new Button(quitImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 -35 ,
            Gdx.graphics.getHeight() - 3 * settingImage.getHeight() / 2 -5, 1.2f);
        Button saveButton = new Button(saveImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 -35,
            Gdx.graphics.getHeight() - 3 * settingImage.getHeight() / 2 - 25 - quitImage.getHeight(), 1.2f);
        game.batch.begin();
        game.batch.draw(background, 0, 0, width, height);
        //Home Icon
        homeButton.getButtonSprite().draw(game.batch);
        //Map Icon
        mapButton.getButtonSprite().draw(game.batch);
        //Setting Icon
        settingButton.getButtonSprite().draw(game.batch);
        //Shop Icon
        shopButton.getButtonSprite().draw(game.batch);
        game.batch.draw(title, Gdx.graphics.getWidth() / 2 - 180, 590, 3 * title.getWidth() / 2, 3 * title.getHeight() / 2);

        red.draw(game.batch);
        chuck.draw(game.batch);
        bomb.draw(game.batch);
        blue.draw(game.batch);
        terence.draw(game.batch);
        matilda.draw(game.batch);
        hal.draw(game.batch);
        if(visibility){
            quitButton.getButtonSprite().draw(game.batch);
            saveButton.getButtonSprite().draw(game.batch);
        }
        game.batch.end();

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if((settingButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (shopButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (mapButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (homeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        else if ( (visibility && saveButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) )
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
        else
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        if (Gdx.input.justTouched()) {
            if(visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) System.exit(0);
            if(settingButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
                visibility= !visibility;
            else {
                visibility = false;
                if (homeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    this.dispose();
                    game.setScreen(new HomePage(game));
                }
                else if (mapButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    this.dispose();
                    game.setScreen(new Map(game));
                }
                else if (shopButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    try {
                        this.dispose();
                        game.setScreen(new ShopPage(game));
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                             IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        title.dispose();
        mapImage.dispose();
        homeImage.dispose();
        settingImage.dispose();
        shopImage.dispose();
        quitImage.dispose();
        saveImage.dispose();

    }
}
