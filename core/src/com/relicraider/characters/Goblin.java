package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.SetupVariables;
import com.relicraider.screens.AbstractGameScreen;

public class Goblin extends GameCharacter {
    //attributes
    private boolean isStopped;
    private boolean isCollided;
    private String direction;
    private boolean isAggravated;
    private boolean isAttacking;
    private boolean hasChecked;
    private float timer;

    public Goblin(World world, float xPos, float yPos, int health) {
        super(health, 0.15f, 10, "Sprites/goblinWalk.txt", "Sprites/goblinAttack.txt");

        this.world = world;
        defineBody(xPos, yPos);
        b2dBody.setLinearDamping(10f);
        setBounds(0, 0, 32, 32);

        direction = "forward";
        isAggravated = false;
        isStopped = false;
        isAttacking = false;
        timer = 0;
        hasChecked = false;
    }

    public Goblin(World world, float xPos, float yPos) {
        super(40, 0.15f, 5, "Sprites/goblinWalk.txt", "Sprites/goblinAttack.txt");

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
    }

    @Override
    public void updateSprite(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);
        if (getDistance(this, AbstractGameScreen.player) < 60) {
            isAggravated = true;
        } else {
            isAggravated = false;
        }
        checkHealth();
        if (!isAggravated) {
            setRandomVelocity(dt);
        } else {
            if (isAttacking) {
                attack(dt);
            } else {
                walkToPlayer();
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
        fixtureDef.filter.categoryBits = SetupVariables.BIT_WORLD;
        fixtureDef.filter.maskBits = SetupVariables.BIT_PLAYER | SetupVariables.BIT_WORLD;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }

    public void setRandomVelocity(float dt) {
        int random = (int) (Math.random() * 3 + 1);

        timer += dt;
        if (timer > 5f && !hasChecked) {
            hasChecked = true;
            if (random == 1) {
                isStopped = true;
            }
        }

        if (timer > 8f) {
            isStopped = false;
            hasChecked = false;
            timer = 0f;
        }

        random = (int) (Math.random() * 2 + 1);

        if (b2dBody.getLinearVelocity().isZero() && !isStopped) {
            findDirection(random);
        }

        else if (!b2dBody.getLinearVelocity().isZero() && !isStopped) {
            if (((int) (Math.random() * 100 + 1)) == 1) {
                findDirection(random);
            }
        }
        if (!isStopped) {
            setVelocity();
        }
    }

    private void findDirection(int random) {
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

    public void walkToPlayer () {
        if (AbstractGameScreen.player.b2dBody.getPosition().x - 2 > b2dBody.getPosition().x) {
            direction = "right";
            setVelocity();
        } else if (AbstractGameScreen.player.b2dBody.getPosition().x < b2dBody.getPosition().x) {
            direction = "left";
            setVelocity();
        }

        if (AbstractGameScreen.player.b2dBody.getPosition().y - 2 > b2dBody.getPosition().y) {
            direction = "backward";
            setVelocity();
        } else if (AbstractGameScreen.player.b2dBody.getPosition().y < b2dBody.getPosition().y) {
            direction = "forward";
            setVelocity();
        }

        String originalDirection = direction;

        if (isCollided && (originalDirection.equals("forward") || originalDirection.equals("backward"))) {
            if (AbstractGameScreen.player.b2dBody.getPosition().x - 2 > b2dBody.getPosition().x) {
                direction = "right";
                setVelocity();
            } else if (AbstractGameScreen.player.b2dBody.getPosition().x < b2dBody.getPosition().x) {
                direction = "left";
                setVelocity();
            }
        } else if (isCollided && (originalDirection.equals("left") || originalDirection.equals("right"))) {
            if (AbstractGameScreen.player.b2dBody.getPosition().y - 2 > b2dBody.getPosition().y) {
                direction = "backward";
                setVelocity();
            } else if (AbstractGameScreen.player.b2dBody.getPosition().y < b2dBody.getPosition().y) {
                direction = "forward";
                setVelocity();
            }
        }
    }

    public void attack(float dt) {
        timer += dt;
        if (!hasChecked) {
            AbstractGameScreen.player.takeDamage(strength);
            hasChecked = true;
        }

        if (timer > 1) {
            hasChecked = false;
            timer = 0f;
        }
    }

    public TextureRegion getFrame(float dt) {
        elapsed_time += Gdx.graphics.getDeltaTime();

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
        } else if (b2dBody.getLinearVelocity().x > 0) {
            region = left.getKeyFrame(elapsed_time, true);
        } else if (b2dBody.getLinearVelocity().x < 0) {
            region = right.getKeyFrame(elapsed_time, true);
        } else if (b2dBody.getLinearVelocity().y < 0) {
            region = forward.getKeyFrame(elapsed_time, true);
        } else if (b2dBody.getLinearVelocity().y > 0) {
            region = backward.getKeyFrame(elapsed_time, true);
        }
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

    public boolean hasStopped() {
        return isStopped;
    }

    public void setIsStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }

    public String getDirection() {
        return direction;
    }

    public void setRandomVelocity(String direction) {
        this.direction = direction;
    }

    public boolean isAggravated() {
        return isAggravated;
    }

    public void setAggravated(boolean aggravated) {
        isAggravated = aggravated;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public boolean isCollided() {
        return isCollided;
    }

    public void setCollided(boolean collided) {
        isCollided = collided;
    }
}
