package org.cis1200.brickBreaker;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Ball extends GameObj{

    private static int initialPx = 245;
    private static int initialPy = 475;
    private static int initialXVel = 0;
    private static int initialYVel = 0;

    private static final int width = 10;
    private static final int height = 10;

    private int maxXpos;
    private int maxYpos;

    private int lives;

    private int hits;

    public Ball(int screenWidth, int screenHeight) {
        super (initialXVel, initialYVel, initialPx, initialPy, width, height, screenWidth, screenHeight);
        maxXpos = screenWidth - width;
        maxYpos = screenHeight - height;
        this.lives = 3;
        this.hits = 0;
    }

    public int getLives() {
        return lives;
    }

    public boolean wentOff() {
        return getPy() > maxXpos;
    }

    public void reset() {
        setPx(245);
        setPy(475);
        setVx(0);
        setVy(0);
    }

    public void decreaseLives() {
        lives--;
    }

    public void resetLives() {
        lives = 3;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.magenta);
        g.fillOval(getPx(), getPy(), width, height);
    }

    @Override
    public void move() {
        setPx(Math.min(Math.max(getPx() + getVx(), 0), maxXpos));
        setPy(Math.max(getPy() + getVy(), 0));
    }

        @Override
        public org.cis1200.brickBreaker.Direction hitWall() {
            if (getPx() + getVx() < 0) {
                return org.cis1200.brickBreaker.Direction.LEFT;
            } else if (getPx() + getVx() > maxXpos) {
                return org.cis1200.brickBreaker.Direction.RIGHT;
            }
            if (getPy() + getVy() < 0) {
                return org.cis1200.brickBreaker.Direction.UP;
            } else if (getPy() + getVy() > maxYpos) {
                return org.cis1200.brickBreaker.Direction.UP;
            } else {
                return null;
            }
        }
    }


