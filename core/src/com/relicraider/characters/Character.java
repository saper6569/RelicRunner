package com.relicraider.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

abstract class Character extends Sprite {
    protected int health;
    protected float speed;
    protected int strength;
    protected boolean isAlive;

    protected float elapsed_time;
    private final static float WALK_FRAME_DURATION = 0.55f;
    private final static float ATTACK_FRAME_DURATION = 0.30f;

    protected TextureRegion region;
    protected TextureAtlas walkAtlas;
    protected TextureAtlas attackAtlas;
    
    protected TextureRegion defForward;
    protected TextureRegion defBackward;
    protected TextureRegion defLeft;
    protected TextureRegion defRight;
    protected Animation<TextureRegion> forward;
    protected Animation<TextureRegion> backward;
    protected Animation<TextureRegion> left;
    protected Animation<TextureRegion> right;
    protected Animation<TextureRegion> forwardAttack;
    protected Animation<TextureRegion> backwardAttack;
    protected Animation<TextureRegion> leftAttack;
    protected Animation<TextureRegion> rightAttack;

    public Character(int health, float speed, int strength) {
        this.health = 100;
        this.speed = 0.2f;
        this.strength = 10;
        isAlive = true;
    }

    public Character(int health, float speed, int strength, String walkAtlasFile, String attackAtlasFile) {
        this.health = health;
        this.speed = speed;
        this.strength = strength;
        isAlive = true;

        elapsed_time = 0.0f;
        region = null;

        walkAtlas = new TextureAtlas(walkAtlasFile);
        attackAtlas = new TextureAtlas(attackAtlasFile);

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
    }

    public abstract void updateSprite(float dt);

    public abstract void defineBody();

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void dealDamage(int damage) {
        health -= damage;
    }

    public String toString() {
        return "Knight{" +
                ", health=" + health +
                ", speed=" + speed +
                ", strength=" + strength +
                ", isAlive=" + isAlive +
                '}';
    }
}
