package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room3 extends AbstractGameScreen {

    public Room3(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room3.tmx", 4, playerX, playerY);

        Player.room = "Room3";

        items.add(new HealingPotion(world, 90, 300));

        doors.add(new Door(game, world, "room 4", 368, 466, 334, 284));
        doors.add(new Door(game, world, "room 2", 64, 210, 352, 254));
        doors.add(new Door(game, world, "room 1", 192, 434, 352, 50));
    }
}

