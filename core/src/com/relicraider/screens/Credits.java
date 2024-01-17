/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Credits screen class
 */
package com.relicraider.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class Credits implements Screen {

    private final RelicRaider game;
    private Stage stage;
    private Image backdrop;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private Button backButton;

    /**
     * Primary Constructor for credits screen
     * @param game - The Game Object
     */
    public Credits (final RelicRaider game) {
        this.game = game;

        //camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(SetupVariables.WIDTH, SetupVariables.HEIGHT, camera);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        stage = new Stage(viewport, game.spriteBatch);

        Texture backdropTexture = new Texture(Gdx.files.internal("Credits/creditsScreen.png"));
        backdropTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        backdrop = new Image(backdropTexture);
        stage.addActor(backdrop);

        backButton = new Button("BACK", SetupVariables.WIDTH - Button.width - 80, 50, stage, 24);
        //click listener to find when the user wants to go back
        backButton.getButton().addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
            }
        });

        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {

    }

    /**
     * method for rendering to the screen
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render the stage
        stage.act();
        stage.draw();
    }

    /**
     * Method to resize the screen of the game over screen
     * @param width - New Width of Screen
     * @param height - New Height of Screen
     */
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

    /**
     * Method to dispose assets used
     */

    @Override
    public void dispose() {
        stage.dispose();
    }
}
