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
    public void show() {
        //stage for game resources
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //resource instances
        manager.mainMenuAssets();
        manager.assetManager.finishLoading();

        settingsTexture = manager.assetManager.get(manager.settingsButton, Texture.class);
        creditsTexture = manager.assetManager.get(manager.creditsButton, Texture.class);
        playTexture = manager.assetManager.get(manager.playButton, Texture.class);
        quitTexture = manager.assetManager.get(manager.quitButton, Texture.class);
        backdropTexture = manager.assetManager.get(manager.menuBackdrop, Texture.class);
        menuSong = manager.assetManager.get(manager.mainMenuSong1, Music.class);

        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);

        int origin_x = (Gdx.graphics.getWidth() - playTexture.getWidth()) / 2;
        int origin_y = (Gdx.graphics.getHeight() - playTexture.getHeight()) / 2;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        playTextureRegion = new TextureRegion(playTexture);
        playDrawable = new TextureRegionDrawable(playTextureRegion);
        play = new ImageButton(playDrawable);
        play.setPosition(origin_x, origin_y);
        play.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen1(game));
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

        origin_x = (Gdx.graphics.getWidth() - creditsTexture.getWidth()) / 2;
        origin_y = ((Gdx.graphics.getHeight() - creditsTexture.getHeight()) / 2) + 50;
        creditsTextureRegion = new TextureRegion(creditsTexture);
        creditsDrawable = new TextureRegionDrawable(creditsTextureRegion);
        credits = new ImageButton(creditsDrawable);
        credits.setPosition(origin_x, origin_y);

        credits.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new Credits(game));
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

        origin_x = (Gdx.graphics.getWidth() - settingsTexture.getWidth()) / 2;
        origin_y = ((Gdx.graphics.getHeight() - settingsTexture.getHeight()) / 2) - 50;
        settingsTextureRegion = new TextureRegion(settingsTexture);
        settingsDrawable = new TextureRegionDrawable(settingsTextureRegion);
        settings = new ImageButton(settingsDrawable);
        settings.setPosition(origin_x, origin_y);

        settings.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                ((Game)Gdx.app.getApplicationListener()).setScreen(new Settings(game));
                if (menuSong.isPlaying()) {
                    menuSong.stop();
                }
            }
        });

        origin_x = (Gdx.graphics.getWidth() - quitTexture.getWidth()) / 2;
        origin_y = ((Gdx.graphics.getHeight() - quitTexture.getHeight()) / 2) - 100;
        quitTextureRegion = new TextureRegion(quitTexture);
        quitDrawable = new TextureRegionDrawable(quitTextureRegion);
        quit = new ImageButton(quitDrawable);
        quit.setPosition(origin_x, origin_y);

        quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        backdrop = new Image(backdropTexture);

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
    public void render(float delta) {
        //screen render functions
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
        countSec += delta;

        //music
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
