package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.utilities.Door;

public class Room3 extends AbstractGameScreen {

    protected boolean potionIsUsed = false;
    protected boolean relicIsFound = false;

    public Room3(RelicRaider game, float playerX, float playerY) {
        super(game, "Maps/room3.tmx", 4, playerX, playerY);

        Player.room = "Room3";

        characters.add(new Goblin(world, 62, 251));
        characters.add(new Goblin(world, 194, 303));
        characters.add(new Goblin(world, 326, 407));
        characters.add(new Goblin(world, 394, 359));
        characters.add(new Goblin(world, 330, 255));
        characters.add(new Goblin(world, 402, 295));
        characters.add(new Goblin(world, 187, 347));

        if (!relicIsFound) {
            items.add(new Relic(world, "jem 2", 370, 255));
        }
        if (!potionIsUsed) {
            items.add(new HealingPotion(world, 90, 300));
        }

        doors.add(new Door(game, world, "room 4", 368, 466, 334, 284));
        doors.add(new Door(game, world, "room 2", 64, 210, 352, 254));
        doors.add(new Door(game, world, "room 1", 192, 434, 352, 50));
    }
}

