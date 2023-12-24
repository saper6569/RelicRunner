package com.relicraider.characters;

public class CharacterTEMP {
    protected int health;
    protected float speed;
    protected int strength;
    protected boolean isAlive;
    protected double xPos;
    protected double yPos;

    public static int numCharacters;

    public CharacterTEMP() {
        health = 100;
        speed = 0.5f;
        strength = 10;
        isAlive = true;
        xPos = 0;
        yPos = 0;
    }

    public CharacterTEMP(int health, float speed, int strength) {
        this.health = health;
        this.speed = speed;
        this.strength = strength;
        isAlive = true;
        xPos = 0;
        yPos = 0;
    }

    public void moveX(double x) {
        xPos += x;
    }

    public void moveY(double y) {
        yPos += y;
    }

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

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public void dealDamage(int damage) {
        health -= damage;
    }

    @Override
    public String toString() {
        return "CharacterTEMP{" +
                "health=" + health +
                ", speed=" + speed +
                ", strength=" + strength +
                ", isAlive=" + isAlive +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }
}
