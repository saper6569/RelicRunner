/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   How to Play screen class
 */
package com.relicraider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.screens.utilities.Button;

public class HowToPlay implements Screen {
    private final RelicRaider game;
    private Stage stage;

    private final static float FRAME_DURATION = 0.30f;
    private float elapsedTime;

    private TextureAtlas atlasPlayer;
    private Animation<TextureRegion> animationPlayer;

    private TextureAtlas atlasGoblin;
    private Animation<TextureRegion> animationGoblin;

    private TextureAtlas atlasHealPotion;
    private Animation<TextureRegion> animationHealPotion;

    private Texture backdrop;

    private Button backButton;
    private final OrthographicCamera camera;
    private final FitViewport viewport;

    /**
     * Primary Constructor for How to Play screen
     * @param game - The Game Object
     */
    public HowToPlay(final RelicRaider game) {
        this.game = game;
        elapsedTime = 0;

        backdrop = new Texture(Gdx.files.internal("HowToPlay/HowToPlay.png"));
        backdrop.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        stage = new Stage(viewport, RelicRaider.spriteBatch);

        atlasPlayer = new TextureAtlas(Gdx.files.internal("Sprites/knightWalk.txt"));
        animationPlayer = new Animation<TextureRegion>(FRAME_DURATION, atlasPlayer.findRegions("forward"));

        atlasGoblin = new TextureAtlas(Gdx.files.internal("Sprites/goblinWalk.txt"));
        animationGoblin = new Animation<TextureRegion>(FRAME_DURATION, atlasGoblin.findRegions("forward"));

        atlasHealPotion = new TextureAtlas(Gdx.files.internal("Sprites/healPotion.txt"));
        animationHealPotion = new Animation<TextureRegion>(FRAME_DURATION, atlasHealPotion.getRegions());

        backButton = new Button("BACK", SetupVariables.WIDTH - Button.width - 120, 80, stage, 24);

        //click listener to find when the user wants to go back
        backButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //set the screen to the main menu
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    /**
     * method to update the screen
     * @param dt - time since last render
     */
    public void update(float dt) {
        elapsedTime += dt;

        RelicRaider.spriteBatch.draw(animationPlayer.getKeyFrame(elapsedTime, true), 550, 270, 100, 100);
        RelicRaider.spriteBatch.draw(animationGoblin.getKeyFrame(elapsedTime, true), 550, 220, 100, 100);
        RelicRaider.spriteBatch.draw(animationHealPotion.getKeyFrame(elapsedTime, true), 575, 190, 50, 50);

    }

    /**
     * Method to render the screen
     * @param delta - libGDX screen setup variable
     */
    @Override
    public void render(float delta) {
        camera.update();
        elapsedTime += delta;

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        RelicRaider.spriteBatch.setProjectionMatrix(camera.combined);
        RelicRaider.spriteBatch.begin();

        RelicRaider.spriteBatch.draw(backdrop, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT);

        update(delta);

        RelicRaider.spriteBatch.end();

        stage.act();
        stage.draw();
    }

    /**
     * Method to resize the screen of how to play
     * @param width - New Width of Screen
     * @param height - New Height of Screen
     */
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

    /**
     * Method to dispose assets used
     */
    @Override
    public void dispose() {
        atlasGoblin.dispose();
        atlasPlayer.dispose();
        backdrop.dispose();
        stage.dispose();
    }
}
