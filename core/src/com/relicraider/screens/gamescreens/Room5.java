package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room5 extends AbstractGameScreen {

    public Room5(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room5.tmx", 4, playerX, playerY);

        Player.room = "Room5";

        items.add(new HealingPotion(world, 90, 300));

        doors.add(new Door(game, world, "room 2", 160, 450, 112, 338));
        doors.add(new Door(game, world, "room 2", 272, 226, 320, 114));
    }
}

