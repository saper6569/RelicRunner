/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 3
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room3 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;
    public static Goblin[] goblins = new Goblin[7];
    public static boolean hasBeenLoaded = false;

    /**
     * constructor for creating room3
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public Room3(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room3.tmx", 4, playerX, playerY);

        Player.room = "Room3";

        if (!hasBeenLoaded) {
            goblins[0] = new Goblin(game, world, 62, 251, player);
            goblins[1] = new Goblin(game, world, 194, 303, player);
            goblins[2] = new Goblin(game, world, 326, 407, player);
            goblins[3] = new Goblin(game, world, 394, 359, player);
            goblins[4] = new Goblin(game, world, 330, 255, player);
            goblins[5] = new Goblin(game, world, 402, 295, player);
            goblins[6] = new Goblin(game, world, 187, 347, player);
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

        //if the player has already picked up a relic or healing potion don't draw it again
        if (!relicIsFound) {
            items.add(new Relic(world, "vampire necklace 2", 370, 255, 3));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 90, 300, 3));
        }

        doors.add(new Door(game, world, "room 4", 368, 466, 334, 284));
        doors.add(new Door(game, world, "room 2", 64, 210, 352, 254));
        doors.add(new Door(game, world, "room 1", 192, 434, 352, 50));
    }
}

