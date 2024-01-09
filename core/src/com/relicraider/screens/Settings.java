package com.relicraider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;

public class Settings implements Screen {
    private final RelicRaider game;

    private final static float FRAME_DURATION = 0.30f;

    private TextureAtlas atlasPlayer;
    private Animation<TextureRegion> animationPlayer;

    private TextureAtlas atlasGoblin;
    private Animation<TextureRegion> animationGoblin;

    private TextureAtlas atlasHealPotion;
    private Animation<TextureRegion> animationHealPotion;

    private Texture backdrop;
    private final OrthographicCamera camera;
    private final FitViewport viewport;

    public Settings (RelicRaider game) {
        this.game = game;

        backdrop = new Texture(Gdx.files.internal("HowToPlay/HowToPlay.png"));
        backdrop.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH / SetupVariables.PPM, SetupVariables.HEIGHT / SetupVariables.PPM, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        atlasPlayer = new TextureAtlas(Gdx.files.internal("Sprites/knightWalk.txt"));
        animationPlayer = new Animation<TextureRegion>(FRAME_DURATION, atlasPlayer.findRegions("forward"));

        atlasGoblin = new TextureAtlas(Gdx.files.internal("Sprites/goblinWalk.txt"));
        animationGoblin = new Animation<TextureRegion>(FRAME_DURATION, atlasGoblin.findRegions("forward"));

        atlasHealPotion = new TextureAtlas(Gdx.files.internal("Sprites/knightWalk.txt"));
        animationHealPotion = new Animation<TextureRegion>(FRAME_DURATION, atlasHealPotion.getRegions());
    }

    @Override
    public void show() {

    }

    public void update(float dt) {

    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();

        game.spriteBatch.draw(backdrop, 0, 0, SetupVariables.WIDTH / SetupVariables.PPM, SetupVariables.HEIGHT / SetupVariables.PPM);

        game.spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
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
