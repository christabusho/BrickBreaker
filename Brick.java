package org.cis1200.brickBreaker;

import java.awt.*;

public class Brick extends GameObj implements Comparable {

    private static final int width = 40;

    private static final int height = 15;

    private int xPos;
    private int yPos;
    // Track if the brick has been hit this shot.

    private static final int vel = 0;


    private boolean isHitThisShot;

    private boolean hidesPowerUp;

    private Screen.PowerUp power;

///   private final Color color;

    public Brick (int x, int y, int screenWidth, int screenHeight) {
        super(vel,vel, x, y, width, height, screenWidth, screenHeight);
        xPos = x;
        yPos = y;
        //this.color = color;
        hidesPowerUp = false;
        isHitThisShot = false;
        power = null;
    }


    /**
     * Return whether this target is hit this round.
     */
    public boolean isHit() {
        return isHitThisShot;
    }

    /**
     * Setter function for whether the brick is hit this time.
     */
    public void setHitThisShot(boolean hit) {
        isHitThisShot = hit;
    }


    public boolean hidesPowerUp() {
        return power != null;
    }

    public Screen.PowerUp getPowerUp() {
        return this.power;
    }
    public void setPowerUp(Screen.PowerUp p) {
        this.power = p;
    }

    @Override
    public void draw(Graphics g) {
            g.setColor(Color.BLUE);
            g.fillRect(xPos, yPos,50,20 );
    }

    @Override
    public int compareTo(Object o) {
        return -1;
    }


}
