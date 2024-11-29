package com.angybrids.pages;

import com.angybrids.Button;
import com.angybrids.Main;
import com.angybrids.powerUps.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class ShopPage implements Screen, Serializable {
    final Main game;
    private FitViewport viewport;
    private boolean visibility, powershotVisible, superchargeVisible, birdquakesVisible, potionVisible;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;

    private Texture title, background, mapImage, homeImage, birdImage, settingImage, quitImage, saveImage, powershotImage, superchargeImage, birdquakeImage, potionImage, coin;
    private static int coins = 0;

    public ShopPage(Main game) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        this.game = game;
        viewport = new FitViewport(1280, 720);

        this.background = new Texture("shop/shopBG.jpg");
        this.title = new Texture("shop/shopTitle.png");
        this.coin = new Texture("shop/coin.png");
        shapeRenderer = new ShapeRenderer();
        this.font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(2f);
        this.mapImage = new Texture("icons/mapIcon.png");
        this.settingImage = new Texture("icons/settingIcon.png");
        this.homeImage = new Texture("icons/homeIcon.png");
        this.birdImage = new Texture("icons/birdIcon.png");
        this.quitImage = new Texture("icons/exitIcon.png");
        this.saveImage = new Texture("icons/saveIcon.png");

        this.powershotImage = new Powershot().getImage();
        this.superchargeImage = new Supercharge().getImage();
        this.birdquakeImage = new Birdquake().getImage();
        this.potionImage = new Potion().getImage();
    }

    public static int getCoins() {
        return coins;
    }

    public static void setCoins(int coin) {
        coins = coin;
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
        Button birdButton = new Button(birdImage, birdImage.getWidth() / 2 + 25,
            birdImage.getHeight() / 2, 1.6f);
        Button mapButton = new Button(mapImage, Gdx.graphics.getWidth() - (mapImage.getWidth() / 2) - 75,
            mapImage.getHeight() / 2, 1.5f);
        Button settingButton = new Button(settingImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 - 65,
            Gdx.graphics.getHeight() - settingImage.getHeight() / 2 - 60, 0.7f);
        Button quitButton = new Button(quitImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 - 35,
            Gdx.graphics.getHeight() - 3 * settingImage.getHeight() / 2 - 5, 1.2f);
        Button saveButton = new Button(saveImage, Gdx.graphics.getWidth() - settingImage.getWidth() / 2 - 35,
            Gdx.graphics.getHeight() - 3 * settingImage.getHeight() / 2 - 25 - quitImage.getHeight(), 1.2f);

        Button powershotButton = new Button(powershotImage, 900, 350, 1f);
        Button superchargeButton = new Button(superchargeImage, 200, 75, 1.2f);
        Button birdquakeButton = new Button(birdquakeImage, 200, 400, 1.2f);
        Button potionButton = new Button(potionImage, 900, 75, 1.2f);

        game.batch.begin();
        game.batch.draw(background, 0, 0, width, height);
        game.batch.draw(title, Gdx.graphics.getWidth() / 2 - 85, 590,
            3 * title.getWidth() / 2, 3 * title.getHeight() / 2);
        //Home Icon
        homeButton.getButtonSprite().draw(game.batch);
        //Map Icon
        mapButton.getButtonSprite().draw(game.batch);
        //Bird Icon
        birdButton.getButtonSprite().draw(game.batch);
        //Setting Icon
        settingButton.getButtonSprite().draw(game.batch);

        powershotButton.getButtonSprite().draw(game.batch);
        font.draw(game.batch, "200", powershotButton.getButtonSprite().getX() + 70, powershotButton.getButtonSprite().getY() - 10);
        superchargeButton.getButtonSprite().draw(game.batch);
        font.draw(game.batch, "200", superchargeButton.getButtonSprite().getX() + 65, superchargeButton.getButtonSprite().getY() - 10);
        birdquakeButton.getButtonSprite().draw(game.batch);
        font.draw(game.batch, "200", birdquakeButton.getButtonSprite().getX() + 70, birdquakeButton.getButtonSprite().getY() - 10);
        potionButton.getButtonSprite().draw(game.batch);
        font.draw(game.batch, "200", potionButton.getButtonSprite().getX() + 65, potionButton.getButtonSprite().getY() - 10);

        if (visibility) {
            quitButton.getButtonSprite().draw(game.batch);
//            saveButton.getButtonSprite().draw(game.batch);
        }
        if (powershotVisible) {
            font.setColor(Color.GREEN);
            font.draw(game.batch, "King Sling", powershotButton.getButtonSprite().getX() + 30, powershotButton.getButtonSprite().getY() + 50);
            font.setColor(Color.BLACK);
        }
        if (superchargeVisible) {
            font.setColor(Color.GREEN);
            font.draw(game.batch, "Super Charge", superchargeButton.getButtonSprite().getX() + 10, superchargeButton.getButtonSprite().getY() + 40);
            font.setColor(Color.BLACK);
        }
        if (potionVisible) {
            font.setColor(Color.GREEN);
            font.draw(game.batch, "Power Potion", potionButton.getButtonSprite().getX() + 10, potionButton.getButtonSprite().getY() + 20);
            font.setColor(Color.BLACK);
        }
        if (birdquakesVisible) {
            font.setColor(Color.GREEN);
            font.draw(game.batch, "Bird Quake", birdquakeButton.getButtonSprite().getX() + 35, birdquakeButton.getButtonSprite().getY() + 25);
            font.setColor(Color.BLACK);
        }
        game.batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.94f, 0.23f, 0.57f, 1f);
        shapeRenderer.rect(1000, Gdx.graphics.getHeight() - homeImage.getHeight() / 2 - 50, 120, 45);
        shapeRenderer.end();

        game.batch.begin();
        font.setColor(0, 0, 0, 1f);
        font.draw(game.batch, Integer.toString(coins), 1045, Gdx.graphics.getHeight() - homeImage.getHeight() / 2 - 20);
        game.batch.draw(coin, 950, Gdx.graphics.getHeight() - homeImage.getHeight() / 2 - 50,
            1 * coin.getWidth() / 11, 1 * coin.getWidth() / 11);
        game.batch.end();

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if ((settingButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (birdButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (mapButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (homeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        else if ((visibility && saveButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
        else if (birdquakeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
            birdquakesVisible = true;
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        } else if (potionButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
            potionVisible = true;
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        } else if (powershotButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
            powershotVisible = true;
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        } else if (superchargeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
            superchargeVisible = true;
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        } else {
            birdquakesVisible = false;
            potionVisible = false;
            powershotVisible = false;
            superchargeVisible = false;
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
        if (Gdx.input.justTouched()) {
            if (visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
                System.exit(0);
            else if (settingButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
                visibility = !visibility;
            else {
                visibility = false;
                if (homeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    this.dispose();
                    game.setScreen(new HomePage(game));
                } else if (mapButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    this.dispose();
                    game.setScreen(new Map(game));
                } else if (birdButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    this.dispose();
                    game.setScreen(new BirdPage(game));
                }
            }
            if (superchargeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                if (coins >= 200) {
                    coins -= 200;
                    Inventory.addItem("charge");
                }
            }
            if (powershotButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                if (coins >= 200) {
                    coins -= 200;
                    Inventory.addItem("power");
                }
            }
            if (potionButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                if (coins >= 200) {
                    coins -= 200;
                    Inventory.addItem("potion");
                }
            }
            if (birdquakeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                if (coins >= 200) {
                    coins -= 200;
                    Inventory.addItem("quake");
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
        coin.dispose();
        shapeRenderer.dispose();
        font.dispose();
        mapImage.dispose();
        settingImage.dispose();
        birdImage.dispose();
        quitImage.dispose();
        saveImage.dispose();
        powershotImage.dispose();
        superchargeImage.dispose();
        birdquakeImage.dispose();
        potionImage.dispose();
    }
}
