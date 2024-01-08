package com.relicraider.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.relicraider.SetupVariables;

public abstract class GameCharacter extends Sprite {
    //attributes
    private static int characterCounter = 0;
    private final int characterID;
    protected int health;
    protected float speed;
    protected int strength;
    protected boolean isAlive;

    //counters and constants for animations
    protected float elapsed_time;
    private final static float WALK_FRAME_DURATION = 0.30f;
    private final static float ATTACK_FRAME_DURATION = 0.30f;

    //texture objects for storing frames
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

    //physics objects
    protected World world;
    protected Body b2dBody;

    /**
     * constructor for character with attack and walk animations
     * @param health - health of the character
     * @param speed - speed of the character
     * @param strength - amount of damage dealt per blow
     * @param walkAtlasFile - file location of the information for the walk animation
     * @param attackAtlasFile - file location of the information for the attack animations
     */
    public GameCharacter(int health, float speed, int strength, String walkAtlasFile, String attackAtlasFile) {
        characterCounter++;
        characterID = characterCounter;
        this.health = health;
        this.speed = speed;
        this.strength = strength;
        isAlive = true;

        elapsed_time = 0.0f;
        region = null;

        //set all the textures using the given texture atlas'
        walkAtlas = new TextureAtlas(walkAtlasFile);
        attackAtlas = new TextureAtlas(attackAtlasFile);

        defForward = new TextureRegion(walkAtlas.findRegion("defaultForward"));
        defBackward = new TextureRegion(walkAtlas.findRegion("defaultBackward"));
        defLeft = new TextureRegion(walkAtlas.findRegion("defaultLeft"));
        defRight = new TextureRegion(walkAtlas.findRegion("defaultRight"));
        setRegion(defForward);

        Array<TextureAtlas.AtlasRegion> forwardFrames = walkAtlas.findRegions("forward");
        Array<TextureAtlas.AtlasRegion> backwardFrames = walkAtlas.findRegions("backward");
        Array<TextureAtlas.AtlasRegion> rightFrames = walkAtlas.findRegions("right");
        Array<TextureAtlas.AtlasRegion> leftFrames = walkAtlas.findRegions("left");

        forward = new Animation<TextureRegion>(WALK_FRAME_DURATION, forwardFrames, Animation.PlayMode.LOOP);
        backward = new Animation<TextureRegion>(WALK_FRAME_DURATION, backwardFrames, Animation.PlayMode.LOOP);
        left = new Animation<TextureRegion>(WALK_FRAME_DURATION, rightFrames, Animation.PlayMode.LOOP);
        right = new Animation<TextureRegion>(WALK_FRAME_DURATION, leftFrames, Animation.PlayMode.LOOP);

        Array<TextureAtlas.AtlasRegion> forwardAttackFrames = attackAtlas.findRegions("forward");
        Array<TextureAtlas.AtlasRegion> backwardAttackFrames = attackAtlas.findRegions("backward");
        Array<TextureAtlas.AtlasRegion> rightAttackFrames = attackAtlas.findRegions("right");
        Array<TextureAtlas.AtlasRegion> leftAttackFrames = attackAtlas.findRegions("left");

        forwardAttack = new Animation<TextureRegion>(ATTACK_FRAME_DURATION, forwardAttackFrames, Animation.PlayMode.LOOP);
        backwardAttack = new Animation<TextureRegion>(ATTACK_FRAME_DURATION, backwardAttackFrames, Animation.PlayMode.LOOP);
        leftAttack = new Animation<TextureRegion>(ATTACK_FRAME_DURATION, leftAttackFrames, Animation.PlayMode.LOOP);
        rightAttack = new Animation<TextureRegion>(ATTACK_FRAME_DURATION, rightAttackFrames, Animation.PlayMode.LOOP);
    }

    /**
     * method for checking whether the character is alive or not
     */
    public void checkHealth() {
        //if the health is less than or equal to 0 kill the character
        if (health <= 0) {
            isAlive = false;
            kill();
        }
    }

    /**
     * method for removing the character after iut is killed
     */
    public void kill() {
        //remove the character's physics object and its textures
        world.destroyBody(b2dBody);
        getTexture().dispose();
    }

    /**
     * method for getting the distance between 2 characters (used for pathfinding and agro)
     * @param sprite1
     * @param sprite2
     * @return
     */
    public double getDistance(Sprite sprite1, Sprite sprite2) {
        return Math.sqrt(Math.pow(sprite1.getX() - sprite2.getX(), 2) + Math.pow(sprite1.getY() - sprite2.getY(), 2)) * SetupVariables.PPM;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public abstract void updateSprite(float dt);

    public abstract void defineBody(float xPos, float yPos);

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

    public float getElapsed_time() {
        return elapsed_time;
    }

    public World getWorld() {
        return world;
    }

    public static int getCharacterCounter() {
        return characterCounter;
    }

    public int getCharacterID() {
        return characterID;
    }

    public Body getB2dBody() {
        return b2dBody;
    }

    public String toString() {
        return "GameCharacter{" +
                ", health=" + health +
                ", speed=" + speed +
                ", strength=" + strength +
                ", isAlive=" + isAlive +
                '}';
    }
}
