package edu.virginia.lab1test;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;

import java.awt.*;
import java.io.File;

public class LabThreeSimulator extends Game {

    /*planet and sun sprites*/
    Sprite sun = new Sprite("Sun", "solarSystem" + File.separator+"sun.png", new Point(100,100));
    Sprite earth = new Sprite("Earth", "solarSystem" + File.separator+"earth.png");
    Sprite jupiter = new Sprite("Jupiter", "solarSystem" + File.separator+"jupiter.png");
    Sprite earthMoon = new Sprite("EarthMoon", "solarSystem" + File.separator+"earthMoon.png");
    Sprite jupiterMoon = new Sprite("JupiterMoon", "solarSystem" + File.separator+"jupiterMoon.png");
    Sprite neptune = new Sprite("Neptune", "solarSystem" + File.separator+"neptune.png");
    Sprite comet = new Sprite("Comet", "solarSystem" + File.separator+"comet.png");

    /**
     * Constructor. See constructor in Game.java for details on the parameters given
     * */
    public LabThreeSimulator(){
        super("Lab Three Simulator", 800,800);

        /*add children*/
        sun.addChild(earth);
        sun.addChild(jupiter);
        sun.addChild(neptune);
        sun.addChild(comet);
        earth.addChild(earthMoon);
        jupiter.addChild(jupiterMoon);

        earth.setPosition(earth.globalToLocal(new Point(150, 150)));
        System.out.println(earth.getPosition());
        System.out.println(sun.getPosition());

    }

    /**
     * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
     * the screen, we need to make sure to override this method and call mario's draw method.
     * */
    @Override
    public void draw(Graphics g){
        super.draw(g);

        /* Same, just check for null in case a frame gets thrown in before Mario is initialized */
        if(sun != null) sun.draw(g);
    }

    /**
     * Quick main class that simply creates an instance of our game and starts the timer
     * that calls update() and draw() every frame
     * */
    public static void main(String[] args) {
        LabThreeSimulator game = new LabThreeSimulator();
        game.start();
    }

}
