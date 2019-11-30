package edu.virginia.lab1test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import edu.virginia.engine.display.*;
import edu.virginia.engine.util.SoundManager;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class FinalProject extends Game{

    //Top progress bar
    private DisplayObject levelIcon;
    private DisplayObject cashIcon;
    private DisplayObject accountIcon;
    private DisplayObject levelDisp;
    private DisplayObject cashDisp;
    private DisplayObject accountDisp;
    private int level;
    private int cashBal;
    private int accountBal;

    //back button
    private DisplayObject backbutton;

    //Background object
    private DisplayObject background;
    //city icons
    private DisplayObject city1;
    private DisplayObject city2;
    //building icons
    private DisplayObject office;
    private DisplayObject bank;
    private DisplayObject shop;
    private DisplayObject tutorial;

    //mayor presentation slide variables
    private int slideNum;
    private boolean nextSlide;
    private int city1Slides;
    private int city2Slides;


    //state variables
    private int state; //0=worldMap, 1=city1, 2=city2
    private int building; //0=none, 1=mayor, 2=bank, 3=store, 4=tutoring


    /*Create mario sprite*/
    AnimatedSprite mario = new AnimatedSprite("Mario",
            "animations"+ File.separator+"mario"+File.separator+"mario_frontWalk_0.png",
            new Point(480,500));


    /*initialize sound manager*/
    SoundManager sounds = new SoundManager();



    /**
     * Constructor. See constructor in Game.java for details on the parameters given
     * */
    public FinalProject() {
        super("Financia", 1000, 707);

        //set state
        state = 0;
        building = 0;
        slideNum = 0;
        nextSlide = false;
        city1Slides = 4; //STATIC, DOES NOT CHANGE, SET TO HOW MANY SLIDES THERE ARE
        city2Slides = 0; //STATIC, DOES NOT CHANGE, SET TO HOW MANY SLIDES THERE ARE

        //set levels and balances
        level = 1;
        cashBal = 10;
        accountBal = 0;

        //INITIALIZE ALL OBJECTS
        //background object: update image based on state and building variables
        background = new DisplayObject("world", "backgrounds"+File.separator+"world.jpg");

        //city 1 and 2 in world map
        city1 = new DisplayObject("city1", "buildingsIcons"+File.separator+"city1icon.png");
        city2 = new DisplayObject("city2", "buildingsIcons"+File.separator+"city2icon.png");
        city1.setPosition(new Point(100, 250));
        city2.setPosition(new Point(600, 250));

        //buildings in city map
        office = new DisplayObject("office", "buildingsIcons"+File.separator+"office.png");
        bank = new DisplayObject("bank", "buildingsIcons"+File.separator+"bank.png");
        shop = new DisplayObject("shop", "buildingsIcons"+File.separator+"shop.png");
        tutorial = new DisplayObject("tutorial", "buildingsIcons"+File.separator+"tutorial.png");
        office.setPosition(new Point(0, 210));
        bank.setPosition(new Point(250, 230));
        shop.setPosition(new Point(500, 250));
        tutorial.setPosition(new Point(720, 250));

        //top progress bar icons
        levelIcon = new DisplayObject("level icon","buildingsIcons"+File.separator+"level.png");
        cashIcon = new DisplayObject("cash icon","buildingsIcons"+File.separator+"cashicon.png");
        accountIcon = new DisplayObject("account icon","buildingsIcons"+File.separator+"balanceicon.png");
        levelIcon.setPosition(new Point(10, 10));
        cashIcon.setPosition(new Point(110, 10));
        accountIcon.setPosition(new Point(240, 2));
        //top progress bar values
        levelDisp = new DisplayObject("level display");
        cashDisp = new DisplayObject("cash display");
        accountDisp = new DisplayObject("account display");
        levelDisp.setImage(updateProgress(level));
        cashDisp.setImage(updateProgress(cashBal));
        accountDisp.setImage(updateProgress(accountBal));
        levelDisp.setPosition(new Point(80, 25));
        cashDisp.setPosition(new Point(180, 25));
        accountDisp.setPosition(new Point(310, 25));

        //back button
        backbutton = new DisplayObject("back button", "buildingsIcons" + File.separator+"backbutton.png");
        backbutton.setPosition(new Point(770, 600));


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

        //load and play sounds
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

    /** set background based on state and building **/
    public void setBackground(){
        //world map
        if (state==0){
            background.setImage(background.readImage("backgrounds"+File.separator+"world.jpg"));
        }
        //city 1
        else if(state==1 && building==0){
            background.setImage(background.readImage("backgrounds"+File.separator+"city1.jpg"));
        }
        //city 2
        else if(state==2 && building==0){
            background.setImage(background.readImage("backgrounds"+File.separator+"city2.jpeg"));
        }
        //mayor's office
        else if(building==1){
            if (state==1) {
                background.setImage(background.readImage("mayorPres1" + File.separator + "0.jpg"));
                slideNum = 0;
            }
            else if (state==2) {
                background.setImage(background.readImage("mayorPres2" + File.separator + "0.jpg"));
                slideNum = 0;
            }
        }
        //bank
        else if(building==2){
            background.setImage(background.readImage("backgrounds"+File.separator+"atm.jpg"));
        }
        //shop
        else if(building==3){
            background.setImage(background.readImage("backgrounds"+File.separator+"market.jpg"));
        }
        //shop
        else if(building==4){
            background.setImage(background.readImage("backgrounds"+File.separator+"classroom.jpg"));
        }
    }

    /** make progress bar numbers into images **/
    public BufferedImage updateProgress(int num){
        String text = ""+ num;
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 30);
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
        return img;
    }


    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */
    @Override
    public void update(ArrayList<Integer> pressedKeys){
        super.update(pressedKeys);

            //Movement of mario
            if (mario != null) mario.update(pressedKeys);
            if (pressedKeys.isEmpty() && (mario != null)) {
                if (mario.isPlaying()) {
                    mario.stopAnimation();
                }
            }

            //reset slide boolean
            if(pressedKeys.isEmpty() && nextSlide==true){
                nextSlide = false;
            }

            //COLLISION UPDATE CHECKS
            //collisions in world map
            if(state==0){
                if(mario!=null&& city1!=null&& this.collidesWith(city1)){
                    state = 1;
                    this.setBackground();
                    mario.setPosition(new Point(100, 600));
                }
                else if(mario!=null&& city2!=null&& this.collidesWith(city2)){
                    state = 2;
                    this.setBackground();
                    mario.setPosition(new Point(100, 600));
                }
            }
            //collisions in cities
            else if(mario!=null&&backbutton!=null&&office!=null&&bank!=null&&shop!=null&&tutorial!=null){

                //back button collisions
                if(this.collidesWith(backbutton) && building==0){
                    state = 0;
                    this.setBackground();
                    mario.setPosition(new Point(480, 500));
                }

                //building collisions
                if (building==0 ){
                    if (this.collidesWith(office)) {
                        building = 1;
                        this.setBackground();
                    } else if (this.collidesWith(bank)) {
                        building = 2;
                        this.setBackground();
                    } else if (this.collidesWith(shop)) {
                        building = 3;
                        this.setBackground();
                    } else if (this.collidesWith(tutorial)) {
                        building = 4;
                        this.setBackground();
                    }
                }

            }

            //Key Events
            if (pressedKeys.contains(KeyEvent.VK_UP)) {
                mario.setPosition(new Point(mario.getPosition().x,
                        mario.getPosition().y - 2));
                mario.animate("backWalk");
            }
            if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                mario.setPosition(new Point(mario.getPosition().x,
                        mario.getPosition().y + 2));
                mario.animate("frontWalk");
            }
            if (pressedKeys.contains(KeyEvent.VK_LEFT)) {

                //MAYOR'S OFFICE
                if(building==1 && nextSlide==false){
                    nextSlide = true;
                    //first city
                    if(state==1){
                        if(slideNum==0){slideNum = city1Slides;}
                        else{slideNum = slideNum - 1;}
                        String filename = ""+slideNum+".jpg";
                        background.setImage(background.readImage("mayorPres1" + File.separator + filename));
                    }
                    //second city
                    else if(state==2){
                        if(slideNum==0){slideNum = city2Slides;}
                        else{slideNum = slideNum - 1;}
                        String filename = ""+slideNum+".jpg";
                        background.setImage(background.readImage("mayorPres2" + File.separator + filename));
                    }

                }

                //else mario moves as normal
                else {
                    mario.setPosition(new Point(mario.getPosition().x - 2,
                            mario.getPosition().y));
                    mario.animate("leftWalk");
                }
            }
            if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {

                //MAYOR'S OFFICE
                if(building==1 && nextSlide==false){
                    nextSlide = true;
                    //first city
                    if(state==1){
                        if(slideNum==city1Slides){slideNum = 0;}
                        else{slideNum = slideNum + 1;}
                        String filename = ""+slideNum+".jpg";
                        background.setImage(background.readImage("mayorPres1" + File.separator + filename));
                    }
                    //second city
                    else if(state==2){
                        if(slideNum==city2Slides){slideNum = 0;}
                        else{slideNum = slideNum + 1;}
                        String filename = ""+slideNum+".jpg";
                        background.setImage(background.readImage("mayorPres2" + File.separator + filename));
                    }

                }

                //else mario moves as normal
                else {
                    mario.setPosition(new Point(mario.getPosition().x + 2,
                            mario.getPosition().y));
                    mario.animate("rightWalk");
                }
            }
            if (pressedKeys.contains(KeyEvent.VK_Q)) {
                if(building!=0){
                    int newXpos = 0;
                    if(building==1){newXpos = 100;}
                    else if(building==2){newXpos = 300;}
                    else if(building==3){newXpos = 600;}
                    else if(building==4){newXpos = 800;}
                    building=0;
                    this.setBackground();
                    mario.setPosition(new Point(newXpos, 490));
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

        //Draw background
        if(background!=null){
            background.draw(g);
        }

        //Draw top bar
        if(levelIcon!=null&&levelDisp!=null&&cashIcon!=null&&cashDisp!=null&&accountIcon!=null&&accountDisp!=null){
            levelIcon.draw(g);
            levelDisp.draw(g);
            cashIcon.draw(g);
            cashDisp.draw(g);
            accountIcon.draw(g);
            accountDisp.draw(g);
        }

        //Draw back button
        if(state!=0 && building==0){
            if(backbutton!=null){
                backbutton.draw(g);
            }
        }

        //Cases to draw objects
        if(state==0){
            if(city1!=null){
                city1.draw(g);
            }
            if(city2!=null){
                city2.draw(g);
            }
        }
        else if(building==0){
            if(office!=null){
                office.draw(g);
            }
            if(bank!=null){
                bank.draw(g);
            }
            if(shop!=null){
                shop.draw(g);
            }
            if(tutorial!=null){
                tutorial.draw(g);
            }
        }

        /* Check for null in case a frame gets thrown in before Mario is initialized */
        if (mario != null && building!=1 && building!=2 && building!=4) {
            mario.draw(g);
        }

    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        FinalProject game = new FinalProject();
        game.start();

    }
}
