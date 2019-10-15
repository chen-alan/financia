package edu.virginia.engine.display;

import edu.virginia.engine.util.GameClock;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

public class AnimatedSprite extends Sprite {

    private static final int DEFAULT_ANIMATION_SPEED = 5; // in ms
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
    }

    public Animation getAnimation(String id) {
        for (Animation ani : animations ){
            if (ani.getId().equals(id)){
                return ani;
            }
        }
        return null;
    }

    public void setAnimationSpeed(int newAnimationSpeed) {
        this.animationSpeed = newAnimationSpeed;
    }

    public void setAnimations(ArrayList<Animation> newAnimations) {
        this.animations = newAnimations;
    }

    public void initGameClock() {
        if (this.gameClock == null) {
            this.gameClock = new GameClock();
        }
    }

    public void initializeFrames(String spriteName){
        File[] pictures= new File("resources"+File.separator+
                "animations"+File.separator+spriteName).listFiles();
        for(File pic: pictures){
            BufferedImage picRead = null;
            try {
                picRead = ImageIO.read(pic);
            }
            catch (IOException e) {
                System.out.println("[Error in DisplayObject.java:readImage] Could not read file ");
                e.printStackTrace();
            }
            frames.add(picRead);
        }
    }

    //ANIMATE METHODS
    public void animate(int start, int end) {
        this.startFrame = start;
        this.endFrame = end;
    }

    public void animate(Animation ani) {
        this.startFrame = ani.getStartFrame();
        this.endFrame = ani.getEndFrame();
    }

    public void animate(String id) {
        animate(this.getAnimation(id));
    }

    //STOP ANIMATION METHODS
    public void stopAnimation(int frame) {
        this.currentFrame = frame;
        this.playing = false;

    }

    public void stopAnimation() {
        stopAnimation(0);
    }


    @Override
    public void draw(Graphics g) {
        if (playing && animationSpeed <= gameClock.getElapsedTime()) {
            currentFrame++;
            gameClock.resetGameClock();
        }
        super.draw(g);
    }


}
