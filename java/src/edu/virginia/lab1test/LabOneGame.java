package edu.virginia.lab1test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game{

	//counter for visibility toggle
	private int counter;

	/* Create a sprite object for our game. We'll use mario */
	 Sprite mario = new Sprite("Mario", "Mario.png");

	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
		super("Lab One Test Game", 500, 300);
		counter = 1;
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

		if (pressedKeys.contains(KeyEvent.VK_UP)) {
			mario.setPosition(new Point(mario.getPosition().x,
					mario.getPosition().y - 1));
		}
		if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
			mario.setPosition(new Point(mario.getPosition().x,
					mario.getPosition().y + 1));
		}
		if (pressedKeys.contains(KeyEvent.VK_LEFT)) {
			mario.setPosition(new Point(mario.getPosition().x - 1,
					mario.getPosition().y));
		}
		if (pressedKeys.contains(KeyEvent.VK_RIGHT)) {
			mario.setPosition(new Point(mario.getPosition().x + 1,
					mario.getPosition().y));
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
			if(counter == 3) {
				mario.setVisible(!mario.getVisible());
				counter = 1;
			}
			else {
				counter = counter + 1;
			}
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
		LabOneGame game = new LabOneGame();
		game.start();

	}
}
