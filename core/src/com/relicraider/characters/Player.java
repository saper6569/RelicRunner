/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Creates a player character
 */
package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;

import java.util.ArrayList;

public class Player extends GameCharacter implements Steerable<Vector2> {
    public static int playerHealth = 100;
    public static String room;
    private boolean canSprint;
    private boolean canAttack;
    private ArrayList<GameCharacter> collisions;
    private float attackCooldown;
    public static int relicsCollected = 0;
    private String lastPressed;
    private boolean isBlocking;

    private TextureAtlas blockAtlas;
    private TextureRegion blockForward;
    private TextureRegion blockBackward;
    private TextureRegion blockLeft;
    private TextureRegion blockRight;

    boolean tagged;
    float maxLinearSpeed, maxLinearAcceleration;
    float maxAngularSpeed, maxAngularAcceleration;
    float radius;

    SteeringBehavior<Vector2> behavior;
    SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());;

    /**
     * 1st constructor for creating a player at a desired location
     * @param world - the physics world
     * @param xPos - the x position of the player
     * @param yPos - the y position of the player
     * @param health - the health of the player
     */
    public Player(RelicRaider game, World world, float xPos, float yPos, int health) {
        super(game, health, 0.2f, 10, "Sprites/knightWalk.txt", "Sprites/knightAttack.txt");
        playerHealth = health;

        this.world = world;
        defineBody(xPos, yPos);
        b2dBody.setLinearDamping(20f);
        setBounds(0, 0, 32, 32);

        lastPressed = "s";
        collisions = new ArrayList<GameCharacter>();
        canAttack = true;
        isBlocking = false;

        blockAtlas = new TextureAtlas("Sprites/knightBlock.txt");
        blockForward = new TextureRegion(blockAtlas.findRegion("forward"));
        blockBackward = new TextureRegion(blockAtlas.findRegion("backward"));
        blockLeft = new TextureRegion(blockAtlas.findRegion("left"));
        blockRight = new TextureRegion(blockAtlas.findRegion("right"));

        this.radius = 10;

        maxLinearSpeed = 500;
        maxLinearAcceleration = 5000;
        maxAngularSpeed = 30;
        maxAngularAcceleration = 5;

        this.tagged = false;
    }

    /**
     * 2snd constructor for creating a player at a desired location
     * @param world - the physics world
     * @param xPos - the x position of the player
     * @param yPos - the y position of the player
     */
    public Player(RelicRaider game, World world, float xPos, float yPos) {
        super(game,100, 0.2f, 10, "Sprites/knightWalk.txt", "Sprites/knightAttack.txt");
        playerHealth = health;

        this.world = world;
        defineBody(xPos, yPos);
        b2dBody.setLinearDamping(20f);
        setBounds(0, 0, 32, 32);

        lastPressed = "s";
        relicsCollected = 0;
        collisions = new ArrayList<GameCharacter>();
        canAttack = true;
        isBlocking = false;

        blockAtlas = new TextureAtlas("Sprites/knightBlock.txt");
        blockForward = new TextureRegion(blockAtlas.findRegion("forward"));
        blockBackward = new TextureRegion(blockAtlas.findRegion("backward"));
        blockLeft = new TextureRegion(blockAtlas.findRegion("left"));
        blockRight = new TextureRegion(blockAtlas.findRegion("right"));

        this.radius = 10;

        maxLinearSpeed = 500;
        maxLinearAcceleration = 5000;
        maxAngularSpeed = 30;
        maxAngularAcceleration = 5;

        this.tagged = false;
    }

    /**
     * method for creating the physics body of the player
     * @param xPos - X position of character body
     * @param yPos - Y position of character body
     */
    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos, yPos);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(7, 8);

        fixtureDef.shape = polygonShape;

        //set the player to only collide with the world, items and te doors
        fixtureDef.filter.categoryBits = SetupVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = SetupVariables.BIT_ITEM | SetupVariables.BIT_WORLD | SetupVariables.BIT_DOOR | SetupVariables.BIT_FIREBALL;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }

    /**
     * method for updating the player
     * @param dt - time since last render
     */
    public void updateSprite(float dt) {
        playerHealth = health;
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);
        TextureRegion frame = playerMovement(dt);
        setRegion(frame);
    }

    /**
     * method for taking input from the user and moving the player
     * @param dt - time since last render
     * @return the texture region that needs to be drawn next
     */
    public TextureRegion playerMovement(float dt) {
        elapsed_time += Gdx.graphics.getDeltaTime();
        attackCooldown += dt;

        if (attackCooldown > 0.5) {
            canAttack = true;
        }

        //when the user presses the left button attack any characters that are in contact every 1 second
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            System.out.println(b2dBody.getPosition().x + ", " + b2dBody.getPosition().y);
            RelicRaider.soundPlayer.getFootsteps().pause();
            isBlocking = false;
            if (canAttack) {
                RelicRaider.soundPlayer.getSwordSwoosh().play();
                for (GameCharacter character : collisions) {
                    attack(character);
                }
                canAttack = false;
                attackCooldown = 0;
            }
            b2dBody.setLinearVelocity(0, 0);

            //set the attack frame based on the last direction the player moved in
            if (lastPressed.equals("w")) {
                region = backwardAttack.getKeyFrame(elapsed_time, false);
            } else if (lastPressed.equals("s")) {
                region = forwardAttack.getKeyFrame(elapsed_time, false);
            } else if (lastPressed.equals("d")) {
                region = rightAttack.getKeyFrame(elapsed_time, false);
            } else {
                region = leftAttack.getKeyFrame(elapsed_time, false);
            }
            //if the right button is pressed set the layer to block attacks
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            isBlocking = true;

            RelicRaider.soundPlayer.getFootsteps().pause();

            //set the block frame based on the last direction the player moved in
            if (lastPressed.equals("w")) {
                region = blockBackward;
            } else if (lastPressed.equals("s")) {
                region = blockForward;
            } else if (lastPressed.equals("d")) {
                region = blockRight;
            } else {
                region = blockLeft;
            }
        } else {
            isBlocking = false;

            RelicRaider.soundPlayer.getFootsteps().play();

            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                //set the walk frame based on the last direction the player moved in
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    lastPressed = "s";
                    b2dBody.setLinearVelocity(0,-1.5f * speed);
                    region = forward.getKeyFrame(elapsed_time, true);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    lastPressed = "w";
                    b2dBody.setLinearVelocity(0,1.5f * speed);
                    region = backward.getKeyFrame(elapsed_time, true);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    lastPressed = "a";
                    b2dBody.setLinearVelocity(-1.5f * speed,0);
                    region = right.getKeyFrame(elapsed_time, true);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    lastPressed = "d";
                    b2dBody.setLinearVelocity(1.5f * speed,0);
                    region = left.getKeyFrame(elapsed_time, true);
                }
            } else {
                //set the walk frame based on the last direction the player moved in
                if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    lastPressed = "s";
                    b2dBody.setLinearVelocity(0,-speed);
                    region = forward.getKeyFrame(elapsed_time, true);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    lastPressed = "w";
                    b2dBody.setLinearVelocity(0,speed);
                    region = backward.getKeyFrame(elapsed_time, true);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    lastPressed = "a";
                    b2dBody.setLinearVelocity(-speed,0);
                    region = right.getKeyFrame(elapsed_time, true);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    lastPressed = "d";
                    b2dBody.setLinearVelocity(speed,0);
                    region = left.getKeyFrame(elapsed_time, true);
                }
            }


            //set the idle frame based on the last direction the player moved in
            if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D)){
                RelicRaider.soundPlayer.getFootsteps().pause();
                b2dBody.setLinearVelocity(0, 0);
                if (lastPressed.equals("w")) {
                    region = defBackward;
                } else if (lastPressed.equals("s")) {
                    region = defForward;
                } else if (lastPressed.equals("d")) {
                    region = defRight;
                } else {
                    region = defLeft;
                }
            }
        }

        return region;
    }

    /**
     * method used for taking damage and checking if the player is alive
     * @param damage- The amount of health the character should lose
     */
    public void takeDamage(int damage) {
        //dont take damage when the player is blocking
        if (!isBlocking) {
            health -= damage;
        }

        //check if the player is alive
        if (health <= 0) {
            RelicRaider.soundPlayer.getFootsteps().stop();
            isAlive = false;
        }
    }

    /**
     * Method to have a character get attacked
     * @param character - The character that is getting hurt
     */
    public void attack(GameCharacter character) {
        character.takeDamage(strength); //Take away characters health by the amount of strength the character hitting them has
    }

    /**
     * Method to get what room player is in
     * @return - The room the player is currently in
     */
    public static String getRoom() {
        return room;
    }

    /**
     * Method to set what room player is in
     * @param room - The room player is in is set to
     */
    public static void setRoom(String room) {
        Player.room = room; //Players room = room parameter
    }

    /**
     * Method to get collisions of player
     * @return - The arraylist of collisions of the player
     */
    public ArrayList<GameCharacter> getCollisions() {
        return collisions;
    }

    /**
     * Method to remove a collision with the player
     * @param characterID - Player's ID
     */
    public void removeCollision(int characterID) {
        //Until I is greater than collisions arraylist
        for (int i = 0; i < collisions.size(); i++) {
            if (collisions.get(i).getCharacterID() == characterID) { //If the element in the collision arraylist id equals to character id
                collisions.remove(i); //Remove collision
                break;
            }
        }
    }


    /**
     * Method to get number of relics collected by player
     * @return - Number of relics collected by the player
     */
    public static int getRelicsCollected() {
        return relicsCollected;
    }

    /**
     * Method to set number of relics collected by player
     * @param relicsCollected - New Number of relics collected by the player
     */
    public static void setRelicsCollected(int relicsCollected) {
        RelicRaider.soundPlayer.getCollectRelic().play();
        Player.relicsCollected = relicsCollected;
    }

    public static void resetRelicsCollected () {
        Player.relicsCollected = 0;
    }

    public void update(float dt) {
        if (behavior != null) {
            behavior.calculateSteering(steeringOutput);
            applySteering(dt);
        }
    }

    public void applySteering(float dt) {
        boolean anyAccelerations = false;

        if (!steeringOutput.linear.isZero()) {
            b2dBody.applyForceToCenter(steeringOutput.linear, true);
            anyAccelerations = true;
        }

        if (anyAccelerations) {
            // body.activate();
            Vector2 velocity = b2dBody.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            float maxLinearSpeed = getMaxLinearSpeed();
            if (currentSpeedSquare > maxLinearSpeed * maxLinearSpeed) {
                b2dBody.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }
        }
    }

    @Override
    public Vector2 getLinearVelocity() {
        return b2dBody.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return b2dBody.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return radius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxAngularAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return b2dBody.getPosition();
    }

    @Override
    public float getOrientation() {
        return b2dBody.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }
}
