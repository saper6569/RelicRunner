package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.Items.FireBall;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;
import com.relicraider.screens.gamescreens.AbstractGameScreen;

public class Wizard extends Pathfinding {
    //attributes
    private boolean isStopped;
    private String direction;
    private boolean isAggravated;
    private boolean isAttacking;
    private AbstractGameScreen room;
    private boolean hasChecked;
    private float timer;
    private Player player;

    /**
     * constructor for creating a wizard at a desired location
     * @param world - the physics world
     * @param xPos - the x position of the wizard
     * @param yPos - the y position of the wizard
     */
    public Wizard(RelicRaider game, World world, float xPos, float yPos, Player player, AbstractGameScreen room) {
        super(game, 150, 0.2f, 5, "Sprites/wizardWalk.txt", "Sprites/wizardAttack.txt");

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
        this.room = room;
        this.player = player;

        maxLinearSpeed = 35;
        maxLinearAcceleration = 500;

        Arrive<Vector2> arrive = new Arrive<Vector2>(this, player).setArrivalTolerance(50f).setDecelerationRadius(20);
        this.setBehavior(arrive);
    }

    @Override
    public void updateSprite(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);

        //if the wizard is within a radius of 60 of the player set it to be aggravated
        if (getDistance(this, AbstractGameScreen.player) < 80) {
            isAggravated = true;
        } else {
            isAggravated = false;
        }
        checkHealth();

        //if the wizard is not aggravated walk randomly
        if (!isAggravated) {
            isAttacking = false;
            setRandomVelocity(dt);
            //if the wizard is aggravated either set it to walk to the player or attack the player
        } else {
            if (b2dBody.getLinearVelocity().isZero() && isAggravated) {
                isAttacking = true;
                attack(dt);
            } else {
                isAttacking = false;
                if (behavior != null) {
                    behavior.calculateSteering(steeringOutput);
                    applySteering(dt);
                }
            }
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
        polygonShape.setAsBox(4, 6);

        fixtureDef.shape = polygonShape;

        //set the wizard to only collide with the world and the player
        fixtureDef.filter.categoryBits = SetupVariables.BIT_WORLD;
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER | SetupVariables.BIT_WORLD;

        //attach the class information to the body
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }

    public void setRandomVelocity(float dt) {
        int random = (int) (Math.random() * 3 + 1);

        timer += dt;
        //every 5 seconds if the random number is 1 set the wizard to stop and wait
        if (timer > 5f && !hasChecked) {
            hasChecked = true;
            if (random == 1) {
                isStopped = true;
            }
        }

        //reset the timer and other variables after 8 seconds
        if (timer > 8f) {
            isStopped = false;
            hasChecked = false;
            timer = 0f;
        }

        random = (int) (Math.random() * 2 + 1);

        //if the wizard is not stopped but the velocity is 0 find its new direction
        if (b2dBody.getLinearVelocity().isZero() && !isStopped) {
            findDirection(random);
        }

        //if the wizard is stopped there is a 1/100 chance that the wizard will turn on its own
        else if (!b2dBody.getLinearVelocity().isZero() && !isStopped) {
            if (((int) (Math.random() * 100 + 1)) == 1) {
                findDirection(random);
            }
        }

        //if the wizard should be moving set its velocity
        if (!isStopped) {
            setVelocity();
        }
    }

    /**
     * choose a direction for the wizard to walk based of a random chance and the direction it was moving in previously
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
     * set the velocity corresponding to th direction of the player
     */
    private void setVelocity() {
        if (direction.equals("right")) {
            b2dBody.setLinearVelocity(speed,0);
        } else if (direction.equals("forward")) {
            b2dBody.setLinearVelocity(0,-speed);
        } else if (direction.equals("left")) {
            b2dBody.setLinearVelocity(-speed,0);
        } else {
            b2dBody.setLinearVelocity(0,speed);
        }
    }

