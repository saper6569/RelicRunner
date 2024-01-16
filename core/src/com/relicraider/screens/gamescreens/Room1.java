package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room1 extends AbstractGameScreen {

    public Room1(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room1.tmx", 4, playerX, playerY);

        Player.room = "Room1";

//        characters.add(new Goblin(world, 100, 300));
//        characters.add(new Goblin(world, 220, 300));
//        characters.add(new Goblin(world, 270, 130));
//        characters.add(new Goblin(world, 360, 80));

        items.add(new HealingPotion(world, 90, 300));
        items.add(new Relic(world, "ring 5", 360, 85));

        doors.add(new Door(game, world, "room 6", 80, 418, 120, 30));
        doors.add(new Door(game, world, "room 2", 96, 18, 214, 456));
        doors.add(new Door(game, world, "room 4", 272, 178, 86, 246));
        doors.add(new Door(game, world, "room 3", 352, 50, 182, 423));

    }
}
