/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Abstract Game Screen Class - Every Game Screen has this code
 */
package com.relicraider.screens.gamescreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Item;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.characters.GameCharacter;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.GameOverScreen;
import com.relicraider.screens.GameWinScreen;
import com.relicraider.screens.utilities.Button;
import com.relicraider.screens.utilities.Door;
import com.relicraider.screens.utilities.HUD;

import java.util.ArrayList;

//Abstract Game Screen implements Screen interface
public abstract class AbstractGameScreen implements Screen {
//VARIABLES
    public static Player player;
    protected ArrayList<GameCharacter> characters;
    protected ArrayList<Item> items;
    protected ArrayList<Door> doors;
    protected final TmxMapLoader mapLoader;
    protected final TiledMap map;
    protected final OrthogonalTiledMapRenderer mapRenderer;

    protected final OrthographicCamera camera;
    protected final FitViewport viewport;

    //Box2D setup
    protected final World world;
    protected final Box2DDebugRenderer debugRenderer;

    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    protected float accumulator = 0;

    protected final BodyDef bodyDef;

    protected final RelicRaider game;
    protected final Stage stage;
    protected HUD hud;

    /**
     * Primary Constructor for a Game Screen
     * @param game - Relic Raider Game Object
     * @param mapLocation - What room game screen should display
     * @param objectLayer - libGDX variable to create rooms
     * @param playerX - X position of player
     * @param playerY - Y position of player
     */
    public AbstractGameScreen(RelicRaider game, String mapLocation, int objectLayer, float playerX, float playerY) {
        this.game = game;

//CAMERA
        camera = new OrthographicCamera(); //Create new Camera
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera); //Camera's viewport is set width and height, found in setup variables class

        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0); //Set Camera's Position to x: Width of the World Camera is in, divided by 2. y: Height of the World Camera is in, divided by 2.
        camera.zoom -= 0.7f; //Zoom camera in
//STAGE
        stage = new Stage(viewport, RelicRaider.spriteBatch); ///Create new Stage
        Gdx.input.setInputProcessor(stage); //Get Input to stage

        characters = new ArrayList<>(); //Create new ArrayList for characters
        items = new ArrayList<>(); //Create new ArrayList for items
        doors = new ArrayList<>(); //Create new ArrayList for doors

        //To load map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapLocation);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1);

        //Box2D setup
        world = new World(new Vector2(0, 0), true); //Create new world
        debugRenderer = new Box2DDebugRenderer(); //Create new Box2d Bug Renderer

        //Create new player, add to characters list
        player = new Player(game, world, playerX, playerY, Player.playerHealth);
        characters.add(player);

        //Create new hud
        hud = new HUD(game, RelicRaider.spriteBatch, player);

        //To get player in the world
        bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(0, 10));
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        fDef.filter.categoryBits = SetupVariables.BIT_WORLD;
        fDef.filter.maskBits = SetupVariables.BIT_PLAYER | SetupVariables.BIT_WORLD;
        Body body;

