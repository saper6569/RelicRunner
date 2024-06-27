/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Main Class to create game
 */
package com.relicraider;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.relicraider.screens.MainMenu;
import com.relicraider.screens.gamescreens.TestRoom;
import com.relicraider.screens.utilities.SoundPlayer;

//Relic Raider class extends the Game class
public class RelicRaider extends Game {

	public static SpriteBatch spriteBatch;
	public static SoundPlayer soundPlayer;

	//Create Game Screen
	@Override
	public void create() {
		soundPlayer = new SoundPlayer();
		spriteBatch = new SpriteBatch();
		setScreen(new TestRoom(this, 200, 200));
		//setScreen(new MainMenu(this));
	}

	//Method to Render the screen using the render method in game class
	@Override
	public void render() {
		super.render();
	}

	//Method to dispose the screen after use
	@Override
	public void dispose() {
		spriteBatch.dispose();
		super.dispose();
	}
}