/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Game Character Class
 */

package com.relicraider.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;

//Game Character Class is an abstract class of sprite class, the sprite class is included in the libgdx build
public abstract class GameCharacter extends Sprite {
    //Attributes of a Game Character
    private static int characterCounter = 0;
    private final int characterID;
    protected int health;
    protected float speed;
    protected int strength;
    protected boolean isAlive;

    //Counters and Constants for Animations
    protected float elapsed_time;
    protected float walkFrameDuration = 0.30f;
    protected float attackFrameDuration = 0.50f;

    //Texture objects for storing frames
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
    protected Animation<TextureRegion> forwardRight;
    protected Animation<TextureRegion> forwardLeft;
    protected Animation<TextureRegion> backwardRight;
    protected Animation<TextureRegion> backwardLeft;
    protected Animation<TextureRegion> forwardAttack;
    protected Animation<TextureRegion> backwardAttack;
    protected Animation<TextureRegion> leftAttack;
    protected Animation<TextureRegion> rightAttack;
    protected Animation<TextureRegion> forwardRightAttack;
    protected Animation<TextureRegion> backwardRightAttack;
    protected Animation<TextureRegion> forwardLeftAttack;
    protected Animation<TextureRegion> backwardLeftAttack;

    //Physics objects
    protected World world;
    protected Body b2dBody;
    protected RelicRaider game;

    /**
     * Primary Constructor for a character with attack and walk animations
     * @param health - health of the character
     * @param speed - speed of the character
     * @param strength - amount of damage dealt per blow
     * @param walkAtlasFile - file location of the information for the walk animation
     * @param attackAtlasFile - file location of the information for the attack animations
     */
    public GameCharacter(RelicRaider game, int health, float speed, int strength, String walkAtlasFile, String attackAtlasFile) {
        this.game = game;
        characterCounter++; //Add one to the counter for number of characters
        characterID = characterCounter; //The ID of the character is the current counter number
        this.health = health;
        this.speed = speed * SetupVariables.PPM; //Characters speed is speed multiplied by 100
        this.strength = strength;
        isAlive = true;

        elapsed_time = 0.0f;
        region = null;

        //Set all the textures using the given texture atlas
        walkAtlas = new TextureAtlas(walkAtlasFile);
        attackAtlas = new TextureAtlas(attackAtlasFile);

        defForward = new TextureRegion(walkAtlas.findRegion("defaultForward"));
        defBackward = new TextureRegion(walkAtlas.findRegion("defaultBackward"));
        defLeft = new TextureRegion(walkAtlas.findRegion("defaultLeft"));
        defRight = new TextureRegion(walkAtlas.findRegion("defaultRight"));
        setRegion(defForward); //Characters starting walkAtlas is forward

        //Send all walking frames to an arraylist
        Array<TextureAtlas.AtlasRegion> forwardFrames = walkAtlas.findRegions("forward");
        Array<TextureAtlas.AtlasRegion> backwardFrames = walkAtlas.findRegions("backward");
        Array<TextureAtlas.AtlasRegion> rightFrames = walkAtlas.findRegions("right");
        Array<TextureAtlas.AtlasRegion> leftFrames = walkAtlas.findRegions("left");
        Array<TextureAtlas.AtlasRegion> forwardRightFrames = walkAtlas.findRegions("forwardRight");
        Array<TextureAtlas.AtlasRegion> forwardLeftFrames = walkAtlas.findRegions("forwardLeft");
        Array<TextureAtlas.AtlasRegion> backwardRightFrames = walkAtlas.findRegions("backwardRight");
        Array<TextureAtlas.AtlasRegion> backwardLeftFrames = walkAtlas.findRegions("backwardLeft");

        //Set Animations for each direction of a character walking
        forward = new Animation<TextureRegion>(walkFrameDuration, forwardFrames, Animation.PlayMode.LOOP);
        backward = new Animation<TextureRegion>(walkFrameDuration, backwardFrames, Animation.PlayMode.LOOP);
        left = new Animation<TextureRegion>(walkFrameDuration, rightFrames, Animation.PlayMode.LOOP);
        right = new Animation<TextureRegion>(walkFrameDuration, leftFrames, Animation.PlayMode.LOOP);
        forwardRight = new Animation<TextureRegion>(walkFrameDuration, forwardRightFrames, Animation.PlayMode.LOOP);
        forwardLeft = new Animation<TextureRegion>(walkFrameDuration, forwardLeftFrames, Animation.PlayMode.LOOP);
        backwardRight = new Animation<TextureRegion>(walkFrameDuration, backwardRightFrames, Animation.PlayMode.LOOP);
        backwardLeft = new Animation<TextureRegion>(walkFrameDuration, backwardLeftFrames, Animation.PlayMode.LOOP);

        //Send all attack frames to an arraylist
        Array<TextureAtlas.AtlasRegion> forwardAttackFrames = attackAtlas.findRegions("forward");
        Array<TextureAtlas.AtlasRegion> backwardAttackFrames = attackAtlas.findRegions("backward");
        Array<TextureAtlas.AtlasRegion> rightAttackFrames = attackAtlas.findRegions("right");
        Array<TextureAtlas.AtlasRegion> leftAttackFrames = attackAtlas.findRegions("left");
        Array<TextureAtlas.AtlasRegion> forwardLeftAttackFrames = attackAtlas.findRegions("forwardLeft");
        Array<TextureAtlas.AtlasRegion> backwardLeftAttackFrames = attackAtlas.findRegions("backwardLeft");
        Array<TextureAtlas.AtlasRegion> forwardRightAttackFrames = attackAtlas.findRegions("forwardRight");
        Array<TextureAtlas.AtlasRegion> backwardRightAttackFrames = attackAtlas.findRegions("backwardRight");

        //Set Animations for each direction of a character attacking
        forwardAttack = new Animation<TextureRegion>(attackFrameDuration, forwardAttackFrames, Animation.PlayMode.LOOP);
        backwardAttack = new Animation<TextureRegion>(attackFrameDuration, backwardAttackFrames, Animation.PlayMode.LOOP);
        leftAttack = new Animation<TextureRegion>(attackFrameDuration, leftAttackFrames, Animation.PlayMode.LOOP);
        rightAttack = new Animation<TextureRegion>(attackFrameDuration, rightAttackFrames, Animation.PlayMode.LOOP);
        forwardRightAttack = new Animation<TextureRegion>(attackFrameDuration, forwardRightAttackFrames, Animation.PlayMode.LOOP);
        backwardRightAttack = new Animation<TextureRegion>(attackFrameDuration, backwardRightAttackFrames, Animation.PlayMode.LOOP);
        forwardLeftAttack = new Animation<TextureRegion>(attackFrameDuration, forwardLeftAttackFrames, Animation.PlayMode.LOOP);
        backwardLeftAttack = new Animation<TextureRegion>(attackFrameDuration, backwardLeftAttackFrames, Animation.PlayMode.LOOP);
    }

