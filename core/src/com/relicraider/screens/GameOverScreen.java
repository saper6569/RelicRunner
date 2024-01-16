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
import com.relicraider.screens.utilities.Button;

//Game Over Screen implements Screen interface
public class GameOverScreen implements Screen {
    private final RelicRaider game;
    public Stage stage;
    private Image gameOverBackground;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private Button mainMenuButton;
    private Button quitButton;
    private Music menuSong;
    private double countSec;

    /**
     * Primary Constructor for Game Over Screen
     * @param game - The Game Object
     */
    public GameOverScreen(final RelicRaider game) {
        this.game = game; //Set Game Over Screen's game to parameter

        //MAKE THIS DEATH MUSIC
        menuSong = Gdx.audio.newMusic(Gdx.files.internal("MainMenu/track1.mp3"));

//CAMERA
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        stage = new Stage(viewport, RelicRaider.spriteBatch);

        //create stage object for placing graphics on
        stage = new Stage(viewport, RelicRaider.spriteBatch);
        Gdx.input.setInputProcessor(stage);

        gameOverBackground = new Image(new Texture(Gdx.files.internal("GameOver/gameOverScreen.png")));
        stage.addActor(gameOverBackground);

//MAIN MENU BUTTON

        int origin_x = ((SetupVariables.WIDTH - Button.width) / 2) ;
        int origin_y = ((SetupVariables.HEIGHT - Button.height) / 2 + 20);
        Button mainMenuButton = new Button("MAIN MENU", origin_x, origin_y, stage, 24);
        //click listener to find when the user wants to go back to main menu
        mainMenuButton.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
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