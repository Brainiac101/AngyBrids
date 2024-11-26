//package com.angybrids.level;
//
//import com.angybrids.Button;
//import com.angybrids.Main;
//import com.angybrids.birds.*;
//import com.angybrids.blocks.Block;
//import com.angybrids.pages.*;
//import com.angybrids.pages.Map;
//import com.angybrids.pigs.Pig;
//import com.angybrids.pigs.SmallPig;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.InputAdapter;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.*;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.*;
//import com.badlogic.gdx.physics.box2d.*;
//import com.badlogic.gdx.utils.Json;
//import com.badlogic.gdx.utils.JsonValue;
//
//import java.io.*;
//import java.util.*;
//
//public class Level extends InputAdapter implements Screen/*, Json.Serializable*/ {
//    private Main game;
//    private OrthographicCamera camera;
//    private World world;
//    private SpriteBatch batch;
//    private Matrix4 debugMatrix;
//    private Box2DDebugRenderer debugRenderer;
//
//    private Texture bg, pig, catapult, wood, stone, glass, tempBird1, tempBird2;
//    private final List<String> myBirds;
//    private final List<Pig> myPigs;
//    private Bird curr;
//
//    private Sprite birdSprite, bgSprite, pigSprite, catapultSprite, woodSprite, stoneSprite, glassSprite;
//    private Body birdBody, bgBody, pigBody, catapultBody, woodBody, standBody;
//
//    private final Projectile proj;
//    private boolean flag = false;
//    private final Vector2 initialTouchPosition = new Vector2();
//    private final Vector2 launch;
//    private Vector3 touchPosition;
//    private List<Body> bodiesToDelete;
//    public static final float SCALE_FACTOR = 25f;
//    public int birdHP;
//    private int pigHP = 1;
//    List<Block> blockCollection = new ArrayList<>();
//    private ShapeRenderer shapeRenderer;
//
//    private List<String> roster;
//    private int ctr = 0;
//    private boolean launched;
//
//    private Texture pause;
//    private Texture quit;
//    private Texture resume;
//    private Texture retry;
//    private boolean visibility;
//    private int pigCounter;
//    private int birdCounter;
//    private float screenTop;
//    private float screenRight;
//    private int level;
////    private
////    Texture birdDeath1;
////    Array<TextureRegion> frames;
////    Animation<TextureRegion> birdDeathAnimation;
////    float stateTime = 0f;
////    boolean birdIsDead = false;
////    Texture birdDeath2 = new Texture(Gdx.files.internal("birdDeath_2.png"));
////    Texture birdDeath3 = new Texture(Gdx.files.internal("birdDeath_3.png"));
//
//    public Level(Main game) {
//        this.game = game;
//        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        this.camera.setToOrtho(false);
//        this.touchPosition = new Vector3();
//        this.world = new World(new Vector2(0, -60f), true);
//        proj = new Projectile(world.getGravity().y);
//        this.batch = new SpriteBatch();
//        launch = new Vector2();
//        debugRenderer = new Box2DDebugRenderer();
//        bodiesToDelete = new ArrayList<>();
//        launched = false;
//        pigCounter = 1;
//        birdCounter = 3;
//
//        roster = new ArrayList<>();
//        roster.add("red");
//        roster.add("bomb");
//        roster.add("chuck");
//        roster.add("blue");
//        roster.add("terence");
//        roster.add("matilda");
//        roster.add("hal");
//        myPigs = new ArrayList<>();
//        myBirds = new ArrayList<>();
//        Random rand = new Random();
//        while (myBirds.size() < 3) {
//            int r = rand.nextInt(7);
//            if (myBirds.isEmpty()) myBirds.add(roster.get(r));
//            else if (!myBirds.contains(roster.get(r))) myBirds.add(roster.get(r));
//        }
//        System.out.println(myBirds);
//
//        shapeRenderer = new ShapeRenderer();
//    }
//
//    public Level(){
//        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        this.camera.setToOrtho(false);
//        this.touchPosition = new Vector3();
//        this.world = new World(new Vector2(0, -60f), true);
//        proj = new Projectile(world.getGravity().y);
//        this.batch = new SpriteBatch();
//        launch = new Vector2();
//        debugRenderer = new Box2DDebugRenderer();
//        bodiesToDelete = new ArrayList<>();
//        launched = false;
//        pigCounter = 1;
//        birdCounter = 3;
//        myPigs = new ArrayList<>();
//        myBirds = new ArrayList<>();
//        shapeRenderer = new ShapeRenderer();
//    }
//
////    private void saveState() throws IOException {
////        Json json = new Json();
////        String jsonString = json.toJson(this);
////        File file = new File("state.json");
////        try (FileWriter writer = new FileWriter(file)) {
////            writer.write(jsonString);
////        }
////    }
//
////    public static Level loadState(Main game) throws IOException {
////        File file = new File("state.json");
//////        if(file.exists()) {
//////            try (BufferedReader reader = new BufferedReader(new FileReader("state.json"))) {
//////                Json json = new Json();
////////                return json.fromJson(Level.class, reader);
//////                Level loadedLevel = new Level(game); // Create a new Level instance with the game context
//////                json.readField(loadedLevel, "level", reader); // Use readField instead of fromJson
//////                return loadedLevel;
//////            }
//////            catch (IOException e) {
//////                return null;
//////            }
//////        }
////        Json json = new Json();
////        Level level = new Level(game);
////        String text = json.prettyPrint(level);
////        System.out.println(text);
////        return null;
////        File file = new File("state.json");
////        if (file.exists()) {
////            try (BufferedReader reader = new BufferedReader(new FileReader("state.json"))) {
////                // Read the entire file content as a string
////                StringBuilder content = new StringBuilder();
////                String line;
////                while ((line = reader.readLine()) != null) {
////                    content.append(line).append("\n");
////                }
////
////                // Use Json to pretty print the read content
////                Json json = new Json();
////                String prettyPrinted = json.prettyPrint(content.toString());
////                System.out.println(prettyPrinted);
////
////                // If you want to actually load the level
////                Level level = new Level(game);
////                json.fromJson(Level.class, content.toString());
////                return level;
////            }
////            catch (IOException e) {
////                System.out.println("Error reading state file: " + e.getMessage());
////                e.printStackTrace();
////                return null;
////            }
////        }
////        return null;
////        File file = new File("state.json");
////        if (file.exists()) {
////            try (BufferedReader reader = new BufferedReader(new FileReader("state.json"))) {
////                // Read the entire file content
////                StringBuilder content = new StringBuilder();
////                String line;
////                while ((line = reader.readLine()) != null) {
////                    content.append(line).append("\n");
////                }
////
////                // Print the raw content
////                System.out.println(content.toString());
////
////                // Deserialize if needed
////                Json json = new Json();
////                Level level = json.fromJson(Level.class, content.toString());
////                return level;
////            }
////            catch (IOException e) {
////                System.out.println("Error reading state file: " + e.getMessage());
////                e.printStackTrace();
////                return null;
////            }
////        }
////        return null;
////    }
//
//    private void setActiveBird() {
//        if (birdHP == 0 && ctr < 3) {
//            ctr++;
////            Random rand = new Random();
////            String name = myBirds.get(0);
////            if (birdBody == null) {
////                int r = rand.nextInt(7);
////                name = roster.get(r);
////            }
////            while (!myBirds.isEmpty() && myBirds.contains(name)) {
////                int r = rand.nextInt(7);
////                name = roster.get(r);
////            }
////            myBirds.add(name);
//            switch (myBirds.get(ctr)) {
//                case "red":
//                    curr = new Red(this.world);
//                    break;
//                case "blue":
//                    curr = new Blue(this.world);
//                    break;
//                case "chuck":
//                    curr = new Chuck(this.world);
//                    break;
//                case "bomb":
//                    curr = new Bomb(this.world);
//                    break;
//                case "matilda":
//                    curr = new Matilda(this.world);
//                    break;
//                case "terence":
//                    curr = new Terence(this.world);
//                    break;
//                case "hal":
//                    curr = new Hal(this.world);
//                    break;
//            }
//            birdSprite = curr.getSprite();
//            birdBody = curr.createBody();
//            birdHP = curr.getHealth();
////            birdHP = 2;
//        }
//    }
//
//    private void createStructure(float startX, float startY, int rows, int cols, float blockWidth, float blockHeight, String type) {
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                float posX = startX + j * blockWidth;
//                float posY = startY + i * blockHeight;
//                createBlock(posX, posY, blockWidth, blockHeight, type);
//            }
//        }
//    }
//
//    private void createBlock(float x, float y, float width, float height, String type) {
//        // Create the sprite for the block
//        Sprite blockSprite = new Sprite(new Texture("blocks/" + type + ".png"));
//        blockSprite.setSize(width, height);
//        blockSprite.setPosition(x, y);
//        // Create the BodyDef for the block
//        BodyDef bd = new BodyDef();
//        bd.type = BodyDef.BodyType.DynamicBody;
//        bd.position.set(x / SCALE_FACTOR, y / SCALE_FACTOR);
//        // Create the physics body
//        Body blockBody = world.createBody(bd);
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(width / 2 / SCALE_FACTOR, height / 2 / SCALE_FACTOR);
//        blockBody.createFixture(shape, 0.1f);
//        blockBody.setUserData(blockSprite);
//        shape.dispose();
//
//        Block temp = new Block(new Vector2(x, y), width, height, type);
//        temp.body = blockBody;
//        blockCollection.add(temp);
//    }
//
//    @Override
//    public void show() {
//        Gdx.input.setInputProcessor(this);
//        bg = new Texture("levelAssets/bg2.jpeg");
//        pig = new Texture("pigs/pig.png");
//        catapult = new Texture("catapult.png");
//        wood = new Texture("blocks/wood.png");
//        stone = new Texture("blocks/stone.png");
//        glass = new Texture("blocks/glass.png");
//        pause = new Texture("icons/pauseIcon.png");
//        quit = new Texture("icons/exitIcon.png");
//        resume = new Texture("icons/resumeIcon.png");
//        retry = new Texture("icons/retryIcon.png");
//        tempBird1 = new Texture("birds/" + myBirds.get(1) + ".png");
//        tempBird2 = new Texture("birds/" + myBirds.get(2) + ".png");
//
////        for(int i=0;i<2;i++) {
//        SmallPig pig1 = new SmallPig(this.world, 900, 300);
//        pigBody = pig1.createBody();
//        myPigs.add(pig1);
//
////        }
//        screenRight = (camera.position.x + (camera.viewportWidth / 2)) / SCALE_FACTOR;
//        screenTop = (camera.position.y + camera.viewportHeight / 2) / SCALE_FACTOR;
//        bgSprite = new Sprite(bg);
//        pigSprite = pig1.getSprite();
//        catapultSprite = new Sprite(catapult);
//        woodSprite = new Sprite(wood);
//        stoneSprite = new Sprite(stone);
//        glassSprite = new Sprite(glass);
//        switch (myBirds.get(ctr)) {
//            case "red":
//                curr = new Red(this.world);
//                break;
//            case "blue":
//                curr = new Blue(this.world);
//                break;
//            case "chuck":
//                curr = new Chuck(this.world);
//                break;
//            case "bomb":
//                curr = new Bomb(this.world);
//                break;
//            case "matilda":
//                curr = new Matilda(this.world);
//                break;
//            case "terence":
//                curr = new Terence(this.world);
//                break;
//            case "hal":
//                curr = new Hal(this.world);
//                break;
//        }
//        birdSprite = curr.getSprite();
//        birdBody = curr.createBody();
//        birdHP = curr.getHealth();
//
//        catapultSprite.setPosition(100, 95);
//
////        createStructure(500, 125, 3, 2, stoneSprite.getWidth(), stoneSprite.getHeight(), "glass");
//        createStructure(900, 125, 3, 2, woodSprite.getWidth(), woodSprite.getHeight(), "wood");
////        createStructure(900, 125, 3, 2, stoneSprite.getWidth(), stoneSprite.getHeight(), "stone");
//
//        BodyDef bd = new BodyDef();
//        bd.type = BodyDef.BodyType.StaticBody;
//        bd.position.set(50 / SCALE_FACTOR, 90 / SCALE_FACTOR);
//        bgBody = world.createBody(bd);
//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(camera.viewportWidth / SCALE_FACTOR, 7f / SCALE_FACTOR);
//        bgBody.createFixture(shape, 1.0f);
//        bgBody.setUserData(bgSprite);
//
//        bd.type = BodyDef.BodyType.StaticBody;
//        bd.position.set(0 / SCALE_FACTOR, 0 / SCALE_FACTOR);
//        Body sideBody = world.createBody(bd);
//        shape = new PolygonShape();
//        shape.setAsBox(0 / SCALE_FACTOR, camera.viewportHeight / SCALE_FACTOR);
//        sideBody.createFixture(shape, 1.0f);
//        sideBody.setUserData(bgSprite);
//
//        bd.type = BodyDef.BodyType.StaticBody;
//        bd.position.set((catapultSprite.getX() + catapultSprite.getWidth() / 2 + 10) / SCALE_FACTOR, (catapultSprite.getY() + catapultSprite.getHeight() / 2 - 30) / SCALE_FACTOR);
//        catapultBody = world.createBody(bd);
//        shape = new PolygonShape();
//        shape.setAsBox((catapultSprite.getWidth() / 2 - 15) / SCALE_FACTOR, (catapultSprite.getHeight() / 2 - 30) / SCALE_FACTOR);
//        catapultBody.createFixture(shape, 1.0f);
//        catapultBody.setUserData(catapultSprite);
//
////        bd.type = BodyDef.BodyType.DynamicBody;
////        bd.position.set((pigSprite.getX() + pigSprite.getWidth() / 2) / SCALE_FACTOR, (pigSprite.getY() + pigSprite.getHeight() / 2) / SCALE_FACTOR);
////        pigBody = world.createBody(bd);
////        shape = new PolygonShape();
////        shape.setAsBox(pigSprite.getWidth() / 2 / SCALE_FACTOR, pigSprite.getHeight() / 2 / SCALE_FACTOR);
////        pigBody.createFixture(shape, 0.1f);
////        pigBody.setUserData(pigSprite);
//        shape.dispose();
//        world.setContactListener(new ContactListener() {
//            @Override
//            public void beginContact(Contact contact) {
//                Fixture fig1 = contact.getFixtureA();
//                Fixture fig2 = contact.getFixtureB();
//                // bird + ground
//                if ((fig1.getBody().equals(birdBody) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(birdBody))) {
//                    System.out.println("zameen bc");
//                    birdHP = 0;
////                    birdIsDead=true;
////                    birdBody.setLinearVelocity(0, 0);
//                    bodiesToDelete.add(birdBody);
//                    birdCounter--;
//                    birdSprite = null;
//                }
//                // pig + bird
//                else if ((fig1.getBody().equals(birdBody) && fig2.getBody().equals(pigBody)) || (fig1.getBody().equals(pigBody) && fig2.getBody().equals(birdBody))) {
//                    if (birdHP == 0) {
//                        return;
//                    }
//                    if (pigHP > birdHP) {
//                        pigHP -= birdHP;
//                        birdHP = 0;
//                        birdCounter--;
//                        birdBody.setLinearVelocity(0, 0);
//                    } else {
//                        birdHP -= pigHP;
//                        pigHP = 0;
//                        System.out.println("pig ki mkb");
//                        pigCounter -= 1;
//                        if (birdHP == 0) {
//                            birdCounter--;
//                            bodiesToDelete.add(birdBody);
//                            birdSprite = null;
//                        }
//                        bodiesToDelete.add(pigBody);
//                        pigSprite = null;
//                        //destruction
//                    }
//                }
//                // pig + ground
//                else if ((fig1.getBody().equals(pigBody) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(pigBody))) {
//                    if (pigHP == 0) {
//                        return;
//                    }
//                    if (Math.abs(pigBody.getLinearVelocity().y) >= 1) {
//                        pigHP -= 1;
//                        if (pigHP <= 0) {
//                            System.out.println("pig ki mkb");
//
//                            pigCounter -= 1;
//                            bodiesToDelete.add(pigBody);
//                            pigSprite = null;
//                            //destruction
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < blockCollection.size(); i++) {
//                        Body temp = blockCollection.get(i).body;
//                        // block + bird
//                        if ((fig1.getBody().equals(temp) && fig2.getBody().equals(birdBody)) || (fig1.getBody().equals(birdBody) && fig2.getBody().equals(temp))) {
//                            System.out.println("lag gyi");
////                            if (birdHP == 0) {
////                                System.out.println("mar gya bkl");
////                                bodiesToDelete.add(birdBody);
////                                birdSprite = null;
////                            }
//                            if (blockCollection.get(i).hp > birdHP) {
//                                System.out.println("block jeet gaya");
//                                blockCollection.get(i).hp -= birdHP;
//                                birdHP = 0;
//                                if (blockCollection.get(i).hp == 0) {
//                                    bodiesToDelete.add(blockCollection.get(i).body);
//                                    blockCollection.get(i).sp = null;
//                                }
//                                birdBody.setLinearVelocity(1, 1);
//                                birdCounter--;
//                                bodiesToDelete.add(birdBody);
////                                birdIsDead=true;
//                                birdSprite = null;
//                            } else {
//                                System.out.println("uwaaa bird jeet gayi");
//                                birdHP -= blockCollection.get(i).hp;
//                                System.out.println(blockCollection.get(i).hp);
//                                blockCollection.get(i).hp = 0;
//                                birdBody.setLinearVelocity(1, 1);
//                                if (birdHP == 0) {
//                                    birdCounter--;
//                                    bodiesToDelete.add(birdBody);
//                                    System.out.println("mar gya bkl");
//                                    birdSprite = null;
//                                }
////                                else birdBody.setLinearVelocity(birdBody.getLinearVelocity().x, 0);
//
//                                bodiesToDelete.add(blockCollection.get(i).body);
//                                blockCollection.get(i).sp = null;
//                                // + destruction
//                            }
//                        }
//                        // block + ground
//                        else if ((fig1.getBody().equals(temp) && fig2.getBody().equals(bgBody)) || (fig1.getBody().equals(bgBody) && fig2.getBody().equals(temp))) {
//                            if (Math.abs(temp.getLinearVelocity().y) >= 1) {
//                                blockCollection.get(i).hp -= 1;
//                                if (blockCollection.get(i).hp <= 0) {
//                                    bodiesToDelete.add(blockCollection.get(i).body);
//                                    blockCollection.get(i).sp = null;
//                                    // destruction
//                                }
//                            }
//                        }
//                        // pig + block
//                        else if ((fig1.getBody().equals(pigBody) && fig2.getBody().equals(temp)) || (fig1.getBody().equals(temp) && fig2.getBody().equals(pigBody))) {
//                            System.out.println("pig says hi");
//                            if (Math.abs(pigBody.getLinearVelocity().y) >= 1 || Math.abs(temp.getLinearVelocity().y) >= 1) {
//                                pigHP -= 1;
//                                if (pigHP <= 0) {
//                                    System.out.println("pig ki mkb");
//                                    pigCounter -= 1;
//                                    bodiesToDelete.add(pigBody);
//                                    pigSprite = null;
//                                }
//                                blockCollection.get(i).hp -= 1;
//                                if (blockCollection.get(i).hp <= 0) {
//                                    bodiesToDelete.add(blockCollection.get(i).body);
//                                    blockCollection.get(i).sp = null;
//                                }
//                            }
//                        }
//                        // block + block
//                        else if ((fig1.getBody().equals(temp) || fig2.getBody().equals(temp))) {
//                            if (Math.abs(temp.getLinearVelocity().y) >= 1) {
//                                blockCollection.get(i).hp -= 1;
//                                if (blockCollection.get(i).hp <= 0) {
//                                    bodiesToDelete.add(blockCollection.get(i).body);
//                                    blockCollection.get(i).sp = null;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void endContact(Contact contact) {
//            }
//
//            @Override
//            public void preSolve(Contact contact, Manifold oldManifold) {
//            }
//
//            @Override
//            public void postSolve(Contact contact, ContactImpulse impulse) {
//            }
//        });
//    }
//
//    @Override
//    public void render(float delta) {
//        camera.update();
//        world.step(1 / 60f, 6, 2);
//        Button pauseButton = new Button(pause, 20, Gdx.graphics.getHeight() - 90, 1f);
//        Button resumeButton = new Button(resume, Gdx.graphics.getWidth() / 2 - 125, Gdx.graphics.getHeight() / 2, 1.4f);
//        Button quitButton = new Button(quit, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 + 2, 1.6f);
//        Button retryButton = new Button(retry, Gdx.graphics.getWidth() / 2 + 115, Gdx.graphics.getHeight() / 2 + 4, 1.6f);
//        if ((birdBody.getPosition().x > screenRight || birdBody.getPosition().y > screenTop) && birdSprite != null) {
//            System.out.println("nigga");
//            birdCounter--;
//            birdHP = 0;
//            bodiesToDelete.add(birdBody);
//            birdSprite = null;
//        }
//        if (ctr < 2) setActiveBird();
//        if (birdSprite != null)
//            birdSprite.setPosition((birdBody.getPosition().x * SCALE_FACTOR) - birdSprite.getWidth() / 2, (birdBody.getPosition().y * SCALE_FACTOR) - birdSprite.getHeight() / 2);
//        if (pigSprite != null) {
//            pigSprite.setOrigin(pigSprite.getWidth() / 2, pigSprite.getHeight() / 2); // Set origin to the center
//            pigSprite.setPosition((pigBody.getPosition().x * SCALE_FACTOR) - pigSprite.getWidth() / 2, (pigBody.getPosition().y * SCALE_FACTOR) - pigSprite.getHeight() / 2);
//            pigSprite.setRotation((float) Math.toDegrees(pigBody.getAngle())); // Rotate around the center
////            pigSprite.setPosition((pigBody.getPosition().x * SCALE_FACTOR) - pigSprite.getWidth() / 2, (pigBody.getPosition().y * SCALE_FACTOR) - pigSprite.getHeight() / 2);
////            pigSprite.setRotation((float) Math.toDegrees(pigBody.getAngle()));
//        }
//        for (int i = 0; i < blockCollection.size(); i++) {
//            Sprite temp = blockCollection.get(i).sp;
//            if (temp != null) {
//                temp.setPosition((blockCollection.get(i).body.getPosition().x * SCALE_FACTOR) - temp.getWidth() / 2, (blockCollection.get(i).body.getPosition().y * SCALE_FACTOR) - temp.getHeight() / 2);
//                temp.setRotation((float) Math.toDegrees(blockCollection.get(i).body.getAngle()));
//            }
//        }
//        if (pigCounter <= 0 && birdCounter <= 0) game.setScreen(new WinScreen(game));
//        if (pigCounter <= 0) game.setScreen(new WinScreen(game));
//        if (birdCounter <= 0) game.setScreen(new LoseScreen(game));
//
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.setProjectionMatrix(camera.combined);
//        update(delta);
//
//        batch.begin();
//        bgSprite.draw(batch);
//        if (pigSprite != null) pigSprite.draw(batch);
//        for (int i = 0; i < blockCollection.size(); i++) {
//            Sprite temp = blockCollection.get(i).sp;
//            if (temp != null) temp.draw(batch);
//        }
//        if (ctr == 0) {
//            Sprite temp = new Sprite(tempBird1);
//            temp.setSize(temp.getWidth() * 0.2f, temp.getHeight() * 0.2f);
//            temp.setPosition(75, 95);
//            temp.draw(batch);
//            temp = new Sprite(tempBird2);
//            temp.setSize(temp.getWidth() * 0.2f, temp.getHeight() * 0.2f);
//            temp.setPosition(25, 95);
//            temp.draw(batch);
//        } else if (ctr == 1) {
//            Sprite temp = new Sprite(tempBird2);
//            temp.setSize(temp.getWidth() * 0.2f, temp.getHeight() * 0.2f);
//            temp.setPosition(75, 95);
//            temp.draw(batch);
//        }
//        catapultSprite.draw(batch);
//        if (birdSprite != null) birdSprite.draw(batch);
//        pauseButton.getButtonSprite().draw(batch);
//        batch.end();
//
//        if (visibility) {
//            Gdx.gl.glEnable(GL20.GL_BLEND);
//            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
//            shapeRenderer.setColor(new Color(0, 0, 0, 0.5f));
//            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//            shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
//            shapeRenderer.end();
//            Gdx.gl.glDisable(GL20.GL_BLEND);
//            batch.begin();
//            retryButton.getButtonSprite().draw(batch);
//            resumeButton.getButtonSprite().draw(batch);
//            quitButton.getButtonSprite().draw(batch);
//            batch.end();
//        }
//
//        float touchX = Gdx.input.getX();
//        float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();
//        if (pauseButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)
//            || (visibility && pauseButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
//            || (visibility && resumeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
//            || (visibility && quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY))
//            || (visibility && retryButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)))
//
//            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Hand);
//        else Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
//        if (Gdx.input.justTouched()) {
//            if (!visibility && pauseButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                visibility = true;
//            } else {
//                if (resumeButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                    visibility = false;
//                } else if (quitButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                    try {
//                        saveState();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                    game.setScreen(new Map(this.game));
//                } else if (retryButton.getButtonSprite().getBoundingRectangle().contains(touchX, touchY)) {
//                    game.setScreen(new Level(game));
//                } else {
//                    visibility = false;
//                }
//            }
//        }
//        if (flag) {
//            float t = 0f;
//            float maxT = 1f;
//            proj.startPoint.set((initialTouchPosition.x * SCALE_FACTOR), (initialTouchPosition.y * SCALE_FACTOR));
//            proj.startVelocity.set(launch.x * SCALE_FACTOR, launch.y * SCALE_FACTOR);
//            ShapeRenderer shape = new ShapeRenderer();
//            shape.setProjectionMatrix(camera.combined);
//            shape.begin(ShapeRenderer.ShapeType.Filled);
//            shape.setColor(1, 1, 1, 1);
//            while (t < maxT) {
//                float x = proj.getX(t);
//                float y = proj.getY(t);
//                shape.circle(x, y, 3f);
//                t += 0.1f;
//            }
//            shape.end();
//            shape.dispose();
//        }
//        debugMatrix = batch.getProjectionMatrix().cpy().scale(SCALE_FACTOR, SCALE_FACTOR, 0);
//        debugRenderer.render(world, debugMatrix);
//    }
//
//    public void update(float delta) {
//        // Step the physics simulation
//        world.step(delta, 6, 2);
//
//        for (Body body : bodiesToDelete) {
//            if (body != null) {
//                try {
//                    world.destroyBody(body); // Destroy the body
//                } catch (Exception e) {
//                    System.err.println("Error destroying body: " + e.getMessage());
//                }
//            }
//        }
//        bodiesToDelete.clear(); // Clear the list
//    }
//
//    @Override
//    public void resize(int width, int height) {
//    }
//
//    @Override
//    public void pause() {
//    }
//
//    @Override
//    public void resume() {
//    }
//
//    @Override
//    public void hide() {
//    }
//
//    @Override
//    public void dispose() {
//        bg.dispose();
//        pig.dispose();
//        wood.dispose();
//        stone.dispose();
//        glass.dispose();
//        tempBird1.dispose();
//        tempBird2.dispose();
//        catapult.dispose();
//        world.dispose();
//        batch.dispose();
//    }
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        // Convert screen coordinates to world coordinates
//        Vector3 touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
//        // Check if the touch is within the bounds of the bird
//        flag = catapultSprite.getBoundingRectangle().contains(touchPosition.x, touchPosition.y);
//        touchPosition.x /= SCALE_FACTOR;
//        touchPosition.y /= SCALE_FACTOR;
//        if (flag && !launched) initialTouchPosition.set(touchPosition.x, touchPosition.y);
//        return true;
//    }
//
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        if (flag && !launched) {
//            // Convert screen coordinates to world coordinates
//            Vector3 touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
//            // Update the bird's position to follow the drag
//            touchPosition.x /= SCALE_FACTOR;
//            touchPosition.y /= SCALE_FACTOR;
//            launch.set(initialTouchPosition.x - touchPosition.x, initialTouchPosition.y - touchPosition.y);
//        }
//        return true;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        if (flag && !launched) {
//            // Convert the final touch position to world coordinates
//            touchPosition = camera.unproject(new Vector3(screenX, screenY, 0));
//            touchPosition.x /= SCALE_FACTOR;
//            touchPosition.y /= SCALE_FACTOR;
//            // Calculate the velocity based on the initial touch position and final touch position
//            float impulseX = initialTouchPosition.x - touchPosition.x;  // Inverted direction
//            float impulseY = initialTouchPosition.y - touchPosition.y;  // Inverted direction
////            Vector2 direction = new Vector2(impulseX, impulseY).nor();
//            float dragDistance = initialTouchPosition.dst(touchPosition.x, touchPosition.y);
//            launch.set(impulseX, impulseY).scl(dragDistance * 0.09f);
////            launch.limit(20);
//            birdBody.applyLinearImpulse(launch, birdBody.getWorldCenter(), true);
//            // Reset the flag to stop tracking the drag
//            flag = false;
//        }
//        return true;
//    }
//
////    @Override
////    public void write(Json json) {
////        // Serialize level number
////        json.writeValue("level", this.level);
////
////        // Serialize remaining birds
////        json.writeValue("remainingBirds", this.myBirds);
////
////        // Serialize remaining pigs
////        List<java.util.Map<String, Object>> pigData = new ArrayList<>();
////        for (Pig pig : this.myPigs) {
////            java.util.Map<String, Object> pigInfo = new HashMap<>();
////            pigInfo.put("x", pig.getBody().getPosition().x * SCALE_FACTOR);
////            pigInfo.put("y", pig.getBody().getPosition().y * SCALE_FACTOR);
////            pigInfo.put("hp", pigHP);
////            pigData.add(pigInfo);
////        }
////        json.writeValue("pigs", pigData);
////
////        // Serialize block collection
////        List<java.util.Map<String, Object>> blockData = new ArrayList<>();
////        for (Block block : this.blockCollection) {
////            java.util.Map<String, Object> blockInfo = new HashMap<>();
////            blockInfo.put("x", block.body.getPosition().x * SCALE_FACTOR);
////            blockInfo.put("y", block.body.getPosition().y * SCALE_FACTOR);
////            blockInfo.put("width", block.width);
////            blockInfo.put("height", block.height);
////            blockInfo.put("type", block.type);
////            blockInfo.put("hp", block.hp);
////            blockData.add(blockInfo);
////        }
////        json.writeValue("blocks", blockData);
////    }
////
////    @Override
////    public void read(Json json, JsonValue jsonData) {
////        // Deserialize level number
////        this.level = jsonData.getInt("level");
////
////        // Deserialize remaining birds
////        this.myBirds.clear();
////        JsonValue birdsJson = jsonData.get("remainingBirds");
////        for (JsonValue birdJson : birdsJson) {
////            this.myBirds.add(birdJson.asString());
////        }
////
////        // Deserialize remaining pigs
////        this.myPigs.clear();
////        JsonValue pigsJson = jsonData.get("pigs");
////        for (JsonValue pigJson : pigsJson) {
////            float x = pigJson.getFloat("x") / SCALE_FACTOR;
////            float y = pigJson.getFloat("y") / SCALE_FACTOR;
////            SmallPig pig = new SmallPig(this.world, (int) (x * SCALE_FACTOR), (int) (y * SCALE_FACTOR));
////            Body pigBody = pig.createBody();
////            this.myPigs.add(pig);
////            this.pigHP = pigJson.getInt("hp");
////        }
////
////        // Deserialize block collection
////        this.blockCollection.clear();
////        JsonValue blocksJson = jsonData.get("blocks");
////        for (JsonValue blockJson : blocksJson) {
////            float x = blockJson.getFloat("x") / SCALE_FACTOR;
////            float y = blockJson.getFloat("y") / SCALE_FACTOR;
////            float width = blockJson.getFloat("width");
////            float height = blockJson.getFloat("height");
////            String type = blockJson.getString("type");
////
////            // Use existing createBlock method
////            createBlock(x * SCALE_FACTOR, y * SCALE_FACTOR, width, height, type);
////
////            // Set HP for the last created block
////            if (!blockCollection.isEmpty()) {
////                Block lastBlock = blockCollection.get(blockCollection.size() - 1);
////                lastBlock.hp = blockJson.getInt("hp");
////            }
////        }
////    }
//}
