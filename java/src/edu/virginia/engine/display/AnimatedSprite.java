package edu.virginia.engine.display;

import edu.virginia.engine.util.GameClock;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimatedSprite extends Sprite {

    private static final int DEFAULT_ANIMATION_SPEED = 0; // in ms
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

    @Override
    public void draw(Graphics g) {
        if (playing && animationSpeed <= gameClock.getElapsedTime()) {
            currentFrame++;
            gameClock.resetGameClock();
        }
        super.draw(g);
    }
}
