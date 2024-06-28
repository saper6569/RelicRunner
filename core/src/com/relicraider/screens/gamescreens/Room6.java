/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Sets up Room 6
 */
package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.*;
import com.relicraider.screens.utilities.Door;

import java.util.ArrayList;

public class Room6 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;
    public static ArrayList<GameCharacter> charactersInRoom = new ArrayList<>();
    public static boolean hasBeenLoaded = false;

    /**
     * constructor for creating room6
     * @param game - the game that is used to manage screens
     * @param playerX - x position of the player
     * @param playerY - y postion of the player
     */
    public Room6(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room6.tmx", 4, playerX, playerY);

        Player.room = "Room6";

        if (!hasBeenLoaded) {
            charactersInRoom.add(new Goblin(game, world, 76, 180, player));
            charactersInRoom.add(new Goblin(game, world, 256, 180, player));
            charactersInRoom.add(new Goblin(game, world, 364, 94, player));
            charactersInRoom.add(new Goblin(game, world, 400, 190, player));
            charactersInRoom.add(new Goblin(game, world, 84, 355, player));
            charactersInRoom.add(new Goblin(game, world, 84, 356, player));
            charactersInRoom.add(new Goblin(game, world, 208, 356, player));
            charactersInRoom.add(new Wizard(game, world, 308, 420, player, this));
            charactersInRoom.add(new Goblin(game, world, 430, 312, player));
            charactersInRoom.add(new Wizard(game, world, 230, 356, player, this));
            charactersInRoom.add(new Goblin(game, world, 288, 428, player));
            charactersInRoom.add(new Goblin(game, world, 52, 54, player));
            charactersInRoom.add(new Wizard(game, world, 202, 160, player, this));

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
            items.add(new Relic(world, "heart in a jar", 256, 396, 6));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 90, 300, 6));
        }

        doors.add(new Door(game, world, "room 1", 128, 18, 70, 406));
        doors.add(new Door(game, world, "room 4", 368, 50, 175, 426));
    }

    @Override
    public void addCharacter(GameCharacter character) {
        charactersInRoom.add(character);
        characters.add(character);
    }
}


