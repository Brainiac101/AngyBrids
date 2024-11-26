package com.angybrids;

import com.angybrids.pages.HomePage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    public SpriteBatch batch;
    public Music music;

    @Override
    public void create() {
        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
        this.setScreen(new HomePage(this));
    }

    public void render() {
        super.render();
    }

    public void dispose() {
        batch.dispose();
        music.dispose();
    }
}
