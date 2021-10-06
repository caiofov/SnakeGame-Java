    package snakegame;
    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
    import java.util.Random;

    public class GamePanel extends JPanel implements ActionListener{

    static final int SCREEN_WIDTH = 1300;
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 175;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
        
        //drawing snake body parts
        for (int i =0 ; i< bodyParts; i++) {
        	if (i==0) { //snake head
        		g.setColor(Color.green);
        		g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
        		
        	}
        	else {
        		g.setColor(new Color(45,180,0));
        		g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
        	}
        }
        
        
        //drawing some lines to make a grid
        //it will ease our work
        for(int i=0; i<SCREEN_HEIGHT; i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
        }
        
    
    }
    public void newApple(){
    	appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    	appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    
    public void move(){
        for(int i = bodyParts; i>0; i--) { //position of the body parts in the back are now the same of the body parts in the front
        	x[i] = x[i-1]; //x coordinate
        	y[i] = y[i-1]; //y coordinate
        }
        switch(direction) {
	        case 'U': //up
	        	y[0] = y[0] - UNIT_SIZE;
	        	break;
	        
		    case 'D': //down
		    	y[0] = y[0] + UNIT_SIZE;
		    	break;
		    
		    case 'L': //left
		    	x[0] = x[0] - UNIT_SIZE;
		    	break;
		    
		    case 'R': //right
		    	x[0] = x[0] + UNIT_SIZE;
		    	break;
	    }
        
    }
    public void checkApple() {

    }
    public void checkCollisions() {
        //checks if head collides with body
    	for(int i = bodyParts; i>0;i--) {
        	if((x[0] == x[i]) && (y[0] == y[i])) { //if the head has the same location of any other body part
        		running = false; //game over
        	}
        }
    	//checks if head touches the borders
    	if(x[0] < 0) { //left border
    		running = false;
    	}
    	if(x[0] > SCREEN_WIDTH) { //right border
    		running = false;
    	}
    	if(y[0] < 0) { //top border
    		running = false;
    	}
    	if(y[0] > SCREEN_HEIGHT) { //bottom border
    		running = false;
    	}
    	
    	if(!running) {
    		timer.stop(); //stops time
    	}
    }
    public void gameOver(Graphics g) {
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) { //if the game is running
        	move();
        	checkApple();
        	checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            
            
        }
    }
}