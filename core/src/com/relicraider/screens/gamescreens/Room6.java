/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 6
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room6 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;

    /**
     * constructor for creating room6
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public Room6(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room6.tmx", 4, playerX, playerY);

        Player.room = "Room6";

        //add all the actor to the game screen

        characters.add(new Goblin(world, 76, 180));
        characters.add(new Goblin(world, 256, 180));
        characters.add(new Goblin(world, 364, 94));
        characters.add(new Goblin(world, 400, 190));
        characters.add(new Goblin(world, 84, 355));
        characters.add(new Goblin(world, 84, 356));
        characters.add(new Goblin(world, 208, 356));
        characters.add(new Goblin(world, 308, 420));
        characters.add(new Goblin(world, 430, 312));
        characters.add(new Goblin(world, 230, 356));
        characters.add(new Goblin(world, 288, 428));
        characters.add(new Goblin(world, 52, 54));

        //if the player has already picked up a relic or healing potion don't draw it again
        if (!relicIsFound) {
            items.add(new Relic(world, "heart in a jar", 256, 396, 6));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 90, 300, 6));
        }

        doors.add(new Door(game, world, "room 1", 128, 18, 70, 406));
        doors.add(new Door(game, world, "room 4", 368, 50, 175, 426));
    }
}


