package com.relicraider.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.relicraider.SetupVariables;
import com.relicraider.screens.GameScreen1;

public class Goblin extends Sprite implements Character {
    //character attributes
    private int health;
    private float speed = 0.2f;
    private int strength;
    private boolean isAlive;

    public World world;
    public Body b2dBody;

    private float elapsed_time = 0.0f;
    private final static float WALK_FRAME_DURATION = 0.55f;
    private final static float ATTACK_FRAME_DURATION = 0.30f;

    TextureRegion region = null;

    public Goblin(World world, GameScreen1 screen) {
        this.world = world;
        setBounds(0, 0, 32 / SetupVariables.PPM, 32 / SetupVariables.PPM);
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
        return "Goblin{" +
                ", health=" + health +
                ", speed=" + speed +
                ", strength=" + strength +
                ", isAlive=" + isAlive +
                '}';
    }
}
