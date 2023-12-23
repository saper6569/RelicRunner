package com.relicraider.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagers {
    public final AssetManager assetManager = new AssetManager();

    public final String playButton = "MainMenu/playButton.png";
    public final String creditsButton = "MainMenu/creditsButton.png";
    public final String settingsButton = "MainMenu/settingsButton.png";
    public final String quitButton = "MainMenu/quitButton.png";
    public final String menuBackdrop = "MainMenu/backdrop.png";
    public final String mainMenuSong1 = "MainMenu/track1.mp3";

    public void mainMenuAssets() {
        assetManager.load(mainMenuSong1, Music.class);
        assetManager.load(playButton, Texture.class);
        assetManager.load(creditsButton, Texture.class);
        assetManager.load(settingsButton, Texture.class);
        assetManager.load(quitButton, Texture.class);
        assetManager.load(menuBackdrop, Texture.class);
        assetManager.finishLoading();
    }

    public void gameScreen1Assets() {

    }
}
