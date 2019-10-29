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
    private int earthRadius;
    private int earthMoonRadius;
    private int jupiterRadius;
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
        earth.addChild(earthMoon);
        sun.addChild(jupiter);

        earth.setPosition(earth.globalToLocal(new Point(450, 450)));
        this.earthRadius = earth.getPosition().x;
        earthMoon.setPosition(earthMoon.globalToLocal(new Point(500, 500)));
        this.earthMoonRadius = earthMoon.getPosition().x;
        jupiter.setPosition(jupiter.globalToLocal(new Point(700, 700)));
        this.jupiterRadius = jupiter.getPosition().x;

    }

    /**
     * Engine will automatically call this update method once per frame and pass to us
     * the set of keys (as strings) that are currently being pressed down
     * */


    @Override
    public void update(ArrayList<Integer> pressedKeys) {
        super.update(pressedKeys);
        tick++;

        System.out.println(pressedKeys);

        if(earth != null) {
            earth.update(pressedKeys);
            double newX = (earth.getParentPosition().x - this.earthRadius) * Math.cos(this.tick * DEG_TO_RAD * 2) + 30;
            double newY = (earth.getParentPosition().y - this.earthRadius) * Math.sin(this.tick * DEG_TO_RAD * 2) + 10;
            Point newPosition = new Point((int) newX, (int) newY);
            earth.setPosition(newPosition);
        }

        if(earth != null) {
            earthMoon.update(pressedKeys);
            double newX = (this.earthMoonRadius) * Math.cos(this.tick * DEG_TO_RAD * 6) + 20;
            double newY = (this.earthMoonRadius) * Math.sin(this.tick * DEG_TO_RAD * 6) + 10;
            Point newPosition = new Point((int) newX, (int) newY);
            earthMoon.setPosition(newPosition);
        }

        if(jupiter != null) {
            jupiter.update(pressedKeys);
            double newX = (jupiter.getParentPosition().x - this.jupiterRadius - 200) * Math.cos(this.tick / 2 * DEG_TO_RAD) + 30;
            double newY = (jupiter.getParentPosition().y - this.jupiterRadius - 200) * Math.sin(this.tick / 2 * DEG_TO_RAD) + 30;
            Point newPosition = new Point((int) newX, (int) newY);
            jupiter.setPosition(newPosition);
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
