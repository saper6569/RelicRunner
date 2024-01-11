package com.relicraider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;

public class GameStory implements Screen {
    private final RelicRaider game;
    public Stage stage;
    private Texture backdrop1;
    private Texture backdrop2;
    private Texture backdrop3;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private Button button;
    private Table table;

    private int backdrop;

    public GameStory(RelicRaider game) {
        this.game = game;

        //camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        stage = new Stage(viewport, game.spriteBatch);

        backdrop1 = new Texture(Gdx.files.internal("GameStory/gameStory1.png"));
        backdrop1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backdrop2 = new Texture(Gdx.files.internal("GameStory/gameStory2.png"));
        backdrop2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backdrop2 = new Texture(Gdx.files.internal("GameStory/gameStory3.png"));
        backdrop2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        button = new Button("button", 100, 0);

        stage.addActor(button.getButton());

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();
        game.spriteBatch.draw(backdrop1, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT);
        game.spriteBatch.end();
        stage.draw();
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
        stage.dispose();
        backdrop1.dispose();
        backdrop2.dispose();
        backdrop3.dispose();
    }
}
