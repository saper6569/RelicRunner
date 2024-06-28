/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Setup Variables Class
 */
package com.relicraider;

import com.badlogic.gdx.ApplicationAdapter;

public class SetupVariables extends ApplicationAdapter {
    //VARIABLES FOR SCREEN
    public static int WIDTH = 768;
    public static int HEIGHT = 514;
    public static float PPM = 100;

    //VARIABLES FOR COLLISIONS
    public static final short BIT_PLAYER = 1;
    public static final short BIT_ITEM = 2;
    public static final short BIT_WORLD = 4;
    public static final short BIT_DOOR = 16;
    public static final short BIT_FIREBALL = 32;
}
