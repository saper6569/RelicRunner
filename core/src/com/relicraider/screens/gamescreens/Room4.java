/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 4
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.*;
import com.relicraider.screens.utilities.Door;

import java.util.ArrayList;

public class Room4 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;
    public static ArrayList<GameCharacter> charactersInRoom = new ArrayList<>();
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
            charactersInRoom.add(new Goblin(game, world, 390, 316, player));
            charactersInRoom.add(new Goblin(game, world, 202, 336, player));
            charactersInRoom.add(new Goblin(game, world, 58, 292, player));
            charactersInRoom.add(new Wizard(game, world, 71, 311, player, this));
            charactersInRoom.add(new Goblin(game, world, 183, 256, player));
            charactersInRoom.add(new Goblin(game, world, 198, 260, player));
            hasBeenLoaded = true;
        } else {
            for (int i = 0; i < charactersInRoom.size(); i++) {
                charactersInRoom.set(i, new Goblin(game, world, charactersInRoom.get(i).getB2dBody().getPosition().x, charactersInRoom.get(i).getB2dBody().getPosition().y, player, charactersInRoom.get(i).getHealth()));
            }
        }

        //add all the actor to the game screen
        characters.clear();
        Slime slime = new Slime(game, world, player.getB2dBody().getPosition().x, player.getB2dBody().getPosition().y, player);

        characters.add(slime);
        for (int i = 0; i < charactersInRoom.size(); i++) {
            characters.add(charactersInRoom.get(i));
        }
        characters.add(player);

        doors.add(new Door(game, world, "room 3", 336, 274, 368, 456));
        doors.add(new Door(game, world, "room 6", 176, 434, 368, 60));
        doors.add(new Door(game, world, "room 1", 96, 226, 272, 168));
    }

    @Override
    public void addCharacter(GameCharacter character) {
        charactersInRoom.add(character);
        characters.add(character);
    }
}


