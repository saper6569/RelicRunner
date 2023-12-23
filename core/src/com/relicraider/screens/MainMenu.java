package com.relicraider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.relicraider.RelicRaider;
import com.relicraider.managers.AssetManagers;
import com.badlogic.gdx.graphics.GL20;
import com.relicraider.variables.SetupVariables;

public class MainMenu implements Screen {

    //game resources
    private Stage stage;

    private Texture playTexture;
    private TextureRegion playTextureRegion;
    private TextureRegionDrawable playDrawable;
    private ImageButton play;

    private Texture settingsTexture;
    private TextureRegion settingsTextureRegion;
    private TextureRegionDrawable settingsDrawable;
    private ImageButton settings;

    private Texture creditsTexture;
    private TextureRegion creditsTextureRegion;
    private TextureRegionDrawable creditsDrawable;
    private ImageButton credits;

    private Texture quitTexture;
    private TextureRegion quitTextureRegion;
    private TextureRegionDrawable quitDrawable;
    private ImageButton quit;

    private Texture backdropTexture;
    private Image backdrop;

    private Music menuSong;

    //frame counter
    private double countSec;

    //camera
    private OrthographicCamera camera;
    private FitViewport viewport;

    private RelicRaider game;
    public AssetManagers manager = new AssetManagers();

    @Override
    /**
     * This method contains any code that is run before the Main Menu is shown to the user
     */
    public void show() {
        //stage for game resources
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //resource instances
        //all reasources are loaded by the asset manager all at the beginning of the new screen
        manager.mainMenuAssets();
        manager.assetManager.finishLoading();

        //load resources from disk
        settingsTexture = manager.assetManager.get(manager.settingsButton, Texture.class);
        creditsTexture = manager.assetManager.get(manager.creditsButton, Texture.class);
        playTexture = manager.assetManager.get(manager.playButton, Texture.class);
        quitTexture = manager.assetManager.get(manager.quitButton, Texture.class);
        backdropTexture = manager.assetManager.get(manager.menuBackdrop, Texture.class);
        menuSong = manager.assetManager.get(manager.mainMenuSong1, Music.class);

        //camera objects for creating view of game for user
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);

        //create stage object for placing graphics on
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //temp variables for placing graphics at disorder locations
        int origin_x = (Gdx.graphics.getWidth() - playTexture.getWidth()) / 2;
        int origin_y = (Gdx.graphics.getHeight() - playTexture.getHeight()) / 2;
        //code for creating the play image button and placing it at the desired location
        playTextureRegion = new TextureRegion(playTexture);
        playDrawable = new TextureRegionDrawable(playTextureRegion);
        play = new ImageButton(playDrawable);
        play.setPosition(origin_x, origin_y);

        //click listener to find when the user wants to switch to the first game screen
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen1(game));
                //stop the music if it is playing
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

        //code for creating the credits image button and placing it at the desired location
        origin_x = (Gdx.graphics.getWidth() - creditsTexture.getWidth()) / 2;
        origin_y = ((Gdx.graphics.getHeight() - creditsTexture.getHeight()) / 2) + 50;
        creditsTextureRegion = new TextureRegion(creditsTexture);
        creditsDrawable = new TextureRegionDrawable(creditsTextureRegion);
        credits = new ImageButton(creditsDrawable);
        credits.setPosition(origin_x, origin_y);

        //click listener to find when the user wants to switch to the credits screen
        credits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new Credits(game));
                //stop the music if it is playing
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

        //code for creating the settings image button and placing it at the desired location
        origin_x = (Gdx.graphics.getWidth() - settingsTexture.getWidth()) / 2;
        origin_y = ((Gdx.graphics.getHeight() - settingsTexture.getHeight()) / 2) - 50;
        settingsTextureRegion = new TextureRegion(settingsTexture);
        settingsDrawable = new TextureRegionDrawable(settingsTextureRegion);
        settings = new ImageButton(settingsDrawable);
        settings.setPosition(origin_x, origin_y);

        //click listener to find when the user wants to switch to the settings screen
        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new Settings(game));
                //stop the music if it is playing
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

        //code for creating the quit image button and placing it at the desired location
        origin_x = (Gdx.graphics.getWidth() - quitTexture.getWidth()) / 2;
        origin_y = ((Gdx.graphics.getHeight() - quitTexture.getHeight()) / 2) - 100;
        quitTextureRegion = new TextureRegion(quitTexture);
        quitDrawable = new TextureRegionDrawable(quitTextureRegion);
        quit = new ImageButton(quitDrawable);
        quit.setPosition(origin_x, origin_y);

        //click listener to find when the user wants to exit
        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        backdrop = new Image(backdropTexture);

        //add all graphics and music to the stage
        stage.addActor(backdrop);
        stage.addActor(play);
        stage.addActor(settings);
        stage.addActor(credits);
        stage.addActor(quit);
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
            menuSong = manager.assetManager.get(manager.mainMenuSong1, Music.class);
            menuSong.play();
            countSec = 0;
        }

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
        playTexture.dispose();
        creditsTexture.dispose();
        settingsTexture.dispose();
        quitTexture.dispose();
        backdropTexture.dispose();
        menuSong.dispose();
        manager.assetManager.dispose();
    }
}
