/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 1
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.*;
import com.relicraider.screens.utilities.Door;

import java.util.ArrayList;

public class Room1 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;
    public static ArrayList<GameCharacter> charactersInRoom = new ArrayList<>();
    public static boolean hasBeenLoaded = false;

    /**
     * constructor for creating room1
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public Room1(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room1.tmx", 4, playerX, playerY);

        Player.room = "Room1";

        if (!hasBeenLoaded) {
            charactersInRoom.add(new Goblin(game, world, 100, 300, player));
            charactersInRoom.add(new Goblin(game, world, 270, 130, player));
            charactersInRoom.add(new Goblin(game, world, 360, 80, player));
            charactersInRoom.add(new Wizard(game, world, 150, 93, player, this));
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

    @Override
    public void addCharacter(GameCharacter character) {
        charactersInRoom.add(character);
        characters.add(character);
    }
}
