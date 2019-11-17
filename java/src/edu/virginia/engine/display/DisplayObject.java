package edu.virginia.engine.display;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A very basic display object for a java based gaming engine
 *
 *
 * */
public class DisplayObject {

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;

	private Point position;
	private Point pivotPoint;
	private int rotation;
	private boolean visible;
	private float alpha;
	private float oldAlpha;
	private double scale;
	private DisplayObject parent;
	private Point parentPosition;
	private double zoom;
	private boolean stationary;
	private boolean hasPhysics;


	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
		this.position = new Point(0, 0);
		this.pivotPoint = new Point(0, 0);
		this.rotation = 0;
		this.alpha = 1.0f;
		this.oldAlpha = 0.0f;
		this.scale = 1.0;
		this.visible = true;
		this.parent = null;
		this.parentPosition = new Point(0, 0);
		this.zoom = 1;
		this.hasPhysics = false;
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
		this.position = new Point(0, 0);
		this.pivotPoint = new Point(0, 0);
		this.rotation = 0;
		this.alpha = 1.0f;
		this.oldAlpha = 0.0f;
		this.scale = 1.0;
		this.visible = true;
		this.parent = null;
		this.parentPosition = new Point(0, 0);
		this.zoom = 1;
		this.hasPhysics = false;
	}

	public DisplayObject() {
		this.setId(null);
		this.position = new Point(0, 0);
		this.pivotPoint = new Point(0, 0);
		this.rotation = 0;
		this.alpha = 1.0f;
		this.oldAlpha = 0.0f;
		this.scale = 1.0;
		this.visible = true;
		this.parent = null;
		this.parentPosition = new Point(0, 0);
		this.zoom = 1;
		this.hasPhysics = false;
	}

	public void setPhysics(boolean phys){
		this.hasPhysics = phys;
	}

	public boolean getPhysics(){
		return this.hasPhysics;
	}

	public void setStationary(boolean newStationary) {
		this.stationary = newStationary;
	}

	public boolean getStationary() {
		return this.stationary;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/* LAB 1 content */

	public Point getPosition() { return this.position; }

	public void setPosition(Point newPoint) { this.position = newPoint; }

	public Point getPivotPoint() { return this.pivotPoint; }

	public void setPivotPoint(Point newPivotPoint) { this.pivotPoint = newPivotPoint; }

	public int getRotation() { return this.rotation; }

	public void setRotation(int newRotation) { this.rotation += newRotation; }

	public double getScale() { return this.scale; }

	public void setScale(double x) { this.scale = x; }

	public float getAlpha() { return this.alpha; }

	public void setAlpha(float a) { this.alpha = a; }

	public float getOldAlpha() { return this.oldAlpha; }

	public void setOldAlpha(float a) { this.oldAlpha = a; }

	public boolean getVisible() { return this.visible; }

	public void setVisible(boolean x) { this.visible = x;}

	public DisplayObject getParent() { return this.parent; }

	public double getZoom() { return this.zoom; }

	public void setZoom(double z) { this.zoom = z;}

	/**
	 * NEW HITBOX FXN
	 * @return AffineTransform
	 */
    public Shape getHitbox() {
        AffineTransform at = new AffineTransform();
        at.rotate(this.rotation * (Math.PI / 180), this.pivotPoint.x, this.pivotPoint.y);
		at.scale(this.scale, this.scale);

		Shape s;
		if (this.getId().equals("Mario")) {
			s = new Rectangle((int)(this.position.x), (int)(this.position.y), (int) (this.displayImage.getWidth()), (int) (this.displayImage.getHeight()));
		}
		else {
			s = new Rectangle(this.position.x, this.position.y, (int) (this.displayImage.getWidth()), (int) (this.displayImage.getHeight()));
		}

		return at.createTransformedShape(s);
    }


	/**Metho.dds to set and get parent**/
	public void setParent(DisplayObject newParent) {
		this.parent = newParent;
		this.setParentPosition(newParent.getPosition());
	}

	public Point getParentPosition() { return this.getParent().getPosition(); }

	public void setParentPosition(Point newPoint) { this.parentPosition = newPoint; }

	/**
	 * given a point in the local coordinate system, return its corresponding point
	 * in the global coordinate system
	 * @param p
	 * @return Point object with coordinates in global coordinate system
	 */
	public Point localToGlobal(Point p) {
		if (this.parent == null) {
			return p;
		}

		Point parentGlobalPosition = this.parent.localToGlobal(this.parent.getPosition());
		int globalX = (int)(parentGlobalPosition.getX() + p.getX());
		int globalY = (int)(parentGlobalPosition.getY() + p.getY());

		return new Point(globalX, globalY);
	}

	/**
	 * given a point in the global coordinate system, return its corresponding point
	 * in the DisplayObject's own coordinate system
	 * @param p
	 * @return Point object with coordinates in DisplayObject's own coordinate system
	 */
	public Point globalToLocal(Point p) {
		if (this.parent == null) {
			return p;
		}

		Point parentGlobalPosition = this.parent.localToGlobal(this.parent.getPosition());
		int localX = (int)(p.getX() - parentGlobalPosition.getX());
		int localY = (int)(p.getY() - parentGlobalPosition.getY());

		return new Point(localX, localY);
	}

	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * */
	protected void update(ArrayList<Integer> pressedKeys) {

	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {

		if (displayImage != null) {

			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			//g2d.draw(this.getHitbox());
			if (this.stationary) {
				g2d.drawImage(displayImage, this.position.x,
						this.position.y,
						(int) (this.getUnscaledWidth()),
						(int) (this.getUnscaledHeight()), null);
			}
			applyTransformations(g2d);
			if (!this.stationary) {
				if (this.visible) {
					g2d.drawImage(displayImage, this.position.x,
							this.position.y,
							(int) (this.getUnscaledWidth()),
							(int) (this.getUnscaledHeight()), null);
				}
			}
			/* Actually draw the image, perform the pivot point translation here */
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			reverseTransformations(g2d);
		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(this.position.x / 64, this.position.y / 64);
		g2d.rotate(Math.toRadians(this.getRotation()), this.pivotPoint.x,
			this.pivotPoint.y);
		g2d.scale(this.scale, this.scale);
		float curAlpha;
		this.oldAlpha = curAlpha = ((AlphaComposite)
				g2d.getComposite()).getAlpha();
		g2d.setComposite(AlphaComposite.getInstance(3, curAlpha *
				this.alpha));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(3,
				this.oldAlpha));
		g2d.scale(1/this.scale, 1/this.scale);
		g2d.rotate(-Math.toRadians(this.getRotation()), this.pivotPoint.x,
				this.pivotPoint.y);
		g2d.translate(-this.position.x / 64, -this.position.y / 64);
	}

}
