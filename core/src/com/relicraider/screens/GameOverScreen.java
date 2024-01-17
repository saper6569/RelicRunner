/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Game Over Screen Class
 */
package com.relicraider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.characters.Player;
import com.relicraider.screens.gamescreens.*;
import com.relicraider.screens.utilities.Button;

//Game Over Screen implements Screen interface
public class GameOverScreen implements Screen {
    private final RelicRaider game;
    public Stage stage;
    private Image gameOverBackground;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private Music menuSong;
    private double countSec;

    /**
     * Primary Constructor for Game Over Screen
     * @param game - The Game Object
     */
    public GameOverScreen(final RelicRaider game) {
        //Set Game Over Screen's game to parameter
        this.game = game;

        resetGame();

        //Import Song File
        menuSong = Gdx.audio.newMusic(Gdx.files.internal("MainMenu/track1.mp3"));

//CAMERA
        camera = new OrthographicCamera(); //Create new Camera
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera); //Camera's viewport is set width and height, found in setup variables class
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0); //Set Camera's Position to x: Width of the World Camera is in, divided by 2. y: Height of the World Camera is in, divided by 2.

        //create stage object for placing graphics on
        stage = new Stage(viewport, RelicRaider.spriteBatch);
        Gdx.input.setInputProcessor(stage);

        //Import background image for game over screen, add to stage
        gameOverBackground = new Image(new Texture(Gdx.files.internal("GameOver/gameOverScreen.png")));
        stage.addActor(gameOverBackground);

//MAIN MENU BUTTON
        int origin_x = ((SetupVariables.WIDTH - Button.width) / 2) ; //Buttons x value is the width of the screen minus the width of the button, divided by two
        int origin_y = ((SetupVariables.HEIGHT - Button.height) / 2 + 20); //Buttons y value is the height of the screen minus the height of the button, divided by two
        Button mainMenuButton = new Button("MAIN MENU", origin_x, origin_y, stage, 24); //Create new button to go to main meny
        //click listener to find when the user wants to go back to main menu
        mainMenuButton.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game)); //If button is clicked go to main menu screen
                //stop the music if it is playing
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });


//QUIT BUTTON
        //code for creating the Quit Game button and placing it at the desired location
        origin_x = ((SetupVariables.WIDTH - Button.width) / 2);
        origin_y = ((SetupVariables.HEIGHT - Button.height) / 2) - 50;
        Button quitButton = new Button("QUIT", origin_x, origin_y, stage, 24);

        //Click listener to find when the user wants to exit program
        quitButton.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        //Set Volume of Main Menu Music and Play, start count.
        menuSong.setVolume((float) 0.3);
        menuSong.play();

        countSec = 0;
    }

    /**
     * method used for resetting the game in order to let the game be run again
     */
    public void resetGame() {
        //reset all the variables
        Player.playerHealth = 100;
        Player.setRelicsCollected(0);

        Room1.relicIsFound = false;
        Room1.potionIsUsed = false;

        Room2.relicIsFound = false;
        Room2.potionIsUsed = false;

        Room3.relicIsFound = false;
        Room3.potionIsUsed = false;

        Room4.relicIsFound = false;
        Room4.potionIsUsed = false;

        Room5.relicIsFound = false;
        Room5.potionIsUsed = false;

        Room6.relicIsFound = false;
        Room6.potionIsUsed = false;
    }

    @Override
    public void show() {

    }

    /**
     * Method to render the screen
     * @param delta - libGDX screen setup variable
     */
    @Override //Overides method in superclass
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

    /**
     * Method to resize the screen of the game over screen
     * @param width - New Width of Screen
     * @param height - New Height of Screen
     */
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

    /**
     * Method to dispose assets used
     */
    @Override
    public void dispose() {
        //manual garbage disposal
        stage.dispose();
        menuSong.dispose();
    }
}