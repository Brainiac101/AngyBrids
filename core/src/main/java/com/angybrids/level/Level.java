package com.angybrids.level;

import com.angybrids.Button;
import com.angybrids.Main;
import com.angybrids.birds.*;
import com.angybrids.blocks.Block;
import com.angybrids.pages.LoseScreen;
import com.angybrids.pages.Map;
import com.angybrids.pages.WinScreen;
import com.angybrids.pigs.Crazy;
import com.angybrids.pigs.King;
import com.angybrids.pigs.Pig;
import com.angybrids.pigs.SmallPig;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    private Texture bg, /*pig,*/
        catapult, wood, stone, glass, tempBird1, tempBird2;
    private final List<String> myBirds;
    private final List<Pig> myPigs;
    private Bird curr;

    private Sprite birdSprite, bgSprite, /*pigSprite,*/
        catapultSprite, woodSprite, stoneSprite, glassSprite;
    private Body birdBody, bgBody, /*pigBody,*/
        catapultBody, woodBody, standBody;

    private final Projectile proj;
    private boolean flag = false;
    private final Vector2 initialTouchPosition = new Vector2();
    private final Vector2 launch;
    private Vector3 touchPosition;
    private List<Body> bodiesToDelete;
    public static final float SCALE_FACTOR = 25f;
    public int birdHP;
    private int pigHP;
    List<Block> blockCollection = new ArrayList<>();
    private ShapeRenderer shapeRenderer;

    private List<String> roster;
    private int ctr;
    private boolean launched;

    public boolean isLoaded = false;
    private Texture pause;
    private Texture quit;
    private Texture resume;
    private Texture retry;
    private boolean visibility;
    private int pigCounter;
    private int birdCounter;
    private float screenTop;
    private float screenRight;
    private int level;
    private float timer = 3.5f;

    public String alert;

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

        shapeRenderer = new ShapeRenderer();
    }

    public Level(Main game) {
        this.game = game;
//        level = l;
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
        pigCounter = 1;
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

    private void createStructure(float startX, float startY, int rows, int cols, float blockWidth, float blockHeight, String type) {
//        Random rand = new Random();
//        rows=rand.nextInt(4)+1;
//        cols=rand.nextInt(4)+1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                float posX = startX + j * blockWidth;
                float posY = startY + i * blockHeight;
                createBlock(posX, posY, blockWidth, blockHeight, type);
            }
        }
    }

    private void createBlock(float x, float y, float width, float height, String type) {
        // Create the sprite for the block
        Sprite blockSprite = new Sprite(new Texture("blocks/" + type + ".png"));
        blockSprite.setSize(width, height);
        blockSprite.setPosition(x, y);
        // Create the BodyDef for the block
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x / SCALE_FACTOR, y / SCALE_FACTOR);
        // Create the physics body
        Body blockBody = world.createBody(bd);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / SCALE_FACTOR, height / 2 / SCALE_FACTOR);
        blockBody.createFixture(shape, 0.1f);
        blockBody.setUserData(blockSprite);
        shape.dispose();

        Block temp = new Block(new Vector2(x, y), width, height, type);
        temp.body = blockBody;
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
        if (level % 3 == 0) {
            bg = new Texture("levelAssets/bg2.png");
        } else if (level % 3 == 1) {
            bg = new Texture("levelAssets/bg1.png");
        } else if (level % 3 == 2) {
            bg = new Texture("levelAssets/bg3.png");
        }
