package com.relicraider;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.relicraider.screens.GameStory;
import com.relicraider.screens.HowToPlay;
import com.relicraider.screens.MainMenu;
import com.relicraider.screens.gamescreens.Room1;

public class RelicRaider extends Game {

	public static SpriteBatch spriteBatch;


	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		setScreen(new MainMenu(this));
		//setScreen(new Room1(this));
		//setScreen(new GameStory(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		super.dispose();
	}
}