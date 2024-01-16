package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room6 extends AbstractGameScreen {

    public Room6(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room6.tmx", 4, playerX, playerY);

        Player.room = "Room6";

        items.add(new HealingPotion(world, 90, 300));

        doors.add(new Door(game, world, "room 1", 128, 18, 70, 406));
        doors.add(new Door(game, world, "room 4", 368, 50, 175, 426));
    }
}


