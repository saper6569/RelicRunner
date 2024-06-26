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
    //attributes
    private boolean isStopped;
    private String direction;
    private boolean isAggravated;
    private boolean isAttacking;
    private boolean hasChecked;
    private float timer;

    /**
     * constructor for creating a slime at a desired location
     * @param world - the physics world
     * @param xPos - the x position of the wizard
     * @param yPos - the y position of the wizard
     */
    public Slime(RelicRaider game, World world, float xPos, float yPos, Player player) {
        super(game, 80, 0.15f, 5, "Sprites/wizardWalk.txt", "Sprites/wizardAttack.txt");

        this.world = world;
        defineBody(xPos, yPos);
        b2dBody.setLinearDamping(10f);
        setBounds(0, 0, 32, 32);

        direction = "forward";
        isAggravated = false;
        isStopped = false;
        timer = 0;
        hasChecked = false;
        isAttacking = false;

        Arrive<Vector2> arrive = new Arrive<Vector2>(this, player).setArrivalTolerance(50f).setDecelerationRadius(3);
        this.setBehavior(arrive);
    }

    @Override
    public void updateSprite(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);

        if (behavior != null) {
            behavior.calculateSteering(steeringOutput);
            applySteering(dt);
        }

        TextureRegion frame = getFrame(dt);
        setRegion(frame);
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

        //set the slime to only collide with the world and the player
        fixtureDef.filter.categoryBits = SetupVariables.BIT_WORLD;
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER | SetupVariables.BIT_WORLD;

        //attach the class information to the body
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }

    /**
     * choose a direction for the slime to walk based of a random chance and the direction it was moving in previously
     * @param random - a random number 1-2
     */
    private void findDirection(int random) {
        //set the direction randomly to one of the perpendicular directions to that of the original direction
        if (direction.equals("left")) {
            if (random == 1) {
                direction = "forward";
            } else {
                direction = "backward";
            }
        } else if (direction.equals("right")) {
            if (random == 1) {
                direction = "forward";
            } else {
                direction = "backward";
            }
        } else if (direction.equals("forward")) {
            if (random == 1) {
                direction = "right";
            } else  {
                direction = "left";
            }
        } else if (direction.equals("backward")) {
            if (random == 1) {
                direction = "right";
            } else {
                direction = "left";
            }
        }
    }


    /**
     * method used for getting the frame that needs to be drawn
     * @param dt - time since last render
     * @return the texture region that needs to be drawn next
     */
    public TextureRegion getFrame(float dt) {
        elapsed_time += Gdx.graphics.getDeltaTime();

        //if the slime is attacking find its attack frame using its direction
        if (isAttacking) {
            if (direction.equals("forward")) {
                region = forwardAttack.getKeyFrame(elapsed_time, false);
            } else if (direction.equals("backward")) {
                region = backwardAttack.getKeyFrame(elapsed_time, false);
            } else if (direction.equals("right")) {
                region = rightAttack.getKeyFrame(elapsed_time, false);
            } else {
                region = leftAttack.getKeyFrame(elapsed_time, false);
            }
            //if the slime is moving find its walk frame using its direction
        } else if (b2dBody.getLinearVelocity().x > 0) {
            region = left.getKeyFrame(elapsed_time, true);
        } else if (b2dBody.getLinearVelocity().x < 0) {
            region = right.getKeyFrame(elapsed_time, true);
        } else if (b2dBody.getLinearVelocity().y < 0) {
            region = forward.getKeyFrame(elapsed_time, true);
        } else if (b2dBody.getLinearVelocity().y > 0) {
            region = backward.getKeyFrame(elapsed_time, true);
        }
        //if the slime is staying still find its idle frame using its direction
        else {
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

