package org.cis1200.brickBreaker;

// imports necessary libraries for Java swing

import org.cis1200.brickBreaker.Screen;

import javax.swing.*;
import java.awt.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunBrickBreaker implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for
        // local variables.

        // Top-level frame in which game components live.
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("BRICK BREAKER");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        JLabel status = new JLabel("Press Start");
        status_panel.add(status);
        // score label
        JLabel score = new JLabel("0");
        status_panel.add(score);
        String filePath = "files/levelThree.txt";
        // Main playing area
        final Screen game = new Screen(status, score, filePath);
        frame.add(game, BorderLayout.CENTER);

//        JLabel ballLives = new JLabel(Integer.toString(game.getBall().getLives()));
//        frame.add(ballLives, BorderLayout.WEST);
        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);


        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> game.reset());
        control_panel.add(reset);

        // Start game
        final JButton start = new JButton("Start");
        start.addActionListener(e -> game.startPressed());
        control_panel.add(start);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        game.restartGame();
    }
}