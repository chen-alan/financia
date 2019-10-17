package edu.virginia.engine.display;

import edu.virginia.engine.util.GameClock;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;

public class AnimatedSprite extends Sprite {

    private static final int DEFAULT_ANIMATION_SPEED = 200; // in ms
    private ArrayList<Animation> animations;
    private String fileName;
    private ArrayList<BufferedImage> frames;
    private boolean playing;
    private int currentFrame;
    private int startFrame;
    private int endFrame;
    private GameClock gameClock;
    private int animationSpeed; // check against gameClock

    public AnimatedSprite(String id, String imageFileName, Point position) {
        super(id, imageFileName);
        this.setPosition(position);
        this.gameClock = new GameClock();
        this.animationSpeed = DEFAULT_ANIMATION_SPEED;
        this.frames = new ArrayList<BufferedImage>();
        this.animations = new ArrayList<Animation>();
        this.playing = false;
        this.currentFrame = 0;
        this.startFrame = 0;
        this.endFrame = 0;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public Animation getAnimation(String id) {
        for (Animation ani : animations ){
            if (ani.getId().equals(id)){
                return ani;
            }
        }
        return null;
    }

    public void setAnimations(ArrayList<Animation> newAnimations) {
        this.animations = newAnimations;
    }

    public int getAnimationSpeed() {
        return this.animationSpeed;
    }

    public void setAnimationSpeed(int newAnimationSpeed) {
        this.animationSpeed = newAnimationSpeed;
    }

    public void initGameClock() {
        if (this.gameClock == null) {
            this.gameClock = new GameClock();
        }
    }

    public void initializeFrames(String spriteName){
        File[] pictures= new File("resources"+File.separator+
                "animations"+File.separator+spriteName).listFiles();
        Arrays.sort(pictures);
        for(File pic : pictures){
            //BufferedImage picRead = null;
            try {
                BufferedImage picRead = ImageIO.read(pic);
                frames.add(picRead);
            }
            catch (IOException e) {
                System.out.println("[Error in DisplayObject.java:readImage] Could not read file ");
                e.printStackTrace();
            }
        }
    }

    //ANIMATE METHODS
    public void animate(int start, int end) {
        this.playing = true;
        this.startFrame = start;
        this.endFrame = end;
        //if already in current animation, keep going
        if (!(this.currentFrame >= startFrame && this.currentFrame <= endFrame)){
            this.currentFrame = startFrame;
        }
    }

    public void animate(Animation ani) {
        this.playing = true;
        this.startFrame = ani.getStartFrame();
        this.endFrame = ani.getEndFrame();
        //if already in current animation, keep going
        if (!(this.currentFrame >= startFrame && this.currentFrame <= endFrame)){
            this.currentFrame = startFrame;
        }
    }

    public void animate(String id) {
        animate(this.getAnimation(id));
    }

    //STOP ANIMATION METHODS
    public void stopAnimation(int frame) {
        this.playing = false;
        this.currentFrame = frame;
    }

    public void stopAnimation() {
        this.playing = false;
        stopAnimation(startFrame);
    }


    @Override
    public void draw(Graphics g) {
        if (playing && animationSpeed <= gameClock.getElapsedTime()) {
            super.setImage(this.frames.get(currentFrame));
            if (currentFrame == endFrame){
                currentFrame = startFrame;
            }
            else{
                currentFrame++;
            }
            super.draw(g);
            gameClock.resetGameClock();
        }
        else{
           super.setImage(this.frames.get(currentFrame));
           super.draw(g);
        }

    }


}
