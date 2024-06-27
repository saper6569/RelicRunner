/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 2
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room2 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;
    public static Goblin[] goblins = new Goblin[6];
    public static boolean hasBeenLoaded = false;

    /**
     * constructor for creating room2
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public Room2(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room2.tmx", 4, playerX, playerY);

        Player.room = "Room2";

        if (!hasBeenLoaded) {
            goblins[0] = new Goblin(game, world, 140, 385, player);
            goblins[1] = new Goblin(game, world, 260, 300, player);
            goblins[2] = new Goblin(game, world, 270, 150, player);
            goblins[3] = new Goblin(game, world, 340, 220, player);
            goblins[4] = new Goblin(game, world, 495, 235, player);
            goblins[5] = new Goblin(game, world, 560, 163, player);
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
            items.add(new Relic(world, "scroll open", 548, 188, 2));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 340, 200, 2));
        }

        doors.add(new Door(game, world, "room 1", 224, 466, 106, 30));
        doors.add(new Door(game, world, "room 5", 112, 338, 150, 440));
        doors.add(new Door(game, world, "room 5", 320, 114, 262, 216));
        doors.add(new Door(game, world, "room 3", 352, 274, 54, 220));
    }
}

