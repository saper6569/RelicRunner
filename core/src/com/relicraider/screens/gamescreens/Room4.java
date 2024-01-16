package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room4 extends AbstractGameScreen {

    public Room4(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room4.tmx", 4, playerX, playerY);

        Player.room = "Room4";

        items.add(new HealingPotion(world, 90, 300));

        doors.add(new Door(game, world, "room 3", 336, 274, 368, 456));
        doors.add(new Door(game, world, "room 6", 176, 434, 368, 50));
        doors.add(new Door(game, world, "room 1", 96, 226, 272, 168));
    }
}


