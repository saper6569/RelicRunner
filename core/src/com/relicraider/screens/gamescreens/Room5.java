/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 5
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.GameCharacter;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

import java.util.ArrayList;

public class Room5 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;
    public static ArrayList<GameCharacter> charactersInRoom = new ArrayList<>();
    public static boolean hasBeenLoaded = false;

    /**
     * constructor for creating room5
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public Room5(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room5.tmx", 4, playerX, playerY);

        Player.room = "Room5";

        //if the player has already picked up a relic or healing potion don't draw it again
        if (!relicIsFound) {
            items.add(new Relic(world, "crystal ball", 120, 236, 5));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 180, 324, 5));
        }

        if (!hasBeenLoaded) {
            charactersInRoom.add(new Goblin(game, world, 76, 384, player));
            charactersInRoom.add(new Goblin(game, world, 167, 284, player));
            charactersInRoom.add(new Goblin(game, world, 211, 152, player));
            charactersInRoom.add(new Goblin(game, world, 78, 219, player));
            hasBeenLoaded = true;
        } else {
            for (int i = 0; i < charactersInRoom.size(); i++) {
                charactersInRoom.set(i, new Goblin(game, world, charactersInRoom.get(i).getB2dBody().getPosition().x, charactersInRoom.get(i).getB2dBody().getPosition().y, player, charactersInRoom.get(i).getHealth()));
            }
        }

        //add all the actor to the game screen
        characters.clear();
        for (int i = 0; i < charactersInRoom.size(); i++) {
            characters.add(charactersInRoom.get(i));
        }
        characters.add(player);

        doors.add(new Door(game, world, "room 2", 160, 450, 112, 338));
        doors.add(new Door(game, world, "room 2", 272, 226, 320, 114));
    }

    @Override
    public void addCharacter(GameCharacter character) {
        charactersInRoom.add(character);
        characters.add(character);
    }
}

