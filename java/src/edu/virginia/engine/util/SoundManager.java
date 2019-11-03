package edu.virginia.engine.util;
import java.io.*;
import java.util.HashMap;
import javax.sound.sampled.*;

public class SoundManager {

    private HashMap<String, Clip> music;
    private HashMap<String, Clip> effects;

    public SoundManager(){
        this.music = new HashMap();
        this.effects = new HashMap();

    }

    public void loadSoundEffect(String id, String filename){
        try {
            File sound = new File("resources"+File.separator+"sounds"+ File.separator+"effects"+File.separator+filename);
            // Open an audio input stream.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            this.effects.put(id, clip);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public void playSoundEffect(String id){
        Clip clip = this.effects.get(id);
        clip.start();
        if(!clip.isRunning()) {
            clip.setFramePosition(0);
        }
    }

    public void loadMusic(String id, String filename){
        try {
            File sound = new File("resources"+File.separator+"sounds"+ File.separator+"music"+File.separator+filename);
            // Open an audio input stream.
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(sound);
            // Get a sound clip resource.
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            this.music.put(id, clip);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playMusic(String id){
       Clip clip = this.music.get(id);
       clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
