package org.cis1200.brickBreaker;

import java.io.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLOutput;
import java.util.*;


import javax.swing.*;
import javax.swing.Timer;

public class Screen extends JPanel {
    // the state of the game logic

    // the paddle controlled by the left and right keys
    private Paddle paddle;
    // the ball that will bounce
    private Ball ball;
    // a 2D array that stocks the position of the bricks
    private Coordinates[][] bricksPos;

    // the fixed bricks and their state(if it is broken or not)
    //private Map<Brick,Boolean> bricksState ;

    private LinkedList<Brick> bricks;

    // the linked list of powerUps to draw
    private LinkedList<PowerUp> powerUps;

    private boolean running = false; // whether the game is running
    private JLabel status; // Current status text, i.e. "Running..."


    private boolean isGameOver;

    private String filePath;

    private JLabel score; // the score text

    private int currScore; // the actual score

    // Game constants
    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 500;
    public static final int PADDLE_VEL = 6;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;


    public Screen(JLabel status, JLabel score, String filePath) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called update() that actually does everything
        // that should be done in a single time step.
        Timer timer = new Timer(INTERVAL, e -> update());
        timer.start(); // MAKE SURE TO START THE TIMER!


        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the paddle to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    paddle.setVx(-PADDLE_VEL);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    paddle.setVx(PADDLE_VEL);
                }
            }

            public void keyReleased(KeyEvent e) {
                paddle.setVx(0);
                paddle.setVy(0);
            }
        });
        this.score = score;
        this.status = status;
        this.filePath = filePath;
        isGameOver = false;
    }

    void restartGame() {
        //  change the positions and the velocities to the initial ones
        // Of the paddle
        // Of the ball
        paddle = new Paddle(SCREEN_WIDTH, SCREEN_HEIGHT);
        ball = new Ball(SCREEN_WIDTH, SCREEN_HEIGHT);
        bricks = new LinkedList<>();
        powerUps = new LinkedList<>();
        Level gameLevel = new Level(filePath);
        bricksPos = gameLevel.getBrickPos();
        for (int i = 0; i < bricksPos.length; i++) {
            for (int j = 0; j < bricksPos[i].length; j++) {
                if(bricksPos[i][j] != null) {
                    Brick brick = new Brick(bricksPos[i][j].getX(),bricksPos[i][j].getY(),
                            SCREEN_WIDTH, SCREEN_HEIGHT);
                    Double random = Math.random();
                    if (random >= 0.95) {
                        PowerUp a = new ScoreUp(bricksPos[i][j].getX(),bricksPos[i][j].getY());
                        brick.setPowerUp(a);
                        powerUps.add(a);
                    } else if (random >= 0.90){
                        PowerUp b = new Extender(bricksPos[i][j].getX(), bricksPos[i][j].getY());
                        brick.setPowerUp(b);
                        powerUps.add(b);
                    } else if (random >= 0.85){
                        PowerUp c = new SlowDown(bricksPos[i][j].getX(), bricksPos[i][j].getY());
                        brick.setPowerUp(c);
                        powerUps.add(c);
                    } else if (random >= 0.80) {
                        PowerUp d = new ExtraLives(bricksPos[i][j].getX(), bricksPos[i][j].getY());
                        brick.setPowerUp(d);
                        powerUps.add(d);
                    }
                    bricks.add(brick);
                }
            }
        }

        running = true;
        status.setText("Press start!");
        isGameOver = false;
        requestFocusInWindow();

    }

    void update() {
        if (running) {
            paddle.move();
            ball.move();
          // now make the ball bounce when it hits the wall or the paddle
            ball.bounce(ball.hitWall());
            ball.bounce(ball.hitObj(paddle));
            ListIterator<Brick> it = bricks.listIterator();
            while (it.hasNext()) {
                Brick curr = it.next();
                if (ball.intersects(curr)) {
                    ball.bounce(ball.hitObj(curr));
                    it.remove();
                    if (curr.hidesPowerUp()) {
                        PowerUp power = curr.getPowerUp();
                        power.setHit(true);
                        currScore += 7;
                        score.setText(Integer.toString(currScore));
                    }
                }
            }

            ListIterator<PowerUp> itTwo = powerUps.listIterator();
            LinkedList<PowerUp> toRemove = new LinkedList();
            while (itTwo.hasNext()) {
                PowerUp next = itTwo.next();
                    if (next.isHit()) {
                        next.move();
                        if (next.intersects(paddle)) {
                            System.out.println("here");
                            next.apply();
                            toRemove.add(next);
                        }
                    }
            }
            powerUps.removeAll(toRemove);



            if (ball.wentOff()) {
                if (ball.getLives() > 1) {
                    ball.decreaseLives();
                    paddle.reset();
                    ball.reset();
                } else {
                    running = false;
                    isGameOver = true;
                    status.setText("You lost!");
                }
            }

            if (bricks.isEmpty()) {
                running = false;
                isGameOver = true;
                status.setText("You won!");
            }
            repaint();
        }
    }

    // start over the game
    void reset() {
        this.currScore = 0;
        score.setText(Integer.toString(currScore));
        restartGame();
        running = true;
        isGameOver = false;
        requestFocusInWindow();
    }

    // start pressed
    void startPressed() {
        ball.setVx(-3);
        ball.setVy(-5);
        status.setText("Playing...");
        requestFocusInWindow();
    }




    @Override
    public void paintComponent(Graphics g) {
       super.paintComponent(g);
        paddle.draw(g);
        ball.draw(g);
        for (Brick brick : bricks) {
            brick.draw(g);
            for (PowerUp p : powerUps) {
                if (p.isHit()) {
                    p.draw(g);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    }


    public abstract class PowerUp extends GameObj{

        private boolean isHit = false;

        private static int initialVel = 0;

        private static final int width = 10;
        private static final int height = 10;

        public PowerUp(int initialXPos , int initialYPos) {
            super(initialVel, initialVel, initialXPos, initialYPos,
                    width, height, SCREEN_WIDTH, SCREEN_HEIGHT);
        }

        public boolean isHit() {
            return this.isHit;
        }

        public void setHit(boolean b) {
            this.isHit = b;
        }


        public abstract void draw(Graphics g);


        @Override
        public void move() {
            this.setVy(3);
            this.setPy(this.getPy() + this.getVy());
        }

        public abstract void apply();

    }

    public class ScoreUp extends PowerUp {
        public ScoreUp(int x, int y){
            super(x,y);
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(Color.YELLOW);
            g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        }

        @Override
        public void apply() {
            currScore += 100;
            score.setText(Integer.toString(currScore));
        }
    }

    public class Extender extends PowerUp {

        public Extender(int x, int y) {
            super(x, y);
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(Color.RED);
            g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        }

        @Override
        public void apply() {
            paddle.setWidth(100);
        }
    }

    public class SlowDown extends PowerUp {

        public SlowDown(int x, int y) {
            super(x,y);
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(Color.GREEN);
            g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        }

        @Override
        public void apply() {
            ball.setVx(-2);
            ball.setVy(-2);
        }
    }

    public class ExtraLives extends PowerUp {

        public ExtraLives(int x, int y) {
            super(x,y);
        }

        @Override
        public void draw(Graphics g) {
            g.setColor(Color.CYAN);
            g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
        }

        @Override
        public void apply() {
            ball.resetLives();
        }
    }

}
