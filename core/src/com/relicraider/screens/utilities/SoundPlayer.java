package com.relicraider.screens.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SoundPlayer {
    private static String lastSongPlayed = "";
    private Music mainTheme;
    private Music song1;
    private Music song2;
    private Music song3;
    private Music footsteps;
    private Music swordSwoosh;
    private Music daggerSwoosh;
    private Music collectRelic;
    private Music pageFlip;
    private Music buttonPress;
    private Music doorOpen;


    public SoundPlayer() {
        footsteps = Gdx.audio.newMusic(Gdx.files.internal("sounds/footsteps.ogg"));
        footsteps.setLooping(true);
        footsteps.setVolume(1.0f);
        swordSwoosh = Gdx.audio.newMusic(Gdx.files.internal("sounds/swordSwoosh.ogg"));
        daggerSwoosh = Gdx.audio.newMusic(Gdx.files.internal("sounds/daggerSwoosh.ogg"));
        collectRelic = Gdx.audio.newMusic(Gdx.files.internal("sounds/collectRelic.ogg"));
        pageFlip = Gdx.audio.newMusic(Gdx.files.internal("sounds/pageFlip.ogg"));
        buttonPress = Gdx.audio.newMusic(Gdx.files.internal("sounds/buttonPress.ogg"));
        doorOpen = Gdx.audio.newMusic(Gdx.files.internal("sounds/doorOpen.ogg"));
        doorOpen.setVolume(0.7f);

        mainTheme = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/mainTheme.mp3"));
        song1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/auf-grunen-wiesen.mp3"));
        song1.setVolume(0.7f);
        song2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/ruins.mp3"));
        song2.setVolume(0.7f);
        song3 = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/the-time-is-upon-us.mp3"));
        song3.setVolume(0.7f);
    }

    public void stopMusic() {
        mainTheme.stop();
        song1.stop();
        song2.stop();
        song3.stop();
    }

    public void playGameMusic () {
        if (!mainTheme.isPlaying() && !song1.isPlaying() && !song2.isPlaying() && !song3.isPlaying()) {
            int random = (int) (Math.random() * 2 + 1);
            if (lastSongPlayed.equals("song 1")) {
                if (random == 1) {
                    song2.play();
                    lastSongPlayed = "song 2";
                } else {
                    song3.play();
                    lastSongPlayed = "song 3";
                }

            } else if (lastSongPlayed.equals("song 2")) {
                if (random == 1) {
                    song1.play();
                    lastSongPlayed = "song 1";
                } else {
                    song3.play();
                    lastSongPlayed = "song 3";
                }

            } else if (lastSongPlayed.equals("song 3")) {
                if (random == 1) {
                    song1.play();
                    lastSongPlayed = "song 1";
                } else {
                    song2.play();
                    lastSongPlayed = "song 2";
                }

            } else {
                random = (int) (Math.random() * 3 + 1);

                if (random == 1) {
                    song1.play();
                    lastSongPlayed = "song 1";
                } else if (random == 2) {
                    song2.play();
                    lastSongPlayed = "song 2";
                } else {
                    song3.play();
                    lastSongPlayed = "song 3";
                }
            }
        }
    }

    public Music getMainTheme() {
        return mainTheme;
    }

    public Music getSong1() {
        return song1;
    }

    public Music getSong2() {
        return song2;
    }

    public Music getSong3() {
        return song3;
    }

    public Music getFootsteps() {
        return footsteps;
    }

    public Music getSwordSwoosh() {
        return swordSwoosh;
    }

    public Music getCollectRelic() {
        return collectRelic;
    }

    public Music getDaggerSwoosh() {
        return daggerSwoosh;
    }

    public Music getPageFlip() {
        return pageFlip;
    }

    public Music getButtonPress() {
        return buttonPress;
    }

    public Music getDoorOpen() {
        return doorOpen;
    }
}
