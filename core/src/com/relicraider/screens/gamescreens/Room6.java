package com.relicraider.screens.gamescreens;

import com.relicraider.Items.HealingPotion;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;

public class Room6 extends AbstractGameScreen {

    public Room6(RelicRaider game) {
        super(game, "Maps/room6.tmx", 4);

        Player.room = "Room6";

        characters.add(new Goblin(world, 100, 300));
        characters.add(new Goblin(world, 220, 300));
        characters.add(new Goblin(world, 270, 130));
        characters.add(new Goblin(world, 360, 80));

        items.add(new HealingPotion(world, 90, 300));
    }
}


