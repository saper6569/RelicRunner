package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.screens.GameScreen1;
import com.relicraider.SetupVariables;

import java.util.ArrayList;

public class Knight extends Character {
    //character attributes
    private ArrayList<Object> inventory;
    private float elapsed_time = 0.0f;

    public World world;
    public Body b2dBody;

    private String lastPressed;

    public Knight(World world, GameScreen1 screen) {
        super(100, 0.2f, 10, "Sprites/knightWalk.txt", "Sprites/knightAttack.txt");

        this.world = world;
        defineBody();
        setBounds(0, 0, 32 / SetupVariables.PPM, 32 / SetupVariables.PPM);

        lastPressed = "s";
    }

    public void defineBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(200 / SetupVariables.PPM, 300 / SetupVariables.PPM);
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
        if (frame != null) {
            setRegion(frame);
        }
    }

    public TextureRegion playerMovement(float dt) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            b2dBody.setLinearVelocity(0, 0);
            elapsed_time += Gdx.graphics.getDeltaTime();

            if (lastPressed.equals("w")) {
                region = forwardAttack.getKeyFrame(elapsed_time, false);
            } else if (lastPressed.equals("s")) {
                region = backwardAttack.getKeyFrame(elapsed_time, false);
            } else if (lastPressed.equals("d")) {
                region = rightAttack.getKeyFrame(elapsed_time, false);
            } else {
                region = leftAttack.getKeyFrame(elapsed_time, false);
            }
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                lastPressed = "s";
                b2dBody.setLinearVelocity(0,-speed);
                elapsed_time += Gdx.graphics.getDeltaTime();
                region = backward.getKeyFrame(elapsed_time, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                lastPressed = "w";
                b2dBody.setLinearVelocity(0,speed);
                elapsed_time += Gdx.graphics.getDeltaTime();
                region = forward.getKeyFrame(elapsed_time, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                lastPressed = "a";
                b2dBody.setLinearVelocity(-speed,0);
                elapsed_time += Gdx.graphics.getDeltaTime();
                region = right.getKeyFrame(elapsed_time, true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                lastPressed = "d";
                b2dBody.setLinearVelocity(speed,0);
                elapsed_time += Gdx.graphics.getDeltaTime();
                region = left.getKeyFrame(elapsed_time, true);
            }
            if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D)){
                b2dBody.setLinearVelocity(0, 0);
                elapsed_time += Gdx.graphics.getDeltaTime();
                if (lastPressed.equals("w")) {
                    region = defForward;
                } else if (lastPressed.equals("s")) {
                    region = defBackward;
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
