package com.relicraider.Items;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.SetupVariables;
import com.relicraider.characters.Player;

public class Relic extends Item {
    public Relic(World world, String itemName, float xPos, float yPos) {
        super(itemName, "Sprites/relics.txt", itemName, xPos, yPos);

        this.world = world;
        defineBody(xPos, yPos);
        setBounds(0, 0, 16, 16);
    }

    @Override
    public void itemIsPickedUp() {
        isPickedUp = true;
        Player.setRelicsCollected(Player.getRelicsCollected() + 1);
    }

    @Override
    public void update(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);
        setRegion(image);
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
