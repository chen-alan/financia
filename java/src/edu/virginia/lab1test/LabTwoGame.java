package edu.virginia.lab1test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Animation;
import edu.virginia.engine.display.Game;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabTwoGame extends Game{

    /* Create a sprite object for our game. Default is mario_frontWalk_0.png */
    AnimatedSprite mario = new AnimatedSprite("Mario",
            "animations"+ File.separator+"mario"+File.separator+"mario_frontWalk_0.png",
            new Point(0,0));


    /**
     * Constructor. See constructor in Game.java for details on the parameters given
     * */
    public LabTwoGame() {
        super("Lab Two Test Game", 500, 300);

        /*make new animations and add then to animated sprite*/
        mario.initializeFrames("mario");
        ArrayList<Animation> aniList = new ArrayList<Animation>();
        Animation frontWalk = new Animation("frontWalk", 5, 8);
        Animation backWalk = new Animation("backWalk", 1, 4);
        Animation leftWalk = new Animation("leftWalk", 9, 12);
        Animation rightWalk = new Animation("rightWalk", 13, 16);
        aniList.add(frontWalk);
        aniList.add(backWalk);
        aniList.add(leftWalk);
        aniList.add(rightWalk);
        mario.setAnimations(aniList);
    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */
    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);
        /* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
        if(mario != null) mario.update(pressedKeys);

        if (pressedKeys.isEmpty() && (mario != null)) {
            if (mario.isPlaying()) {
                mario.stopAnimation();
            }
        }

        if (pressedKeys.contains(KeyEvent.VK_UP)) {
            mario.setPosition(new Point(mario.getPosition().x,
                    mario.getPosition().y - 1));
            mario.animate("backWalk");
        }
        if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
            mario.setPosition(new Point(mario.getPosition().x,
                    mario.getPosition().y + 1));
            mario.animate("frontWalk");
        }
        if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
            mario.setPosition(new Point(mario.getPosition().x - 1,
                    mario.getPosition().y));
            mario.animate("leftWalk");
        }
        if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            mario.setPosition(new Point(mario.getPosition().x + 1,
                    mario.getPosition().y));
            mario.animate("rightWalk");
        }
        if (pressedKeys.contains(KeyEvent.VK_I)) {
            mario.setPivotPoint(new Point(mario.getPivotPoint().x,
                    mario.getPivotPoint().y - 1));
        }
        if (pressedKeys.contains(KeyEvent.VK_K)) {
            mario.setPivotPoint(new Point(mario.getPivotPoint().x,
                    mario.getPivotPoint().y + 1));
        }
        if (pressedKeys.contains(KeyEvent.VK_J)) {
            mario.setPivotPoint(new Point(mario.getPivotPoint().x - 1,
                    mario.getPivotPoint().y));
        }
        if (pressedKeys.contains(KeyEvent.VK_L)) {
            mario.setPivotPoint(new Point(mario.getPivotPoint().x + 1,
                    mario.getPivotPoint().y));
        }
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            mario.setRotation(1);
        }
        if (pressedKeys.contains(KeyEvent.VK_Q)) {
            mario.setRotation(-1);
        }

        if(pressedKeys.contains(KeyEvent.VK_V)){
            mario.setVisible(!mario.getVisible());
        }

        if(pressedKeys.contains(KeyEvent.VK_Z)) {
            if (mario.getAlpha() >= 0.01f) {
                mario.setOldAlpha(mario.getAlpha());
                mario.setAlpha(mario.getAlpha() - 0.01f);
            }
        }

        if(pressedKeys.contains(KeyEvent.VK_X)) {
            if (mario.getAlpha() <= 0.99f) {
                mario.setOldAlpha(mario.getAlpha());
                mario.setAlpha(mario.getAlpha() + 0.01f);
            }
        }

        if(pressedKeys.contains(KeyEvent.VK_A)){
            mario.setScale(mario.getScale() + 0.01);
        }

        if(pressedKeys.contains(KeyEvent.VK_S)){
            if (mario.getScale() >=0.01) {
                mario.setScale(mario.getScale() - 0.01);
            }
        }

        //CHANGING ANIMATION SPEED
        if(pressedKeys.contains(KeyEvent.VK_1)){
            int currSpeed = mario.getAnimationSpeed();
            if (currSpeed > 20) {
                mario.setAnimationSpeed(currSpeed - 5);
            }
        }

        if(pressedKeys.contains(KeyEvent.VK_2)){
            int currSpeed = mario.getAnimationSpeed();
            mario.setAnimationSpeed(currSpeed + 5);
        }
    }

    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
     * the screen, we need to make sure to override this method and call mario's draw method.
     * */
    @Override
    public void draw(Graphics g){
        super.draw(g);

        /* Same, just check for null in case a frame gets thrown in before Mario is initialized */
        if(mario != null) mario.draw(g);
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        LabTwoGame game = new LabTwoGame();
        game.start();

    }
}