package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.screens.gamescreens.AbstractGameScreen;

public class Slime extends Pathfinding {

    /**
     * constructor for creating a slime at a desired location
     * @param world - the physics world
     * @param xPos - the x position of the wizard
     * @param yPos - the y position of the wizard
     */
    public Slime(RelicRaider game, World world, float xPos, float yPos, Player player) {
        super(game, 80, 0.15f, 5, "Sprites/slime.txt", 0.1f);

        this.world = world;
        defineBody(xPos, yPos);
        b2dBody.setLinearDamping(10f);
        setBounds(0, 0, 32, 32);

        Arrive<Vector2> arrive = new Arrive<Vector2>(this, player).setArrivalTolerance(20f).setDecelerationRadius(1);
        this.setBehavior(arrive);
    }

    @Override
    public void updateSprite(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) + 8);

        if (behavior != null) {
            behavior.calculateSteering(steeringOutput);
            applySteering(dt);
        }

        elapsed_time += Gdx.graphics.getDeltaTime();
        setRegion(forward.getKeyFrame(elapsed_time, false));
    }

    @Override
    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos, yPos);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(4, 8);

        fixtureDef.shape = polygonShape;

        //set the slime to not collide with anything
        fixtureDef.filter.categoryBits = SetupVariables.BIT_WORLD;
        fixtureDef.filter.maskBits = 36;

        //attach the class information to the body
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }
}

