package com.relicraider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    private Button continueButton;
    private Button skipButton;

    private int backdrop;

    public GameStory(final RelicRaider game) {
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

        backdrop3 = new Texture(Gdx.files.internal("GameStory/gameStory3.png"));
        backdrop3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        continueButton = new Button("CONTINUE", SetupVariables.WIDTH - Button.width - 100, 50, stage, 24);

        //click listener to find when the user wants to switch to the next page
        continueButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backdrop++;
                if (backdrop == 3) {
                    continueButton.setText("ENTER");
                } if (backdrop == 4) {
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen1(game));
                }
            }
        });

        skipButton = new Button("SKIP", 100, 50, stage, 24);

        //click listener to find when the user wants to switch to the next page
        skipButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen1(game));
            }
        });

        backdrop = 1;

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    public void update() {
        if (backdrop == 1) {
            game.spriteBatch.draw(backdrop1, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT);
        } else if (backdrop == 2) {
            game.spriteBatch.draw(backdrop2, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT);
        } else {
            game.spriteBatch.draw(backdrop3, 0, 0, SetupVariables.WIDTH, SetupVariables.HEIGHT);
            continueButton.setText("ENTER");
            continueButton.getButton().setPosition(SetupVariables.WIDTH / 2 - Button.width / 2, 50);
            skipButton.getButton().remove();
        }
    }

    @Override
    public void render(float delta) {
        //screen render functions
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();

        update();

        game.spriteBatch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height,true);
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
