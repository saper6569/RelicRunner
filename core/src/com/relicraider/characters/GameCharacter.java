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
    private final static float WALK_FRAME_DURATION = 0.30f;
    private final static float ATTACK_FRAME_DURATION = 0.50f;

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
    protected Animation<TextureRegion> forwardAttack;
    protected Animation<TextureRegion> backwardAttack;
    protected Animation<TextureRegion> leftAttack;
    protected Animation<TextureRegion> rightAttack;

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

        //Set Animations for each direction of a character walking
        forward = new Animation<TextureRegion>(WALK_FRAME_DURATION, forwardFrames, Animation.PlayMode.LOOP);
        backward = new Animation<TextureRegion>(WALK_FRAME_DURATION, backwardFrames, Animation.PlayMode.LOOP);
        left = new Animation<TextureRegion>(WALK_FRAME_DURATION, rightFrames, Animation.PlayMode.LOOP);
        right = new Animation<TextureRegion>(WALK_FRAME_DURATION, leftFrames, Animation.PlayMode.LOOP);

        //Send all attack frames to an arraylist
        Array<TextureAtlas.AtlasRegion> forwardAttackFrames = attackAtlas.findRegions("forward");
        Array<TextureAtlas.AtlasRegion> backwardAttackFrames = attackAtlas.findRegions("backward");
        Array<TextureAtlas.AtlasRegion> rightAttackFrames = attackAtlas.findRegions("right");
        Array<TextureAtlas.AtlasRegion> leftAttackFrames = attackAtlas.findRegions("left");

        //Set Animations for each direction of a character attacking
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
        return Math.sqrt(Math.pow(sprite1.getX() - sprite2.getX(), 2) + Math.pow(sprite1.getY() - sprite2.getY(), 2));
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
     * Method to Get Speed of the Character
     * @return - The Speed of the Character
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Method to Set Speed of the Character
     * @param speed - The New Speed of the Character
     */
    public void setSpeed(float speed) {
        this.speed = speed; //The Speed of the Character = New Set Speed
    }

    /**
     * Method to Get Strength of the Character
     * @return - Current Strength of the Character
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Method to Set Strength of the Character
     * @param strength - New Strength of the Character
     */
    public void setStrength(int strength) {
        this.strength = strength; //The Strength of the Character = New Set Strength
    }

    /**
     * Method to see if character is dead or alive
     * @return - True if character is still alive, False if the character is dead
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Method to set if character is dead or alive
     * @param alive - True if character is set to be alive, False if character is set to be dead
     */
    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Method to get elapsed time of character life
     * @return - Elapsed Time The Character has been alive
     */
    public float getElapsed_time() {
        return elapsed_time;
    }

    /**
     * Method to get world character is currently in
     * @return - What world character is currently in
     */
    public World getWorld() {
        return world;
    }

    /**
     * Method to get total number of character
     * @return - Total number of characters
     */
    public static int getCharacterCounter() {
        return characterCounter;
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
