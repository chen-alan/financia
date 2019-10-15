package edu.virginia.engine.display;

import edu.virginia.engine.util.GameClock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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

    public void initializeFrames(String folderName){

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
        ani = this.getAnimation(id);
        animate(ani);
    }

    //STOP ANIMATION METHODS
    public void stopAnimation(int frame){

    }

    public void stopAnimation() {

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
