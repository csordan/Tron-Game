package TRON;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import acm.breadboards.OneButtonBreadboard;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.toys.PixelGrid;
import acm.util.SoundClip;

public class tronwithtimer extends GraphicsProgram implements java.awt.event.KeyListener {

	JLabel label;
	JTextArea textArea;
	
	Background background = new Background();
	TronButton tronButton = new TronButton();
	
	int speed1 = 0;
	int speed2 = 0;
	
	int WIDTH = 80;
	int HEIGHT = 60;
	
	int[][] field = new int[HEIGHT][WIDTH];
	Player player1 = new Player(WIDTH/2 + 14,HEIGHT/2,1);
	Player player2 = new Player(WIDTH/2 - 15,HEIGHT/2,3);
	PowerUp power = new PowerUp((int)(WIDTH * Math.random()),(int)(HEIGHT * Math.random()), 4);
	
	boolean gameEnd;
	boolean gameEnd2;
	boolean gameStarted;
	
	
	PixelGrid pg;
	
	Timer timer;
	Timer timer2;
	Timer timer3;
	Timer timer4;
	
	public class TimerActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			int r;
			int c;
			
			if (gameEnd) {
				timer.stop();
				power.setPowerTimer(0);
			}
			
			for (r = 0; r < HEIGHT; r++) {
				for (c = 0; c < WIDTH; c++) {
					if (field[r][c] == 0 && c!=0 && r!=HEIGHT-1) {
					
						pg.plot(c, r, Color.BLACK);
						
						if (power.getLocationX() == c && power.getLocationY() == r) {

							if (power.getPowerTimer() > 450 && power.getPowerTimer() <= 500 && power.getType() != 4) {
								pg.plot(c, r, Color.GRAY);
							}
							else {
						    	if (power.getType() >= 0 && power.getType() < 1) {
						    		pg.plot(c, r, Color.GREEN);
						    	}
						    	else if (power.getType() >= 1 && power.getType() < 2) {
						    		pg.plot(c, r, Color.YELLOW);
						    	}
						    	else if (power.getType() >= 2 && power.getType() < 2.7) {
						    		pg.plot(c, r, Color.WHITE);
						    	}
						    	else if (power.getType() >= 2.7 && power.getType() < 3) {
						    		pg.plot(c, r, Color.PINK);
						    	}
							}
						}
					    
						
					}
					 
					if (r == 0 | r == HEIGHT-1) {
					    	pg.plot(c, r, Color.CYAN);
					}
					 
					if (c == 0 | c == WIDTH-1) {
					    	pg.plot(c, r, Color.CYAN);
					}
					 
					//player1 is blue, player2 is red
					else if (field[r][c] == 1) {
						pg.plot(c, r, Color.BLUE);
					}
					else if (field[r][c] == 2) {
						pg.plot(c, r, Color.RED);
					}	
				}
			}
			
			//powerTimer++ --> adjust all powerup if statements to reset powerTimer to 0 when power has been collected and is 
			//being reset;
			
			power.setPowerTimer(power.getPowerTimer() + 1);
			
			if (power.getPowerTimer() > 500 && power.getPowerTimer() < 800) {
				power.setType(4);;
			}
			
