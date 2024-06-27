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
    public static Goblin[] goblins = new Goblin[5];
    public static boolean hasBeenLoaded = false;

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

        if (!hasBeenLoaded) {
            goblins[0] = new Goblin(game, world, 390, 316, player);
            goblins[1] = new Goblin(game, world, 202, 336, player);
            goblins[2] = new Goblin(game, world, 58, 292, player);
            goblins[3] = new Goblin(game, world, 183, 256, player);
            goblins[4] = new Goblin(game, world, 198, 260, player);
            hasBeenLoaded = true;
        } else {
            for (int i = 0; i < goblins.length; i++) {
                goblins[i] = new Goblin(game, world, goblins[i].getB2dBody().getPosition().x, goblins[i].getB2dBody().getPosition().y, player, goblins[i].getHealth());
            }
        }

        //add all the actor to the game screen
        characters.clear();
        for (int i = 0; i < goblins.length; i++) {
            characters.add(goblins[i]);
        }
        characters.add(player);

        doors.add(new Door(game, world, "room 3", 336, 274, 368, 456));
        doors.add(new Door(game, world, "room 6", 176, 434, 368, 50));
        doors.add(new Door(game, world, "room 1", 96, 226, 272, 168));
    }
}


