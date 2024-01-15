package com.relicraider.screens.gamescreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.physics.box2d.*;
import com.relicraider.Items.HealingPotion;
import com.relicraider.RelicRaider;
import com.relicraider.characters.Goblin;
import com.relicraider.characters.Player;

public class Room3 extends AbstractGameScreen {

    public Room3(RelicRaider game) {
        super(game, "Maps/room3.tmx", 4);

        Player.room = "Room3";

        characters.add(new Goblin(world, 100, 300));
        characters.add(new Goblin(world, 220, 300));
        characters.add(new Goblin(world, 270, 130));
        characters.add(new Goblin(world, 360, 80));

        items.add(new HealingPotion(world, 90, 300));
    }
}

