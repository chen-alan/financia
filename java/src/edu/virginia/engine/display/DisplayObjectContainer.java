package edu.virginia.engine.display;

import edu.virginia.engine.display.DisplayObject;

import java.awt.*;
import java.util.ArrayList;

public class DisplayObjectContainer extends DisplayObject {

    private ArrayList<DisplayObject> children;

    public DisplayObjectContainer(String id) {
        super(id);
        this.children = new ArrayList<>();
    }

    public DisplayObjectContainer(String id, String imageFileName) {
        super(id, imageFileName);
        this.children = new ArrayList<>();
    }

    public DisplayObjectContainer(ArrayList<DisplayObject> newChildren) {
        this.children = newChildren;
    }

    /**
     * add a child to the end of the ArrayList children
     * @param child
     * @return true if this.children changed as a result of the call
     */
    public boolean addChild(DisplayObject child) {
        child.setParent(this);
        return this.children.add(child);
    }

    /**
     * add a child at index i of ArrayList children
     * @param child
     * @param i
     */
    public void addChildAtIndex(DisplayObject child, int i) {
        this.children.add(i, child);
    }

    /**
     * remove child with a specific id
     * @param id
     * @return true if this.children contained the specified element
     */
    public boolean removeChild(String id) {
        for (DisplayObject child : this.children) {
            if (child.getId() == id) {
                return this.children.remove(child);
            }
        }
        return false;
    }

    /**
     * remove child at index i
     * @param i
     * @return the element that was removed from the list
     */
    public DisplayObject removeByIndex(int i) {
        return this.children.remove(i);
    }

    /**
     * remove all children from the ArrayList children
     */
    public void removeAll() {
        this.children.clear();
    }

    /**
     * check if this.children contains specified element
     * @param child
     * @return true if the child is in the ArrayList children
     */
    public boolean contains(DisplayObject child) {
        return this.children.contains(child);
    }

    /**
     * get this.children
     * @return the ArrayList children
     */
    public ArrayList<DisplayObject> getChildren() {
        return this.children;
    }

    @Override
    public void draw(Graphics g){
        super.draw(g); //Draw myself
        Graphics2D g2d = (Graphics2D) g;

        if (this.children != null) {
            for (DisplayObject child : this.children) {
                child.draw(g);
            }
        }

        applyTransformations(g2d); //apply my transformations to my children
        reverseTransformations(g2d);
    }


}