			else if (power.getPowerTimer() == 800){
				power.relocate(field, WIDTH, HEIGHT);
				power.setPowerTimer(0);
			}
		
			
		}
	}
	
	public class TimerActionListener2 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			player1Move();
		}
		
	}
	
	public void player1Move() {
		player1.move();
		
		//player1 gameOver
		
		if (field[player1.getLocationY()][player1.getLocationX()] != 0) {
			player1.hasCrashed();
			gameEnd = true;
		}
		
		
		//outOfBounds Death
		if (player1.getDirection() == 1 && (player1.getLocationX()) == 0) {
			player1.hasCrashed();
			gameEnd = true;
		}
		//up
		else if (player1.getDirection() == 2 && (player1.getLocationY()) == 0) {
			player1.hasCrashed();
			gameEnd = true;
		}
		//right
		else if ((player1.getDirection() == 3) && (player1.getLocationX() == field[0].length-1)) {
			player1.hasCrashed();
			gameEnd = true;
		}
		//down
		else if (player1.getDirection() == 4 && (player1.getLocationY()) == field.length-1) {
			player1.hasCrashed();
			gameEnd = true;
		}

		
		//timer stops for gameEnd
		
		if (gameEnd || gameEnd2) {
			//timer.stop();//remove this to have TIE condition look correct (head-on collision); refer to above notes;
			timer2.stop();
		}
		
		//need to adjust gameEnd to say: TIE, if bikes crash head on
		
		//TYPE1 POWERUP
		if (power.getType() >= 0 && power.getType() < 1) {
			if (player1.getLocationX() == power.getLocationX() && player1.getLocationY() == power.getLocationY()) {
				if (timer2.getDelay() - speed1 > 10) {
					speed1 += 10;
				}
				timer2.setDelay(timer2.getDelay()-speed1);
				
				power.relocate(field, WIDTH, HEIGHT);
				power.setType(4);
			}
		}
		
		//TYPE2 POWERUP
		if (power.getType() >= 1 && power.getType() < 2) {
			if (player1.getLocationX() == power.getLocationX() && player1.getLocationY() == power.getLocationY()) {
				
				
				timer3.setDelay(timer3.getDelay() + 10);
				
				power.relocate(field, WIDTH, HEIGHT);
				power.setType(4);
			}
		}
		
		//TYPE3 POWERUP
		
		if (power.getType() >= 2 && power.getType() < 2.7) {
			if (player1.getLocationX() == power.getLocationX() && player1.getLocationY() == power.getLocationY()) {
				
				for (int r = 0; r < HEIGHT; r++) {
					for (int c = 0; c < WIDTH; c++) {
						field[r][c] = 0;
					}
				}
									
				power.relocate(field, WIDTH, HEIGHT);
				power.setType(4);
			}
		}
		
		//TYPE4 POWERUP
		
		if (power.getType() >= 2.7 && power.getType() <= 3) {
			if (player1.getLocationX() == power.getLocationX() && player1.getLocationY() == power.getLocationY()) {
				
				int x = player2.getLocationX();
				int y = player2.getLocationY();
				int z = player2.getDirection();
				
				player2.setLocationX(player1.getLocationX());
				player2.setLocationY(player1.getLocationY());
				player2.setDirection(player1.getDirection());
				
				player1.setLocationX(x);
				player1.setLocationY(y);
				player1.setDirection(z);
									
				power.relocate(field, WIDTH, HEIGHT);
				power.setType(4);
			}
		}
		
		field[player1.getLocationY()][player1.getLocationX()] = 1;
	}
	
	
	public class TimerActionListener3 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			player2Move();
		}
		
	}
	
	public void player2Move() {
		player2.move();
		
		
		//player2 gameOver
		if (field[player2.getLocationY()][player2.getLocationX()] != 0) {
			player1.hasCrashed();
			gameEnd2 = true;
		}
		
		//outOfBounds Death
		if (player2.getDirection() == 1 && (player2.getLocationX()) == 0) {
			player2.hasCrashed();
			gameEnd2 = true;
		}
		//up
		else if (player2.getDirection() == 2 && (player2.getLocationY()) == 0) {
			player2.hasCrashed();
			gameEnd2 = true;
		}
		//right
		else if ((player2.getDirection() == 3) && (player2.getLocationX() == field[0].length-1)) {
			player2.hasCrashed();
			gameEnd2 = true;
		}
		//down
		else if (player2.getDirection() == 4 && (player2.getLocationY()) == field.length-1) {
			player2.hasCrashed();
			gameEnd2 = true;
		}
		
		if (gameEnd | gameEnd2) {
			timer.stop();
			timer3.stop();
		}
		
		
		//TYPE1 POWERUP
		if (power.getType() >= 0 && power.getType() < 1) {
			if (player2.getLocationX() == power.getLocationX() && player2.getLocationY() == power.getLocationY()) {
				if (timer3.getDelay() - speed2 > 10) {
					speed2 += 10;
				}
				timer3.setDelay(timer3.getDelay()-speed2);
				
				power.relocate(field, WIDTH, HEIGHT);
				power.setType(4);
			}
		}
		
		//TYPE2 POWERUP
		if (power.getType() >= 1 && power.getType() < 2) {
			if (player2.getLocationX() == power.getLocationX() && player2.getLocationY() == power.getLocationY()) {
										
				timer2.setDelay(timer2.getDelay() + 10);
				
				power.relocate(field, WIDTH, HEIGHT);
				power.setType(4);
			}
		}
		
		//TYPE3 POWERUP
		
		if (power.getType() >= 2 && power.getType() < 2.7) {
			if (player2.getLocationX() == power.getLocationX() && player2.getLocationY() == power.getLocationY()) {
				
				for (int r = 0; r < HEIGHT; r++) {
					for (int c = 0; c < WIDTH; c++) {
						field[r][c] = 0;
					}
				}
									
				power.relocate(field, WIDTH, HEIGHT);
				power.setType(4);
			}
		}
		
		//TYPE4 POWERUP
		
		if (power.getType() >= 2.7 && power.getType() <= 3) {
			if (player2.getLocationX() == power.getLocationX() && player2.getLocationY() == power.getLocationY()) {
				
				int x = player2.getLocationX();
				int y = player2.getLocationY();
				int z = player2.getDirection();
				
				player2.setLocationX(player1.getLocationX());
				player2.setLocationY(player1.getLocationY());
				player2.setDirection(player1.getDirection());
				
				player1.setLocationX(x);
				player1.setLocationY(y);
				player1.setDirection(z);
									
				power.relocate(field, WIDTH, HEIGHT);
				power.setType(4);
			}
		}
		
		
		field[player2.getLocationY()][player2.getLocationX()] = 2;
	}
	
	public class TimerActionListener4 implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (gameEnd && gameEnd2) {
				JOptionPane.showMessageDialog(null, "BOTH PLAYERS GOT GREKT AT THE SAME TIME" + "\n" + 
													"BOTH PLAYERS ARE TERRIBLE AT THIS GAME!" + "\n" +
													"Press SPACE to play again!");
				timer4.stop();
				reset();
			}
			else if (gameEnd) {
				JOptionPane.showMessageDialog(null, "BLUE PLAYER GOT GREKT" + "\n" + 
													"RED PLAYER WINS!" + "\n" +
													"Press SPACE to play again!");
				timer4.stop();
				reset();

			}
			else if (gameEnd2) {
				JOptionPane.showMessageDialog(null, "RED PLAYER GOT GREKT" + "\n" + 
													"BLUE PLAYER WINS!" + "\n" +
													"Press SPACE to play again!");
				timer4.stop();
				reset();

			}
		}
	}
	
	public void reset() {
		speed1 = 0;
		speed2 = 0;
		
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				field[r][c] = 0;
			}
		}
		
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				 if (field[r][c] == 0) {
				
					pg.plot(c, r, Color.BLACK);
				 }
			}
		}
		
		player1.setLocationX(WIDTH/2 + 14);
		player1.setLocationY(HEIGHT/2);
		player1.setDirection(1);
		player1.setCrashed(false);
		
		player2.setLocationX(WIDTH/2 - 15);
		player2.setLocationY(HEIGHT/2);
		player2.setDirection(3);
		player2.setCrashed(false);
		
		power.setType(4);
		
		gameEnd = false;
		gameEnd2 = false;
		gameStarted = false;
		
		timer3.setDelay(100);
		timer2.setDelay(100);

	}
	
	public void run() {
		SoundClip backgroundmusic;
		backgroundmusic = new SoundClip("TM.wav");
		backgroundmusic.setVolume(0.6);
		backgroundmusic.play();
		
		//backgroundmusic.loop(backgroundmusic.LOOP_CONTINUOUSLY);
		
		this.add(background);
		
		pg = new PixelGrid(8*WIDTH,8*HEIGHT,WIDTH,HEIGHT,0);
		pg.setLocation(150,110);
		this.add(pg);
				
		timer = new Timer(10, new TimerActionListener());
		timer2 = new Timer(100, new TimerActionListener2());
		timer3 = new Timer(100, new TimerActionListener3());
		timer4 = new Timer(100, new TimerActionListener4());
		
		/*
		this.label = new JLabel("Player 1: Use A,S,W,D to move");
		label.setForeground (Color.WHITE);
		this.add(label,800,220);
		
		this.label = new JLabel("Player 2: Use arrow keys to move");
		label.setForeground (Color.WHITE);
		this.add(label,5,220);
		*/
		
		/*
		this.textArea = new JTextArea(4, 35);
		this.textArea.setText("I'm a text area");
		this.add(textArea, SOUTH);
		*/
		
		
		this.setSize(1000,800);
		
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				field[r][c] = 0;
			}
		}
		
		for (int r = 0; r < HEIGHT; r++) {
			for (int c = 0; c < WIDTH; c++) {
				if (field[r][c] == 0 && c!=0 && r!=HEIGHT-1) {
				
					pg.plot(c, r, Color.BLACK);
				}
				if (r == 0 | r == HEIGHT-1) {
				    	pg.plot(c, r, Color.CYAN);
				}
				 
				if (c == 0 | c == WIDTH-1) {
				    	pg.plot(c, r, Color.CYAN);
				}
			}
		}
		
		this.add(tronButton);
		final GOval oval1 = new GOval(337,160,250,255);
		oval1.setColor(Color.WHITE);
		this.add(oval1);
		
		this.getGCanvas().addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (oval1.contains(e.getX(),e.getY())) {
					timer.start();
					tronButton.setVisible(false);
				}
			}

			public void mousePressed(MouseEvent e) {
				if (oval1.contains(e.getX(),e.getY())) {
					oval1.setVisible(false);
				}
			}
			
			public void mouseReleased(MouseEvent e) {}

			public void mouseEntered(MouseEvent e) {}

			public void mouseExited(MouseEvent e) {}
		});
		
		
		
		this.addKeyListeners(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if ((player1.getDirection() % 2 == 0 && e.getKeyCode() % 2 != 0) ||
						 player1.getDirection() % 2 != 0 && e.getKeyCode() % 2 == 0){
						switch( e.getKeyCode()) {
							//player1
							case 37 : player1.setDirection(1); player1Move(); break;
							case 38 : player1.setDirection(2); player1Move(); break;
							case 39 : player1.setDirection(3); player1Move(); break;
							case 40 : player1.setDirection(4); player1Move(); break;
						}
				}
				if ((player2.getDirection() % 2 != 0 && e.getKeyCode() > 70) ||
					 player2.getDirection() % 2 == 0 && e.getKeyCode() < 70) {
					switch( e.getKeyCode()) {
						case 65 : player2.setDirection(1); player2Move(); break;
						case 87 : player2.setDirection(2); player2Move(); break;
						case 68 : player2.setDirection(3); player2Move(); break;
						case 83 : player2.setDirection(4); player2Move(); break;
					}
				}
				
					switch ( e.getKeyCode()) {
					case 32 : timer.start(); timer2.start(); timer3.start(); 
							  timer4.start(); //label.setLocation(10000,10000);
							  power.relocate(field, WIDTH, HEIGHT); break;
					}
				
				
				}
			public void keyReleased(KeyEvent e) {
			}
		});
		this.getGCanvas().requestFocus();
		
	}

}