    /**
     * method used for when the wizard is attacking the player
     * @param dt - time since last render
     */
    public void attack(float dt) {
        timer += dt;
        //attack the player once every 2 second
        if (timer > 2) {
            hasChecked = false;
            timer = 0f;
        }
        if (!hasChecked) {
            int random = ((int) (Math.random() * 6));
            if (random == 1) {
                summonGoblin();
            } else if (random == 2 || random == 3) {
                summonFireBall();
                timer = 1.5f;
            } else {
                summonFireBall();
            }
            RelicRaider.soundPlayer.getFireBall().play();

            hasChecked = true;
        }
    }

    public void summonGoblin() {
        room.addCharacter(new Goblin(game, world, b2dBody.getPosition().x + 5, b2dBody.getPosition().y + 5, player));
    }

    public void summonFireBall() {
        room.addItem(new FireBall(world, b2dBody.getPosition().x, b2dBody.getPosition().y + 3));
    }

    /**
     * method used for getting the fram that needs to be drawn
     * @param dt - time since last render
     * @return the texture region that needs to be drawn next
     */
    public TextureRegion getFrame(float dt) {
        elapsed_time += Gdx.graphics.getDeltaTime();

        //if the wizard is attacking find its attack frame using its direction
        if (isAttacking) {
            float angle = (float)Math.atan2(-(b2dBody.getPosition().x - AbstractGameScreen.player.getPosition().x), b2dBody.getPosition().y - AbstractGameScreen.player.getPosition().y) * (float)(180/Math.PI);
            if (-5 <= angle && angle <= 5) {
                region = forwardAttack.getKeyFrame(elapsed_time, false);
            } else if (85 <= angle && angle <= 95) {
                region = rightAttack.getKeyFrame(elapsed_time, false);
            } else if (-95 <= angle && angle <= -85) {
                region = leftAttack.getKeyFrame(elapsed_time, false);
            } else if (-5 > angle && angle > -85) {
                region = forwardLeftAttack.getKeyFrame(elapsed_time, false);
            } else if (-95 > angle && angle > -175) {
                region = backwardLeftAttack.getKeyFrame(elapsed_time, false);
            } else if (95 < angle && angle < 175) {
                region = backwardRightAttack.getKeyFrame(elapsed_time, false);
            } else if (5 < angle && angle < 85) {
                region = forwardRightAttack.getKeyFrame(elapsed_time, false);
            } else {
                region = backwardAttack.getKeyFrame(elapsed_time, false);
            }
        }

        //if the wizard is staying still find its idle frame using its direction
        else if (b2dBody.getLinearVelocity().isZero()) {
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

        else if (-5 <= vectorToAngle(b2dBody.getLinearVelocity()) && vectorToAngle(b2dBody.getLinearVelocity()) <= 5) {
            region = backward.getKeyFrame(elapsed_time, true);
        } else if (85 <= vectorToAngle(b2dBody.getLinearVelocity()) && vectorToAngle(b2dBody.getLinearVelocity()) <= 95) {
            region = right.getKeyFrame(elapsed_time, true);
        } else if (-95 <= vectorToAngle(b2dBody.getLinearVelocity()) && vectorToAngle(b2dBody.getLinearVelocity()) <= -85) {
            region = left.getKeyFrame(elapsed_time, true);
        } else if (-5 > vectorToAngle(b2dBody.getLinearVelocity()) && vectorToAngle(b2dBody.getLinearVelocity()) > -85) {
            region = backwardRight.getKeyFrame(elapsed_time, true);
        } else if (-95 > vectorToAngle(b2dBody.getLinearVelocity()) && vectorToAngle(b2dBody.getLinearVelocity()) > -175) {
            region = forwardRight.getKeyFrame(elapsed_time, true);
        } else if (95 < vectorToAngle(b2dBody.getLinearVelocity()) && vectorToAngle(b2dBody.getLinearVelocity()) < 175) {
            region = forwardLeft.getKeyFrame(elapsed_time, true);
        } else if (5 < vectorToAngle(b2dBody.getLinearVelocity()) && vectorToAngle(b2dBody.getLinearVelocity()) < 85) {
            region = backwardLeft.getKeyFrame(elapsed_time, true);
        } else{
            region = forward.getKeyFrame(elapsed_time, true);
        }

        return region;
    }
}
