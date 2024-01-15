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

    public Door(final RelicRaider game, World world, String room, float x, float y) {
        this.world = world;
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
        polygonShape.setAsBox(16, 2);

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER;
        fixtureDef.filter.categoryBits = SetupVariables.BIT_DOOR;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getB2dBody() {
        return b2dBody;
    }

    public void setB2dBody(Body b2dBody) {
        this.b2dBody = b2dBody;
    }
}
