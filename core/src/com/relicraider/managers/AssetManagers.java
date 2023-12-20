package com.relicraider.managers;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class AssetManagers {
    public final AssetManager assetManager = new AssetManager();

    public final String playButton = "MainMenu/testButton.png";
    public final String mainMenuSong1 = "MainMenu/track1.mp3";
    public final String mainMenuSong2 = "MainMenu/track2.mp3";

    public void mainMenuAssets() {
        assetManager.load(mainMenuSong1, Music.class);
        assetManager.load(mainMenuSong2, Music.class);
        assetManager.load(playButton, Texture.class);
        assetManager.finishLoading();
    }
}
