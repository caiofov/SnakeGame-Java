package snakegame;

// import java.awt.Graphics;
// import java.awt.Dimension;
// import java.awt.Color;

// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.awt.event.KeyAdapter;
// import java.awt.event.KeyEvent;

// import javax.crypto.KeyAgreement;
// import javax.swing.JPanel;
// import javax.swing.Timer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


import java.util.Random;

public class GamePanel extends JPanel{
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; //how big we want the objects in this game
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE; //how many objects we can have in our screen
    static final int DELAY = 75; //defines speed of the game
    //cordinates
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6; //snake length
    int applesEaten; //number of apples eaten by the snake
    //apple location
    int appleX;
    int appleY;
    char direction = 'R'; //to where the snake is facing (starts facing right)
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.startGame();
    }
    public void startGame(){
        newApple(); //spawns a new apple
        running = true; //starts running the game
        timer = new Timer(DELAY, this); //sets timer
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.draw(g);
    }
    public void draw(Graphics g){

        //drawing lines to make a grid (makes seeing easier)
        for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
        }
    }
    public void newApple(){

    }
    
    public void move(){

    }
    public void checkApple(){

    }
    public void checkCollision(){

    }
    public void gameOver(Graphics g){

    }
    // @Override
    // public void actionPerformed(ActionEvent e){
        
    // }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

        }

    }
}