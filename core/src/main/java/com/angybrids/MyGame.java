package com.angybrids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class MyGame implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Body dynamicBody;

    @Override
    public void show() {
        // Set up camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);  // Set screen size (800x600)

        batch = new SpriteBatch();

        // Set up the Box2D world with gravity
        world = new World(new Vector2(0, -9.8f), true); // Gravity: (0, -9.8)

        // Debug renderer for visualizing Box2D bodies
        debugRenderer = new Box2DDebugRenderer();

        // Create a dynamic body (falling box)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 4);  // Position (x, y)

        dynamicBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1, 1);  // A 2x2 box

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.2f;

        dynamicBody.createFixture(fixtureDef);
        shape.dispose();  // Dispose shape when done with it

        // Create a ground (static body)
        BodyDef groundDef = new BodyDef();
        groundDef.position.set(0, -10);  // Position of the ground

        Body ground = world.createBody(groundDef);
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(-10, -10, 10, -10);  // Horizontal ground line

        ground.createFixture(groundShape, 0);  // Static body, so no need for density or friction
        groundShape.dispose();  // Dispose the ground shape
    }

    @Override
    public void render(float delta) {
        // Update the world (simulate physics)
        float timeStep = 1 / 60f;  // Fixed time step (60 FPS)
        int velocityIterations = 6;
        int positionIterations = 2;

        world.step(timeStep, velocityIterations, positionIterations);

        // Clear the screen (black background)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera (make sure it's at the right position)
        camera.position.set(0, 0, 0);  // Set the camera to look at the center of the screen
        camera.update();

        // Render the Box2D debug information (showing the bodies)
        debugRenderer.render(world, camera.combined);

        // If you want to render other game objects, you can do that here using the batch
        batch.begin();
        // Your regular game rendering goes here...
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void hide() {
        // Clean up resources when the screen is hidden
        world.dispose();
        debugRenderer.dispose();
    }

    @Override
    public void pause() {
        // Optional: Handle game pause logic
    }

    @Override
    public void resume() {
        // Optional: Handle game resume logic
    }

    @Override
    public void dispose() {
        // Dispose of other resources
        batch.dispose();
    }
}
