package com.relicraider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.RelicRaider;
import com.badlogic.gdx.graphics.GL20;
import com.relicraider.SetupVariables;

public class MainMenu implements Screen {

    //game resources
    private Stage stage;

    private Texture buttonTexture;
    private TextureRegion buttonTextureRegion;
    private TextureRegionDrawable buttonDrawable;
    private ImageButton buttonBorder;

    private Texture backdropTexture;
    private Image backdrop;

    private Music menuSong;

    //frame counter
    private double countSec;

    //camera
    private OrthographicCamera camera;
    private FitViewport viewport;

    private RelicRaider game;

    @Override
    /**
     * This method contains any code that is run before the Main Menu is shown to the user
     */
    public void show() {
        //load resources from disk
        buttonTexture = new Texture(Gdx.files.internal("MainMenu/MenuButtonBorder.png"));
        backdropTexture = new Texture(Gdx.files.internal("MainMenu/backdrop.png"));
        menuSong = Gdx.audio.newMusic(Gdx.files.internal("MainMenu/track1.mp3"));

        //camera objects for creating view of game for user
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);

        //create stage object for placing graphics on
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

//PLAY BUTTON
        //code for creating the play button and placing it at the desired location
        int origin_x = ((Gdx.graphics.getWidth() - buttonTexture.getWidth()) / 2) + 100;
        int origin_y = ((Gdx.graphics.getHeight() - buttonTexture.getHeight()) / 2) + 100;
        Button playButton = new Button("PLAY",origin_x - 150 ,origin_y - 70);

        //click listener to find when the user wants to switch to the credits screen
        playButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen1(game));
                //stop the music if it is playing
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

//CREDITS BUTTON
        //code for creating the credits button and placing it at the desired location
        origin_x = ((Gdx.graphics.getWidth() - buttonTexture.getWidth()) / 2) + 100;
        origin_y = ((Gdx.graphics.getHeight() - buttonTexture.getHeight()) / 2) + 100;
        Button creditsButton = new Button("CREDITS",origin_x - 150,origin_y - 125);

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
    origin_x = ((Gdx.graphics.getWidth() - buttonTexture.getWidth()) / 2) + 100;
    origin_y = ((Gdx.graphics.getHeight() - buttonTexture.getHeight()) / 2) + 100;
    Button howToPlayButton = new Button("HOW TO PLAY",origin_x - 150,origin_y - 180);

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
        origin_x = ((Gdx.graphics.getWidth() - buttonTexture.getWidth()) / 2) + 100;
        origin_y = ((Gdx.graphics.getHeight() - buttonTexture.getHeight()) / 2) + 100;
        Button quitButton = new Button("QUIT",origin_x - 150,origin_y - 235);

        //Click listener to find when the user wants to exit program
        quitButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        backdrop = new Image(backdropTexture);

        //add all graphics and music to the stage

        stage.addActor(backdrop);
        stage.addActor(creditsButton.getButton());
        stage.addActor(playButton.getButton());
        stage.addActor(quitButton.getButton());
        stage.addActor(howToPlayButton.getButton());
        menuSong.setVolume((float)0.4);
        menuSong.play();

        countSec = 0;
    }

    @Override
    /**
     * Render method for the Main Menu - resets and redraws the screen when called
     */
    public void render(float delta) {
        //screen render functions
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        buttonTexture.dispose();
        backdropTexture.dispose();
        menuSong.dispose();
    }
}