    /**
     * Primary Constructor for a character with walk animations
     * @param health - health of the character
     * @param speed - speed of the character
     * @param strength - amount of damage dealt per blow
     * @param walkAtlasFile - file location of the information for the walk animation
     */
    public GameCharacter(RelicRaider game, int health, float speed, int strength, String walkAtlasFile, float frameDuration) {
        this.game = game;
        characterCounter++; //Add one to the counter for number of characters
        characterID = characterCounter; //The ID of the character is the current counter number
        this.health = health;
        this.speed = speed * SetupVariables.PPM; //Characters speed is speed multiplied by 100
        this.strength = strength;
        isAlive = true;

        elapsed_time = 0.0f;
        region = null;

        //Set all the textures using the given texture atlas
        walkAtlas = new TextureAtlas(walkAtlasFile);

        //Send all walking frames to an arraylist
        Array<TextureAtlas.AtlasRegion> forwardFrames = walkAtlas.findRegions("forward");

        //Set Animations for each direction of a character walking
        forward = new Animation<TextureRegion>(frameDuration, forwardFrames, Animation.PlayMode.LOOP);

        setRegion(forward.getKeyFrame(0, true));
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

    public void resetCharacter () {
        b2dBody.setLinearVelocity(0, 0);
        setRegion(defForward);
        elapsed_time = 0.0f;
        region = null;
    }

    public float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y) * (float)(180/Math.PI);
    }

    /**
     * method for getting the distance between 2 characters (used for pathfinding and agro)
     * @param sprite1
     * @param sprite2
     * @return the distance between the 2 sprites
     */
    public double getDistance(Sprite sprite1, Sprite sprite2) {
        return Math.sqrt(Math.pow(sprite1.getX() - sprite2.getX(), 2) + Math.pow(sprite1.getY() - sprite2.getY(), 2));
    }

    public double getDistanceX(Sprite sprite1, Sprite sprite2) {
        return Math.abs(sprite1.getX() - sprite2.getX());
    }

    public double getDistanceY(Sprite sprite1, Sprite sprite2) {
        return Math.abs(sprite1.getY() - sprite2.getY());
    }

    /**
     * Method for taking away health from character after it takes damage
     * @param damage- The amount of health the character should lose
     */
    public void takeDamage(int damage) {
        health -= damage; //The new health of the character is it's health take away the amount of damage taken
    }

    /**
     * Method to update sprite using libgdx build
     * @param dt - Time at which to update sprites
     */
    public abstract void updateSprite(float dt);

    /**
     * Method to state x and y position of character
     * @param xPos - X position of character body
     * @param yPos - Y position of character body
     */
    public abstract void defineBody(float xPos, float yPos);

    /**
     * Method to Get Health of the Character
     * @return - Health of the Character
     */
    public int getHealth() {
        return health;
    }

    /**
     * Method to Set Health of the Character
     * @param health - New health of the character
     */
    public void setHealth(int health) {
        RelicRaider.soundPlayer.getCollectRelic().play();
        this.health = health; //Health of character = New set Health
    }

    /**
     * Method to see if character is dead or alive
     * @return - True if character is still alive, False if the character is dead
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Method to get world character is currently in
     * @return - What world character is currently in
     */
    public World getWorld() {
        return world;
    }

    /**
     * Method to get character's ID
     * @return - The ID of the character
     */
    public int getCharacterID() {
        return characterID;
    }

    /**
     * Method to get Box 2D Body of Character, Box 2D is part of LibGDX build
     * @return - Box 2D body of the character
     */
    public Body getB2dBody() {
        return b2dBody;
    }

    /**
     * Method to send characters attributes to a string
     * @return - String containing characters attributes
     */
    public String toString() {
        return "GameCharacter{" +
                ", health=" + health +
                ", speed=" + speed +
                ", strength=" + strength +
                ", isAlive=" + isAlive +
                '}';
    }
}
