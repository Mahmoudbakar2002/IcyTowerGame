package com.fsci.games.controller;

public class MusicEngine {
    /***** Path for musics ********/
    private static final String MUSIC_SOURCE_FOLDER="src/assets/Sounds";
    private static final String GAME_MUSIC_RELATIVE=MUSIC_SOURCE_FOLDER+"/icy_tower_ingame.wav";


    /*** Music objects ******/
    private static Music gameMusic;
    private static Music menuSound,amazingSound,dieAndtTyAgineSound;

    /**Load Musics */
    static {
        gameMusic = new Music(GAME_MUSIC_RELATIVE);
        menuSound = new Music(MUSIC_SOURCE_FOLDER+"/icy_tower.wav");
        amazingSound = new Music(MUSIC_SOURCE_FOLDER+"/amazing.wav");
        dieAndtTyAgineSound = new Music(MUSIC_SOURCE_FOLDER+"/dieandtryagine.wav");
    }

    /***********************************************************
     *        Getter
     ***********************************************************/

    public static Music getGameMusic() {
        return gameMusic;
    }

    public static Music getMenuSound() {
        return menuSound;
    }

    public static Music getAmazingSound() {
        return amazingSound;
    }

    public static Music getDieAndtTyAgineSound() {
        return dieAndtTyAgineSound;
    }
}
