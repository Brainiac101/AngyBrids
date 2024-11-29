package com.angybrids.level;

import com.angybrids.Button;
import com.angybrids.Main;
import com.angybrids.birds.*;
import com.angybrids.blocks.Block;
import com.angybrids.blocks.Glass;
import com.angybrids.blocks.Stone;
import com.angybrids.blocks.Wood;
import com.angybrids.pages.*;
import com.angybrids.pages.Map;
import com.angybrids.pigs.*;
import com.angybrids.powerUps.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.IOException;
import java.util.*;

public class Level extends InputAdapter implements Screen, Json.Serializable {
    private Main game;
    private OrthographicCamera camera;
    private World world;
    private SpriteBatch batch;
    private Matrix4 debugMatrix;
    private Box2DDebugRenderer debugRenderer;

    private List<String> myBirds;
    private List<Pig> myPigs;
    public List<Body> bodiesToDelete;
    private List<String> roster;
    private boolean launched, charged, visibility;
    private int ctr;
    private int birdHP;
    public int pigCounter;
    public int birdCounter;
    private Bird curr;

    private int level;

    private Texture bg, catapult, wood, stone, glass, tempBird1, tempBird2;
    private Texture powershotImage, superchargeImage, birdquakeImage, potionImage;
    private Texture pause, quit, resume, retry;
    private Sprite birdSprite, bgSprite, catapultSprite, woodSprite, stoneSprite, glassSprite;
    private Body birdBody, bgBody, catapultBody;
    private Button powershotButton, superchargeButton, birdquakeButton, potionButton, retryButton, resumeButton, pauseButton, quitButton;

    private final Projectile proj;
    private boolean flag = false;
    private final Vector2 initialTouchPosition = new Vector2();
    private final Vector2 launch;
    private Vector3 touchPosition;
    List<Block> blockCollection = new ArrayList<>();
    private ShapeRenderer shapeRenderer;

    private boolean isLoaded = false;
    private float screenTop;
    private float screenRight;

    private float multiplier = 1f;
    public String alert;

    private LevelData data;
    private boolean reloaded;
    public static final float SCALE_FACTOR = 25f;

    public Level(int l, Main game) {
        this.game = game;
        level = l;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.setToOrtho(false);
        this.touchPosition = new Vector3();
        this.world = new World(new Vector2(0, -60f), true);
        proj = new Projectile(world.getGravity().y);
        this.batch = game.batch;
        launch = new Vector2();
        debugRenderer = new Box2DDebugRenderer();
        bodiesToDelete = new ArrayList<>();
        launched = false;
        birdCounter = 3;

        roster = new ArrayList<>();
        roster.add("red");
        roster.add("bomb");
        roster.add("chuck");
        roster.add("blue");
        roster.add("terence");
        roster.add("matilda");
        roster.add("hal");
        myPigs = new ArrayList<>();
        myBirds = new ArrayList<>();
        Random rand = new Random();
        while (myBirds.size() < 3) {
            int r = rand.nextInt(7);
            if (myBirds.isEmpty()) myBirds.add(roster.get(r));
            else if (!myBirds.contains(roster.get(r))) myBirds.add(roster.get(r));
        }
        data = new LevelData();
        data.setLevel(level);
        data.setMyBird(myBirds);
        shapeRenderer = new ShapeRenderer();
    }

    public Level(int l, Main game, boolean reloaded) {
        this.game = game;
        this.level = l;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.setToOrtho(false);
        this.touchPosition = new Vector3();
        this.world = new World(new Vector2(0, -60f), true);
        proj = new Projectile(world.getGravity().y);
        this.batch = game.batch;
        launch = new Vector2();
        debugRenderer = new Box2DDebugRenderer();
        bodiesToDelete = new ArrayList<>();
        launched = false;
        this.reloaded = reloaded;
        birdCounter = 3;
        data = LevelDatabase.get(level);

        myPigs = new ArrayList<>();
        for (Pig p : data.getMyPig()) {
            Pig temp;
            if (p instanceof SmallPig) {
                temp = new SmallPig(world, p.getPosition().x, p.getPosition().y);
            } else if (p instanceof Crazy)
                temp = new Crazy(world, p.getPosition().x, p.getPosition().y);
            else temp = new King(world, p.getPosition().x, p.getPosition().y);
            temp.createBody();
            myPigs.add(temp);
        }
        blockCollection = new ArrayList<>();
        for (Block b : data.getBlockCollection()) {

            Block temp;
            if (b instanceof Wood) {
                temp = new Wood(world, b.position.x, b.position.y);
            } else if (b instanceof Glass)
                temp = new Glass(world, b.position.x, b.position.y);
            else temp = new Stone(world, b.position.x, b.position.y);
            temp.createBody();
            blockCollection.add(temp);

        }
        myBirds = data.getMyBird();
        shapeRenderer = new ShapeRenderer();
    }

