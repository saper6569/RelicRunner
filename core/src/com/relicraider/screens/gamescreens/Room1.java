package com.relicraider.screens.gamescreens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.relicraider.Items.HealingPotion;
import com.relicraider.Items.Relic;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;
import com.relicraider.screens.GameOverScreen;
import com.relicraider.screens.utilities.Door;
import com.relicraider.screens.utilities.HUD;

public class Room1 extends AbstractGameScreen {

    public Room1(RelicRaider game) {
        super(game, "Maps/room1.tmx", 4);

        Player.room = "Room1";

        characters.add(new Goblin(world, 100, 300));
        characters.add(new Goblin(world, 220, 300));
        characters.add(new Goblin(world, 270, 130));
        characters.add(new Goblin(world, 360, 80));

        items.add(new HealingPotion(world, 90, 300));
        items.add(new Relic(world, "ring 5", 360, 85));

        doors.add(new Door(game, world, "room 2", 80, 416));

    }
}
