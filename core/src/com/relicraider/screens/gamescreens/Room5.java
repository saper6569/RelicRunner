package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room5 extends AbstractGameScreen {

    public static boolean potionIsUsed = false;
    public static boolean relicIsFound = false;

    public Room5(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room5.tmx", 4, playerX, playerY);

        Player.room = "Room5";

        if (!relicIsFound) {
            items.add(new Relic(world, "crystal ball", 120, 236, 5));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 231, 324, 5));
        }

        characters.add(new Goblin(world, 76, 384));
        characters.add(new Goblin(world, 167, 284));
        characters.add(new Goblin(world, 211, 152));
        characters.add(new Goblin(world, 78, 219));

        doors.add(new Door(game, world, "room 2", 160, 450, 112, 338));
        doors.add(new Door(game, world, "room 2", 272, 226, 320, 114));
    }
}

