package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.SetupVariables;

public class Goblin extends Character {

    private String direction;

    public Goblin(World world, float xPos, float yPos) {
        super(100, 0.3f, 10, "Sprites/goblinWalk.txt", "Sprites/goblinAttack.txt");

        this.world = world;
        defineBody(xPos, yPos);
        setBounds(0, 0, 32 / SetupVariables.PPM, 32 / SetupVariables.PPM);

        direction = "forward";
    }

    @Override
    public void updateSprite(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3 / SetupVariables.PPM);
        TextureRegion frame = moveGoblin(dt);
        setRegion(frame);
    }

    @Override
    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos / SetupVariables.PPM, yPos / SetupVariables.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(6 / SetupVariables.PPM, 6 / SetupVariables.PPM);

        fixtureDef.shape = polygonShape;
        b2dBody.createFixture(fixtureDef);
    }

    public TextureRegion moveGoblin(float dt) {
        elapsed_time += Gdx.graphics.getDeltaTime();

        if (b2dBody.getLinearVelocity().x > 0) {
            direction = "left";
            region = left.getKeyFrame(elapsed_time, true);
        } else if (b2dBody.getLinearVelocity().x < 0) {
            region = right.getKeyFrame(elapsed_time, true);
            direction = "right";
        } else if (b2dBody.getLinearVelocity().y < 0) {
            region = forward.getKeyFrame(elapsed_time, true);
            direction = "forward";
        } else if (b2dBody.getLinearVelocity().y > 0) {
            region = backward.getKeyFrame(elapsed_time, true);
            direction = "backward";
        } else {
            if (direction.equals("forward")) {
                region = defForward;
            } else if (direction.equals("backward")) {
                region = defBackward;
            } else if (direction.equals("right")) {
                region = defLeft;
            } else {
                region = defRight;
            }
        }
        return region;
    }
}
