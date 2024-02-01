/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Creates a player character
 */
package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.RelicRaider;
import com.relicraider.SetupVariables;

import java.util.ArrayList;

public class Player extends GameCharacter {
    public static int playerHealth = 100;
    public static String room;
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

    /**
     * 1st constructor for creating a player at a desired location
     * @param world - the physics world
     * @param xPos - the x position of the player
     * @param yPos - the y position of the player
     * @param health - the health of the player
     */
    public Player(RelicRaider game, World world, float xPos, float yPos, int health) {
        super(game, health, 0.2f, 20, "Sprites/knightWalk.txt", "Sprites/knightAttack.txt");
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
    }

    /**
     * 2snd constructor for creating a player at a desired location
     * @param world - the physics world
     * @param xPos - the x position of the player
     * @param yPos - the y position of the player
     */
    public Player(RelicRaider game, World world, float xPos, float yPos) {
        super(game,100, 0.2f, 5, "Sprites/knightWalk.txt", "Sprites/knightAttack.txt");
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
        polygonShape.setAsBox(6, 8);

        fixtureDef.shape = polygonShape;

        //set the player to only collide with the world, items and te doors
        fixtureDef.filter.categoryBits = SetupVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = SetupVariables.BIT_ITEM | SetupVariables.BIT_WORLD | SetupVariables.BIT_DOOR;
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
     * Method to see if player can attack
     * @return - True - if play can attack, False - if player cant attack
     */
    public boolean isCanAttack() {
        return canAttack;
    }

    /**
     * Method to set if a player can attack
     * @param canAttack - True - if player is set to be able to attack, False -  if player is set to not be able to attack
     */
    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack; //Set if player can attack to parameter
    }

    /**
     * Method to get last pressed input
     * @return - The last pressed input
     */
    public String getLastPressed() {
        return lastPressed;
    }

    /**
     * Method to set last pressed input
     * @param lastPressed - The input that lastPressed is to be set to
     */
    public void setLastPressed(String lastPressed) {
        this.lastPressed = lastPressed;
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
     * Method to set collisions with the player
     * @param collisions - ArrayList containing collision id's
     */
    public void setCollisions(ArrayList<GameCharacter> collisions) {
        this.collisions = collisions;
    }

    /**
     * Method to get time of attack cooldown
     * @return - Time of attack cooldown
     */
    public float getAttackCooldown() {
        return attackCooldown;
    }

    /**
     * Method to set time of attack cooldown
     * @param attackCooldown - New set time of attack cooldown
     */
    public void setAttackCooldown(float attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    /**
     * Method to see if player is currently blocking
     * @return - True - if player is blocking, False -  if player isnt blocking
     */
    public boolean isBlocking() {
        return isBlocking;
    }

    /**
     * Method to set if player is blocking
     * @param blocking - True - if set player to be blocking, False - if set player to not be blocking
     */
    public void setBlocking(boolean blocking) {
        isBlocking = blocking;
    }

    /**
     * Method to get Block Atlas of player
     * @return - The Block Atlas of the player
     */
    public TextureAtlas getBlockAtlas() {
        return blockAtlas;
    }

    /**
     * Method to set Block Atlas of player
     * @param blockAtlas - The new Block Atlas of the player
     */
    public void setBlockAtlas(TextureAtlas blockAtlas) {
        this.blockAtlas = blockAtlas;
    }

    /**
     * Method to get texture region of player's forward block
     * @return - Texture Region of players forward block
     */
    public TextureRegion getBlockForward() {
        return blockForward;
    }

    /**
     * Method to set texture region of player's forward block
     * @param blockForward - New Texture region of player's forward block
     */
    public void setBlockForward(TextureRegion blockForward) {
        this.blockForward = blockForward;
    }

    /**
     * Method to get texture region of player's backward block
     * @return - Texture Region of players backward block
     */
    public TextureRegion getBlockBackward() {
        return blockBackward;
    }

    /**
     * Method to set texture region of player's backward block
     * @param blockBackward - New Texture region of player's backward block
     */
    public void setBlockBackward(TextureRegion blockBackward) {
        this.blockBackward = blockBackward;
    }

    /**
     * Method to get texture region of player's leftwards block
     * @return - Texture Region of players leftwards block
     */
    public TextureRegion getBlockLeft() {
        return blockLeft;
    }

    /**
     * Method to set texture region of player's leftwards block
     * @param blockLeft - New Texture region of player's leftwards block
     */
    public void setBlockLeft(TextureRegion blockLeft) {
        this.blockLeft = blockLeft;
    }

    /**
     * Method to get texture region of player's rightwards block
     * @return - Texture Region of players rightwards block
     */
    public TextureRegion getBlockRight() {
        return blockRight;
    }

    /**
     * Method to set texture region of player's rightwards block
     * @param blockRight - New Texture region of player's rightwards block
     */
    public void setBlockRight(TextureRegion blockRight) {
        this.blockRight = blockRight;
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

    /**
     * Method to get health of player
     * @return - Health of player
     */
    public static int getPlayerHealth() {
        return playerHealth;
    }

    /**
     * Method to set health of player
     * @param playerHealth - New Health of player
     */
    public static void setPlayerHealth(int playerHealth) {
        Player.playerHealth = playerHealth;
    }
}
