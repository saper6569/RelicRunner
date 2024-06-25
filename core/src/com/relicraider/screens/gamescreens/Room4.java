/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 4
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room4 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;

    /**
     * constructor for creating room4
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public Room4(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room4.tmx", 4, playerX, playerY);

        Player.room = "Room4";

        //add all the actor to the game screen

        //if the player has already picked up a relic or healing potion don't draw it again
        if (!relicIsFound) {
            items.add(new Relic(world, "book 1", 42, 288, 4));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 90, 300, 4));
        }

        characters.add(new Goblin(game, world, 390, 316, player));
        characters.add(new Goblin(game, world, 202, 336, player));
        characters.add(new Goblin(game, world, 58, 292, player));
        characters.add(new Goblin(game, world, 183, 256, player));
        characters.add(new Goblin(game, world, 198, 260, player));

        doors.add(new Door(game, world, "room 3", 336, 274, 368, 456));
        doors.add(new Door(game, world, "room 6", 176, 434, 368, 50));
        doors.add(new Door(game, world, "room 1", 96, 226, 272, 168));
    }
}


