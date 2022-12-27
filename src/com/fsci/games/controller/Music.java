package com.fsci.games.controller;

import com.fsci.games.utills.Config;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;


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
        FloatControl gainControl=(FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        float volume= Config.SOUND_LEVEL*1.0f/100.0f;
        gainControl.setValue(20f*(float) Math.log10(volume));
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
