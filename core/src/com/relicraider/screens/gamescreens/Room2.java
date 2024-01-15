package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;

public class Room2 extends AbstractGameScreen {

    public Room2(RelicRaider game) {
        super(game, "Maps/room2.tmx", 4);

        Player.room = "Room2";

        characters.add(new Goblin(world, 100, 300));
        characters.add(new Goblin(world, 220, 300));
        characters.add(new Goblin(world, 270, 130));
        characters.add(new Goblin(world, 360, 80));

        items.add(new HealingPotion(world, 90, 300));
    }
}

