package com.relicraider.Items;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.SetupVariables;
import com.relicraider.screens.gamescreens.*;

public class HealingPotion extends Item {
    private int roomNumber;

    public HealingPotion(World world, float xPos, float yPos, int roomNumber) {
        super("Healing Potion", "Sprites/healPotion.txt", xPos, yPos);

        this.world = world;
        this.roomNumber = roomNumber;
        defineBody(xPos, yPos);
        setBounds(0, 0, 16, 16);
    }

    @Override
    public void itemIsPickedUp() {
        isPickedUp = true;
        AbstractGameScreen.player.setHealth(AbstractGameScreen.player.getHealth() + 20);

        if (roomNumber == 1) {
            Room1.potionIsUsed = true;

        } else if (roomNumber == 2) {
            Room2.potionIsUsed = true;

        } else if (roomNumber == 3) {
            Room3.potionIsUsed = true;

        } else if (roomNumber == 4) {
            Room4.potionIsUsed = true;

        } else if (roomNumber == 5) {
            Room5.potionIsUsed = true;

        } else if (roomNumber == 6) {
            Room6.potionIsUsed = true;

        }
    }

    @Override
    public void update(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);
        setRegion(getFrame(dt));
        if (isPickedUp) {
            removeItem();
        }
    }

    @Override
    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos, yPos);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(4, 4);

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER;
        fixtureDef.filter.categoryBits = SetupVariables.BIT_ITEM;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }
}
