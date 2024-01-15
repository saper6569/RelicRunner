/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Main Menu Class
 */
package com.relicraider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.RelicRaider;
import com.badlogic.gdx.graphics.GL20;
import com.relicraider.SetupVariables;
import com.relicraider.screens.gamescreens.Room1;
import com.relicraider.screens.utilities.Button;

//Main Menu Class Implements Screen Interface
public class MainMenu implements Screen {

    //Game resources
    private Stage stage;

    private Image backdrop;

    private Music menuSong;

    //frame counter
    private double countSec;

    //Camera
    private OrthographicCamera camera;
    private FitViewport viewport;

    private RelicRaider game;

    /**
     * Primary Constructor for Main Menu
     * @param game - Game Main Menu is for
     */
    public MainMenu(final RelicRaider game) {
        this.game = game;
        //Load main menu song file
        menuSong = Gdx.audio.newMusic(Gdx.files.internal("MainMenu/track1.mp3"));

        //Camera objects for creating view of game for user
        camera = new OrthographicCamera(); //Create new Camera
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera); //Camera's viewport is set width and height, found in setup variables class
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0); //Set Camera's Position to x: Width of the World Camera is in, divided by 2. y: Height of the World Camera is in, divided by 2.

        //create stage object for placing graphics on
        stage = new Stage(viewport, RelicRaider.spriteBatch);
        Gdx.input.setInputProcessor(stage);

        //Add Background Image to Main Menu
        backdrop = new Image(new Texture(Gdx.files.internal("MainMenu/backdrop.png")));
        stage.addActor(backdrop); //Add to main menu stage

//PLAY BUTTON
        //code for creating the play button and placing it at the desired location
        int origin_x = ((SetupVariables.WIDTH - Button.width) / 2) - 140; //x origin of the button is (setup width take away width of button) from that number take away 140
        int origin_y = ((SetupVariables.HEIGHT - Button.height) / 2) + 30; //y origin of the button is (setup height take away height of button) from that number add 30
        Button playButton = new Button("PLAY", origin_x, origin_y, stage, 24); //Create New button with text, x, y, Stage, font size of text


        //click listener to find when the user wants to switch to the credits screen
        playButton.getButton().addListener(new ClickListener(){
            @Override
            /**
             *
             */
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameStory(game));
                //stop the music if it is playing
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

//CREDITS BUTTON
        //code for creating the credits button and placing it at the desired location
        origin_x = ((SetupVariables.WIDTH - Button.width) / 2) - 140;
        origin_y = ((SetupVariables.HEIGHT - Button.height) / 2) - 25;
        Button creditsButton = new Button("CREDITS", origin_x, origin_y, stage, 24);

        //click listener to find when the user wants to switch to the credits screen
        creditsButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new Credits(game));
                //stop the music if it is playing
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

//HOW TO PLAY BUTTON
        //code for creating the settings button and placing it at the desired location
        origin_x = ((SetupVariables.WIDTH - Button.width) / 2) - 140;
        origin_y = ((SetupVariables.HEIGHT - Button.height) / 2) - 80;
        Button howToPlayButton = new Button("HOW TO PLAY", origin_x, origin_y, stage, 24);

        //click listener to find when the user wants to switch to the credits screen
        howToPlayButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new HowToPlay(game));
                //stop the music if it is playing
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

//QUIT BUTTON
        //code for creating the Quit Game button and placing it at the desired location
        origin_x = ((SetupVariables.WIDTH - Button.width) / 2) - 140;
        origin_y = ((SetupVariables.HEIGHT - Button.height) / 2) - 135;
        Button quitButton = new Button("QUIT", origin_x, origin_y, stage, 24);

        //Click listener to find when the user wants to exit program
        quitButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Set Volume of Main Menu Music and Play, start count.
        menuSong.setVolume((float)0.4);
        menuSong.play();

        countSec = 0;
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        camera.update();

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render the stage
        stage.act();
        stage.draw();
        countSec += delta;

        //loop through the menu song with a 300-second break between each repetition
        if (!menuSong.isPlaying() && countSec > 300) {
            menuSong.play();
            countSec = 0;
        }

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height,true);
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
        //manual garbage disposal
        stage.dispose();
        menuSong.dispose();
    }
}
