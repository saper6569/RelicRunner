package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.relicraider.screens.GameScreen1;
import com.relicraider.SetupVariables;

import java.util.ArrayList;

public class Knight extends Sprite implements Character {
    //character attributes
    private ArrayList<Object> inventory;
    private int health;
    private float speed = 0.2f;
    private int strength;
    private boolean isAlive;

    private float elapsed_time = 0.0f;
    private final static float WALK_FRAME_DURATION = 0.55f;
    private final static float ATTACK_FRAME_DURATION = 0.30f;

    TextureRegion region = null;
    private final TextureRegion defForward;
    private final TextureRegion defBackward;
    private final TextureRegion defLeft;
    private final TextureRegion defRight;
    private final TextureAtlas walkAtlas;
    private final TextureAtlas attackAtlas;
    private final Animation<TextureRegion> forward;
    private final Animation<TextureRegion> backward;
    private final Animation<TextureRegion> left;
    private final Animation<TextureRegion> right;
    private final Animation<TextureRegion> forwardAttack;
    private final Animation<TextureRegion> backwardAttack;
    private final Animation<TextureRegion> leftAttack;
    private final Animation<TextureRegion> rightAttack;

    public World world;
    public Body b2dBody;

    private String lastPressed;

    public Knight(World world, GameScreen1 screen) {
        inventory = new ArrayList<>();
        health = 100;
        speed = 0.2f;
        strength = 10;
        isAlive = true;

        this.world = world;
        definePlayer();
        setBounds(0, 0, 32 / SetupVariables.PPM, 32 / SetupVariables.PPM);

        walkAtlas = new TextureAtlas("Sprites/knightWalk.txt");
        attackAtlas = new TextureAtlas("Sprites/knightAttack.txt");

        defForward = new TextureRegion(walkAtlas.findRegion("defaultBackward"));
        defBackward = new TextureRegion(walkAtlas.findRegion("defaultForward"));
        defLeft = new TextureRegion(walkAtlas.findRegion("defaultLeft"));
        defRight = new TextureRegion(walkAtlas.findRegion("defaultRight"));
        setRegion(defBackward);

        Array<TextureAtlas.AtlasRegion> forwardFrames = walkAtlas.findRegions("backward");
        Array<TextureAtlas.AtlasRegion> backwardFrames = walkAtlas.findRegions("forward");
        Array<TextureAtlas.AtlasRegion> rightFrames = walkAtlas.findRegions("right");
        Array<TextureAtlas.AtlasRegion> leftFrames = walkAtlas.findRegions("left");

        forward = new Animation<TextureRegion>(WALK_FRAME_DURATION, forwardFrames, Animation.PlayMode.LOOP);
        backward = new Animation<TextureRegion>(WALK_FRAME_DURATION, backwardFrames, Animation.PlayMode.LOOP);
        left = new Animation<TextureRegion>(WALK_FRAME_DURATION, rightFrames, Animation.PlayMode.LOOP);
        right = new Animation<TextureRegion>(WALK_FRAME_DURATION, leftFrames, Animation.PlayMode.LOOP);

        Array<TextureAtlas.AtlasRegion> forwardAttackFrames = attackAtlas.findRegions("backward");
        Array<TextureAtlas.AtlasRegion> backwardAttackFrames = attackAtlas.findRegions("forward");
        Array<TextureAtlas.AtlasRegion> rightAttackFrames = attackAtlas.findRegions("right");
        Array<TextureAtlas.AtlasRegion> leftAttackFrames = attackAtlas.findRegions("left");

        forwardAttack = new Animation<TextureRegion>(ATTACK_FRAME_DURATION, forwardAttackFrames, Animation.PlayMode.LOOP);
        backwardAttack = new Animation<TextureRegion>(ATTACK_FRAME_DURATION, backwardAttackFrames, Animation.PlayMode.LOOP);
        leftAttack = new Animation<TextureRegion>(ATTACK_FRAME_DURATION, leftAttackFrames, Animation.PlayMode.LOOP);
        rightAttack = new Animation<TextureRegion>(ATTACK_FRAME_DURATION, rightAttackFrames, Animation.PlayMode.LOOP);

        lastPressed = "s";
    }

    public void definePlayer() {
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

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = strength;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public void dealDamage(int damage) {
        health -= damage;
    }

    @Override
    public String toString() {
        return "Knight{" +
                "inventory=" + inventory +
                ", health=" + health +
                ", speed=" + speed +
                ", strength=" + strength +
                ", isAlive=" + isAlive +
                '}';
    }
}