    public Level(String x) {
        this.game = new Main();
        camera = new OrthographicCamera();
        this.touchPosition = new Vector3();
        proj = new Projectile(-9.8f);
        launch = new Vector2();
        launched = false;
        pigCounter = 0;
        birdCounter = 2;

        roster = new ArrayList<>();
        roster.add("red");
        roster.add("bomb");
        roster.add("chuck");
        roster.add("blue");
        roster.add("terence");
        roster.add("matilda");
        roster.add("hal");
        myPigs = new ArrayList<>();
        myBirds = new ArrayList<>();
        Random rand = new Random();
        while (myBirds.size() < 3) {
            int r = rand.nextInt(7);
            if (myBirds.isEmpty()) myBirds.add(roster.get(r));
            else if (!myBirds.contains(roster.get(r))) myBirds.add(roster.get(r));
        }
    }

    public Level(Main game) {
        this.game = game;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.setToOrtho(false);
        this.touchPosition = new Vector3();
        this.world = new World(new Vector2(0, -60f), true);
        proj = new Projectile(world.getGravity().y);
        this.batch = game.batch;
        launch = new Vector2();
        debugRenderer = new Box2DDebugRenderer();
        bodiesToDelete = new ArrayList<>();
        launched = false;
        shapeRenderer = new ShapeRenderer();
    }

    public Level() {
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.camera.setToOrtho(false);
        this.touchPosition = new Vector3();
        this.world = new World(new Vector2(0, -60f), true);
        proj = new Projectile(world.getGravity().y);
        this.batch = new SpriteBatch();
        launch = new Vector2();
        debugRenderer = new Box2DDebugRenderer();
        bodiesToDelete = new ArrayList<>();
        launched = false;
        pigCounter = 1;
        birdCounter = 3;
        myPigs = new ArrayList<>();
        myBirds = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
    }

    public void setGame(Main game) {
        this.game = game;
    }

    private void setActiveBird() {
        if (birdHP == 0 && ctr < 3) {
            ctr++;
            switch (myBirds.get(ctr)) {
                case "red":
                    curr = new Red(this.world);
                    break;
                case "blue":
                    curr = new Blue(this.world);
                    break;
                case "chuck":
                    curr = new Chuck(this.world);
                    break;
                case "bomb":
                    curr = new Bomb(this.world);
                    break;
                case "matilda":
                    curr = new Matilda(this.world);
                    break;
                case "terence":
                    curr = new Terence(this.world);
                    break;
                case "hal":
                    curr = new Hal(this.world);
                    break;
            }
            birdSprite = curr.getSprite();
            birdBody = curr.createBody();
            birdHP = curr.getHealth();
        }
    }

    public int handleWin() {
        ShopPage.setCoins(ShopPage.getCoins() + 100);
        return ShopPage.getCoins();
    }

    public int handleLoss() {
        return ShopPage.getCoins();
    }

