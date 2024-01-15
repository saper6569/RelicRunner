package com.relicraider.screens.utilities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.screens.gamescreens.*;

public class Door extends Sprite {
    private String room;
    private World world;
    private Body b2dBody;
    private final RelicRaider game;

    public Door(final RelicRaider game, String room, float x, float y) {
        this.game = game;
        this.room = room;

        defineBody(x, y);
    }

    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos, yPos);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(2, 32);

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER;
        fixtureDef.filter.categoryBits = SetupVariables.BIT_DOOR;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }

    private void playerIsEntering() {
        if (room.equals("room 1")) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new Room1(game));
        } else if (room.equals("room 2")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room2(game));
        } else if (room.equals("room 3")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room3(game));
        } else if (room.equals("room 4")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room4(game));
        } else if (room.equals("room 5")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room5(game));
        } else if (room.equals("room 6")) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Room6(game));
        }
    }
}
