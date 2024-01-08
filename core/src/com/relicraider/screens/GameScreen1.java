package com.relicraider.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.Items.HealingPotion;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;

public class GameScreen1 extends AbstractGameScreen {
    private final HUD hud;

    public GameScreen1 (RelicRaider game) {
        super(game, "GameScreen1/map.tmx", 4);

        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA.getUserData() instanceof HealingPotion) {
                    ((HealingPotion) fixtureA.getUserData()).itemIsPickedUp();
                } else if (fixtureB.getUserData() instanceof HealingPotion) {
                    ((HealingPotion) fixtureB.getUserData()).itemIsPickedUp();
                }

                if (fixtureA.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureA.getUserData()).setCollided(true);
                } else if (fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setCollided(true);
                }

                if (fixtureA.getUserData() instanceof Goblin && fixtureB.getUserData() instanceof Player) {
                    ((Goblin) fixtureA.getUserData()).setAttacking(true);
                    AbstractGameScreen.player.getCollisons().add((Goblin) fixtureA.getUserData());
                } else if (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setAttacking(true);
                    AbstractGameScreen.player.getCollisons().add((Goblin) fixtureB.getUserData());
                }
            }

            @Override
            public void endContact(Contact contact) {
                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                if (fixtureA.getUserData() instanceof Goblin && fixtureB.getUserData() instanceof Player) {
                    ((Goblin) fixtureA.getUserData()).setAttacking(false);
                    AbstractGameScreen.player.removeCollision(((Goblin) fixtureA.getUserData()).getCharacterID());
                } else if (fixtureA.getUserData() instanceof Player && fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setAttacking(false);
                    AbstractGameScreen.player.removeCollision(((Goblin) fixtureB.getUserData()).getCharacterID());
                }

                if (fixtureA.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureA.getUserData()).setCollided(false);
                } else if (fixtureB.getUserData() instanceof Goblin) {
                    ((Goblin) fixtureB.getUserData()).setCollided(false);
                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }

            private boolean fixtureIsObstacle(Fixture fixture) {
                if (!(fixture.getUserData() instanceof Player || fixture.getUserData() instanceof Goblin)) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        Player.room = "GameScreen1";

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
