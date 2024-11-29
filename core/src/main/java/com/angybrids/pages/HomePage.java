package com.angybrids.pages;

import com.angybrids.Button;
import com.angybrids.Main;
import com.angybrids.level.Level;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.IOException;
import java.io.Serializable;

public class HomePage implements Screen, Serializable {
    final Main game;
    private Texture playButton;
    private Texture background;
    private Texture title;
    private Texture quitImage;
    private Texture loadImage;
    private FitViewport viewport;

    OrthographicCamera camera;

    public HomePage(final Main game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);

        viewport = new FitViewport(1280, 720, camera);
        background = new Texture(Gdx.files.internal("loading/titleBG.jpeg"));
        playButton = new Texture("loading/play_button.png");
        quitImage = new Texture("icons/exitIcon.png");
        loadImage = new Texture("icons/loadIcon.png");
        title = new Texture("loading/title.png");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        Button play = new Button(playButton, (Gdx.graphics.getWidth() / 2) - (playButton.getWidth() / 2) + 25,
            Gdx.graphics.getHeight() / 2 - playButton.getHeight() / 2 - 150, 0.6f);
        Button quit = new Button(quitImage, (Gdx.graphics.getWidth() / 2) - (quitImage.getWidth() / 2) - 25,
            Gdx.graphics.getHeight() / 2 - quitImage.getHeight() / 2 - 265, 1.75f);
        Button save = new Button(loadImage, (Gdx.graphics.getWidth() / 2) - (loadImage.getWidth() / 2) + 75,
            Gdx.graphics.getHeight() / 2 - loadImage.getHeight() / 2 - 265, 1.75f);

        game.batch.begin();
        game.batch.draw(background, 0, 0, width, height);
        game.batch.draw(title, Gdx.graphics.getWidth() / 2 - title.getWidth() / 2 - 10, 600, 5 * title.getWidth() / 4, 5 * title.getHeight() / 4);
        play.getButtonSprite().draw(game.batch);
        quit.getButtonSprite().draw(game.batch);
        save.getButtonSprite().draw(game.batch);
        game.batch.end();

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if (play.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)
            || quit.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)
            || save.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        else Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        if (Gdx.input.justTouched()) {
            if (play.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
                game.setScreen(new Map(game));
            else if (quit.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
                System.exit(0);
            else if (save.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)){
                Level lvl = null;
                try {
                    lvl = Level.loadState(game);
                } catch (IOException e) {
                    System.out.println("Failed to load level");
                    System.exit(0);
                }
                if(lvl != null) game.setScreen(lvl);
            }
        }

    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        background.dispose();
        playButton.dispose();
        quitImage.dispose();
        loadImage.dispose();
        title.dispose();
    }
}
