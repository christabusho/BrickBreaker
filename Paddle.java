package org.cis1200.brickBreaker;

import java.awt.*;

/*
* A paddle is the platform that helps us direct the ball
* and prevents the ball to go off the bottom of the screen
 */

public class Paddle extends GameObj {
    // the initial coordinates x and y of the paddle on the screen
    private static int initialXPos = 225;
    private static final int INITIALYPOS = 485;
    // the current velocities in x and y of the paddle
    private static int initialXVel = 0;
    private static int initialYVel = 0;
    private static int width = 50;
    private static final int HEIGHT = 15;



    public Paddle(int screenWidth, int screenHeight) {
        super(initialXVel, initialYVel, initialXPos, INITIALYPOS,
                width, HEIGHT, screenWidth, screenHeight);
    }

    public void reset() {
        setPx(initialXPos);
        setVx(initialXVel);
        setVy(initialYVel);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(getPx(), getPy(), width, HEIGHT);
    }
}
