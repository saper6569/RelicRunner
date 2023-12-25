package com.relicraider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Knight;

public class GameScreen1 extends AbstractGameScreen {
    private final Knight player;

    public GameScreen1 (RelicRaider game) {
        super(game, "GameScreen1/map.tmx");

        player = new Knight(world, this);
    }

    public void update(float dt){
        camera.update();

        camera.position.x = player.b2dBody.getPosition().x;
        camera.position.y = player.b2dBody.getPosition().y;

        player.updateSprite(dt);

        stepWorld();
    }

    @Override
    public void render(float delta) {
        update(delta);

        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mapRenderer.setView(camera);
        mapRenderer.render();
        //debugRenderer.render(world, camera.combined);

        game.spriteBatch.setProjectionMatrix(camera.combined);
        game.spriteBatch.begin();

        //player movement
        player.playerMovement(delta);
        player.draw(game.spriteBatch);
        game.spriteBatch.end();

        stepWorld();
    }

    @Override
    public void dispose() {
        world.dispose();
        map.dispose();
        mapRenderer.dispose();
        debugRenderer.dispose();
    }
}
