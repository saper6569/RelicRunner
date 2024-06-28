package com.relicraider.Items;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.SetupVariables;
import com.relicraider.characters.GameCharacter;
import com.relicraider.screens.gamescreens.AbstractGameScreen;

import javax.swing.*;

public class FireBall extends Item {
    private float angle;
    public FireBall(World world, float xPos, float yPos) {
        super("Sprites/fireball.txt", "00", true);
        this.world = world;
        defineBody(xPos, yPos);
        setBounds(0, 0, 16, 16);
        angle = (float) Math.atan((yPos - AbstractGameScreen.player.getPosition().y) / (xPos - AbstractGameScreen.player.getB2dBody().getPosition().x) * (float)(180/Math.PI));
        this.rotate(-angle);
        float speed = 3f;
        b2dBody.setLinearVelocity((float)-(Math.sqrt(speed) * (xPos - AbstractGameScreen.player.getPosition().x))/speed, (float)-(Math.sqrt(speed) * (yPos - AbstractGameScreen.player.getPosition().y))/speed);
    }

    @Override
    public void itemIsPickedUp() {
        isPickedUp = true;
        AbstractGameScreen.player.takeDamage(15);
    }

    public void collide(GameCharacter character) {
        isPickedUp = true;
        character.takeDamage(20);
    }

    public void collide() {
        isPickedUp = true;
    }

    @Override
    public void update(float dt) {
        //if the fireball has collided remove it
        if (isPickedUp) {
            removeItem();
        }
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);
        setRegion(getFrame(dt));
    }

    @Override
    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos, yPos);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(4, 4);

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = SetupVariables.BIT_FIREBALL;
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER | SetupVariables.BIT_WORLD;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }
}
