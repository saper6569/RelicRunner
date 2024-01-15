package com.relicraider.screens.gamescreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Item;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.characters.GameCharacter;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;
import com.relicraider.screens.utilities.HUD;

import java.util.ArrayList;

public abstract class AbstractGameScreen implements Screen {
    public static Player player;
    protected ArrayList<GameCharacter> characters;
    protected ArrayList<Item> items;
    protected ArrayList<Door> doors;
    protected final TmxMapLoader mapLoader;
    protected final TiledMap map;
    protected final OrthogonalTiledMapRenderer mapRenderer;

    protected final OrthographicCamera camera;
    protected final FitViewport viewport;

    //B2D setup
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


    public AbstractGameScreen(RelicRaider game, String mapLocation, int objectLayer) {
        this.game = game;
        //camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);

        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.zoom -= 0.7f;

        stage = new Stage(viewport, this.game.spriteBatch);

        characters = new ArrayList<>();
        items = new ArrayList<>();
        doors = new ArrayList<>();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapLocation);
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1);

        //B2D setup
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        player = new Player(world, 200, 300);
        characters.add(player);

        hud = new HUD(game.spriteBatch, player);

        bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(0, 10));
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        fDef.filter.categoryBits = SetupVariables.BIT_WORLD;
        fDef.filter.maskBits = SetupVariables.BIT_PLAYER | SetupVariables.BIT_WORLD;
        Body body;

        //walls
        for (MapObject object : map.getLayers().get(objectLayer).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2), (rectangle.getY() + rectangle.getHeight() / 2));

            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA.getUserData() instanceof HealingPotion) {
                    ((HealingPotion) fixtureA.getUserData()).itemIsPickedUp();
                } else if (fixtureB.getUserData() instanceof HealingPotion) {
                    ((HealingPotion) fixtureB.getUserData()).itemIsPickedUp();
                }

                if (fixtureA.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureA.getUserData()).setCollided(true);
                } else if (fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setCollided(true);
                }

                if (fixtureA.getUserData() instanceof Goblin && fixtureB.getUserData() instanceof Player) {
                    ((Goblin) fixtureA.getUserData()).setAttacking(true);
                    AbstractGameScreen.player.getCollisions().add((Goblin) fixtureA.getUserData());
                } else if (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setAttacking(true);
                    AbstractGameScreen.player.getCollisions().add((Goblin) fixtureB.getUserData());
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA.getUserData() instanceof Goblin && fixtureB.getUserData() instanceof Player) {
                    ((Goblin) fixtureA.getUserData()).setAttacking(false);
                    AbstractGameScreen.player.removeCollision(((Goblin) fixtureA.getUserData()).getCharacterID());
                } else if (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setAttacking(false);
                    AbstractGameScreen.player.removeCollision(((Goblin) fixtureB.getUserData()).getCharacterID());
                }

                if (fixtureA.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureA.getUserData()).setCollided(false);
                } else if (fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setCollided(false);
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

    protected void stepWorld() {
        //B2D physics
        float delta = Gdx.graphics.getDeltaTime();

        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;

            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }

    public void update(float dt){
        camera.update();

        camera.position.x = player.getB2dBody().getPosition().x;
        camera.position.y = player.getB2dBody().getPosition().y;
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).updateSprite(dt);
        }

        for (int i = 0; i < characters.size(); i++) {
            if (!characters.get(i).isAlive()) {
                characters.remove(i);
            }
        }

        for (int i = 0; i < items.size(); i++) {
            items.get(i).update(dt);
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isPickedUp()) {
                items.remove(i);
            }
        }

        hud.update(dt);

        stepWorld();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();
        //debugRenderer.render(world, camera.combined);

        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();

        //player movement
        player.playerMovement(delta);
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).draw(game.spriteBatch);
        }

        for (int i = 0; i < items.size(); i++) {
            items.get(i).draw(game.spriteBatch);
        }

        for (int i = 0; i < doors.size(); i++) {
            doors.get(i).draw(game.spriteBatch);
        }

        game.spriteBatch.end();

        game.spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        stepWorld();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        world.dispose();
        map.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
    }

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
