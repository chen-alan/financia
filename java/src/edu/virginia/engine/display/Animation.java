package edu.virginia.engine.display;


public class Animation {
    private String id;
    private int startFrame;
    private int endFrame;

    public Animation(String newId, int newStartFrame, int newEndFrame) {
        this.id = newId;
        this.startFrame = newStartFrame;
        this.endFrame = newEndFrame;
    }

    public String getId() {
        return this.id;
    }

    public int getStartFrame() {
        return this.startFrame;
    }

    public int getEndFrame() {
        return this.endFrame;
    }

    public void setId(String newId) {
        this.id = newId;
    }

    public void setStartFrame(int newStartFrame) {
        this.startFrame = newStartFrame;
    }

    public void setEndFrame(int newEndFrame) {
        this.endFrame = newEndFrame;
    }
}
