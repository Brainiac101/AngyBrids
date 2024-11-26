package com.angybrids.pages;

import com.angybrids.Button;
//import com.angybrids.Level;
import com.angybrids.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Map implements Screen {
    final Main game;

    private Texture settingImage;
    private Texture shopImage;
    private Texture birdImage;
    private Texture backImage;
    private Texture quitImage;
    private Texture saveImage;

    private Texture mapTitle;
    private Texture background;
    private Texture levelSelect;

    private FitViewport viewport;
    private BitmapFont font;
    private boolean visibility;

    public Map(Main game) {
        this.game = game;
        viewport = new FitViewport(1280, 720);

        settingImage = new Texture("icons/settingIcon.png");
        birdImage = new Texture("icons/birdIcon.png");
        shopImage = new Texture("icons/shopIcon.png");
        backImage = new Texture("icons/homeIcon.png");
        mapTitle = new Texture("map/mapTitle.png");
        background = new Texture("map/mapBG.jpg");
        levelSelect = new Texture("map/levelSelect.png");
        quitImage = new Texture("icons/exitIcon.png");
        saveImage = new Texture("icons/saveIcon.png");

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(3.5f);
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

        Button settingButton = new Button(settingImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 - 65,
            Gdx.graphics.getHeight() - settingImage.getHeight() / 2 - 60, 0.7f);
        Button shopButton = new Button(shopImage, Gdx.graphics.getWidth() - (shopImage.getWidth() / 2) - 75,
            shopImage.getHeight() / 2, 1.5f);
        Button birdButton = new Button(birdImage, birdImage.getWidth() / 2 + 25,
            birdImage.getHeight() / 2, 1.6f);
        Button backButton = new Button(backImage, backImage.getWidth() / 2 + 25,
            Gdx.graphics.getHeight() - backImage.getHeight() / 2 - 65, 1.6f);
        Button quitButton = new Button(quitImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 - 35,
            Gdx.graphics.getHeight() - 3 * settingImage.getHeight() / 2 - 5, 1.2f);
        Button saveButton = new Button(saveImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 - 35,
            Gdx.graphics.getHeight() - 3 * settingImage.getHeight() / 2 - 25 - quitImage.getHeight(), 1.2f);

        game.batch.begin();
        //Map background
        game.batch.draw(background, 0, 0, width, height);
        //Map title
        game.batch.draw(mapTitle, Gdx.graphics.getWidth() / 2 - 115, Gdx.graphics.getHeight() - 125, 7 * mapTitle.getWidth() / 5, 7 * mapTitle.getHeight() / 5);
        //Setting Icon
        settingButton.getButtonSprite().draw(game.batch);
        //Shop Icon
        shopButton.getButtonSprite().draw(game.batch);
        //Bird Icon
        birdButton.getButtonSprite().draw(game.batch);
        //Back Icon
        backButton.getButtonSprite().draw(game.batch);
        if (visibility) {
            quitButton.getButtonSprite().draw(game.batch);
            saveButton.getButtonSprite().draw(game.batch);
        }
        game.batch.end();
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.setColor(1f, 0.8f, 0.2f, 1f); // honey gold
//        shapeRenderer.setColor(0.72f, 0.43f, 0.47f, 1f); // rose gold
        // burnt sienna
        font.setColor(Color.WHITE);
        ArrayList<Button> levels = new ArrayList<>();
        int x = 240;
        int y = 375;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                levels.add(new Button(levelSelect, x, y, 1f));
                x += 300;
            }
            x = 240;
            y -= 250;
        }
        game.batch.begin();
        for (Button b : levels) {
            b.getButtonSprite().draw(game.batch);
        }
        x = 230;
        y = 400;
        int level = 1;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                font.draw(game.batch, Integer.toString(level), x + 100, y + 75);
                x += 300;
                level++;
            }
            x = 230;
            y -= 250;
        }
        game.batch.end();

        //Changing cursor to a pointer while hovering over an icon
        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if ((settingButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (shopButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (birdButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (backButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (levels.get(0).getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (levels.get(1).getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (levels.get(2).getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (levels.get(3).getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || levels.get(4).getButtonSprite().getBoundingRectangle().contains(touchX, touchY)
            || levels.get(5).getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        else if ((visibility && saveButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
        else
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        if (Gdx.input.justTouched()) {
            if (visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
                System.exit(0);
//            else if(visibility && saveButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {}
            if (settingButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
                visibility = !visibility;
            else {
                visibility = false;
                if (backButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    this.dispose();
                    game.setScreen(new HomePage(game));
                } else if (birdButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    this.dispose();
                    game.setScreen(new BirdPage(game));
                } else if (shopButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    try {
                        this.dispose();
                        game.setScreen(new ShopPage(game));
                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
                             IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else if (levels.get(0).getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    this.dispose();
                    game.setScreen(new com.angybrids.level.Level(game));
//                        game.setScreen(new Level(1, game));

//                } else if (levels.get(1).getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                    try {
//                        this.dispose();
//                        game.setScreen(new com.angybrids.level.Level(game));
//
//                        game.setScreen(new Level(2, game));
//                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
//                             IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else if (levels.get(2).getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                    try {
//                        this.dispose();
//                        game.setScreen(new com.angybrids.level.Level(game));
//
//                        game.setScreen(new Level(3, game));
//                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
//                             IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//                } else if (levels.get(3).getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                    try {
//                        this.dispose();
//                        game.setScreen(new Level(4, game));
//                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
//                             IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                } else if (levels.get(4).getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                    try {
//                        this.dispose();
//                        game.setScreen(new Level(5, game));
//                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
//                             IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                else if (levels.get(5).getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                    try {
//                        this.dispose();
//                        game.setScreen(new Level(6, game));
//                    } catch (InvocationTargetException | NoSuchMethodException | InstantiationException |
//                             IllegalAccessException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
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
        settingImage.dispose();
        birdImage.dispose();
        shopImage.dispose();
        backImage.dispose();
        mapTitle.dispose();
        background.dispose();
        levelSelect.dispose();
        quitImage.dispose();
        saveImage.dispose();
        font.dispose();
    }
}