    private void createStructure(float startX, float startY, int rows, int cols, float blockWidth, float blockHeight, String type) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float posX = startX + j * blockWidth;
                float posY = startY + i * blockHeight;
                createBlock(posX, posY, blockWidth, blockHeight, type);
            }
        }
    }

    private void createBlock(float x, float y, float width, float height, String type) {
        Block temp;
        if (type.equals("wood")) {
            temp = new Wood(world, x, y);
        } else if (type.equals("stone")) {
            temp = new Stone(world, x, y);
        } else temp = new Glass(world, x, y);
        temp.createBody();
        blockCollection.add(temp);
    }

    public void birdGroundContact() {
        birdHP = 0;
        bodiesToDelete.add(birdBody);
        birdCounter--;
        birdSprite = null;
        alert = "BIRD HP DECREASED TO 0";
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        if (level % 3 == 0) bg = new Texture("levelAssets/bg2.png");
        else if (level % 3 == 1) bg = new Texture("levelAssets/bg1.png");
        else if (level % 3 == 2) bg = new Texture("levelAssets/bg3.png");

        catapult = new Texture("catapult.png");
        wood = new Texture("blocks/wood.png");
        stone = new Texture("blocks/stone.png");
        glass = new Texture("blocks/glass.png");
        pause = new Texture("icons/pauseIcon.png");
        quit = new Texture("icons/exitIcon.png");
        resume = new Texture("icons/resumeIcon.png");
        retry = new Texture("icons/retryIcon.png");
        tempBird1 = new Texture("birds/" + myBirds.get(1) + ".png");
        tempBird2 = new Texture("birds/" + myBirds.get(2) + ".png");

        this.powershotImage = new Powershot().getImage();
        this.superchargeImage = new Supercharge().getImage();
        this.birdquakeImage = new Birdquake().getImage();
        this.potionImage = new Potion().getImage();

        screenRight = (camera.position.x + (camera.viewportWidth / 2)) / SCALE_FACTOR;
        screenTop = (camera.position.y + camera.viewportHeight / 2) / SCALE_FACTOR;
        bgSprite = new Sprite(bg);
        catapultSprite = new Sprite(catapult);
        woodSprite = new Sprite(wood);
        stoneSprite = new Sprite(stone);
        glassSprite = new Sprite(glass);
        ctr = 3 - birdCounter;
        switch (myBirds.get(ctr)) {
            case "red":
                curr = new Red(this.world);
                break;
            case "blue":
                curr = new Blue(this.world);
                break;
            case "chuck":
                curr = new Chuck(this.world);
                break;
            case "bomb":
                curr = new Bomb(this.world);
                break;
            case "matilda":
                curr = new Matilda(this.world);
                break;
            case "terence":
                curr = new Terence(this.world);
                break;
            case "hal":
                curr = new Hal(this.world);
                break;
        }
        birdSprite = curr.getSprite();
        birdBody = curr.createBody();
        birdHP = curr.getHealth();
        catapultSprite.setPosition(100, 95);
        Random rand = new Random();
        int randomnum = rand.nextInt(6);
        int randnum = rand.nextInt(3);
        String randText = "wood";
        String randText2 = "wood";
        if (randnum == 0) {
            randText = "wood";
        } else if (randnum == 1) {
            randText = "glass";
        } else if (randnum == 2) {
            randText = "stone";
        }
        int randnum2 = rand.nextInt(3);
        if (randnum2 == 0) {
            randText2 = "wood";
        } else if (randnum2 == 1) {
            randText2 = "glass";
        } else if (randnum2 == 2) {
            randText2 = "stone";
        }
        if (!isLoaded && !reloaded) {
            if (level == 0) {
                createStructure(900, 125, 2, 2, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
                Pig pig1 = new SmallPig(this.world, 900, 245);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 1 || (randomnum == 1 && level > 5)) {
                if (level == 1) {
                    createStructure(900, 125, 3, 2, stoneSprite.getWidth(), stoneSprite.getHeight(), "stone");
                } else {
                    createStructure(900, 125, 3, 2, woodSprite.getWidth(), woodSprite.getHeight(), randText);
                }
                Pig pig1 = new SmallPig(this.world, 900, 300);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 2 || ((randomnum == 2 || randomnum == 0) && level > 5)) {
                if (level == 2) {
                    createStructure(900, 125, 3, 3, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
                } else {
                    createStructure(900, 125, 3, 3, woodSprite.getWidth(), woodSprite.getHeight(), randText);
                }
                Pig pig1 = new SmallPig(this.world, 875, 310);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new SmallPig(this.world, 1035, 310);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 3 || (randomnum == 3 && level > 5)) {
                if (level == 3) {
                    createStructure(750, 125, 2, 1, glassSprite.getWidth(), glassSprite.getHeight(), "glass");
                    createStructure(950, 125, 2, 1, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
                } else {
                    createStructure(750, 125, 2, 1, glassSprite.getWidth(), glassSprite.getHeight(), randText);
                    createStructure(950, 125, 2, 1, woodSprite.getWidth(), woodSprite.getHeight(), randText2);
                }
                Pig pig1 = new SmallPig(this.world, 730, 225);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new Crazy(this.world, 825, 110);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new SmallPig(this.world, 930, 225);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 4 || (randomnum == 4 && level > 5)) {
                if (level == 4) {
                    createStructure(650, 125, 3, 1, glassSprite.getWidth(), glassSprite.getHeight(), "glass");
                    createStructure(950, 125, 3, 1, glassSprite.getWidth(), glassSprite.getHeight(), "glass");
                } else {
                    createStructure(650, 125, 3, 1, glassSprite.getWidth(), glassSprite.getHeight(), randText);
                    createStructure(950, 125, 3, 1, glassSprite.getWidth(), glassSprite.getHeight(), randText2);
                }
                Pig pig1 = new SmallPig(this.world, 635, 310);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new Crazy(this.world, 725, 110);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new Crazy(this.world, 810, 110);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new SmallPig(this.world, 935, 310);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 5 || (randomnum == 5 && level > 5)) {
                if (level == 5) {
                    createStructure(750, 125, 3, 1, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
                    createStructure(950, 125, 3, 1, stoneSprite.getWidth(), stoneSprite.getHeight(), "stone");
                } else {
                    createStructure(750, 125, 3, 1, woodSprite.getWidth(), woodSprite.getHeight(), randText);
                    createStructure(950, 125, 3, 1, stoneSprite.getWidth(), stoneSprite.getHeight(), randText2);
                }
                Pig pig1 = new Crazy(this.world, 720, 320);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new King(this.world, 800, 100);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new Crazy(this.world, 920, 320);
                pig1.createBody();
                myPigs.add(pig1);
            }
            List<Block> cpy = new ArrayList<>(blockCollection);
            data.setBlockCollection(cpy);
            List<Pig> clown = new ArrayList<>(myPigs);
            data.setMyPig(clown);
            LevelDatabase.add(data);
        }
        pigCounter = myPigs.size();

        powershotButton = new Button(powershotImage, 35, Gdx.graphics.getHeight() - 165, 0.25f);
        superchargeButton = new Button(superchargeImage, 105, Gdx.graphics.getHeight() - 155, 0.35f);
        birdquakeButton = new Button(birdquakeImage, 180, Gdx.graphics.getHeight() - 125, 0.35f);
        potionButton = new Button(potionImage, 260, Gdx.graphics.getHeight() - 125, 0.45f);
        pauseButton = new Button(pause, 20, Gdx.graphics.getHeight() - 90, 1f);
        resumeButton = new Button(resume, Gdx.graphics.getWidth() / 2 - 125, Gdx.graphics.getHeight() / 2, 1.4f);
        quitButton = new Button(quit, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2, 1.6f);
        retryButton = new Button(retry, Gdx.graphics.getWidth() / 2 + 115, Gdx.graphics.getHeight() / 2 + 4, 1.6f);

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(50 / SCALE_FACTOR, 90 / SCALE_FACTOR);
        bgBody = world.createBody(bd);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(camera.viewportWidth / SCALE_FACTOR, 7f / SCALE_FACTOR);
        bgBody.createFixture(shape, 1.0f);
        bgBody.setUserData(bgSprite);

        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set(0 / SCALE_FACTOR, 0 / SCALE_FACTOR);
        Body sideBody = world.createBody(bd);
        shape = new PolygonShape();
        shape.setAsBox(0 / SCALE_FACTOR, camera.viewportHeight / SCALE_FACTOR);
        sideBody.createFixture(shape, 1.0f);
        sideBody.setUserData(bgSprite);

        bd.type = BodyDef.BodyType.StaticBody;
        bd.position.set((catapultSprite.getX() + catapultSprite.getWidth() / 2 + 10) / SCALE_FACTOR, (catapultSprite.getY() + catapultSprite.getHeight() / 2 - 30) / SCALE_FACTOR);
        catapultBody = world.createBody(bd);
        shape = new PolygonShape();
        shape.setAsBox((catapultSprite.getWidth() / 2 - 15) / SCALE_FACTOR, (catapultSprite.getHeight() / 2 - 30) / SCALE_FACTOR);
        catapultBody.createFixture(shape, 1.0f);
        catapultBody.setUserData(catapultSprite);
        shape.dispose();
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture fig1 = contact.getFixtureA();
                Fixture fig2 = contact.getFixtureB();
                if ((fig1.getBody().equals(birdBody) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(birdBody))) {
                    birdGroundContact();
                    if (charged) {
                        explode(birdBody);
                        charged = false;
                        birdHP = 0;
                    }
                } else {
                    for (int j = 0; j < myPigs.size(); j++) {
                        Body pigBody = myPigs.get(j).getBody();
                        int pigHP = myPigs.get(j).getHealth();
                        if ((fig1.getBody().equals(birdBody) && fig2.getBody().equals(pigBody)) || (fig1.getBody().equals(pigBody) && fig2.getBody().equals(birdBody))) {
                            if (myPigs.get(j).getSprite() == null) return;
                            if (birdHP == 0) {
                                continue;
                            }
                            if (charged) {
                                explode(birdBody);
                                charged = false;
                                birdHP = 0;
                            }
                            if (pigHP > birdHP) {
                                pigHP -= birdHP;
                                myPigs.get(j).setHealth(pigHP);
                                birdHP = 0;
                                birdCounter--;
                                birdBody.setLinearVelocity(birdBody.getLinearVelocity().x, 0);
                                bodiesToDelete.add(birdBody);
                                birdSprite = null;
                            } else {
                                birdHP -= pigHP;
                                pigHP = 0;
                                myPigs.get(j).setHealth(pigHP);
                                pigCounter -= 1;
                                if (birdHP == 0) {
                                    birdCounter--;
                                    bodiesToDelete.add(birdBody);
                                    birdSprite = null;
                                }
                                bodiesToDelete.add(pigBody);
                                myPigs.get(j).setSprite(null);
                            }
                        } else if ((fig1.getBody().equals(pigBody) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(pigBody))) {
                            if (myPigs.get(j).getSprite() == null) return;
                            if (Math.abs(pigBody.getLinearVelocity().y) >= 1) {
                                pigHP -= 1;
                                myPigs.get(j).setHealth(pigHP);
                                if (pigHP <= 0) {
                                    pigCounter -= 1;
                                    bodiesToDelete.add(pigBody);
                                    myPigs.get(j).setSprite(null);
                                }
                            }
                        } else {
                            for (int i = 0; i < blockCollection.size(); i++) {
                                if (blockCollection.get(i).sp == null) return;
                                Body temp = blockCollection.get(i).body;
                                if ((fig1.getBody().equals(temp) && fig2.getBody().equals(birdBody)) || (fig1.getBody().equals(birdBody) && fig2.getBody().equals(temp))) {
                                    if (birdHP == 0) {
                                        continue;
                                    }
                                    if (charged) {
                                        explode(birdBody);
                                        charged = false;
                                        birdHP = 0;
                                    }
                                    if (blockCollection.get(i).hp > birdHP) {
                                        blockCollection.get(i).hp -= birdHP;
                                        birdHP = 0;
                                        if (blockCollection.get(i).hp == 0) {
                                            bodiesToDelete.add(blockCollection.get(i).body);
                                            blockCollection.get(i).sp = null;
                                        }
                                        birdBody.setLinearVelocity(birdBody.getLinearVelocity().x, 0);
                                        birdCounter--;
                                        bodiesToDelete.add(birdBody);
                                        birdSprite = null;
                                    } else {
                                        birdHP -= blockCollection.get(i).hp;
                                        blockCollection.get(i).hp = 0;
                                        birdBody.setLinearVelocity(birdBody.getLinearVelocity().x, 0);
                                        if (birdHP == 0) {
                                            birdCounter--;
                                            bodiesToDelete.add(birdBody);
                                            birdSprite = null;
                                        }
                                        bodiesToDelete.add(blockCollection.get(i).body);
                                        blockCollection.get(i).sp = null;
                                    }
                                } else if ((fig1.getBody().equals(temp) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(temp))) {
                                    if (blockCollection.get(i).sp == null) return;
                                    if (Math.abs(temp.getLinearVelocity().y) >= 1) {
                                        blockCollection.get(i).hp -= 1;
                                        if (blockCollection.get(i).hp <= 0) {
                                            bodiesToDelete.add(blockCollection.get(i).body);
                                            blockCollection.get(i).sp = null;
                                        }
                                    }
                                } else if ((fig1.getBody().equals(pigBody) && fig2.getBody().equals(temp)) || (fig1.getBody().equals(temp) && fig2.getBody().equals(pigBody))) {
                                    if (myPigs.get(j).getSprite() == null) return;
                                    if (blockCollection.get(i).sp == null) return;
                                    if (Math.abs(pigBody.getLinearVelocity().y) >= 1 || Math.abs(temp.getLinearVelocity().y) >= 1) {
                                        pigHP -= 1;
                                        myPigs.get(j).setHealth(pigHP);
                                        if (pigHP <= 0) {
                                            pigCounter -= 1;
                                            bodiesToDelete.add(pigBody);
                                            myPigs.get(j).setSprite(null);
                                        }
                                        blockCollection.get(i).hp -= 1;
                                        if (blockCollection.get(i).hp <= 0) {
                                            bodiesToDelete.add(blockCollection.get(i).body);
                                            blockCollection.get(i).sp = null;
                                        }
                                    }
                                } else if ((fig1.getBody().equals(pigBody) || fig2.getBody().equals(pigBody))) {
                                    if (myPigs.get(j).getSprite() == null) return;
                                    if (Math.abs(pigBody.getLinearVelocity().y) >= 1) {
                                        pigHP -= 1;
                                        myPigs.get(j).setHealth(pigHP);
                                        if (pigHP <= 0) {
                                            pigCounter -= 1;
                                            bodiesToDelete.add(myPigs.get(j).getBody());
                                            myPigs.get(j).setSprite(null);
                                        }
                                    }
                                } else if ((fig1.getBody().equals(temp) || fig2.getBody().equals(temp))) {
                                    if (blockCollection.get(i).sp == null) return;
                                    if (Math.abs(temp.getLinearVelocity().y) >= 1) {
                                        blockCollection.get(i).hp -= 1;
                                        if (blockCollection.get(i).hp <= 0) {
                                            bodiesToDelete.add(blockCollection.get(i).body);
                                            blockCollection.get(i).sp = null;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }

    @Override
    public void render(float delta) {
        camera.update();
        world.step(1 / 60f, 6, 2);

        if ((birdBody.getPosition().x > screenRight || birdBody.getPosition().y > screenTop) && birdSprite != null) {
            birdCounter--;
            birdHP = 0;
            bodiesToDelete.add(birdBody);
            birdSprite = null;
        }
        for (int i = 0; i < blockCollection.size(); i++) {
            Block temp = blockCollection.get(i);
            if ((temp.body.getPosition().x > screenRight || temp.body.getPosition().y > screenTop) && temp.sp != null) {
                bodiesToDelete.add(temp.body);
                blockCollection.get(i).sp = null;
            }
        }
        for (int i = 0; i < myPigs.size(); i++) {
            Pig temp = myPigs.get(i);
            if ((temp.getBody().getPosition().x > screenRight || temp.getBody().getPosition().y > screenTop) && temp.getSprite() != null) {
                bodiesToDelete.add(temp.getBody());
                temp.setSprite(null);
                pigCounter--;
            }
        }
        if (ctr < 2) setActiveBird();
        if (birdSprite != null)
            birdSprite.setPosition((birdBody.getPosition().x * SCALE_FACTOR) - birdSprite.getWidth() / 2, (birdBody.getPosition().y * SCALE_FACTOR) - birdSprite.getHeight() / 2);
        for (int i = 0; i < myPigs.size(); i++) {
            Sprite pigSprite = myPigs.get(i).getSprite();
            if (pigSprite != null) {
                pigSprite.setOrigin(pigSprite.getWidth() / 2, pigSprite.getHeight() / 2);
                pigSprite.setPosition((myPigs.get(i).getBody().getPosition().x * SCALE_FACTOR) - pigSprite.getWidth() / 2, (myPigs.get(i).getBody().getPosition().y * SCALE_FACTOR) - pigSprite.getHeight() / 2);
                pigSprite.setRotation((float) Math.toDegrees(myPigs.get(i).getBody().getAngle()));
            }
        }
        for (int i = 0; i < blockCollection.size(); i++) {
            Sprite temp = blockCollection.get(i).sp;
            if (temp != null) {
                temp.setPosition((blockCollection.get(i).body.getPosition().x * SCALE_FACTOR) - temp.getWidth() / 2, (blockCollection.get(i).body.getPosition().y * SCALE_FACTOR) - temp.getHeight() / 2);
                temp.setRotation((float) Math.toDegrees(blockCollection.get(i).body.getAngle()));
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        update(delta);

        BitmapFont font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(1.5f);
        font.setColor(Color.BLACK);

        batch.begin();
        bgSprite.draw(batch);
        for (int i = 0; i < myPigs.size(); i++) {
            Sprite temp = myPigs.get(i).getSprite();
            if (temp != null) temp.draw(batch);
        }
        for (int i = 0; i < blockCollection.size(); i++) {
            Sprite temp = blockCollection.get(i).sp;
            if (temp != null) temp.draw(batch);
        }
        if (ctr == 0) {
            Sprite temp = new Sprite(tempBird1);
            temp.setSize(temp.getWidth() * 0.2f, temp.getHeight() * 0.2f);
            temp.setPosition(75, 95);
            temp.draw(batch);
            temp = new Sprite(tempBird2);
            temp.setSize(temp.getWidth() * 0.2f, temp.getHeight() * 0.2f);
            temp.setPosition(25, 95);
            temp.draw(batch);
        } else if (ctr == 1) {
            Sprite temp = new Sprite(tempBird2);
            temp.setSize(temp.getWidth() * 0.2f, temp.getHeight() * 0.2f);
            temp.setPosition(75, 95);
            temp.draw(batch);
        }
        catapultSprite.draw(batch);
        if (birdSprite != null) birdSprite.draw(batch);
        pauseButton.getButtonSprite().draw(batch);
        powershotButton.getButtonSprite().draw(batch);
        potionButton.getButtonSprite().draw(batch);
        superchargeButton.getButtonSprite().draw(batch);
        birdquakeButton.getButtonSprite().draw(batch);
        font.draw(batch, Integer.toString(Inventory.getItemCount("power")), powershotButton.getButtonSprite().getX() + 92, powershotButton.getButtonSprite().getY() + 85);
        font.draw(batch, Integer.toString(Inventory.getItemCount("charge")), superchargeButton.getButtonSprite().getX() + 92, superchargeButton.getButtonSprite().getY() + 73);
        font.draw(batch, Integer.toString(Inventory.getItemCount("quake")), birdquakeButton.getButtonSprite().getX() + 92, birdquakeButton.getButtonSprite().getY() + 50);
        font.draw(batch, Integer.toString(Inventory.getItemCount("potion")), potionButton.getButtonSprite().getX() + 92, potionButton.getButtonSprite().getY() + 50);
        batch.end();

        if (visibility) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
            batch.begin();
            retryButton.getButtonSprite().draw(batch);
            resumeButton.getButtonSprite().draw(batch);
            quitButton.getButtonSprite().draw(batch);
            batch.end();
        }

        float touchX = Gdx.input.getX();
        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
        if ((Inventory.getItemCount("quake") <= 0 && birdquakeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (Inventory.getItemCount("charge") <= 0 && superchargeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (Inventory.getItemCount("potion") <= 0 && potionButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (Inventory.getItemCount("power") <= 0 && powershotButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.NotAllowed);
        else if (pauseButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)
            || (visibility && pauseButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && resumeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && retryButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (birdquakeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (superchargeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (potionButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (powershotButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
        else Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);

        if (Gdx.input.justTouched()) {
            if (!visibility && pauseButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                visibility = true;
            } else {
                if (resumeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    visibility = false;
                } else if (quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    try {
                        saveState();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    game.setScreen(new Map(this.game));
                } else if (retryButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                    game.setScreen(new Level(level, game, true));
                } else {
                    visibility = false;
                }
            }
            if (superchargeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                if (Inventory.getItemCount("charge") > 0) {
                    Inventory.useItem("charge");
                    charged = true;
                }
            }
            if (powershotButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                if (Inventory.getItemCount("power") > 0) {
                    Inventory.useItem("power");
                    multiplier = 5;
                }
            }
            if (potionButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                if (Inventory.getItemCount("potion") > 0) {
                    Inventory.useItem("potion");
                    birdHP += 4;
                }
            }
            if (birdquakeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
                if (Inventory.getItemCount("quake") > 0) {
                    Inventory.useItem("quake");
                    for (Pig pig : myPigs) {
                        if (pig.getHealth() > 0) {
                            pig.setHealth(pig.getHealth() - 1);
                            if (pig.getHealth() <= 0) {
                                bodiesToDelete.add(pig.getBody());
                                pig.setSprite(null);
                            }
                        }
                    }
                    for (Block block : blockCollection) {
                        if (block.hp > 0) {
                            block.hp -= 1;
                            if (block.hp <= 0) {
                                bodiesToDelete.add(block.body);
                                block.sp = null;
                            }
                        }
                    }
                }
            }
        }
        if (flag) {
            float t = 0f;
            float maxT = 1f;
            proj.startPoint.set((initialTouchPosition.x * SCALE_FACTOR), (initialTouchPosition.y * SCALE_FACTOR));
            proj.startVelocity.set(launch.x * SCALE_FACTOR, launch.y * SCALE_FACTOR);
            ShapeRenderer shape = new ShapeRenderer();
            shape.setProjectionMatrix(camera.combined);
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(Color.VIOLET);
            while (t < maxT) {
                float x = proj.getX(t);
                float y = proj.getY(t);
                shape.circle(x, y, 3f);
                t += 0.1f;
            }
            shape.end();
            shape.dispose();
        }

        Array<Body> worldBodies = new Array<>();
        world.getBodies(worldBodies);
        boolean temp = true;
        for (Body b : worldBodies) {
            if (b.getLinearVelocity().x != 0 && b.getLinearVelocity().y != 0) temp = false;
        }
        if (temp) {
            if (pigCounter <= 0 && birdCounter <= 0) {
                handleWin();
                if (level % 6 == 5) {
                    Map.levelctr++;
                }
                game.setScreen(new WinScreen(level, game));
            } else if (pigCounter <= 0) {
                handleWin();
                if (level % 6 == 5) {
                    Map.levelctr++;
                }
                game.setScreen(new WinScreen(level, game));
            } else if (birdCounter <= 0) {
                handleLoss();
                game.setScreen(new LoseScreen(level, game));
            }
        }

//        debugMatrix = batch.getProjectionMatrix().cpy().scale(SCALE_FACTOR, SCALE_FACTOR, 0);
//        debugRenderer.render(world, debugMatrix);
    }

    private void update(float delta) {
        world.step(delta, 6, 2);
        Set<Body> s = new HashSet<Body>(bodiesToDelete);
        for (Body body : s) {
            if (body != null) {
                try {
                    for (int i = 0; i < blockCollection.size(); i++) {
                        if (blockCollection.get(i).body.equals(body)) {
                            blockCollection.remove(i);
                            break;
                        }
                    }
                    for (int i = 0; i < myPigs.size(); i++) {
                        if (myPigs.get(i).getBody().equals(body)) {
                            myPigs.remove(i);
                            break;
                        }
                    }
                    world.destroyBody(body);
                } catch (Exception e) {
                    System.err.println("Error destroying body: " + e.getMessage());
                }
            }
        }
        s.clear();
        bodiesToDelete.clear();
    }

    public void explode(Body body) {
        float explosionRadius = 100f;
        Vector2 explosionCenter = body.getPosition();
        world.QueryAABB(new QueryCallback() {
                            @Override
                            public boolean reportFixture(Fixture fixture) {
                                Body body = fixture.getBody();
                                Vector2 bodyPos = body.getPosition();
                                if (bodyPos.dst(explosionCenter) <= explosionRadius) {
                                    for (Pig pig : myPigs) {
                                        if (body.equals(pig.getBody())) {
                                            pig.setHealth(pig.getHealth() - 1);
                                            if (pig.getHealth() <= 0) {
                                                bodiesToDelete.add(pig.getBody());
                                                pig.setSprite(null);
                                            }
                                        }
                                    }
                                    for (Block b : blockCollection) {
                                        if (body.equals(b.body)) {
                                            b.hp = b.hp - 1;
                                            if (b.hp <= 0) {
                                                bodiesToDelete.add(b.body);
                                                b.sp = null;
                                            }
                                        }
                                    }
                                }
                                return true;
                            }
                        },
            explosionCenter.x - explosionRadius,
            explosionCenter.y - explosionRadius,
            explosionCenter.x + explosionRadius,
            explosionCenter.y + explosionRadius);

        bodiesToDelete.add(body);
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
        bg.dispose();
        wood.dispose();
        stone.dispose();
        glass.dispose();
        tempBird1.dispose();
        tempBird2.dispose();
        catapult.dispose();
        world.dispose();
        batch.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
        flag = catapultSprite.getBoundingRectangle().contains(touchPosition.x, touchPosition.y);
        touchPosition.x /= SCALE_FACTOR;
        touchPosition.y /= SCALE_FACTOR;
        if (flag && !launched) initialTouchPosition.set(touchPosition.x, touchPosition.y);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (flag && !launched) {
            Vector3 touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
            touchPosition.x /= SCALE_FACTOR;
            touchPosition.y /= SCALE_FACTOR;
            launch.set(initialTouchPosition.x - touchPosition.x, initialTouchPosition.y - touchPosition.y);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (flag && !launched) {
            touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
            touchPosition.x /= SCALE_FACTOR;
            touchPosition.y /= SCALE_FACTOR;
            float impulseX = initialTouchPosition.x - touchPosition.x;
            float impulseY = initialTouchPosition.y - touchPosition.y;
            float dragDistance = initialTouchPosition.dst(touchPosition.x, touchPosition.y);
            launch.set(impulseX, impulseY).scl(dragDistance * 0.09f * multiplier);
            birdBody.applyLinearImpulse(launch, birdBody.getWorldCenter(), true);
            multiplier = 1;
            flag = false;
        }
        return true;
    }

    @Override
    public void write(Json json) {
        json.writeValue("level", this.level);
        json.writeValue("birdCounter", this.birdCounter);
        json.writeValue("pigCounter", this.pigCounter);
        json.writeValue("coins", ShopPage.getCoins());
        json.writeValue("remainingBirds", this.myBirds);
        json.writeValue("levelctr", Map.levelctr);
        json.writeValue("inventory", Inventory.inventory);
        List<HashMap<String, Object>> pigData = new ArrayList<>();
        for (Pig pig : this.myPigs) {
            HashMap<String, Object> pigInfo = new HashMap<>();
            pigInfo.put("x", pig.getBody().getPosition().x * SCALE_FACTOR - 35);
            pigInfo.put("y", pig.getBody().getPosition().y * SCALE_FACTOR - 35);
            pigInfo.put("hp", pig.getHealth());
            int val = 0;
            if (pig instanceof SmallPig) val = 1;
            else if (pig instanceof Crazy) val = 2;
            else if (pig instanceof King) val = 3;
            pigInfo.put("val", val);
            pigData.add(pigInfo);
        }
        json.writeValue("pigs", pigData);
        List<HashMap<String, Object>> blockData = new ArrayList<>();
        for (Block block : this.blockCollection) {
            HashMap<String, Object> blockInfo = new HashMap<>();
            blockInfo.put("x", block.body.getPosition().x * SCALE_FACTOR);
            blockInfo.put("y", block.body.getPosition().y * SCALE_FACTOR);
            blockInfo.put("width", block.width);
            blockInfo.put("height", block.height);
            if(block instanceof Wood) blockInfo.put("type", "wood");
            else if(block instanceof Glass) blockInfo.put("type", "glass");
            else blockInfo.put("type", "stone");
            blockInfo.put("hp", block.hp);
            blockData.add(blockInfo);
        }
        json.writeValue("blocks", blockData);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        this.myBirds.clear();
        this.myPigs.clear();
        this.blockCollection.clear();

        this.level = jsonData.getInt("level", 0);
        this.birdCounter = jsonData.getInt("birdCounter", 3);
        this.pigCounter = jsonData.getInt("pigCounter", 1);
        ShopPage.setCoins(jsonData.getInt("coins", 0));
        JsonValue birdsJson = jsonData.get("remainingBirds");
        myBirds = new ArrayList<>();
        for (JsonValue birdJson : birdsJson) {
            this.myBirds.add(birdJson.getString("value"));
        }
        Map.levelctr = jsonData.getInt("levelctr", 0);
        JsonValue inventoryy = jsonData.get("inventory");
        if (inventoryy.get("quake") != null && inventoryy.get("quake").getInt("value") > 0)
            Inventory.inventory.put("quake", inventoryy.get("quake").getInt("value"));
        if (inventoryy.get("power") != null && inventoryy.get("power").getInt("value") > 0)
            Inventory.inventory.put("power", inventoryy.get("power").getInt("value"));
        if (inventoryy.get("charge") != null && inventoryy.get("charge").getInt("value") > 0)
            Inventory.inventory.put("charge", inventoryy.get("charge").getInt("value"));
        if (inventoryy.get("potion") != null && inventoryy.get("potion").getInt("value") > 0)
            Inventory.inventory.put("potion", inventoryy.get("potion").getInt("value"));
        JsonValue pigsJson = jsonData.get("pigs");
        myPigs = new ArrayList<>();
        for (JsonValue pigJson : pigsJson) {
            float x = pigJson.get("x").getFloat("value");
            float y = pigJson.get("y").getFloat("value");
            int z = pigJson.get("val").getInt("value");
            Pig pig = new SmallPig(this.world, x, y);
            if (z == 1) pig = new SmallPig(this.world, x, y);
            else if (z == 2) pig = new Crazy(this.world, x, y);
            else if (z == 3) pig = new King(this.world, x, y);
            pig.createBody();
            pig.setHealth(pigJson.get("hp").getInt("value"));
            this.myPigs.add(pig);
        }

        JsonValue blocksJson = jsonData.get("blocks");
        for (JsonValue blockJson : blocksJson) {
            float x = blockJson.get("x").getFloat("value");
            float y = blockJson.get("y").getFloat("value");
            float width = blockJson.get("width").getFloat("value");
            float height = blockJson.get("height").getFloat("value");
            String type = blockJson.get("type").getString("value");
            createBlock(x, y, width, height, type);
        }
    }

    public void saveState() throws IOException {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        FileHandle saveFile = Gdx.files.local("state.json");
        saveFile.writeString(json.toJson(this), false);
    }

    public static Level loadState(Main game) throws IOException {
        Json json = new Json();
        FileHandle saveFile = Gdx.files.local("state.json");
        if (saveFile.exists()) {
            Level loadedLevel = json.fromJson(Level.class, saveFile.readString());
            loadedLevel.setGame(game);
            loadedLevel.isLoaded = true;
            return loadedLevel;
        }
        return new Level(game);
    }
}
