package com.relicraider;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.relicraider.RelicRaider;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Relic Raider");
		config.setWindowPosition(650,250);
		config.setWindowIcon(Files.FileType.Internal,"Other/ICON.png");
		new Lwjgl3Application(new RelicRaider(), config);
	}
}
