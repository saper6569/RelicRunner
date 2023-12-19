package com.relicraider;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.relicraider.screens.MainMenu;

public class RelicRaider extends Game {

	public static SpriteBatch spriteBatch;

	@Override
	public void create() {
		spriteBatch = new SpriteBatch();
		setScreen(new MainMenu());
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