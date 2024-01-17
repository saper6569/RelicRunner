/* Relic Raider ; Final Project ICS4U
   Sanija, Ryder, Amin
   December 15th, 2023 - January 16th, 2024
   Main Class to create game
 */
package com.relicraider;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.relicraider.screens.GameWinScreen;
import com.relicraider.screens.MainMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

//Relic Raider class extends the Game class
public class RelicRaider extends Game {

	public static SpriteBatch spriteBatch;

	//Create Game Screen
	@Override
	public void create() {
		//couldn't think of a way to use sort or read from a file
		InputStream in = getClass().getResourceAsStream("randomText.txt");
		BufferedReader scanner = new BufferedReader(new InputStreamReader(in));

		int[] numbers = new int[10];

		//loop through 10 times
		for (int i = 0; i < 10; i++) {
			try {
				numbers[i] = Integer.parseInt(scanner.readLine());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		insertionSortAscending(numbers);
		System.out.println(Arrays.toString(numbers));

		spriteBatch = new SpriteBatch();
		setScreen(new MainMenu(this));
	}

	/**
	 * insertion sort method
	 * @param numbers - array holding unsorted numbers
	 */
	private void insertionSortAscending(int[] numbers) {
		//Repeat for each number starting at the second number
		for (int i = 1; i < numbers.length; i++) {
			//Get the number at the given index
			int itemToInsert = numbers[i];
			//Get the position of the last sorted number
			int lastSorted = i - 1;
			//Repeat while there are still numbers to check and the last sorted
			//number is greater than the number being checked
			while (lastSorted >= 0 && numbers[lastSorted] > itemToInsert) {
				//shift the sorted number up one place
				numbers[lastSorted + 1] = numbers[lastSorted];
				//Get the position of the next sorted item
				lastSorted = lastSorted - 1;
			}
			//Move the number into the correct position
			numbers[lastSorted + 1] = itemToInsert;
		}
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