package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.SetupVariables;

import java.util.ArrayList;

public class Knight extends Character {
    //character attributes
    private ArrayList<Object> inventory;

    private String lastPressed;

    public Knight(World world, float xPos, float yPos) {
        super(100, 0.2f, 10, "Sprites/knightWalk.txt", "Sprites/knightAttack.txt");

        this.world = world;
        defineBody(xPos, yPos);
        setBounds(0, 0, 32 / SetupVariables.PPM, 32 / SetupVariables.PPM);

        lastPressed = "s";
    }

    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos / SetupVariables.PPM, yPos / SetupVariables.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(6 / SetupVariables.PPM, 10 / SetupVariables.PPM);

        fixtureDef.shape = polygonShape;
        b2dBody.createFixture(fixtureDef);
    }

    public void updateSprite(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3 / SetupVariables.PPM);
        TextureRegion frame = playerMovement(dt);
        setRegion(frame);
    }

    public TextureRegion playerMovement(float dt) {
        elapsed_time += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            b2dBody.setLinearVelocity(0, 0);

            if (lastPressed.equals("w")) {
                region = backwardAttack.getKeyFrame(elapsed_time, false);
            } else if (lastPressed.equals("s")) {
                region = forwardAttack.getKeyFrame(elapsed_time, false);
            } else if (lastPressed.equals("d")) {
                region = rightAttack.getKeyFrame(elapsed_time, false);
            } else {
                region = leftAttack.getKeyFrame(elapsed_time, false);
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                lastPressed = "s";
                b2dBody.setLinearVelocity(0,-speed);
                region = forward.getKeyFrame(elapsed_time, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                lastPressed = "w";
                b2dBody.setLinearVelocity(0,speed);
                region = backward.getKeyFrame(elapsed_time, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                lastPressed = "a";
                b2dBody.setLinearVelocity(-speed,0);
                region = right.getKeyFrame(elapsed_time, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                lastPressed = "d";
                b2dBody.setLinearVelocity(speed,0);
                region = left.getKeyFrame(elapsed_time, true);
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D)){
                b2dBody.setLinearVelocity(0, 0);
                if (lastPressed.equals("w")) {
                    region = defBackward;
                } else if (lastPressed.equals("s")) {
                    region = defForward;
                } else if (lastPressed.equals("d")) {
                    region = defRight;
                } else {
                    region = defLeft;
                }
            }
        }

        return region;
    }
}
