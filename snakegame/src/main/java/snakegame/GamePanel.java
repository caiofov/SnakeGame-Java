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
        
    	if(running) {  //if the game is running
    		g.setColor(Color.red); //sets apple color
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); //draws the apple
            
            //drawing snake body parts
            for (int i =0 ; i< bodyParts; i++) {
            	if (i==0) { //snake head
            		g.setColor(Color.green); //sets head color
            		g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE); //draws snake head
            		
            	}
            	else {
            		g.setColor(new Color(45,180,0)); //sets body color
            		g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255))); //colorful snake
            		g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE); //draws snake body parts
            	}
            }
            
            //drawing the score
        	g.setColor(Color.red);
        	g.setFont( new Font("Ink Free",Font.BOLD,40));
        	FontMetrics metrics = getFontMetrics(g.getFont()); //helps centralizing the text
        	//drawing a centralized text in the top
        	g.drawString("Score: "+ applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());
        	
        	
        	//drawing some lines to make a grid
            //it will ease our work
//        	g.setColor(Color.gray);
//            for(int i=0; i<SCREEN_HEIGHT; i++){
//                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
//            }
    	}
    	
    	else { //if the game is not running
    		gameOver(g);
    	}
    }
    public void newApple(){ //draw a new apple in a random position
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
    public void checkApple() { //checks if snake head has the same position of the apple generated
    	if((x[0] == appleX) && (y[0] == appleY)) {
    		bodyParts++; //increments body parts
    		applesEaten++; //increments apple eaten count
    		newApple(); //spawns a new apple
    	}
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
    	//Writes a game over text
    	g.setColor(Color.red);
    	g.setFont( new Font("Ink Free",Font.BOLD,75));
    	FontMetrics metrics1 = getFontMetrics(g.getFont()); //helps centralizing the text
    	//drawing a centralized text:
    	g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    	
    	//drawing the score
    	g.setFont(new Font("Ink Free",Font.BOLD,40));
    	FontMetrics metrics2 = getFontMetrics(g.getFont()); //helps centralizing the text
    	//drawing a centralized text in the top
    	g.drawString("Score: "+ applesEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());
    
    }
    
    //@Override
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
            switch(e.getKeyCode()) {
            	case KeyEvent.VK_LEFT: //left arrow
            		if(direction != 'R') { //if the snake is not facing right
            			direction = 'L'; //then we can turn left
            		}
            		break;
            	case KeyEvent.VK_RIGHT: //right arrow
            		if(direction != 'L') { //if the snake is not facing left
            			direction = 'R'; //then we can turn right
            		}
            		break;
            	case KeyEvent.VK_UP: //up arrow
            		if(direction != 'D') { //if the snake is not facing down
            			direction = 'U'; //then we can turn up
            		}
            		break;
            	
            	case KeyEvent.VK_DOWN: //down arrow
            		if(direction != 'U') { //if the snake is not facing up
            			direction = 'D'; //then we can turn down
            		}
            		break;
            
            }
            
        }
    }
}