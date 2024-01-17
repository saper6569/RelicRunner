/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Creates a door
 */
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
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER;
        fixtureDef.filter.categoryBits = SetupVariables.BIT_DOOR;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }

    /**
     * getter for the room
     * @return the room that the player will go to
     */
    public String getRoom() {
        return room;
    }

    /**
     * setter for the room
     * @param room - the room that the player will go to
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * getter for the physics world
     * @return - physics world
     */
    public World getWorld() {
        return world;
    }

    /**
     * getter for the x position
     * @return the x postion
     */
    @Override
    public float getX() {
        return x;
    }

    /**
     * getter for the y position
     * @return the y postion
     */
    @Override
    public float getY() {
        return y;
    }

    /**
     * getter for the next x position
     * @return the next x postion
     */
    public float getNextX() {
        return nextX;
    }

    /**
     * getter for the next xy position
     * @return the next y postion
     */
    public float getNextY() {
        return nextY;
    }
}
