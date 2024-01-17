package com.relicraider.screens.utilities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;

public class Door extends Sprite {
    private String room;
    private float x, y, nextX, nextY;
    private World world;
    private Body b2dBody;
    private final RelicRaider game;

    /**
     * constructor for creating a door
     * @param game - game object used to switch screens
     * @param world - the physics world
     * @param room - the room that the door leads to
     * @param x - the x position of the door
     * @param y - the y position of the door
     * @param nextX - the x position that the player will be moved to
     * @param nextY - the y position that the player will be moved to
     */

    public Door(final RelicRaider game, World world, String room, float x, float y, float nextX, float nextY) {
        this.world = world;
        this.game = game;
        this.room = room;
        this.nextX = nextX;
        this.nextY = nextY;

        defineBody(x, y);
    }

    /**
     * method for creating the physics body of the door
     * @param xPos - X position of character body
     * @param yPos - Y position of character body
     */
    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos, yPos);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(16, 2);

        fixtureDef.shape = polygonShape;

        //set the door to only collide with the player
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

    @Override
    public float getX() {
        return x;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    public float getNextX() {
        return nextX;
    }

    public float getNextY() {
        return nextY;
    }
}
