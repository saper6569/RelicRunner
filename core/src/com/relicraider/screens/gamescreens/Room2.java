package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room2 extends AbstractGameScreen {

    protected boolean potionIsUsed = false;
    protected boolean relicIsFound = false;

    public Room2(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room2.tmx", 4, playerX, playerY);

        Player.room = "Room2";

        characters.add(new Goblin(world, 140, 385));
        characters.add(new Goblin(world, 260, 300));
        characters.add(new Goblin(world, 270, 150));
        characters.add(new Goblin(world, 340, 220));
        characters.add(new Goblin(world, 495, 235));
        characters.add(new Goblin(world, 560, 163));

        if (!relicIsFound) {
            items.add(new Relic(world, "scroll open", 548, 188));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 340, 200));
        }

        doors.add(new Door(game, world, "room 1", 224, 466, 106, 30));
        doors.add(new Door(game, world, "room 5", 112, 338, 150, 440));
        doors.add(new Door(game, world, "room 5", 320, 114, 262, 216));
        doors.add(new Door(game, world, "room 3", 352, 274, 54, 220));
    }
}

