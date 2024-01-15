package com.relicraider.screens.gamescreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.relicraider.Items.HealingPotion;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.HUD;

public class Room1 extends AbstractGameScreen {
    private final HUD hud;

    public Room1(RelicRaider game) {
        super(game, "Maps/room1.tmx", 4);

        Player.room = "Room1";

        characters.add(new Goblin(world, 100, 300));
        characters.add(new Goblin(world, 220, 300));
        characters.add(new Goblin(world, 270, 130));
        characters.add(new Goblin(world, 360, 80));

        items.add(new HealingPotion(world, 90, 300));

        hud = new HUD(game.spriteBatch, player);
    }

    public void update(float dt){
        camera.update();

        camera.position.x = player.getB2dBody().getPosition().x;
        camera.position.y = player.getB2dBody().getPosition().y;
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).updateSprite(dt);
        }

        for (int i = 0; i < characters.size(); i++) {
            if (!characters.get(i).isAlive()) {
                characters.remove(i);
            }
        }

        for (int i = 0; i < items.size(); i++) {
            items.get(i).update(dt);
        }

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).isPickedUp()) {
                items.remove(i);
            }
        }

        hud.update(dt);

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
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).draw(game.spriteBatch);
        }

        for (int i = 0; i < items.size(); i++) {
            items.get(i).draw(game.spriteBatch);
        }
        game.spriteBatch.end();

        game.spriteBatch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        stepWorld();
    }
}
