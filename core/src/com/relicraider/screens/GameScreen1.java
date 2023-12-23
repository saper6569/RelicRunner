package com.relicraider.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.RelicRaider;
import com.relicraider.variables.SetupVariables;

public class GameScreen1 implements Screen {
    //game resources
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;

    private final OrthographicCamera camera;
    private final FitViewport viewport;

    //B2D setup
    private final World world;
    private final Box2DDebugRenderer debugRenderer;

    private static final float STEP_TIME = 1f / 60f;
    private static final int VELOCITY_ITERATIONS = 6;
    private static final int POSITION_ITERATIONS = 2;

    private float accumulator = 0;

    private final BodyDef bodyDef;

    private final RelicRaider game;
    public GameScreen1 (RelicRaider game) {
        this.game = game;

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("GameScreen1/spaceShip.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / SetupVariables.PPM);

        //camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH / SetupVariables.PPM, SetupVariables.HEIGHT / SetupVariables.PPM, camera);

        camera.position.set(viewport.getWorldWidth(), viewport.getWorldHeight(), 0);

        //B2D setup
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(0, 10));
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        //walls
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / SetupVariables.PPM, (rectangle.getY() + rectangle.getHeight() /  2) / SetupVariables.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth() / 2 / SetupVariables.PPM, rectangle.getHeight() / 2 / SetupVariables.PPM);
            fDef.shape = shape;
            body.createFixture(fDef);
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
