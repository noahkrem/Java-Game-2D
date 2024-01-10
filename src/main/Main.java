package main;

import java.io.File;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        // Testing purposes, find what is the root dir:
        System.out.println(new File("").getAbsolutePath());

        // SETUP -------
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2D Game");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        // Sets the window to the preferred size and layout (i.e. our GamePanel class)
        window.pack();

        window.setLocationRelativeTo(null);
        // We want to perform this step last in our setup
        window.setVisible(true);



        gamePanel.startGameThread();
    }
}