//Trisha Sharma
//Period 7
//MGame.java
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import javax.swing.ImageIcon;

public class MGame extends JPanel implements ActionListener, KeyListener
{
	private JPanel cardPanel;
	
	private JPanel menuPanel;
	private JPanel gamePanel;
	
	private PowerUp[] powerUps = new PowerUp[10];
	private int powerUpCount = 0;
    private int width = 40;
    private int x;
    private int y;
    private NPC[] characters = new NPC[80];
    public Timer time;
    private int counter;
    private int maxCharacters = 50;
    private int characterCount = 0;
    private boolean gameOver = false;
    private int lives;
    private final int threeLives = 3;
    private JButton playAgain;
    private JButton exit;
    
    private int score = 0; //keeps track of the scoring system
    
    
	/*private int level = 1;	//adding this for different levels
	private int speed2 = 6;
	private int pSpeed = 4;
	*/
	
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Falling Blocks");
        MGame mg = new MGame();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mg);
        frame.setLocation(0, 0);
        frame.setVisible(true);
        mg.setFocusable(true);
        mg.requestFocusInWindow();
        mg.addKeyListener(mg);
    }
    
    public MGame()
    {
		
		lives = 3;
		x = 400 - width / 4;
		y = 800 - width/ 2 - 20;
		time = new Timer(30, this);
		
		exit = new JButton("Exit");
		setLayout(null);
		exit.setBounds(350, 500, 80, 40);
		exit.setVisible(false);
		exit.addActionListener(this);
		
		
		playAgain = new JButton("Play");
		setLayout(null);
		playAgain.setBounds(350, 400, 80, 40);
		playAgain.setVisible(false);
		playAgain.addActionListener(this);
		add(playAgain);
		add(exit);
		
		for(int i = 0; i < characters.length; i++)
		{
			characters[i] = null;
		}
		time.start();
		repaint();
    }
    ///when touching or interacting with powerups, you get a multiplier which increases your score + rank
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        
        g.setColor(Color.PINK);
        g.fillRect(x , y , width/2, width/2);
        
        //draw the falling objects
        g.setColor(Color.YELLOW);
        for(int i = 0; i < characterCount; i++)
        {
			g.fillOval(characters[i].x, characters[i].y, characters[i].size, characters[i].size);
		}
		
		g.setColor(Color.CYAN); //powerups falling
		for (int i = 0; i < powerUpCount; i++)
		{
			if (powerUps[i] != null && powerUps[i].active)
			{
				g.fillRect(powerUps[i].x, powerUps[i].y, powerUps[i].size, powerUps[i].size);
			}
		}
		
		g.setColor(Color.WHITE);
		g.drawString("Lives:" + lives, 10, 30);
		g.drawString("Score: " + score, getWidth() / 2 - 30, 30);
		
		if(gameOver)
		{
			g.setColor(Color.RED);
			g.drawString("GAME OVER", getWidth() / 2 - 40, getHeight() / 2);
		}
		
		
	g.setColor(Color.CYAN); //powerup for now..
	
	for (int i = 0; i < powerUpCount; i++) 
	{
		if (powerUps[i] != null && powerUps[i].active) {
			g.fillRect(powerUps[i].x, powerUps[i].y, powerUps[i].size, powerUps[i].size);
		}
	}
    }
    
    public void resetGame() {
    lives = threeLives;
    characterCount = 0;
    powerUpCount = 0;
    gameOver = false;
    score = 0;
    x = 400 - width / 4;
    y = 800 - width / 2 - 20;
    
    for (int i = 0; i < characters.length; i++) {
        characters[i] = null;
    }
    
    for (int i = 0; i < powerUps.length; i++) {
        powerUps[i] = null;
    }
    
    playAgain.setVisible(false);
    exit.setVisible(false);
    time.start();
    repaint();
}
    
    public void actionPerformed(ActionEvent evt)
    {
		if (counter % 100 == 0 && powerUpCount < powerUps.length) 
		{
			int px = (int)(Math.random() * (getWidth() - 30));
			powerUps[powerUpCount++] = new PowerUp(px, 0, 30, 4);
		}
			
		for (int i = 0; i < powerUpCount; i++) 
		{
			PowerUp pu = powerUps[i];
			if (pu != null && pu.active) 
			{
				pu.y += pu.speed;

				
				if (pu.x < x + width / 2 && pu.x + pu.size > x && pu.y < y + width / 2 && pu.y + pu.size > y) 
				{
					
					pu.active = false; //powerup is collected
					score += 10;
				}

				//removes powerups
				if (pu.y > getHeight()) 
				{
					pu.active = false;
				}
			}
		}

		if (evt.getSource() == playAgain)
		{
			lives = threeLives;
			characterCount = 0;
			gameOver = false;
			score = 0;
			x = 400 - width / 4;
			y = 800 - width / 2 - 20;
			for (int i = 0; i < characters.length; i++) 
			{
				characters[i] = null;
			}
			playAgain.setVisible(false);
			exit.setVisible(true);
			time.start();
			repaint();
			return;
		}
		
		 if(evt.getSource() == exit)
		 {
			 System.exit(0);
		 }
		
		if(!gameOver)
		{
			for(int i = 0; i < characterCount; i++)
			{
				characters[i].y += characters[i].speed;
				
				if (characters[i].x < x + width / 2 && characters[i].x + characters[i].size > x && characters[i].y < y + width / 2 && characters[i].y + characters[i].size > y)
				{
					lives--;
					characters[i].y = -1000; //removes block from view
					
					if(lives <= 0)
					{
						gameOver = true;
						time.stop();
						JOptionPane.showMessageDialog(this, "You died!! Play again?");
						playAgain.setVisible(true);
						exit.setVisible(true);
					}
				}
				
					if (counter % 100 == 0 && powerUpCount < powerUps.length)
					 {
						 int x = (int)(Math.random() * (getWidth() - 40));
						 powerUps[powerUpCount++] = new PowerUp(x, 0, 30, 4);
					 }
					 

				//resets npc to the top and randomizes the x position from where it drops
				if (characters[i].y > getHeight()) 
				{
					characters[i].y = -characters[i].size;  
					characters[i].x = (int)(Math.random() * (getWidth() - characters[i].size)); 
					score += 5; //avoiding obstacles 
				}
			}
			counter++;
		
		if(counter % 10 == 0 && maxCharacters > characterCount)
		{
			int x = (int)(Math.random()* 800);
			characters[characterCount] = new NPC(x, 0 , 40, 6);
			characterCount ++;
			
		}	
		repaint();
		
	
    }
  } 
    public void keyPressed(KeyEvent evt)
    {
        int key = evt.getKeyCode();
        
        if (key == KeyEvent.VK_UP)
        {
            y -= 20;
        }
        if (key == KeyEvent.VK_DOWN)
        {
            y += 20;
        }
        if (key == KeyEvent.VK_RIGHT)
        {
            x += 20;
        }
        if (key == KeyEvent.VK_LEFT)
        {
            x -= 20;
        }

		if (x < 0)
        {
            x = 0;
        }
        
        if (y < 0)
        {
            y = 0;
        }
        
        if (x > getWidth() - width)
        {
            x = getWidth() - width;
        }
        
        if (y > getHeight() - width)
        {
            y = getHeight() - width;
        }

        repaint();
        
       
    }
    
    public void keyTyped(KeyEvent evt)
    { }
    
    public void keyReleased(KeyEvent evt)
    { }
    
//create timer, force repaint(), takes new value for y component and redraws it
//randomly create the character objects and add it to an array
//loop through array and update the location of each character objects
//call repaint()
class NPC 
{
	int x;
	int y;
	int size;
	int speed;
	
	
	public NPC(int x, int y, int size, int speed)
	{
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = speed;
		
	}
}
}
class PowerUp 
{
    int x, y, size, speed;
    boolean active;

    public PowerUp(int x, int y, int size, int speed) 
    {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.active = true;
    }
}
