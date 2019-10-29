package edu.virginia.lab1test;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class LabThreeSimulator extends Game {

    /*planet and sun sprites*/
    private int tick;
    private static final double DEG_TO_RAD = Math.PI / 180;
    Sprite sun = new Sprite("Sun", "solarSystem" + File.separator+"sun.png", new Point(300, 300));
    Sprite earth = new Sprite("Earth", "solarSystem" + File.separator+"earth.png");
    Sprite jupiter = new Sprite("Jupiter", "solarSystem" + File.separator+"jupiter.png");
    Sprite earthMoon = new Sprite("EarthMoon", "solarSystem" + File.separator+"earthMoon.png");

    /**
     * Constructor. See constructor in Game.java for details on the parameters given
     * */
    public LabThreeSimulator(){
        super("Lab Three Simulator", 800,800);

        this.tick = 0;

        /*add children*/
        sun.addChild(earth);
//        sun.addChild(jupiter);
//        earth.addChild(earthMoon);

        earth.setPosition(earth.globalToLocal(new Point(450, 450)));
//        jupiter.setPosition(jupiter.globalToLocal(new Point(400, 400)));
//        earthMoon.setPosition(earthMoon.globalToLocal(new Point(500, 500)));

    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */


    @Override
    public void update(ArrayList<Integer> pressedKeys) {
        super.update(pressedKeys);

        if(earth != null) {
            earth.update(pressedKeys);
            tick++;
            System.out.println(earth.getPosition());
            double newX = (earth.getParentPosition().x - earth.getPosition().x) * Math.cos(this.tick * DEG_TO_RAD);
            double newY = (earth.getParentPosition().y - earth.getPosition().y) * Math.sin(this.tick * DEG_TO_RAD);
//            System.out.println("newX: " + newX + "\tnewY: " + newY);
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
