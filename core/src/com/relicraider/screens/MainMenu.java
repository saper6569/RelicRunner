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

public class MainMenu implements Screen {

    //game resources
    private Stage stage;

    private Image backdrop;

    private Music menuSong;

    //frame counter
    private double countSec;

    //camera
    private OrthographicCamera camera;
    private FitViewport viewport;

    private RelicRaider game;

    public MainMenu(final RelicRaider game) {
        this.game = game;
        //load resources from disk
        menuSong = Gdx.audio.newMusic(Gdx.files.internal("MainMenu/track1.mp3"));

        //camera objects for creating view of game for user
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        //create stage object for placing graphics on
        stage = new Stage(viewport, game.spriteBatch);
        Gdx.input.setInputProcessor(stage);

        backdrop = new Image(new Texture(Gdx.files.internal("MainMenu/backdrop.png")));
        stage.addActor(backdrop);

//TITLE

//LOGO
//PLAY BUTTON
        //code for creating the play button and placing it at the desired location
        int origin_x = ((SetupVariables.WIDTH - Button.width) / 2) - 140;
        int origin_y = ((SetupVariables.HEIGHT - Button.height) / 2) + 30;
        Button playButton = new Button("PLAY", origin_x, origin_y, stage, 24);


        //click listener to find when the user wants to switch to the credits screen
        playButton.getButton().addListener(new ClickListener(){
            @Override
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
    /**
     * Render method for the Main Menu - resets and redraws the screen when called
     */
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
