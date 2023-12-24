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
    private float speed;
    private int strength;
    private boolean isAlive;
    private double xPos;
    private double yPos;

    private float elapsed_time = 0.0f;
    private final static float FRAME_DURATION = 0.55f;

    TextureRegion region = null;
    private final TextureRegion def;
    private final TextureAtlas textureAtlas;
    private final Animation<TextureRegion> forward;
    private final Animation<TextureRegion> backward;
    private final Animation<TextureRegion> left;
    private final Animation<TextureRegion> right;

    public World world;
    public Body b2dBody;

    public Knight(World world, GameScreen1 screen) {
        this.world = world;
        definePlayer();

        textureAtlas = new TextureAtlas("Sprites/playerSprite.txt");

        def = new TextureRegion(textureAtlas.findRegion("default"));
        setBounds(0, 0, 32 / SetupVariables.PPM, 32 / SetupVariables.PPM);
        setRegion(def);

        Array<TextureAtlas.AtlasRegion> forwardFrames = textureAtlas.findRegions("backward");
        Array<TextureAtlas.AtlasRegion> backwardFrames = textureAtlas.findRegions("forward");
        Array<TextureAtlas.AtlasRegion> rightFrames = textureAtlas.findRegions("right");
        Array<TextureAtlas.AtlasRegion> leftFrames = textureAtlas.findRegions("left");

        forward = new Animation<TextureRegion>(FRAME_DURATION, forwardFrames, Animation.PlayMode.LOOP);
        backward = new Animation<TextureRegion>(FRAME_DURATION, backwardFrames, Animation.PlayMode.LOOP);
        left = new Animation<TextureRegion>(FRAME_DURATION, rightFrames, Animation.PlayMode.LOOP);
        right = new Animation<TextureRegion>(FRAME_DURATION, leftFrames, Animation.PlayMode.LOOP);
    }

    public void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(871 / SetupVariables.PPM, 1415 / SetupVariables.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(8 / SetupVariables.PPM, 12 / SetupVariables.PPM);

        fixtureDef.shape = polygonShape;
        b2dBody.createFixture(fixtureDef);
    }

    public void updateSprite(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, b2dBody.getPosition().y - getHeight() / 2);
        TextureRegion frame = playerMovement(dt);
        if (frame != null) {
            setRegion(frame);
        }
    }

    public TextureRegion playerMovement(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            b2dBody.setLinearVelocity(0,-speed);
            elapsed_time += Gdx.graphics.getDeltaTime();
            region = forward.getKeyFrame(elapsed_time, true);
            moveY(-1);

        }
        else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            b2dBody.setLinearVelocity(0,speed);
            elapsed_time += Gdx.graphics.getDeltaTime();
            region = backward.getKeyFrame(elapsed_time, true);
            moveY(1);

        }
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            b2dBody.setLinearVelocity(-speed,0);
            elapsed_time += Gdx.graphics.getDeltaTime();
            region = right.getKeyFrame(elapsed_time, true);
            moveX(-1);

        }
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            b2dBody.setLinearVelocity(speed,0);
            elapsed_time += Gdx.graphics.getDeltaTime();
            region = left.getKeyFrame(elapsed_time, true);
            moveX(-1);
        }
        else{
            b2dBody.setLinearVelocity(0, 0);
            elapsed_time += Gdx.graphics.getDeltaTime();
            region = def;
        }
        return region;
    }

    @Override
    public void moveX(double x) {
        xPos += x;
    }

    @Override
    public void moveY(double y) {
        yPos += y;
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
    public double getXPos() {
        return xPos;
    }

    @Override
    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    @Override
    public double getYPos() {
        return yPos;
    }

    @Override
    public void setYPos(double yPos) {
        this.yPos = yPos;
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
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
