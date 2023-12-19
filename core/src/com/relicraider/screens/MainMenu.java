package com.relicraider.screens;

import com.badlogic.gdx.Screen;

public class MainMenu implements Screen {

    //game resources
    private Stage stage;
    private Texture playTexture;
    private TextureRegion playTextureRegion;
    private TextureRegionDrawable playDrawable;
    private ImageButton play;

    //frame counter
    private int count;
    private int countSec;

    //camera
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private Main game;
    public AssetManagers manager = new AssetManagers();

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
        manager.assetManager.dispose();
    }
}
