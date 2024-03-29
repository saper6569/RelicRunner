/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Creates a Relic item
 */
package com.relicraider.Items;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.SetupVariables;
import com.relicraider.characters.Player;
import com.relicraider.screens.gamescreens.*;

public class Relic extends Item {
    private int roomNumber;

    /**
     * constructor for a relic
     * @param world - physics world
     * @param xPos - the x pos of the potion
     * @param yPos - the y pos of the potion
     * @param roomNumber - the room that the potion is in
     */
    public Relic(World world, String itemName, float xPos, float yPos, int roomNumber) {
        super(itemName, "Sprites/relics.txt", itemName);

        this.world = world;
        this.roomNumber = roomNumber;
        defineBody(xPos, yPos);
        setBounds(0, 0, 20, 20);
    }

    /**
     * method that makes sure that a relic cant be reused
     */
    @Override
    public void itemIsPickedUp() {
        isPickedUp = true;
        Player.setRelicsCollected(Player.getRelicsCollected() + 1);

        //set the room that the relic was from to not recreate a relic
        if (roomNumber == 1) {
            Room1.relicIsFound = true;

        } else if (roomNumber == 2) {
            Room2.relicIsFound = true;

        } else if (roomNumber == 3) {
            Room3.relicIsFound = true;

        } else if (roomNumber == 4) {
            Room4.relicIsFound = true;

        } else if (roomNumber == 5) {
            Room5.relicIsFound = true;

        } else if (roomNumber == 6) {
            Room6.relicIsFound = true;

        }
    }

    /**
     * method used for updating the relic
     * @param dt - time since last render
     */
    @Override
    public void update(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);
        setRegion(image);

        //if the relic is picked up remove it
        if (isPickedUp) {
            removeItem();

        }
    }

    /**
     * method for creating the physics body of the relic
     * @param xPos - X position of character body
     * @param yPos - Y position of character body
     */
    @Override
    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos, yPos);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(8, 8);

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER;
        fixtureDef.filter.categoryBits = SetupVariables.BIT_ITEM;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }
}