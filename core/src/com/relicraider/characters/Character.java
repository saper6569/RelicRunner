package com.relicraider.characters;

public interface Character {

    public int getHealth();

    public void setHealth(int health);

    public float getSpeed();

    public void setSpeed(float speed);

    public int getStrength();

    public void setStrength(int strength);

    public boolean isAlive();

    public void setAlive(boolean alive);

    public void dealDamage(int damage);
    public String toString();
}
