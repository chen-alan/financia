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
    private DisplayObject creditIcon;
    private DisplayObject levelDisp;
    private DisplayObject cashDisp;
    private DisplayObject accountDisp;
    private DisplayObject creditDisp;
    private int level;
    private int cashBal;
    private int accountBal;
    private int creditBal;

    //back button
    private DisplayObject backbutton;

    //level updates
    private boolean level1Done;
    private boolean level2Done;
    private boolean level3Done;

    //levelup messages
    private DisplayObject levelUp1;
    private DisplayObject levelUp2;
    private boolean levelUp;


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

    //NPC objects
    private DisplayObject npc1;
    private DisplayObject npc2;
    private DisplayObject npc3;

    //Message objects
    private DisplayObject msg1;
    private DisplayObject msg2;
    private DisplayObject msg2Fail;
    private DisplayObject msg3;
    private boolean showtask1;
    private boolean showtask2;
    private boolean show2Fail;
    private boolean showtask3;

    //Payment option objects
    private DisplayObject payCash;
    private DisplayObject payDebit;
    private DisplayObject payCredit;
    private int paymentMethod; //1=cash, 2=debit, 3=credit

    //store messages and purchase icons
    private DisplayObject storeError;
    private DisplayObject storeSuccess;
    private int storeMessage; //0=none, 1=complete, 2=error
    private int nextPurchase; //where next purchase icon goes
    private boolean storePress;

    //TASK1 ice cream objects
    private DisplayObject ice1;
    private DisplayObject ice2;
    private DisplayObject ice3;
    private boolean ice1Bought;
    private boolean ice2Bought;
    private boolean ice3Bought;
    private DisplayObject ice1icon;
    private DisplayObject ice2icon;
    private DisplayObject ice3icon;

    //TASK2 school supply objects
    private DisplayObject marker;
    private DisplayObject notebook;
    private DisplayObject pencil;
    private boolean markerBought;
    private boolean notebookBought;
    private boolean pencilBought;
    private DisplayObject markerIcon;
    private DisplayObject pencilIcon;
    private DisplayObject notebookIcon;

    //mayor presentation slide variables
    private int slideNum;
    private boolean nextSlide;
    private int city1Slides;
    private int city2Slides;

    //bank variables
    private int bankScreen; //0=home, 1=deposit, 2=withdraw, 3=pay bills, 4=updated, 5=error
    private boolean bankPress; //key pressed

    //tutoring center variables
    private int currQuestionCity1; //current question number, -1 = no questions left
    private boolean onQ; //on a question
    private boolean onFeed; //on feedback
    private boolean qPress; //key pressed
    private boolean q1City1; //were these questions successfully answered?
    private boolean q2City1;
    private boolean q3City1;


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

        //levels
        level1Done = false;
        level2Done = false;
        level3Done = false;

        //set mayor variables
        slideNum = 0;
        nextSlide = false;
        city1Slides = 15; //STATIC, DOES NOT CHANGE, SET TO HOW MANY SLIDES THERE ARE
        city2Slides = 6; //STATIC, DOES NOT CHANGE, SET TO HOW MANY SLIDES THERE ARE

        //set bank variables
        bankScreen = 0;
        bankPress = false;

        //set tutoring center variables
        currQuestionCity1 = 1;
        onQ = true;
        onFeed = false;
        qPress = false;
        q1City1 = false;
        q2City1 = false;
        q3City1 = false;

        //set levels and balances
        level = 1;
        cashBal = 0;
        accountBal = 0;
        creditBal = 0;

        //INITIALIZE ALL OBJECTS
        //background object: update image based on state and building variables
        background = new DisplayObject("world", "backgrounds"+File.separator+"world.jpg");

        //Level Up messages
        levelUp1 = new DisplayObject("lu1", "levelUp"+File.separator+"level1.jpg");
        levelUp2 = new DisplayObject("lu2", "levelUp"+File.separator+"level2.jpg");
        levelUp1.setPosition(new Point(150, 0));
        levelUp2.setPosition(new Point(150, 0));
        levelUp = false;

        //NPC objects
        npc1 = new DisplayObject("npc1", "NPC"+File.separator+"1.png");
        npc2 = new DisplayObject("npc2", "NPC"+File.separator+"2.png");
        npc3 = new DisplayObject("npc3", "NPC"+File.separator+"3.png");
        npc1.setPosition(new Point(300, 550));
        npc2.setPosition(new Point(300, 550));
        npc3.setPosition(new Point(300, 550));

        //message objects
        msg1 = new DisplayObject("msg1", "messages"+File.separator+"1.png");
        msg2 = new DisplayObject("msg2", "messages"+File.separator+"2.png");
        msg2Fail = new DisplayObject("msg2Fail", "messages"+ File.separator+"2a.png");
        msg3 = new DisplayObject("msg3", "messages"+File.separator+"3.png");
        msg1.setPosition(new Point(325, 520));
        msg2.setPosition(new Point(325, 520));
        msg2Fail.setPosition(new Point(325, 520));
        msg3.setPosition(new Point(325, 520));
        showtask1=false;
        showtask2=false;
        show2Fail = false;
        showtask3=false;

        //store messages
        storeError = new DisplayObject("storeError", "store"+File.separator+"error.jpg");
        storeSuccess = new DisplayObject("storeSuccess", "store"+File.separator+"complete.jpg");
        storeError.setPosition(new Point(250, 200));
        storeSuccess.setPosition(new Point(250, 200));
        storeMessage = 0;
        nextPurchase = 0;
        storePress = false;


        //payment options
        payCash = new DisplayObject("payCash", "paymentMethod"+File.separator+"cash.png");
        payDebit = new DisplayObject("payDebit", "paymentMethod"+File.separator+"debit.png");
        payCredit = new DisplayObject("payCredit", "paymentMethod"+File.separator+"credit.png");
        payCash.setPosition(new Point (400, 10));
        payDebit.setPosition(new Point (400, 10));
        payCredit.setPosition(new Point (400, 10));
        paymentMethod = 1; //initialize to cash

        //ice cream objects for TASK 1
        ice1 = new DisplayObject("ice1", "iceCream"+File.separator+"0.png");
        ice2 = new DisplayObject("ice2", "iceCream"+File.separator+"1.png");
        ice3 = new DisplayObject("ice3", "iceCream"+File.separator+"2.png");
        ice1.setPosition(new Point(200, 230));
        ice2.setPosition(new Point(400, 230));
        ice3.setPosition(new Point(600, 230));
        ice1icon = new DisplayObject("ice1icon", "iceCream"+File.separator+"0s.png");
        ice2icon = new DisplayObject("ice2icon", "iceCream"+File.separator+"1s.png");
        ice3icon = new DisplayObject("ice3icon", "iceCream"+File.separator+"2s.png");
        ice1Bought = false;
        ice2Bought = false;
        ice3Bought = false;

        //school supply objects for TASK 2
        marker = new DisplayObject("marker", "markers"+File.separator+"marker.png");
        notebook = new DisplayObject("notebook", "markers"+File.separator+"notebook.jpg");
        pencil = new DisplayObject("pencil", "markers"+File.separator+"pencil.png");
        marker.setPosition(new Point(200, 230));
        notebook.setPosition(new Point(400, 230));
        pencil.setPosition(new Point(600, 230));
        markerIcon = new DisplayObject("markerIcon", "markers"+File.separator+"markerS.png");
        notebookIcon = new DisplayObject("notebookIcon", "markers"+File.separator+"notebookS.jpg");
        pencilIcon = new DisplayObject("pencilIcon", "markers"+File.separator+"pencilS.png");
        markerBought = false;
        notebookBought = false;
        pencilBought = false;

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
        creditIcon = new DisplayObject("credit icon","buildingsIcons"+File.separator+"creditcard.png");
        levelIcon.setPosition(new Point(5,10));
        cashIcon.setPosition(new Point(80, 13));
        accountIcon.setPosition(new Point(184, 0));
        creditIcon.setPosition((new Point(285, 22)));
        //top progress bar values
        levelDisp = new DisplayObject("level display");
        cashDisp = new DisplayObject("cash display");
        accountDisp = new DisplayObject("account display");
        creditDisp = new DisplayObject("credit display");
        levelDisp.setImage(updateProgress(level));
        cashDisp.setImage(updateProgress(cashBal));
        accountDisp.setImage(updateProgress(accountBal));
        creditDisp.setImage(updateProgress(0));
        levelDisp.setPosition(new Point(60, 25));
        cashDisp.setPosition(new Point(150, 25));
        accountDisp.setPosition(new Point(254, 25));
        creditDisp.setPosition(new Point(345, 25));
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
            background.setImage(background.readImage("bankScreens"+File.separator+"home.jpg"));
        }
        //shop
        else if(building==3){
            background.setImage(background.readImage("backgrounds"+File.separator+"market.jpg"));
            mario.setPosition(new Point(200, 500));
        }
        //tutoring center
        else if(building==4){
            if (state==1) {
                if (currQuestionCity1!=-1) {
                    String filename = "" + currQuestionCity1 + ".jpg";
                    background.setImage(background.readImage("tutor1" + File.separator + filename));
                }
                else{
                    background.setImage(background.readImage("tutor1" + File.separator + "done.jpg"));
                }
                onFeed = false;
                onQ = true;
            }
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

            System.out.println("bankScreen: " + this.bankScreen + "\t state: " + this.state);
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
            //reset bank boolean
            if(pressedKeys.isEmpty() && bankPress==true){
                bankPress = false;
            }

            //reset tutoring boolean
            if(pressedKeys.isEmpty() && qPress==true){
                qPress = false;
            }

            //reset store boolean
            if(pressedKeys.isEmpty() && storePress==true){
                storePress = false;
            }

            //COLLISION UPDATE CHECKS
            //collisions in world map
            if(state==0){
                if(mario!=null&& city1!=null&& this.collidesWith(city1)){
                    state = 1;
                    this.setBackground();
                    mario.setPosition(new Point(100, 600));
                }
                else if(mario!=null&& city2!=null&& this.collidesWith(city2) && this.level1Done && this.level2Done){
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
                    showtask1=false;
                    showtask2=false;
                    show2Fail = false;
                    showtask3=false;
                }

                //npc collisions
                if(this.collidesWith(npc1)&&level==1&&building==0&&state==1){
                    if(ice1Bought==true||ice2Bought==true||ice3Bought==true) {
                        //LEVEL UP
                        level1Done = true;
                        nextPurchase = 0;
                        levelUp = true;
                        level = 2;
                        levelDisp.setImage(updateProgress(level));
                    }
                    else{
                        showtask1=true;
                    }
                }
                if(this.collidesWith(npc2)&&level==2&&building==0&&state==1){
                    if(markerBought==true){
                        //LEVEL UP
                        level2Done = true;
                        nextPurchase = 0;
                        levelUp = true;
                        level = 3;
                        levelDisp.setImage(updateProgress(level));
                    }
                    else if(pencilBought==true||notebookBought==true){
                        showtask2 = false;
                        show2Fail = true;
                    }
                    else {
                        showtask2 = true;
                    }
                }
                if(this.collidesWith(npc3)&&level==3&&building==0&&state==2){
                    showtask3=true;
                }

                //building collisions
                if (building==0 ){
                    if (this.collidesWith(office)) {
                        building = 1;
                        this.setBackground();
                        showtask1=false;
                        showtask2=false;
                        show2Fail = false;
                        showtask3=false;
                    } else if (this.collidesWith(bank)) {
                        building = 2;
                        this.setBackground();
                        showtask1=false;
                        showtask2=false;
                        show2Fail = false;
                        showtask3=false;
                    } else if (this.collidesWith(shop)) {
                        building = 3;
                        this.setBackground();
                        showtask1=false;
                        showtask2=false;
                        show2Fail = false;
                        showtask3=false;
                    } else if (this.collidesWith(tutorial)) {
                        if (level != 3) {
                            building = 4;
                            this.setBackground();
                            showtask1 = false;
                            showtask2 = false;
                            show2Fail = false;
                            showtask3 = false;
                        }
                    }
                }
                else if(building==3&&level==1){ //STORE COLLISIONS FOR LEVEL 1
                    if(this.collidesWith(ice1) && ice1Bought==false &&storeMessage==0){
                        if(cashBal < 3){
                            storeMessage=2; //error
                        }
                        else{
                            cashBal = cashBal - 3;
                            cashDisp.setImage(updateProgress(cashBal));
                            ice1icon.setPosition(new Point(nextPurchase, 610));
                            ice1Bought = true;
                            storeMessage=1; //success
                            nextPurchase = nextPurchase + 60;
                        }
                    }
                    else if(this.collidesWith(ice2) && ice2Bought==false&&storeMessage==0){
                        if(cashBal < 5){
                            storeMessage=2; //error
                        }
                        else{
                            cashBal = cashBal - 5;
                            cashDisp.setImage(updateProgress(cashBal));
                            ice2icon.setPosition(new Point(nextPurchase, 610));
                            ice2Bought = true;
                            storeMessage=1; //success
                            nextPurchase = nextPurchase + 60;
                        }
                    }
                    else if(this.collidesWith(ice3) && ice3Bought==false&&storeMessage==0){
                        if(cashBal < 10){
                            storeMessage=2; //error
                        }
                        else{
                            cashBal = cashBal - 10;
                            cashDisp.setImage(updateProgress(cashBal));
                            ice3icon.setPosition(new Point(nextPurchase, 610));
                            ice3Bought = true;
                            storeMessage=1; //success
                            nextPurchase = nextPurchase + 60;
                        }
                    }
                }
                else if (building==3&&level==2){//STORE COLLISIONS FOR LEVEL 2
                    if(this.collidesWith(marker) && markerBought==false &&storeMessage==0){
                        if(accountBal < 5){
                            storeMessage=2; //error
                        }
                        else{
                            accountBal = accountBal - 5;
                            accountDisp.setImage(updateProgress(accountBal));
                            markerIcon.setPosition(new Point(nextPurchase, 610));
                            markerBought = true;
                            storeMessage=1; //success
                            nextPurchase = nextPurchase + 60;
                        }
                    }
                    else if(this.collidesWith(notebook) && notebookBought==false&&storeMessage==0){
                        if(accountBal < 7){
                            storeMessage=2; //error
                        }
                        else{
                            accountBal = accountBal - 7;
                            accountDisp.setImage(updateProgress(accountBal));
                            notebookIcon.setPosition(new Point(nextPurchase, 610));
                            notebookBought = true;
                            storeMessage=1; //success
                            nextPurchase = nextPurchase + 60;
                        }
                    }
                    else if(this.collidesWith(pencil) && pencilBought==false&&storeMessage==0){
                        if(accountBal < 10){
                            storeMessage=2; //error
                        }
                        else{
                            accountBal = accountBal - 10;
                            accountDisp.setImage(updateProgress(accountBal));
                            pencilIcon.setPosition(new Point(nextPurchase, 610));
                            pencilBought = true;
                            storeMessage=1; //success
                            nextPurchase = nextPurchase + 60;
                        }
                    }
                }

            }

            //Key Events
            if (pressedKeys.contains(KeyEvent.VK_UP)) {
                mario.setPosition(new Point(mario.getPosition().x,
                        mario.getPosition().y - 5));
                mario.animate("backWalk");
            }
            if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                mario.setPosition(new Point(mario.getPosition().x,
                        mario.getPosition().y + 5));
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

                //TUTORING CENTER
                else if(building==4 && onFeed==true && qPress == false){
                    qPress = true;
                    if (currQuestionCity1!=-1) {
                        String filename = "" + currQuestionCity1 + ".jpg";
                        background.setImage(background.readImage("tutor1" + File.separator + filename));
                    }
                    else{
                        background.setImage(background.readImage("tutor1" + File.separator + "done.jpg"));
                    }
                    onFeed = false;
                    onQ = true;
                }

                //else mario moves as normal
                else {
                    mario.setPosition(new Point(mario.getPosition().x - 5,
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

                //TUTORING CENTER
                else if(building==4 && onFeed==true && qPress == false){
                    qPress = true;
                    if (currQuestionCity1!=-1) {
                        String filename = "" + currQuestionCity1 + ".jpg";
                        background.setImage(background.readImage("tutor1" + File.separator + filename));
                    }
                    else{
                        background.setImage(background.readImage("tutor1" + File.separator + "done.jpg"));
                    }
                    onFeed = false;
                    onQ = true;
                }

                //else mario moves as normal
                else {
                    mario.setPosition(new Point(mario.getPosition().x + 5,
                            mario.getPosition().y));
                    mario.animate("rightWalk");
                }
            }

            //TUTORING CENTER KEY PRESSES
            if(pressedKeys.contains(KeyEvent.VK_A)){

                //BANK
                if (building==2 && bankPress==false && (bankScreen==0||bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 0){ //home screen
                        bankScreen = 1; //set to deposit
                        background.setImage(background.readImage("bankScreens" + File.separator + "deposit.jpg"));
                    }
                    else if (bankScreen == 1){ //deposit
                        if(cashBal < 1){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 1;
                            accountBal = accountBal + 1;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 1){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 1;
                            accountBal = accountBal - 1;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                }

                //TUTORING CENTER
                else if (building==4 && onQ== true && qPress==false && currQuestionCity1!=-1){
                    qPress = true;
                    if(state==1){ //in first city
                        if(currQuestionCity1==1){ //a is wrong
                            background.setImage(background.readImage("tutor1" + File.separator + "wrong.jpg"));
                        }
                        else if(currQuestionCity1==2){ //a is right
                            background.setImage(background.readImage("tutor1" + File.separator + "correct.jpg"));
                            q2City1 = true;
                            accountBal = accountBal + 5;
                            accountDisp.setImage(updateProgress(accountBal));
                        }
                        else if(currQuestionCity1==3){ //a is wrong
                            background.setImage(background.readImage("tutor1" + File.separator + "wrong.jpg"));
                        }

                        //UPDATE: go to next question or display finished screen
                        if(q1City1==true && q2City1==true && q3City1==true){
                            currQuestionCity1= -1;
                        }
                        else{ //update to next question
                            int newQ = currQuestionCity1;
                            while(true) {
                                //go through question numbers
                                if (newQ != 3) {
                                    newQ = newQ + 1;
                                } else {
                                    newQ = 1;
                                }
                                if ((newQ == 1 && q1City1==false)|| (newQ == 2 && q2City1==false) || (newQ==3 && q3City1 == false)) {
                                    break;
                                }
                            }
                            currQuestionCity1 = newQ;
                        }
                        onQ = false;
                        onFeed = true;
                    }
                }
            }
            if(pressedKeys.contains(KeyEvent.VK_B)){

                //BANK
                if (building==2 && bankPress==false && (bankScreen==0||bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 0){ //home screen
                        bankScreen = 2; //set to withdraw
                        background.setImage(background.readImage("bankScreens" + File.separator + "withdraw.jpg"));
                    }
                    else if (bankScreen == 1){ //deposit
                        if(cashBal < 2){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 2;
                            accountBal = accountBal + 2;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 2){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 2;
                            accountBal = accountBal - 2;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                }

                //TUTORING CENTER
                if (building==4 && onQ== true && qPress==false && currQuestionCity1!=-1){
                    qPress = true;
                    if(state==1){ //in first city
                        if(currQuestionCity1==1){ //b is right
                            background.setImage(background.readImage("tutor1" + File.separator + "correct.jpg"));
                            q1City1 = true;
                            accountBal = accountBal + 5;
                            accountDisp.setImage(updateProgress(accountBal));
                        }
                        else if(currQuestionCity1==2){ //b is wrong
                            background.setImage(background.readImage("tutor1" + File.separator + "wrong.jpg"));
                        }
                        else if(currQuestionCity1==3){ //b is right
                            background.setImage(background.readImage("tutor1" + File.separator + "correct.jpg"));
                            q3City1 = true;
                            accountBal = accountBal + 5;
                            accountDisp.setImage(updateProgress(accountBal));
                        }

                        //UPDATE: go to next question or display finished screen
                        if(q1City1==true && q2City1==true && q3City1==true){
                            currQuestionCity1= -1;
                        }
                        else{ //update to next question
                            int newQ = currQuestionCity1;
                            while(true) {
                                //go through question numbers
                                if (newQ != 3) {
                                    newQ = newQ + 1;
                                } else {
                                    newQ = 1;
                                }
                                if ((newQ == 1 && q1City1==false)|| (newQ == 2 && q2City1==false) || (newQ==3 && q3City1 == false)) {
                                    break;
                                }
                            }
                            currQuestionCity1 = newQ;
                        }
                        onQ = false;
                        onFeed = true;
                    }
                }
            }
            if(pressedKeys.contains(KeyEvent.VK_C)){

                //BANK
                if (building==2 && bankPress==false && (bankScreen==0||bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 0){ //home screen
                        bankScreen = 3; //set to pay bills
                        background.setImage(background.readImage("bankScreens" + File.separator + "bill.jpg"));
                    }
                    else if (bankScreen == 1){ //deposit
                        if(cashBal < 5){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 5;
                            accountBal = accountBal + 5;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 5){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 5;
                            accountBal = accountBal - 5;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                }

                //TUTORING CENTER
                if (building==4 && onQ== true && qPress==false && currQuestionCity1!=-1){
                    qPress = true;
                    if(state==1){ //in first city
                        if(currQuestionCity1==1){ //c is wrong
                            background.setImage(background.readImage("tutor1" + File.separator + "wrong.jpg"));
                        }
                        else if(currQuestionCity1==2){ //c is wrong
                            background.setImage(background.readImage("tutor1" + File.separator + "wrong.jpg"));
                        }
                        else if(currQuestionCity1==3){ //c is wrong
                            background.setImage(background.readImage("tutor1" + File.separator + "wrong.jpg"));
                        }

                        //UPDATE: go to next question or display finished screen
                        if(q1City1==true && q2City1==true && q3City1==true){
                            currQuestionCity1= -1;
                        }
                        else{ //update to next question
                            int newQ = currQuestionCity1;
                            while(true) {
                                //go through question numbers
                                if (newQ != 3) {
                                    newQ = newQ + 1;
                                } else {
                                    newQ = 1;
                                }
                                if ((newQ == 1 && q1City1==false)|| (newQ == 2 && q2City1==false) || (newQ==3 && q3City1 == false)) {
                                    break;
                                }
                            }
                            currQuestionCity1 = newQ;
                        }
                        onQ = false;
                        onFeed = true;
                    }
                }
            }

            if(pressedKeys.contains(KeyEvent.VK_D)){
                //BANK
                if (building==2 && bankPress==false && (bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 1){ //deposit
                        if(cashBal < 10){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 10;
                            accountBal = accountBal + 10;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 10){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 10;
                            accountBal = accountBal - 10;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                }
            }

            if(pressedKeys.contains(KeyEvent.VK_E)){
                //BANK
                if (building==2 && bankPress==false && (bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 1){ //deposit
                        if(cashBal < 20){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 20;
                            accountBal = accountBal + 20;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 20){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 20;
                            accountBal = accountBal - 20;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                }
            }

            if(pressedKeys.contains(KeyEvent.VK_F)){
                //BANK
                if (building==2 && bankPress==false && (bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 1){ //deposit
                        if(cashBal < 50){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 50;
                            accountBal = accountBal + 50;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 50){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 50;
                            accountBal = accountBal - 50;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                }
            }

            if(pressedKeys.contains(KeyEvent.VK_G)){
                //BANK
                if (building==2 && bankPress==false && (bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 1){ //deposit
                        if(cashBal < 100){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 100;
                            accountBal = accountBal + 100;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 100){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 100;
                            accountBal = accountBal - 100;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                }
            }

            if(pressedKeys.contains(KeyEvent.VK_H)){
                //BANK
                if (building==2 && bankPress==false && (bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 1){ //deposit
                        if(cashBal < 500){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 500;
                            accountBal = accountBal + 500;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 500){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 500;
                            accountBal = accountBal - 500;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                }
            }

            if(pressedKeys.contains(KeyEvent.VK_I)){
                //BANK
                if (building==2 && bankPress==false && (bankScreen==1||bankScreen==2)){
                    bankPress = true;
                    if (bankScreen == 1){ //deposit
                        if(cashBal < 1000){ //error
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal - 1000;
                            accountBal = accountBal + 1000;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
                    else{ //withdraw
                        if(accountBal < 1000){
                            bankScreen = 5;
                            background.setImage(background.readImage("bankScreens" + File.separator + "error.jpg"));
                        }
                        else{
                            cashBal = cashBal + 1000;
                            accountBal = accountBal - 1000;
                            cashDisp.setImage(updateProgress(cashBal));
                            accountDisp.setImage(updateProgress(accountBal));
                            bankScreen = 4;
                            background.setImage(background.readImage("bankScreens" + File.separator + "update.jpg"));
                        }
                    }
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
                    storeMessage = 0;
                    this.setBackground();
                    mario.setPosition(new Point(newXpos, 490));
                }
                if(levelUp==true){
                    levelUp= false;
                }
            }

            if (pressedKeys.contains(KeyEvent.VK_Z)){
                if(building==2){ //bank
                    bankScreen = 0;
                    background.setImage(background.readImage("bankScreens" + File.separator + "home.jpg"));
                }
                else if(building==3 && storeMessage!=0){//store
                    mario.setPosition(new Point(200, 500));
                    storeMessage = 0;
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
            creditIcon.draw(g);
            creditDisp.draw(g);
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
            //draw corresponding NPC
            if(level==1&&state==1){ npc1.draw(g); }
            else if(level==2&&state==1){ npc2.draw(g); }
            else if (level==3&&state==2){ npc3.draw(g); }

            //draw messages
            if(showtask1==true){ msg1.draw(g); }
            else if(showtask2==true){ msg2.draw(g); }
            else if(show2Fail==true){ msg2Fail.draw(g); }
            else if (showtask3==true){ msg3.draw(g); }

            //draw buildings
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
        } else if (building == 3 && level == 1) {
            if(ice1Bought==false) {
                ice1.draw(g);
            }
            if(ice2Bought==false) {
                ice2.draw(g);
            }
            if(ice3Bought==false) {
                ice3.draw(g);
            }
            payCash.draw(g);

            if(storeMessage!=0){
                if(storeMessage==1){
                    storeSuccess.draw(g);
                }
                else{
                    storeError.draw(g);
                }
            }
        }
        else if (building == 3 && level == 2) {
            if(markerBought==false) {
                marker.draw(g);
            }
            if(notebookBought==false) {
                notebook.draw(g);
            }
            if(pencilBought==false) {
                pencil.draw(g);
            }
            payDebit.draw(g);

            if(storeMessage!=0){
                if(storeMessage==1){
                    storeSuccess.draw(g);
                }
                else{
                    storeError.draw(g);
                }
            }
        }

        //level up message
        if(levelUp==true){
            if(level1Done==true){
                levelUp1.draw(g);
            }
            if (level2Done == true) {
                levelUp2.draw(g);
            }
        }

        //draw purchased objects
        if(level1Done==false){
            if(ice1Bought==true){
                ice1icon.draw(g);
            }
            if(ice2Bought==true){
                ice2icon.draw(g);
            }
            if(ice3Bought==true){
                ice3icon.draw(g);
            }
        }
        else if(level1Done && level2Done == false){
            if(markerBought==true){
                markerIcon.draw(g);
            }
            if(notebookBought==true){
                notebookIcon.draw(g);
            }
            if(pencilBought==true){
                pencilIcon.draw(g);
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
