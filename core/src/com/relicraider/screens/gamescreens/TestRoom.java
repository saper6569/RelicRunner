package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.characters.Wizard;
import com.relicraider.screens.utilities.Door;

public class TestRoom extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;

    /**
     * constructor for creating a test room
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public TestRoom(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/testRoom.tmx", 4, playerX, playerY);

        Player.room = "TEST";

        //add all the actor to the game screen
        characters.add(new Wizard(game, world, 110, 150));
    }
}