//WALLS
        for (MapObject object : map.getLayers().get(objectLayer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2), (rectangle.getY() + rectangle.getHeight() / 2));

            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

        createCollisionListener(); //Call createCollisionListener method
    }

    /**
     * Method for creating the collision listener for each room. This will handle any interactions between different physics bodies
     */
    public void createCollisionListener() {
        world.setContactListener(new ContactListener() {

            /**
             * method that runs when 2 bodies come into contact
             * @param contact - the object containing the 2 collided objects
             */
            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                //handles item pickups
                if (fixtureA.getUserData() instanceof Item) {
                    ((Item) fixtureA.getUserData()).itemIsPickedUp();
                } else if (fixtureB.getUserData() instanceof Item) {
                    ((Item) fixtureB.getUserData()).itemIsPickedUp();
                }

                //handles when a goblin collisions
                if (fixtureA.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureA.getUserData()).setCollided(true);
                } else if (fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setCollided(true);
                }

                //handles when a player and goblin collides
                if (fixtureA.getUserData() instanceof Goblin && fixtureB.getUserData() instanceof Player) {
                    ((Goblin) fixtureA.getUserData()).setAttacking(true);
                    AbstractGameScreen.player.getCollisions().add((Goblin) fixtureA.getUserData());
                } else if (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setAttacking(true);
                    AbstractGameScreen.player.getCollisions().add((Goblin) fixtureB.getUserData());
                }

                //handles when a player collides with a door
                if (fixtureA.getUserData() instanceof Door && fixtureB.getUserData() instanceof Player) {
                    hud.setDoorX(((Door) fixtureA.getUserData()).getNextX());
                    hud.setDoorY(((Door) fixtureA.getUserData()).getNextY());
                    hud.setRoom(((Door) fixtureA.getUserData()).getRoom());
                    hud.setShowButton(true);
                } else if (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Door) {
                    hud.setDoorX(((Door) fixtureB.getUserData()).getNextX());
                    hud.setDoorY(((Door) fixtureB.getUserData()).getNextY());
                    hud.setRoom(((Door) fixtureB.getUserData()).getRoom());
                    hud.setShowButton(true);
                }
            }

            /**
             * method that runs when 2 bodies end contact
             * @param contact - the object containing the 2 uncollided objects
             */
            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                //handles when a player and goblin end contact
                if (fixtureA.getUserData() instanceof Goblin && fixtureB.getUserData() instanceof Player) {
                    ((Goblin) fixtureA.getUserData()).setAttacking(false);
                    AbstractGameScreen.player.removeCollision(((Goblin) fixtureA.getUserData()).getCharacterID());
                } else if (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setAttacking(false);
                    AbstractGameScreen.player.removeCollision(((Goblin) fixtureB.getUserData()).getCharacterID());
                }

                //handles when goblin and object end contact
                if (fixtureA.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureA.getUserData()).setCollided(false);
                } else if (fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setCollided(false);
                }

                //handles when the player and door end contact
                if (fixtureA.getUserData() instanceof Door && fixtureB.getUserData() instanceof Player) {
                    hud.setShowButton(false);
                } else if (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Door) {
                    hud.setShowButton(false);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    /**
     * method used to advance the physics world
     */
    protected void stepWorld() {
        //B2D physics
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    /**
     * update method
     * @param dt - represents the time since last render
     */
    public void update(float dt){
        //if the player is dead change to the game over screen
        if (!player.isAlive()) {
            RelicRaider.soundPlayer.stopMusic();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOverScreen(game));
        }

        if (Player.getRelicsCollected() == 6) {
            RelicRaider.soundPlayer.stopMusic();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameWinScreen(game));
        }

        camera.update();
        camera.position.x = player.getB2dBody().getPosition().x;
        camera.position.y = player.getB2dBody().getPosition().y;

        //loop through all the characters and update them
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).updateSprite(dt);
        }

        //loop through all the characters and remove any that are dead
        for (int i = 0; i < characters.size(); i++) {
            if (!characters.get(i).isAlive()) {
                characters.remove(i);
            }
        }

        //loop through all the characters and remove any that are dead
        for (int i = 0; i < items.size(); i++) {
            items.get(i).update(dt);
        }

        //loop through all the items and remove any that are picked up
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isPickedUp()) {
                items.remove(i);
            }
        }

        hud.update(dt);

        RelicRaider.soundPlayer.playGameMusic();

        stepWorld();
    }

    @Override
    public void show() {

    }

    /**
     * render method for the room
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();

        //uncomment to see hitboxes
        //debugRenderer.render(world, camera.combined);

        RelicRaider.spriteBatch.setProjectionMatrix(camera.combined);
        RelicRaider.spriteBatch.begin();

        //player movement
        player.playerMovement(delta);

        //loop through all the items and draw them
        for (int i = 0; i < items.size(); i++) {
            items.get(i).draw(RelicRaider.spriteBatch);
        }

        //loop through all the characters and draw them
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).draw(RelicRaider.spriteBatch);
        }

        RelicRaider.spriteBatch.end();

        RelicRaider.spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        //render the stage
        stage.draw();
        stage.act();

        stepWorld();
    }

    /**
     * method for resizing the screen
     * @param width - New Width of Screen
     * @param height - New Height of Screen
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        hud.getViewport().update(width, height);
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

    /**
     * Method to dispose assets used in the room
     */
    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
    }

    /**
     *
     * @return the tmx map loader
     */
    public TmxMapLoader getMapLoader() {
        return mapLoader;
    }

    public TiledMap getMap() {
        return map;
    }

    public OrthogonalTiledMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public FitViewport getViewport() {
        return viewport;
    }

    public Box2DDebugRenderer getDebugRenderer() {
        return debugRenderer;
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public Stage getStage() {
        return stage;
    }
}
