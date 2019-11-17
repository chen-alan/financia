package edu.virginia.lab1test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import edu.virginia.engine.display.*;
import edu.virginia.engine.util.SoundManager;
import org.w3c.dom.css.Rect;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabFiveGame extends Game{

    //visibility counter
    private int counter;
    private int colCount;
    private boolean collided;
    private boolean win;
    private boolean done;

    private boolean jumping;
    private double velocity;
    private double gravity;
    private double jumpTime;
    private int initY;

    private double fallTime;
    private boolean falling;
    private int ground;

    private boolean reboundLeft;
    private boolean reboundRight;
    private double accel;
    private double boundVel;
    private double boundTime;
    private int initX;

    /* Create a sprite object for our 		s = new Rectangle(this.position.x, this.position.y, (int) (this.displayImage.getWidth()), (int) (this.displayImage.getHeight()));
game. Default is mario_frontWalk_0.png */
    AnimatedSprite mario = new AnimatedSprite("Mario",
            "animations"+ File.separator+"mario"+File.separator+"mario_frontWalk_0.png",
            new Point(0,0));
    Sprite earth = new Sprite("earth","solarSystem"+ File.separator+"earth.png",
            new Point(300,290));
    Sprite trophy = new Sprite("trophy", "trophy.png", new Point(620,290));
    SoundManager sounds = new SoundManager();



    /**
     * Constructor. See constructor in Game.java for details on the parameters given
     * */
    public LabFiveGame() {
        super("Lab Five", 700, 500);

        mario.setStationary(false);
        mario.setPhysics(true);
        earth.setStationary(true);
        trophy.setStationary(true);
        colCount = 20;
        collided = false;
        win = false;
        done = false;

        velocity = -15.0; //pixels per time
        gravity = 2.0;
        jumpTime = 0;
        initY = 0;
        jumping = false;
        falling = true;
        ground = 300;
        fallTime = 0;

        reboundLeft = false;
        reboundRight = false;
        accel = -2.0;
        boundVel = 15.0;
        boundTime = 0;
        initX = 0;

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

        //load sounds
        sounds.loadMusic("background", "background.wav");
        //sounds.playMusic("background");
        sounds.loadSoundEffect("woohoo", "woohoo.wav");
        sounds.loadSoundEffect("bump", "bump.wav");
        sounds.loadSoundEffect("jump", "jump.wav");
        sounds.loadSoundEffect("die", "die.wav");
        sounds.loadSoundEffect("coin", "coin.wav");
        sounds.loadSoundEffect("powerup", "powerup.wav");
    }

    /** Collision Detection  **/
    public boolean collidesWith(DisplayObject other){
        Shape hitbox = mario.getHitbox();
        Shape otherHitbox = other.getHitbox();
        Rectangle2D otherHitboxRect = otherHitbox.getBounds2D();

        return hitbox.intersects(otherHitboxRect);
    }

    public void jump(){
        if (jumpTime == 0) {
            initY = mario.getPosition().y;
        }
        int y = (int) (initY + (velocity * jumpTime) + (0.5 * gravity * Math.pow(jumpTime, 2)));
        if (y > ground) {
            jumpTime = 0;
            mario.setPosition(new Point(mario.getPosition().x, ground));
           // System.out.print(mario.getPosition());
            jumping = false;
        }
        else {
            mario.setPosition(new Point(mario.getPosition().x, y));
            jumpTime = jumpTime + 0.5;
        }
    }

    public void fall(){
        if (fallTime == 0) {
            initY = mario.getPosition().y;
        }
        int y = (int) (initY +(0.5 * gravity * Math.pow(fallTime, 2)));
        if (y > ground) {
            fallTime = 0;
            mario.setPosition(new Point(mario.getPosition().x, ground));
            falling = false;
        }
        else {
            mario.setPosition(new Point(mario.getPosition().x, y));
            fallTime = fallTime + 0.5;
        }

    }

    public void boundLeft(){
        if(boundTime == 0){
            initX = mario.getPosition().x;
        }
        int x = (int) (initX + (boundVel*boundTime) + (0.5*accel*Math.pow(boundTime, 2)));
        if (x < 240){
            boundTime = 0;
            boundVel = 0;
            accel = 0;
            mario.setPosition(new Point(240, mario.getPosition().y));
            reboundLeft = false;
            reboundRight = false;
        }
        else{
            mario.setPosition(new Point(x, mario.getPosition().y));
            boundTime = boundTime + 1;
        }

    }

    public void boundRight(){
        if(boundTime == 0){
            initX = mario.getPosition().x;
        }
        int x = (int) (initX + (boundVel*boundTime) + (0.5*accel*Math.pow(boundTime, 2)));
        if (x > 400){
            boundTime = 0;
            boundVel = 0;
            accel = 0;
            mario.setPosition(new Point(400, mario.getPosition().y));
            reboundRight = false;
            reboundLeft = false;
        }
        else{
            mario.setPosition(new Point(x, mario.getPosition().y));
            boundTime = boundTime + 1;
        }
    }



    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */
    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);
        //reset game
        if (mario != null && pressedKeys.contains(KeyEvent.VK_ENTER)){
            colCount=20;
            win = false;
            done = false;
            reboundRight = false;
            reboundLeft = false;
            falling = false;
            jumping = false;
            mario = new AnimatedSprite("Mario",
                    "animations"+ File.separator+"mario"+File.separator+"mario_frontWalk_0.png",
                    new Point(0,0));
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

        /* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
        if(sounds!= null && colCount==0){
            sounds.playSoundEffect("die");
        }
        else if(sounds != null && win==true){
            sounds.playSoundEffect("woohoo");
        }
        else {
            if (mario != null) mario.update(pressedKeys);
            if (mario != null && trophy != null) {
                if (this.collidesWith(trophy)) {
                    win = true;
                }
            }

            if (mario != null && earth != null) {
                if (this.collidesWith(earth)) {
                    sounds.playSoundEffect("coin");

                    if (collided == false) {
                        this.colCount = this.colCount - 1;
                        collided = true;
                    }

                    //earth position: x = 300, y = 290, dimensions = 60
                    Point currPos =  mario.getPosition();
                    int x = currPos.x;
                    if(x <= 330){
                        this.boundVel = -15.0;
                        this.accel = 2.0;
                        this.reboundLeft = true;
                    }
                    else if(x > 330){
                        this.boundVel = 15.0;
                        this.accel = -2.0;
                        this.reboundRight = true;
                    }
                }
                else {
                    collided = false;
                }
            }

            if(this.reboundLeft==true){
                this.falling = false;
                this.boundLeft();
            }
            if (this.reboundRight==true){
                this.falling = false;
                this.boundRight();
            }

            if (pressedKeys.isEmpty() && (mario != null)) {
                if (mario.isPlaying()) {
                    mario.stopAnimation();
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
                this.jumping = true;
            }

            if(this.jumping==true){
                this.jump();
            }

            if((mario!=null) && (mario.getPosition().y < ground) && (!pressedKeys.contains(KeyEvent.VK_UP)) && (jumping!=true) &&(this.reboundRight!=true) && (this.reboundLeft!=true)){
                this.falling = true;
            }

            if(this.falling==true){
                this.fall();
            }

            if (pressedKeys.contains(KeyEvent.VK_UP)) {
                mario.setPosition(new Point(mario.getPosition().x,
                        mario.getPosition().y - 2));
            }
            if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                if(mario.getPosition().y < ground) {
                    mario.setPosition(new Point(mario.getPosition().x,
                            mario.getPosition().y + 5));
                }
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

            if (pressedKeys.contains(KeyEvent.VK_V)) {
                if (counter == 5) {
                    mario.setVisible(!mario.getVisible());
                    counter = 0;
                } else {
                    counter = counter + 1;
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_Z)) {
                if (mario.getAlpha() >= 0.01f) {
                    mario.setOldAlpha(mario.getAlpha());
                    mario.setAlpha(mario.getAlpha() - 0.01f);
                }
                if (earth.getAlpha() >= 0.01f) {
                    earth.setOldAlpha(earth.getAlpha());
                    earth.setAlpha(earth.getAlpha() - 0.01f);
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_X)) {
                if (mario.getAlpha() <= 0.99f) {
                    mario.setOldAlpha(mario.getAlpha());
                    mario.setAlpha(mario.getAlpha() + 0.01f);
                }
                if (earth.getAlpha() <= 0.99f) {
                    earth.setOldAlpha(earth.getAlpha());
                    earth.setAlpha(earth.getAlpha() + 0.01f);
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_A)) {
                mario.setScale(mario.getScale() + 0.01);
            }

            if (pressedKeys.contains(KeyEvent.VK_S)) {
                if (mario.getScale() >= 0.01) {
                    mario.setScale(mario.getScale() - 0.01);
                }
            }

            //CHANGING ANIMATION SPEED
            if (pressedKeys.contains(KeyEvent.VK_1)) {
                int currSpeed = mario.getAnimationSpeed();
                if (currSpeed > 20) {
                    mario.setAnimationSpeed(currSpeed - 5);
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_2)) {
                int currSpeed = mario.getAnimationSpeed();
                mario.setAnimationSpeed(currSpeed + 5);
            }

        }
    }

    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
     * the screen, we need to make sure to override this method and call mario's draw method.
     * */
    @Override
    public void draw(Graphics g){
        super.draw(g);
        


        //CHECK IF LOSING OR WINNING SCREEN NEEDS TO BE SHOWN
        if(mario!= null && colCount==0){
            String text = "YOU LOSE :( Enter to replay";
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            Font font = new Font("Arial", Font.PLAIN, 32);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(text);
            int height = fm.getHeight();
            g2d.dispose();
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, 0, fm.getAscent());
            g2d.dispose();
            Sprite display = new Sprite("display");
            display.setImage(img);
            display.setPosition(new Point(90, 200));
            display.draw(g);
        }
        else if(mario != null && win==true){
            String text = "YOU WIN!!!!! Enter to replay";
            BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            Font font = new Font("Arial", Font.PLAIN, 32);
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int width = fm.stringWidth(text);
            int height = fm.getHeight();
            g2d.dispose();
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g2d = img.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2d.setFont(font);
            fm = g2d.getFontMetrics();
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, 0, fm.getAscent());
            g2d.dispose();
            Sprite display = new Sprite("display");
            display.setImage(img);
            display.setPosition(new Point(90, 200));
            display.draw(g);

        }
        else {
            /* Same, just check for null in case a frame gets thrown in before Mario is initialized */
            if (mario != null) {

                mario.draw(g);

                //TEXT FOR KEEPING TRACK OF COLLISIONS
                String text = "Lives: " + this.colCount;
                BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = img.createGraphics();
                Font font = new Font("Arial", Font.PLAIN, 17);
                g2d.setFont(font);
                FontMetrics fm = g2d.getFontMetrics();
                int width = fm.stringWidth(text);
                int height = fm.getHeight();
                g2d.dispose();
                img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                g2d = img.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
                g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
                g2d.setFont(font);
                fm = g2d.getFontMetrics();
                g2d.setColor(Color.BLACK);
                g2d.drawString(text, 0, fm.getAscent());
                g2d.dispose();
                Sprite display = new Sprite("display");
                display.setImage(img);
                display.setPosition(new Point(200, 10));
                display.draw(g);

                //DRAW GROUND
                int groundY = (350);
                g2d =( Graphics2D) g;
                g2d.setPaint(new Color(0, 150, 0));
                RenderingHints rh = new RenderingHints(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                rh.put(RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY);

                g2d.setRenderingHints(rh);
                g2d.fillRect(0, groundY, 1000, 300);
            }

            if (earth != null) {
                earth.draw(g);
            }


            if (trophy != null) {
                trophy.draw(g);
            }

        }
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        LabFiveGame game = new LabFiveGame();
        game.start();

    }
}

