/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 1
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room1 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;

    /**
     * constructor for creating room1
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public Room1(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room1.tmx", 4, playerX, playerY);

        Player.room = "Room1";

        //add all the actor to the game screen
        characters.add(new Goblin(game, world, 100, 300));
        characters.add(new Goblin(game, world, 220, 300));
        characters.add(new Goblin(game, world, 270, 130));
        characters.add(new Goblin(game, world, 360, 80));

        //if the player has already picked up a relic or healing potion don't draw it again
        if (!relicIsFound) {
            items.add(new Relic(world, "ring 6", 360, 105, 1));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 90, 300, 1));
        }

        doors.add(new Door(game, world, "room 6", 80, 418, 120, 30));
        doors.add(new Door(game, world, "room 2", 96, 18, 214, 456));
        doors.add(new Door(game, world, "room 4", 272, 178, 86, 246));
        doors.add(new Door(game, world, "room 3", 352, 50, 182, 423));
    }
}
