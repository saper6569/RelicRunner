package com.relicraider.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.SetupVariables;

import java.util.ArrayList;

public class Player extends GameCharacter {
    public static String room;
    private boolean canAttack;
    private ArrayList<GameCharacter> collisions;
    private float attackCooldown;
    private static int relicsCollected;
    private String lastPressed;
    private boolean isBlocking;

    private TextureAtlas blockAtlas;
    private TextureRegion blockForward;
    private TextureRegion blockBackward;
    private TextureRegion blockLeft;
    private TextureRegion blockRight;

    public Player(World world, float xPos, float yPos, int health) {
        super(health, 0.2f, 20, "Sprites/knightWalk.txt", "Sprites/knightAttack.txt");

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

    public Player(World world, float xPos, float yPos) {
        super(100, 0.2f, 10, "Sprites/knightWalk.txt", "Sprites/knightAttack.txt");

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

    public void defineBody(float xPos, float yPos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(xPos, yPos);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2dBody = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(6, 8);

        fixtureDef.shape = polygonShape;
        fixtureDef.filter.categoryBits = SetupVariables.BIT_PLAYER;
        fixtureDef.filter.maskBits = SetupVariables.BIT_ITEM | SetupVariables.BIT_WORLD;
        b2dBody.createFixture(fixtureDef).setUserData(this);
    }

    public void updateSprite(float dt) {
        setPosition(b2dBody.getPosition().x - getWidth() / 2, (b2dBody.getPosition().y - getHeight() / 2) - 3);
        TextureRegion frame = playerMovement(dt);
        setRegion(frame);
    }

    public TextureRegion playerMovement(float dt) {
        elapsed_time += Gdx.graphics.getDeltaTime();
        attackCooldown += dt;
        if (attackCooldown > 1) {
            canAttack = true;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (canAttack) {
                for (GameCharacter character : collisions) {
                    attack(character);
                }
                canAttack = false;
                attackCooldown = 0;
            }
            b2dBody.setLinearVelocity(0, 0);
            System.out.println(b2dBody.getPosition().x + " , " + b2dBody.getPosition().y);

            if (lastPressed.equals("w")) {
                region = backwardAttack.getKeyFrame(elapsed_time, false);
            } else if (lastPressed.equals("s")) {
                region = forwardAttack.getKeyFrame(elapsed_time, false);
            } else if (lastPressed.equals("d")) {
                region = rightAttack.getKeyFrame(elapsed_time, false);
            } else {
                region = leftAttack.getKeyFrame(elapsed_time, false);
            }
        } else if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            isBlocking = true;

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
            if (!Gdx.input.isKeyPressed(Input.Keys.W) && !Gdx.input.isKeyPressed(Input.Keys.A) && !Gdx.input.isKeyPressed(Input.Keys.S) && !Gdx.input.isKeyPressed(Input.Keys.D)){
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

    public void takeDamage(int damage) {
        if (!isBlocking) {
            health -= damage;
        }
    }

    public void attack(GameCharacter character) {
        character.takeDamage(strength);
    }

    public static String getRoom() {
        return room;
    }

    public static void setRoom(String room) {
        Player.room = room;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public String getLastPressed() {
        return lastPressed;
    }

    public void setLastPressed(String lastPressed) {
        this.lastPressed = lastPressed;
    }

    public ArrayList<GameCharacter> getCollisions() {
        return collisions;
    }

    public void removeCollision(int characterID) {
        for (int i = 0; i < collisions.size(); i++) {
            if (collisions.get(i).getCharacterID() == characterID) {
                collisions.remove(i);
                break;
            }
        }
    }

    public void setCollisions(ArrayList<GameCharacter> collisions) {
        this.collisions = collisions;
    }

    public float getAttackCooldown() {
        return attackCooldown;
    }

    public void setAttackCooldown(float attackCooldown) {
        this.attackCooldown = attackCooldown;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    public void setBlocking(boolean blocking) {
        isBlocking = blocking;
    }

    public TextureAtlas getBlockAtlas() {
        return blockAtlas;
    }

    public void setBlockAtlas(TextureAtlas blockAtlas) {
        this.blockAtlas = blockAtlas;
    }

    public TextureRegion getBlockForward() {
        return blockForward;
    }

    public void setBlockForward(TextureRegion blockForward) {
        this.blockForward = blockForward;
    }

    public TextureRegion getBlockBackward() {
        return blockBackward;
    }

    public void setBlockBackward(TextureRegion blockBackward) {
        this.blockBackward = blockBackward;
    }

    public TextureRegion getBlockLeft() {
        return blockLeft;
    }

    public void setBlockLeft(TextureRegion blockLeft) {
        this.blockLeft = blockLeft;
    }

    public TextureRegion getBlockRight() {
        return blockRight;
    }

    public void setBlockRight(TextureRegion blockRight) {
        this.blockRight = blockRight;
    }

    public static int getRelicsCollected() {
        return relicsCollected;
    }

    public static void setRelicsCollected(int relicsCollected) {
        Player.relicsCollected = relicsCollected;
    }
}
