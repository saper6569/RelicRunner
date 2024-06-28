/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 2
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.*;
import com.relicraider.screens.utilities.Door;

import java.util.ArrayList;

public class Room2 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;
    public static ArrayList<GameCharacter> charactersInRoom = new ArrayList<>();
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
            charactersInRoom.add(new Goblin(game, world, 140, 385, player));
            charactersInRoom.add(new Goblin(game, world, 260, 300, player));
            charactersInRoom.add(new Goblin(game, world, 270, 150, player));
            charactersInRoom.add(new Wizard(game, world, 340, 220, player, this));
            charactersInRoom.add(new Goblin(game, world, 495, 235, player));
            charactersInRoom.add(new Goblin(game, world, 560, 163, player));
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

    @Override
    public void addCharacter(GameCharacter character) {
        charactersInRoom.add(character);
        characters.add(character);
    }
}

