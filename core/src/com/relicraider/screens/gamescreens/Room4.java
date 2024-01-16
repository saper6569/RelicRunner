package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room4 extends AbstractGameScreen {

    protected boolean potionIsUsed = false;
    protected boolean relicIsFound = false;

    public Room4(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room4.tmx", 4, playerX, playerY);

        Player.room = "Room4";

        if (!relicIsFound) {
            items.add(new Relic(world, "book 1", 180, 105));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 90, 300));
        }

        characters.add(new Goblin(world, 390, 316));
        characters.add(new Goblin(world, 202, 336));
        characters.add(new Goblin(world, 58, 292));
        characters.add(new Goblin(world, 183, 256));
        characters.add(new Goblin(world, 198, 260));

        doors.add(new Door(game, world, "room 3", 336, 274, 368, 456));
        doors.add(new Door(game, world, "room 6", 176, 434, 368, 50));
        doors.add(new Door(game, world, "room 1", 96, 226, 272, 168));
    }
}


