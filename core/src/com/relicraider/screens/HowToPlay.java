/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   How To Play Screen Class
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
import com.relicraider.screens.gamescreens.Room1;
import com.relicraider.screens.utilities.Button;

//HowToPlay class implements the Screen Superclass
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
     * Primary Constructor for HowToPlay Screen
     * @param game - The Game Object
     */
    public HowToPlay(final RelicRaider game) {
        this.game = game;
        elapsedTime = 0; //Set elapsed time to 0

        //Set Background
        backdrop = new Texture(Gdx.files.internal("HowToPlay/HowToPlay.png"));
        backdrop.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

//CAMERA
        camera = new OrthographicCamera(); //Create new Camera
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera); //Camera's viewport is set width and height, found in setup variables class
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0); //Set Camera's Position to x: Width of the World Camera is in, divided by 2. y: Height of the World Camera is in, divided by 2.

        stage = new Stage(viewport, RelicRaider.spriteBatch); //Create New Stage

        atlasPlayer = new TextureAtlas(Gdx.files.internal("Sprites/knightWalk.txt")); //Import Spritesheet of Knight
        animationPlayer = new Animation<TextureRegion>(FRAME_DURATION, atlasPlayer.findRegions("forward")); //Import images of knight walking forward

        atlasGoblin = new TextureAtlas(Gdx.files.internal("Sprites/goblinWalk.txt")); //Import Spritesheet of Goblin
        animationGoblin = new Animation<TextureRegion>(FRAME_DURATION, atlasGoblin.findRegions("forward")); //Import images of goblin walking forwards

        atlasHealPotion = new TextureAtlas(Gdx.files.internal("Sprites/healPotion.txt")); //Import Spritesheet of the health potion
        animationHealPotion = new Animation<TextureRegion>(FRAME_DURATION, atlasHealPotion.getRegions()); //Import image of health potion

        backButton = new Button("BACK", SetupVariables.WIDTH - Button.width - 120, 80, stage, 24); //Create new button to go back to main menu

        //click listener to find when the user wants to go back
        backButton.getButton().addListener(new ClickListener(){
            @Override
            //If user clicks back button, go back to main menu
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
            }
        });

        Gdx.input.setInputProcessor(stage); //Set input processor to the stage
    }

    @Override
    public void show() {

    }

    /**
     * Method to update image animations
     * @param dt - Down-time
     */
    public void update(float dt) {
        elapsedTime += dt; //Elapsed Time = Elapsed Time + Down Time

        //Draw all images
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

        RelicRaider.spriteBatch.draw(backdrop, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT); //Draw spritebatch

        update(delta); //Call Update Method

        RelicRaider.spriteBatch.end(); //Stop drawing spritebatch

        //Draw stage
        stage.act();
        stage.draw();
    }

    /**
     * Method to resize camera view
     * @param width - Width of camera view
     * @param height - Height of camera view
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true); //Update camera view with new variables
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
     * Method to dispose assets used in the main menu
     */
    @Override
    public void dispose() {
        atlasGoblin.dispose();
        atlasPlayer.dispose();
        backdrop.dispose();
        stage.dispose();
    }
}
