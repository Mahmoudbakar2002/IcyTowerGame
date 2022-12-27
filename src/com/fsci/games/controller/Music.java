package com.fsci.games.controller;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Music {
    int currentFrame= 0;
    Clip clip;
    AudioInputStream audioInputStream;
    File file;
    public Music(String filePath) {
        // create AudioInputStream object
        try {
            file = new File(filePath);
            audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stop() {
        currentFrame = 0;
        clip.stop();
        clip.close();
    }
    public void play() {
        clip.start();
    }

    public void restart() {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0;
        clip.setFramePosition(0);
        this.play();
    }
    public void resetAudioStream() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file.getAbsoluteFile());
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playonce(){
        try {
            clip.setFramePosition(0);
            this.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