//        pig = new Texture("pigs/pig.png");
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

        if (!isLoaded) {
            if (level == 0) {
                createStructure(900, 125, 2, 2, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
                Pig pig1 = new SmallPig(this.world, 900, 210);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 1) {
                createStructure(900, 125, 3, 2, stoneSprite.getWidth(), stoneSprite.getHeight(), "stone");
                Pig pig1 = new SmallPig(this.world, 900, 300);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 2) {
                createStructure(900, 125, 3, 3, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
                Pig pig1 = new SmallPig(this.world, 875, 310);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new SmallPig(this.world, 1035, 310);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 3) {
                createStructure(750, 125, 2, 1, glassSprite.getWidth(), glassSprite.getHeight(), "glass");
                Pig pig1 = new SmallPig(this.world, 730, 225);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new Crazy(this.world, 825, 110);
                pig1.createBody();
                myPigs.add(pig1);
                createStructure(950, 125, 2, 1, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
                pig1 = new SmallPig(this.world, 930, 225);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 4) {
                createStructure(650, 125, 3, 1, glassSprite.getWidth(), glassSprite.getHeight(), "glass");
                Pig pig1 = new SmallPig(this.world, 635, 310);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new Crazy(this.world, 725, 110);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new Crazy(this.world, 810, 110);
                pig1.createBody();
                myPigs.add(pig1);
                createStructure(950, 125, 3, 1, glassSprite.getWidth(), glassSprite.getHeight(), "glass");
                pig1 = new SmallPig(this.world, 935, 310);
                pig1.createBody();
                myPigs.add(pig1);
            } else if (level == 5) {
                createStructure(750, 125, 3, 1, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
                Pig pig1 = new Crazy(this.world, 720, 320);
                pig1.createBody();
                myPigs.add(pig1);
                pig1 = new King(this.world, 800, 100);
                pig1.createBody();
                myPigs.add(pig1);
                createStructure(950, 125, 3, 1, stoneSprite.getWidth(), stoneSprite.getHeight(), "stone");
                pig1 = new Crazy(this.world, 920, 320);
                pig1.createBody();
                myPigs.add(pig1);
            }
            pigCounter = myPigs.size();
        }

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
                // bird + ground
                if ((fig1.getBody().equals(birdBody) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(birdBody))) {
                    birdGroundContact();
                } else {
                    // pig + bird
                    for (int j = 0; j < myPigs.size(); j++) {
                        Body pigBody = myPigs.get(j).getBody();
                        int pigHP = myPigs.get(j).getHealth();
                        if ((fig1.getBody().equals(birdBody) && fig2.getBody().equals(pigBody)) || (fig1.getBody().equals(pigBody) && fig2.getBody().equals(birdBody))) {
                            if (birdHP == 0) {
                                continue;
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
                                //destruction
                            }
                        }
                        // pig + ground
                        else if ((fig1.getBody().equals(pigBody) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(pigBody))) {
                            if (Math.abs(pigBody.getLinearVelocity().y) >= 1) {
                                pigHP -= 1;
                                myPigs.get(j).setHealth(pigHP);
                                if (pigHP <= 0) {
                                    pigCounter -= 1;
                                    bodiesToDelete.add(pigBody);
                                    myPigs.get(j).setSprite(null);
                                    //destruction
                                }
                            }
                        } else {
                            for (int i = 0; i < blockCollection.size(); i++) {
                                Body temp = blockCollection.get(i).body;
                                // block + bird
                                if ((fig1.getBody().equals(temp) && fig2.getBody().equals(birdBody)) || (fig1.getBody().equals(birdBody) && fig2.getBody().equals(temp))) {
                                    if (birdHP == 0) {
                                        continue;
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
                                        // + destruction
                                    }
                                }
                                // block + ground
                                else if ((fig1.getBody().equals(temp) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(temp))) {
                                    if (Math.abs(temp.getLinearVelocity().y) >= 1) {
                                        blockCollection.get(i).hp -= 1;
                                        if (blockCollection.get(i).hp <= 0) {
                                            bodiesToDelete.add(blockCollection.get(i).body);
                                            blockCollection.get(i).sp = null;
                                            // destruction
                                        }
                                    }
                                }
                                // pig + block
                                else if ((fig1.getBody().equals(pigBody) && fig2.getBody().equals(temp)) || (fig1.getBody().equals(temp) && fig2.getBody().equals(pigBody))) {
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
                                }
                                //pig+pig
                                else if ((fig1.getBody().equals(pigBody) || fig2.getBody().equals(pigBody))) {
                                    System.out.println("negro");
                                    if (Math.abs(pigBody.getLinearVelocity().y) >= 1) {
                                        System.out.println(pigHP);
                                        pigHP -= 1;
                                        myPigs.get(j).setHealth(pigHP);
                                        if (pigHP <= 0) {
                                            bodiesToDelete.add(myPigs.get(j).getBody());
                                            myPigs.get(j).setSprite(null);
                                        }
                                    }
                                }
                                // block + block
                                else if ((fig1.getBody().equals(temp) || fig2.getBody().equals(temp))) {
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
//        world.step(1 / 120f, 6, 2);
        world.step(1 / 60f, 6, 2);
        Button pauseButton = new Button(pause, 20, Gdx.graphics.getHeight() - 90, 1f);
        Button resumeButton = new Button(resume, Gdx.graphics.getWidth() / 2 - 125, Gdx.graphics.getHeight() / 2, 1.4f);
        Button quitButton = new Button(quit, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2, 1.6f);
        Button retryButton = new Button(retry, Gdx.graphics.getWidth() / 2 + 115, Gdx.graphics.getHeight() / 2 + 4, 1.6f);
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
            }
        }
        if (ctr < 2) setActiveBird();
        if (birdSprite != null)
            birdSprite.setPosition((birdBody.getPosition().x * SCALE_FACTOR) - birdSprite.getWidth() / 2, (birdBody.getPosition().y * SCALE_FACTOR) - birdSprite.getHeight() / 2);
        for (int i = 0; i < myPigs.size(); i++) {
            Sprite pigSprite = myPigs.get(i).getSprite();
            if (pigSprite != null) {
                pigSprite.setOrigin(pigSprite.getWidth() / 2, pigSprite.getHeight() / 2); // Set origin to the center
                pigSprite.setPosition((myPigs.get(i).getBody().getPosition().x * SCALE_FACTOR) - pigSprite.getWidth() / 2, (myPigs.get(i).getBody().getPosition().y * SCALE_FACTOR) - pigSprite.getHeight() / 2);
                pigSprite.setRotation((float) Math.toDegrees(myPigs.get(i).getBody().getAngle())); // Rotate around the center
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
        if (pauseButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)
            || (visibility && pauseButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && resumeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
            || (visibility && retryButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
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
                    game.setScreen(new Level(level, game));
                } else {
                    visibility = false;
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
//            shape.setColor(1, 1, 1, 1);
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
                com.angybrids.pages.ShopPage.setCoins(com.angybrids.pages.ShopPage.getCoins() + 100);
                game.setScreen(new WinScreen(level, game));
            } else if (pigCounter <= 0) {
                com.angybrids.pages.ShopPage.setCoins(com.angybrids.pages.ShopPage.getCoins() + 100);
                game.setScreen(new WinScreen(level, game));
            } else if (birdCounter <= 0) game.setScreen(new LoseScreen(level, game));
        }

//        debugMatrix = batch.getProjectionMatrix().cpy().scale(SCALE_FACTOR, SCALE_FACTOR, 0);
//        debugRenderer.render(world, debugMatrix);
    }

    public void update(float delta) {
        timer = 3f;
        // Step the physics simulation
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
                    world.destroyBody(body); // Destroy the body
                } catch (Exception e) {
                    System.err.println("Error destroying body: " + e.getMessage());
                }
            }
        }
        s.clear();
        bodiesToDelete.clear(); // Clear the list
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
//        pig.dispose();
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
        // Convert screen coordinates to world coordinates
        Vector3 touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
        // Check if the touch is within the bounds of the bird
        flag = catapultSprite.getBoundingRectangle().contains(touchPosition.x, touchPosition.y);
        touchPosition.x /= SCALE_FACTOR;
        touchPosition.y /= SCALE_FACTOR;
        if (flag && !launched) initialTouchPosition.set(touchPosition.x, touchPosition.y);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (flag && !launched) {
            // Convert screen coordinates to world coordinates
            Vector3 touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
            // Update the bird's position to follow the drag
            touchPosition.x /= SCALE_FACTOR;
            touchPosition.y /= SCALE_FACTOR;
            launch.set(initialTouchPosition.x - touchPosition.x, initialTouchPosition.y - touchPosition.y);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (flag && !launched) {
            // Convert the final touch position to world coordinates
            touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
            touchPosition.x /= SCALE_FACTOR;
            touchPosition.y /= SCALE_FACTOR;
            // Calculate the velocity based on the initial touch position and final touch position
            float impulseX = initialTouchPosition.x - touchPosition.x;  // Inverted direction
            float impulseY = initialTouchPosition.y - touchPosition.y;  // Inverted direction
//            Vector2 direction = new Vector2(impulseX, impulseY).nor();
            float dragDistance = initialTouchPosition.dst(touchPosition.x, touchPosition.y);
            launch.set(impulseX, impulseY).scl(dragDistance * 0.09f);
//            launch.limit(20);
            birdBody.applyLinearImpulse(launch, birdBody.getWorldCenter(), true);
            // Reset the flag to stop tracking the drag
            flag = false;
        }
        return true;
    }

    @Override
    public void write(Json json) {
        // Serialize level metadata
        json.writeValue("level", this.level);
        json.writeValue("birdCounter", this.birdCounter);
        json.writeValue("pigCounter", this.pigCounter);

        // Serialize remaining birds
        json.writeValue("remainingBirds", this.myBirds);

        // Serialize remaining pigs
        List<HashMap<String, Object>> pigData = new ArrayList<>();
        for (Pig pig : this.myPigs) {
            HashMap<String, Object> pigInfo = new HashMap<>();
            pigInfo.put("x", pig.getBody().getPosition().x * SCALE_FACTOR);
            pigInfo.put("y", pig.getBody().getPosition().y * SCALE_FACTOR);
            pigInfo.put("hp", pigHP);
            pigData.add(pigInfo);
        }
        json.writeValue("pigs", pigData);

        // Serialize block collection
        List<HashMap<String, Object>> blockData = new ArrayList<>();
        for (Block block : this.blockCollection) {
            HashMap<String, Object> blockInfo = new HashMap<>();
            blockInfo.put("x", block.body.getPosition().x * SCALE_FACTOR);
            blockInfo.put("y", block.body.getPosition().y * SCALE_FACTOR);
            blockInfo.put("width", block.width);
            blockInfo.put("height", block.height);
            blockInfo.put("type", block.type);
            blockInfo.put("hp", block.hp);
            blockData.add(blockInfo);
        }
        json.writeValue("blocks", blockData);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        // Clear existing collections
        this.myBirds.clear();
        this.myPigs.clear();
        this.blockCollection.clear();

        // Deserialize level metadata
        this.level = jsonData.getInt("level", 0);
        this.birdCounter = jsonData.getInt("birdCounter", 3);
        this.pigCounter = jsonData.getInt("pigCounter", 1);

        // Deserialize remaining birds (with class/value format)
        JsonValue birdsJson = jsonData.get("remainingBirds");
        for (JsonValue birdJson : birdsJson) {
            // Extract the actual value (bird name) from the "value" field
            this.myBirds.add(birdJson.getString("value"));
        }

        // Deserialize remaining pigs
        JsonValue pigsJson = jsonData.get("pigs");
        for (JsonValue pigJson : pigsJson) {
            float x = pigJson.get("x").getFloat("value");
            float y = pigJson.get("y").getFloat("value");
            SmallPig pig = new SmallPig(this.world, (int) (x * SCALE_FACTOR), (int) (y * SCALE_FACTOR));
            pig.createBody();
            this.myPigs.add(pig);
            this.pigHP = pigJson.get("hp").getInt("value");
        }

        // Deserialize block collection
        JsonValue blocksJson = jsonData.get("blocks");
        for (JsonValue blockJson : blocksJson) {
            float x = blockJson.get("x").getFloat("value");
            float y = blockJson.get("y").getFloat("value");
            float width = blockJson.get("width").getFloat("value");
            float height = blockJson.get("height").getFloat("value");
            String type = blockJson.get("type").getString("value");

            // Use existing createBlock method
            createBlock(x, y, width, height, type);
        }
    }

    // Save game state to file
    public void saveState() throws IOException {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        // Create a file in the appropriate save directory
        FileHandle saveFile = Gdx.files.local("state.json");
        saveFile.writeString(json.toJson(this), false);
    }

    // Load game state from file
    public static Level loadState(Main game) throws IOException {
        Json json = new Json();

        // Attempt to read the save file
        FileHandle saveFile = Gdx.files.local("state.json");
        if (saveFile.exists()) {
            Level loadedLevel = json.fromJson(Level.class, saveFile.readString());
            loadedLevel.setGame(game);
            loadedLevel.isLoaded = true;
//            json.readFields(loadedLevel, jsonData);
            return loadedLevel;
        }
        // Return a new level if no save exists
        return new Level(game);
    }
}

