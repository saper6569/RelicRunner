package com.relicraider.screens.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundPlayer {
    Music song1;
    Music song2;
    Music song3;
    Music song4;
    Music footsteps;
    Music swordSwoosh;
    Music collectRelic;

    public SoundPlayer() {
        footsteps = Gdx.audio.newMusic(Gdx.files.internal("sounds/footsteps.ogg"));
        footsteps.setLooping(true);
        swordSwoosh = Gdx.audio.newMusic(Gdx.files.internal("sounds/swordSwoosh.ogg"));
        collectRelic = Gdx.audio.newMusic(Gdx.files.internal("sounds/collectRelic.ogg"));
    }
}
