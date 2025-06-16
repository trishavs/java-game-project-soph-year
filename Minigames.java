//Trisha Sharma
//Period 7
//Minigames.java
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;	//all imports needed
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

public class Minigames			//main class
{
    public static void main(String[] args)
    {
        Minigames m = new Minigames();	//creates instance of class
        m.run();
    }

    public void run()
    {
        JFrame frame = new JFrame("Game Project");	//frame name
        frame.setSize(800, 800);	//dimensions of frame to be a square
        frame.setLocation(0, 0);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainPanel mp = new MainPanel();
        mp.requestFocusInWindow();
        frame.getContentPane().add(mp);
        frame.setVisible(true);
    }
}

class MainPanel extends JPanel //extends JPanel
{
    private CardLayout cards;	//declares cardlayout to handle multiple panels

    private WelcomePage welcomePage;	//here are the mutliple game pages used
    private NamePage namePage;
    private DirectionPage directionPage;
    private CreditsPage creditsPage;
    private MenuPage menuPage;
    public MGame mg;
    public LeaderboardPage leaderboardPage;

    public MainPanel()
    {
        mg = new MGame(this);	//initializes game panel

        cards = new CardLayout();	//initializes cardlayout and sets the layout for mainpanel to be cardlayout
        setLayout(cards);

        welcomePage = new WelcomePage(this);
        menuPage = new MenuPage(this);
        namePage = new NamePage(this);
        directionPage = new DirectionPage(this);
        creditsPage = new CreditsPage(this);
        leaderboardPage = new LeaderboardPage(this);

        add(welcomePage, "welcome");
        add(menuPage, "menu");	//adds each page to the cardlayout
        add(namePage, "name");
        add(directionPage, "directions");
        add(creditsPage, "credits");
        add(mg, "fallinggame");
        add(leaderboardPage, "leaderboard");

        cards.show(this, "name");	//sets this as the initial and first card shown, the name card
    }

    public void showCard(String cardName)
    {
        cards.show(this, cardName);
        if (cardName.equals("fallinggame")) //checks if the cardlayout is at the fallinggame card
        {
            mg.resetGame();
            mg.requestFocusInWindow(); // Important for KeyListener
            // mg.time.start(); // Ensure timer is started if stopped
            repaint();
        }
    }
}


class MenuPage extends JPanel implements ActionListener //menu class deals with all the buttons taking the player to the different cards
{
    private MainPanel parent;
    private JButton newGameButton;
    private JButton directionsButton;
    private JButton highScoresButton;	//multiple buttons to handle the different cards
    private JButton creditsButton;
    private JButton exitButton;
    private JButton settingsButton;

    private Image buttonTemplate;	//sets the background image and the images for the buttons
    private Image backgroundImage;

    public MenuPage(MainPanel parent)
    {
        this.parent = parent;
        //setLayout(new GridLayout(8, 1, 0, 5));

        setLayout(null);	//null layout to set precise locations for each component

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(200, 40);

		final int yAxis = 200;	//set a variable to a set dimention making it easier to add on to to fix positioning

        newGameButton = createButton("New Game", buttonFont, buttonSize);
        newGameButton.setBounds(325, yAxis, 140, 35);

        directionsButton = createButton("Directions", buttonFont, buttonSize);
        directionsButton.setBounds(325, yAxis + 100 , 140, 35);

        highScoresButton = createButton("High Scores", buttonFont, buttonSize);
        highScoresButton.setBounds(325, yAxis + 200, 140, 35);

        creditsButton = createButton("Credits", buttonFont, buttonSize);
        creditsButton.setBounds(325, yAxis + 300, 140, 35);

        exitButton = createButton("Exit", buttonFont, buttonSize);
        exitButton.setBounds(325, yAxis + 400, 140, 35);

        try 		//to attempt to the load the images
        {
            backgroundImage = ImageIO.read(new File("menu.png"));
        }
        catch (IOException e)
        {
            System.out.println("Could not load menu background.");	//prints error message if fails to load
        }

        try
        {
            buttonTemplate = ImageIO.read(new File("button.png"));
        }
        catch (IOException e)
        {
            System.out.println("Could not load button background.");
        }

        add(newGameButton);
        add(directionsButton);
        add(highScoresButton);
        add(creditsButton);

        add(exitButton);
    }

    public JButton createButton(String text, Font font, Dimension size)
    {
		ImageIcon icon = new ImageIcon("button.png");
		JButton button = new JButton(text, icon);

		button.setFont(font);
		//button.setBounds(200, 200, 200, 200);

		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setPreferredSize(size);
		button.setMaximumSize(size);

		button.setHorizontalAlignment(JButton.CENTER);
		button.setVerticalAlignment(JButton.CENTER);

		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);

		button.addActionListener(this);	//adds action listener to the buttons
		return button;
	}

    public void actionPerformed(ActionEvent e)
    {
        Object src = e.getSource();	//makes an object that checks if the button is clicked and what card it shows
        if (src == newGameButton)
        {
            parent.showCard("fallinggame");
        }
        else if (src == directionsButton)
        {
            parent.showCard("directions");
        }
        else if (src == highScoresButton)
        {
            parent.leaderboardPage.loadLeaderboard();
            parent.showCard("leaderboard");
        }
        else if (src == creditsButton)
        {
            parent.showCard("credits");
        }
        else if (src == exitButton) //exits the program when clicked
        {
            System.exit(0);
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);		//draws the image background and the button images
        if (backgroundImage != null)
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        if (buttonTemplate != null)
        {
            g.drawImage(buttonTemplate, 300, 190, 200, 60, this);
            g.drawImage(buttonTemplate, 300, 290, 200, 60, this);
            g.drawImage(buttonTemplate, 300, 390, 200, 60, this);
            g.drawImage(buttonTemplate, 300, 490, 200, 60, this);
            g.drawImage(buttonTemplate, 300, 590, 200, 60, this);
        }
    }
}
class LeaderboardPage extends JPanel implements ActionListener
{
    private MainPanel parent;
    private JButton backButton;
    private JTextArea leaderboardArea;		//displays the text for the leaderboard
    private Image buttonTemplate;
    private Image backgroundImage;


    public LeaderboardPage(MainPanel parent)
    {
        this.parent = parent;
        setLayout(null); //null layout

        try //checks if the images are loaded
        {
            backgroundImage = ImageIO.read(new File("leaderboard.png"));
        }
        catch (IOException e)
        {
            backgroundImage = null;
        }

        try
        {
            buttonTemplate = ImageIO.read(new File("button.png"));
        }
        catch (IOException e)
        {
            System.out.println("Could not load button background.");
        }

        JLabel title = new JLabel("Leaderboard", JLabel.CENTER);	//JLabel as a title
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 50, 800, 40);
        add(title);

        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        leaderboardArea.setFont(new Font("Serif", Font.PLAIN, 18));
        leaderboardArea.setForeground(Color.WHITE);
        leaderboardArea.setBackground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(leaderboardArea);	//as the leaderboard grows the user can scroll down to check user stats
        scrollPane.setBounds(100, 100, 600, 500);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);	//sets the background of the scrollbar to clear so it can display the text for stats
        add(scrollPane);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setForeground(Color.WHITE);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setBounds(355, 705, 90, 25);
        backButton.addActionListener(this);

        if (buttonTemplate != null) //checks to see if the button image was loaded properly
        {
            backButton.setOpaque(false);		//makes the back button clear
            backButton.setBorderPainted(false);
        }
        add(backButton);	//adds button to panel

        loadLeaderboard(); 		//loads the leaderboard data when the page is initialized and loaded
    }

    public void loadLeaderboard()
    {
        try
        {
            File file = new File("leaderboard.txt");	//creates file for leaderboard
            String content = "    LEADERBOARD\n\n"; //creates string to hold the contents of leaderboard

            if (!file.exists())	//if the file doesn't exist
            {
                content += "No scores yet!\nPlay a game to appear here!";
            }
            else
            {
                int entryCount = 0;
                Scanner counterScanner = new Scanner(file);
                while (counterScanner.hasNextLine())
                {
                    if (!counterScanner.nextLine().trim().isEmpty())	//makes sure the line isn't empty
                    {
                        entryCount++;	//increments the entry count of users
                    }
                }
                counterScanner.close();

                String[] entries = new String[entryCount];	//array that stores the leaderboard entries
                int[] scores = new int[entryCount];	//array that stores the scores to it's respective names

                Scanner reader = new Scanner(file);	//scanner to read through content
                int index = 0;
                while (reader.hasNextLine())	//reads through each line
                {
                    String line = reader.nextLine().trim();
                    if (!line.isEmpty())
                    {
                        entries[index] = line;	//stoes the line as an entry
                        scores[index] = extractScore(line);	//takes and stores the score from line
                        index++;
                    }
                }
                reader.close();

                for (int i = 0; i < scores.length - 1; i++)
                {
                    for (int j = 0; j < scores.length - i - 1; j++)
                    {
                        if (scores[j] < scores[j + 1])	//if current score is less than the next score
                        {
                            int tempScore = scores[j];	//swap the scores
                            scores[j] = scores[j + 1];
                            scores[j + 1] = tempScore;

                            String tempEntry = entries[j];	//and also swap the entries
                            entries[j] = entries[j + 1];
                            entries[j + 1] = tempEntry;
                        }
                    }
                }

                for (int i = 0; i < entries.length; i++)
                {
                    content += String.format("%2d. %s\n", i + 1, entries[i]);
                }
            }

            leaderboardArea.setText(content);
        }
        catch (FileNotFoundException e)
        {
            leaderboardArea.setText("Error loading leaderboard");	//error message if the leaderboard doesnt load properly
        }
    }

    public int extractScore(String entry)
    {
        try
        {
            String[] parts = entry.split("Score: ");	//splits the entry string
            String scorePart = parts[1].split(" - ")[0];	//takes out the score
            return Integer.parseInt(scorePart);	//converts the score to integer from string user parseint + returns value

        }
        catch (Exception e)
        {
            return 0;
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (backgroundImage != null)	//draws image background
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (buttonTemplate != null)	//draws the button image
        {
            g.drawImage(buttonTemplate, 350, 690, 100, 50, this);
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == backButton)	//if the back button is clicked, takes user to the menu card
        {
            parent.showCard("menu");
        }
    }
}
class WelcomePage extends JPanel implements ActionListener //welcome page with the game name
{
    private MainPanel parent;
    private JButton startButton;
    private JLabel titleLabel;
    private Image backgroundImage;
    private Image titleName;
    private Image buttonTemplate;

    public WelcomePage(MainPanel parent)
    {
        this.parent = parent;

        setLayout(null);	//null layout like the previous classes

        Font buttonFont = new Font("Arial", Font.BOLD, 16);	//set font and button size
        Dimension buttonSize = new Dimension(200, 40);

        try //tries to load welcome image background
        {
            backgroundImage = ImageIO.read(new File("welcome.png"));
        }
        catch (IOException e)
        {
            System.out.println("Image not found!");
        }

        try //button image loading
        {
            buttonTemplate = ImageIO.read(new File("button.png"));
        }
        catch (IOException e)
        {
            System.out.println("Could not load button background.");
        }

        try  //image for the title
        {
            titleName = ImageIO.read(new File("titlename.png"));
        }
        catch(IOException e)
        {
            System.out.println("welcome not found");
        }

        startButton = createButton("play", buttonFont, buttonSize);
        startButton.setBounds(325, 500, 140, 35);
        add(startButton);
    }

    public JButton createButton(String text, Font font, Dimension size) //this method is one that I reused mutliple times for the buttons
    {
		ImageIcon icon = new ImageIcon("button.png");
		JButton button = new JButton(text, icon);

		button.setFont(font);

		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setPreferredSize(size);
		button.setMaximumSize(size);

		button.setHorizontalAlignment(JButton.CENTER);
		button.setVerticalAlignment(JButton.CENTER);

		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);

		button.addActionListener(this);
		return button;
	}



    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (backgroundImage != null)	//draws background image
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if(titleName != null)	//draws the title name
        {
            g.drawImage(titleName, 0, 50, getWidth() , getHeight() * 1/2, this);
        }

        if (buttonTemplate != null) //button image
        {
            g.drawImage(buttonTemplate, 320, 490, 150, 55, this);
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == startButton)
        {
            parent.showCard("menu");
        }

    }
}
class NamePage extends JPanel implements ActionListener, KeyListener, ItemListener //same as previous classes
{
    private JTextField nameField;
    private MainPanel parent;
    private JButton nextButton;
    private JLabel error;
    private static String playerName;
    private Image backgroundImage;
    private Image buttonTemplate;
    private JCheckBox greatBox;
    private JCheckBox okBox;
    private JCheckBox mehBox;
    private ButtonGroup checkboxGroup; //new button group


    NamePage(MainPanel parent)
    {
        this.parent = parent;
        setLayout(null);

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(200, 40);

        try
        {
            backgroundImage = ImageIO.read(new File("name.png"));
        }
        catch (IOException e)
        {
            backgroundImage = null;
        }

         try
        {
            buttonTemplate = ImageIO.read(new File("button.png"));
        }
        catch (IOException e)
        {
            System.out.println("Could not load button background.");
        }

		setPreferredSize(new Dimension(800, 800));

        int centerX = 250;

        JLabel label = new JLabel("what's your name?");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Serif", Font.BOLD, 24));
        label.setBounds(centerX + 50, 200, 300, 35);
        add(label);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBounds(centerX, 250, 300, 30);
        nameField.setBackground(new Color(245, 245, 245));
        nameField.addKeyListener(this);
        add(nameField);

        error = new JLabel("erm you have to put a name.");	//if user doesn't enter a name
        error.setForeground(new Color(255, 90, 200));
        error.setFont(new Font("Arial", Font.BOLD, 12));
        error.setBounds(centerX, 285, 300, 20);
        error.setVisible(false);
        add(error);

        JLabel statusLabel = new JLabel("how are you doing today? ");
        statusLabel.setForeground(new Color(230, 230, 255));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        statusLabel.setBounds(centerX + 75, 300, 300, 35);
        add(statusLabel);

        greatBox = new JCheckBox("great!!");
        greatBox.setBounds(centerX, 350, 80, 20);
        greatBox.setOpaque(false);
        greatBox.setFont(new Font("Arial", Font.PLAIN, 13));
        greatBox.setForeground(new Color(225, 225, 255));
        greatBox.addItemListener(this); //item listener is added to each of the checkboxes
        add(greatBox);

        okBox = new JCheckBox("okay");
        okBox.setBounds(centerX + 100, 350, 80, 20);
        okBox.setOpaque(false);
        okBox.setFont(new Font("Arial", Font.PLAIN, 13));
        okBox.setForeground(new Color(225, 225, 255));
        okBox.addItemListener(this);
        add(okBox);

        mehBox = new JCheckBox("mmm..");
        mehBox.setBounds(centerX + 200, 350, 80, 20);
        mehBox.setOpaque(false);
        mehBox.setFont(new Font("Arial", Font.PLAIN, 13));
        mehBox.setForeground(new Color(225, 225, 255));
        mehBox.addItemListener(this);
        add(mehBox);

        //created checkbox group so only one box can be selected at a time
        checkboxGroup = new ButtonGroup();
        checkboxGroup.add(greatBox);
        checkboxGroup.add(okBox);
        checkboxGroup.add(mehBox);


        nextButton = createButton("continue", buttonFont, buttonSize);
        nextButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBounds(centerX + 80, 400, 140, 35);
        nextButton.addActionListener(this);
        add(nextButton);

        setOpaque(true);
    }

    public JButton createButton(String text, Font font, Dimension size) //reused from above
    {
		ImageIcon icon = new ImageIcon("button.png");
		JButton button = new JButton(text, icon);

		button.setFont(font);

		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setPreferredSize(size);
		button.setMaximumSize(size);

		button.setHorizontalAlignment(JButton.CENTER);
		button.setVerticalAlignment(JButton.CENTER);

		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);

		button.addActionListener(this);
		return button;
	}

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (backgroundImage != null)
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        else
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (buttonTemplate != null)
        {
            g.drawImage(buttonTemplate, 320, 390, 160, 55, this);
        }

    }

    public void actionPerformed(ActionEvent e)
    {
        submitForm();	//calls method below, if the name is left blank
    }

    public void submitForm()
    {
        String name = nameField.getText().trim();
        if (name.isEmpty())
        {
            error.setVisible(true);
            return;
        }
        error.setVisible(false);
        playerName = name;
        saveNameToFile(name);
        parent.showCard("welcome");
    }

    public static String getPlayerName() //collects user's inputted name
    {
        return playerName;
    }

    public void saveNameToFile(String name)
    {
        try
        {
            FileWriter fw = new FileWriter("leaderboard.txt", true);	//saves user's name to file
            PrintWriter out = new PrintWriter(fw);
            // This was creating an empty entry. We only want to record scores with names.
            // out.println(name); // This line might be problematic if not coordinated with score saving
            out.close();
        }
        catch (IOException ex)
        {
            System.err.println("Could not write name to leaderboard_names.txt");
        }
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getSource() == nameField && e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            submitForm();
        }
    }
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

    public void itemStateChanged(ItemEvent e) //this method is called when the checkboxes are selected or when the state changes
    {
       //the checkboxes are added to a ButtonGroup, so only one can be selected at a time
        if (e.getSource() == greatBox)
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                System.out.println("great!! selected");
            }
        }
        else if (e.getSource() == okBox)
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                System.out.println("okay selected");
            }
        }
        else if (e.getSource() == mehBox)
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                System.out.println("mmm.. selected");
            }
        }
    }
}
class DirectionPage extends JPanel implements ActionListener
{
    private MainPanel parent;
    private JButton backButton;
    private JTextArea directions;
    private Image backgroundImage;
	private Image buttonTemplate;

    public DirectionPage(MainPanel parent)
    {
        this.parent = parent;
        //setLayout(new BorderLayout());
        setLayout(null);
        //setBackground(Color.BLACK);
        
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(200, 40);

        try 
        {
            backgroundImage = ImageIO.read(new File("directions.png"));
        } 
        catch (IOException e) 
        {
            backgroundImage = null;
        }
        
        try 
        {
            buttonTemplate = ImageIO.read(new File("button.png"));
        } 
        catch (IOException e) 
        {
            System.out.println("Could not load button background.");
        }
        
		directions = new JTextArea();	//JTextArea set for the instructions of the game
        directions.setText("Welcome to Minigames!" +
		"\n\nObjective:" +
		"\nDodge falling obstacles and survive through 3 challenging levels,"
		+ "each set in a different biome! Each level gets harder, with obstacles falling faster and more frequently."
		+ "\n\nGameplay:" 
		+ "\n- Use arrow keys to move and dodge obstacles."
		+ "\n- You have 3 lives. However there will be powerups which you will have to answer questions correctly to earn! "
		+ "\n- Powerups will sometimes earn you crazy rewards helping you beat the level and learn trigonometry, but getting it wrong will make you lose a life!"
		+ "\n- You must reach and answer a certain number of questions depending on the level to move on to the next level."
		+ "\nLevels:"
		+ "\nEach level is set in a unique biome with different obstacles. The game gets harder as you advance:"
		+ "\n- Level 1: Easy"
		+ "\n- Level 2: Medium"
		+ "\n- Level 3: Hard"
		+ "\nTips:"
		+ "\n- Dodge obstacles to stay alive."
		+ "\n- Collect power-ups and answer questions correctly to continue."
		+ "\n- Survive all 3 levels to win the game!"
		+ "\nGood luck! ");
        directions.setFont(new Font("Serif", Font.PLAIN, 16));
        directions.setForeground(Color.WHITE);
        directions.setBackground(Color.BLACK);
        directions.setEditable(false);
        directions.setLineWrap(true);
        directions.setWrapStyleWord(true);
        directions.setBounds(100, 100, 600, 500);
        add(directions);

        backButton = createButton("back", buttonFont, buttonSize);
        backButton.setBounds(355, 705, 90, 25);
        add(backButton);
    }
    
    public JButton createButton(String text, Font font, Dimension size) 
    {
		ImageIcon icon = new ImageIcon("button.png");
		JButton button = new JButton(text, icon);

		button.setFont(font);

		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setPreferredSize(size);
		button.setMaximumSize(size);

		button.setHorizontalAlignment(JButton.CENTER);
		button.setVerticalAlignment(JButton.CENTER);

		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);  

		button.addActionListener(this);
		return button;
	}
	
	public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if (backgroundImage != null) //draws background image
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        
        if (buttonTemplate != null) //draws button image
        {
            g.drawImage(buttonTemplate, 350, 690, 100, 50, this);
        }
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == backButton)
        {
            parent.showCard("menu");
        }
    }
}
class CreditsPage extends JPanel implements ActionListener //same as previous methods
{
    private MainPanel parent;
    private JButton backButton;
    private Image backgroundImage;
    private JTextArea creditsArea;
	private Image buttonTemplate;	
    public CreditsPage(MainPanel parent) 
    {
        this.parent = parent;
        //setLayout(new BorderLayout());
        setLayout(null);
        
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(200, 40);
        
        try 
        {
            backgroundImage = ImageIO.read(new File("credits.png"));
        } 
        catch (IOException e) 
        {
            backgroundImage = null;
        }
        
        try 
        {
            buttonTemplate = ImageIO.read(new File("button.png"));
        } 
        catch (IOException e) 
        {
            System.out.println("Could not load button background.");
        }
        
        backButton = createButton("back", buttonFont, buttonSize);
        backButton.setBounds(355, 705, 90, 25);
        add(backButton);
        
        creditsArea = new JTextArea();
        creditsArea.setText("I would like to thank a few people who helped me bring this game to life."
        +"\n\nThank you, Eva for help with ideas on how to play the game."
        +"\n\nThank you, Joyce for helping me debug errors and give ideas for the panels"
        +"\n\nThank you, Toni for helping me implement the buttons with the images even if ImageIcon didn't work :("
        +"\n\nThank you, Suhana for helping me with images and background loading"
        +"\n\nAnd thank you for you playing my game :)"
        +"\n\nI hope you have fun!!");
        creditsArea.setFont(new Font("Serif", Font.PLAIN, 18));
        creditsArea.setForeground(Color.WHITE);
        creditsArea.setBackground(Color.BLACK);
        creditsArea.setEditable(false);
        creditsArea.setLineWrap(true);
        creditsArea.setWrapStyleWord(true);
        creditsArea.setBounds(100, 100, 600, 500);
        add(creditsArea);
     
    }
    
    public JButton createButton(String text, Font font, Dimension size) 
    {
		ImageIcon icon = new ImageIcon("button.png");
		JButton button = new JButton(text, icon);

		button.setFont(font);

		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setPreferredSize(size);
		button.setMaximumSize(size);

		button.setHorizontalAlignment(JButton.CENTER);
		button.setVerticalAlignment(JButton.CENTER);

		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);  

		button.addActionListener(this);
		return button;
	}
    
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if (backgroundImage != null) 
        {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
        
        if (buttonTemplate != null) 
        {
            g.drawImage(buttonTemplate, 350, 690, 100, 50, this);
        }
    }

    public void actionPerformed(ActionEvent e) 
    {
        parent.showCard("menu");
    }
}

class MGame extends JPanel implements ActionListener, KeyListener, MouseListener	//listeners
{
    private MainPanel parent;
	public GameQuestionData gameData; // Renamed GameData to GameQuestionData

    private PowerUp[] powerUps = new PowerUp[30];	//array to store powerups
    private int powerUpCount = 0;
    private int width = 40;	//width and height for the user
    private int x;	//player x and y coordinate
    private int y;
    private NPC[] characters = new NPC[90];	//array to store falling objects
    public Timer time;	//to control how the obstacles and powerups fall
    private int counter;
    private int maxCharacters = 40;
    private int characterCount = 0;
    private boolean gameOver = false;	//checks if game is over
    private int lives;	//checks number of lives
    private final int threeLives = 3;
    private JButton playAgain;
    private JButton exit;
    private int score = 0;

    private final int lanes = 8; //arranges when a row of obstacles fall in the later levels
	private final int width2 = 80;

    private int level = 1;	//level 1 requirements
    private int powerUpsCollected = 0;
    private int powerUpsRequired = 5;
    private int npcFallSpeed = 6;	//obstacles falling speed
    private int powerUpFallSpeed = 4;

    private boolean timerStarted = false;
    private long startTime = 0;
    private long elapsedTime = 0;
    private int totalPowerupsCollected = 0;

    private Image level1Bg;
    private Image level2Bg;	//all background images
    private Image level3Bg;

    private Image level1Obstacle;
    private Image level2Obstacle;	//all falling obstacles
    private Image level3Obstacle;

    private Image level1Powerup;
    private Image level2Powerup;
    private Image level3Powerup;	//all powerups

    private Image playerImageLevel1;	//all player images
    private Image playerImageLevel2;
    private Image playerImageLevel3;

    private Image buttonTemplate;	//button image


    public MGame(MainPanel parent)
    {
        this.parent = parent;
		this.gameData = new GameQuestionData(); // Initialize with the new class name
		this.gameData.setCurrentLevel(level); // Set initial level for question data

        setLayout(null);

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        Dimension buttonSize = new Dimension(120, 35); // Adjusted size for consistency

        lives = threeLives;	//sets initial lives to 3
        x = 400 - width / 4;
        y = 800 - width/ 2 - 20;
        time = new Timer(20, this);

        try //loads all the images
        {
            buttonTemplate = ImageIO.read(new File("button.png"));
        }
        catch (IOException e)
        {
            System.out.println("Could not load button background.");
        }
        try
        {
            level1Bg = ImageIO.read(new File("level1.png"));
            level1Obstacle = ImageIO.read(new File("obstacle1.png"));
            level1Powerup = ImageIO.read(new File("powerup1.png"));
            playerImageLevel1 = ImageIO.read(new File("player1.png"));
         }


        catch (IOException e)
        {
            System.out.println("level 1 crashed " + e.getMessage());	//if a any one of the images for the level doesn't load
        }

        try
        {
			level2Bg = ImageIO.read(new File("level2.png"));
			level2Obstacle = ImageIO.read(new File("obstacle2.png"));
			level2Powerup = ImageIO.read(new File("powerup2.png"));
			playerImageLevel2 = ImageIO.read(new File("player2.png"));
		}
		catch (IOException e)
		{
			System.out.println("level 2 crashed " + e.getMessage());
		}

		try
		{
			level3Bg = ImageIO.read(new File("level3.png"));
            level3Obstacle = ImageIO.read(new File("obstacle3.png"));
            level3Powerup = ImageIO.read(new File("powerup3.png"));
            playerImageLevel3 = ImageIO.read(new File("player3.png"));
        }
        catch (IOException e)
		{
			System.out.println("level 3 crashed " + e.getMessage());
		}

        exit = createButton("exit", buttonFont, buttonSize);	//takes user back to menu page
        exit.setBounds(350, 540, 120, 35);
        exit.setVisible(false);
        add(exit);
        
        playAgain = createButton("play again", buttonFont, buttonSize);	//play again button once player dies
        playAgain.setBounds(350, 490, 120, 35);
        playAgain.setVisible(false);
        add(playAgain);

        add(playAgain);
        add(exit);


        for(int i = 0; i < characters.length; i++)
        {
            characters[i] = null;	//initializes the obstacle/character array
        }

        time.start();
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
    }
    
	 public JButton createButton(String text, Font font, Dimension size) //this method is one that I reused mutliple times for the buttons
    {
		ImageIcon icon = new ImageIcon("button.png");
		JButton button = new JButton(text, icon);

		button.setFont(font);

		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setPreferredSize(size);
		button.setMaximumSize(size);

		button.setHorizontalAlignment(JButton.CENTER);
		button.setVerticalAlignment(JButton.CENTER);

		button.setHorizontalTextPosition(JButton.CENTER);
		button.setVerticalTextPosition(JButton.CENTER);

		button.addActionListener(this);
		return button;
	}
	public Image getCurrentBg() //sets the appropriate background for each level
	{
        if (level == 1)
        {
			return level1Bg;
		}
        if (level == 2)
        {
			return level2Bg;
		}
		else
		{
			return level3Bg;
		}
    }

    public Image getCurrentObstacle() //sets the appropriate obstacle for each level
    {
        if (level == 1)
        {
			return level1Obstacle;
		}
        if (level == 2)
        {
			return level2Obstacle;
		}
		else
		{
			return level3Obstacle;
		}
    }

    public Image getCurrentPowerup() //sets the appropriate powerup for each level
    {
        if (level == 1)
        {
			return level1Powerup;
		}
        if (level == 2)
        {
			return level2Powerup;
		}
		else
		{
			return level3Powerup;
		}
    }

    public Image getCurrentPlayerImage() //sets the appropriate player icon for each level
    {
        if (level == 1)
        {
			return playerImageLevel1;
		}
        if (level == 2)
        {
			return playerImageLevel2;
		}
		else
		{
			return playerImageLevel3;
		}
    }

    public String getLevelName() //level names
    {
        if (level == 1)
        {
			return "outer space";
		}
        if (level == 2)
        {
			return "under the sea";
		}
		else
		{
			return "meadow fields";
		}
    }

	public void handlePowerUpCollision(PowerUp powerUp)
	{
		powerUp.active = false;
		score += 10;


		int playerFrontX = x + width / 2;
		int playerY = y;
		int radius = 10;

		for (int i = 0; i < characterCount; i++)
		{
			NPC npc = characters[i];
			if (npc != null)
			{
				int centerNpcX = npc.x + npc.size / 2;
				int centerNpcY = npc.y + npc.size / 2;
				//trying to remove the obstacles above the player whihc basically means a higher y position
				if (centerNpcY > playerY)
				{
					double dist = Math.sqrt(Math.pow(centerNpcX - playerFrontX, 2) + Math.pow(centerNpcY - playerY, 2));
					if (dist <= radius)
					{
						npc.y = -1000; //takes the obstacle out of the player's view
					}
				}
			}
		}

    showQuestionDialog();
}

public void showQuestionDialog() //shows the question and the answer for the questions are taken from the indexes found above
{
    gameData.grabQuestionFromFile(level); // Pass the current level
    time.stop();	//stops everything from moving when the questions pops up/when player touches powerup

    JPanel panel = new JPanel(new BorderLayout());	//sets the top part of the question to borderlayout
    JTextArea questionArea = new JTextArea(gameData.getQuestion());	//adds question text to the panel with borderlayout
    questionArea.setEditable(false);
    questionArea.setLineWrap(true);
    questionArea.setWrapStyleWord(true);
    questionArea.setFont(new Font("Arial", Font.BOLD, 16));
    questionArea.setBackground(Color.WHITE);
    questionArea.setPreferredSize(new Dimension(500, 80));
    panel.add(new JScrollPane(questionArea), BorderLayout.NORTH);

    JPanel answerPanel = new JPanel(new GridLayout(2, 2, 10, 10));	//uses gridlayout to format the answers

    JButton[] answerButtons = new JButton[4];	//the 4 answer choices

		JDialog dialog = new JDialog((JFrame)getTopLevelAncestor(), "question time!", true);
		dialog.setContentPane(panel);	//adds the question panel to the Jdialog, like adding components to regular panels but this is a popup
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);	//so player can't skip the question

    for (int i = 0; i < 4; i++) //loop goes through 4 times to set up 4 answer buttons
    {
        answerButtons[i] = new JButton(gameData.getAnswer(i));	//the text is taken from gameData which was taken from the txt files for each respective level/question
        answerButtons[i].setFont(new Font("Arial", Font.PLAIN, 16));
        answerButtons[i].addActionListener(new QuestionActionListener(this, i, dialog));	//action listener is added and when it passes the button's index, the Jdialog will check how to respond(aka correct, dissapears, wrong, different message)
        answerPanel.add(answerButtons[i]);	//added to the answer panel
    }

    panel.add(answerPanel, BorderLayout.CENTER);	//adds answer panel to the main panel and uses borderlayout to place in the center region
    dialog.pack();
    dialog.setSize(600, 400);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}

public void removeObstacleInFront()
{
    int playerCenterX = x + width / 4;
    int playerY = y;
    int minDist = Integer.MAX_VALUE;
    int minIdx = -1;

    for (int i = 0; i < characterCount; i++) //loops through all obstacles in character array
    {
        NPC npc = characters[i];	//gets object at current index
        if (npc != null)
        {
            int npcCenterX = npc.x + npc.size / 2;	//calculates and gets the x and y coordinate
            int npcY = npc.y;
            if (Math.abs(npcCenterX - playerCenterX) < 20 && npcY > playerY) //checks whether the obstacle is inline with or in contact with the player
            {
                int dist = npcY - playerY;
                if (dist < minDist)
                {
                    minDist = dist;
                    minIdx = i;
                }
            }
        }
    }
    if (minIdx != -1)
    {
        characters[minIdx].y = -1000;	//removes beyond player view
    }
}

public void checkAnswer(JRadioButton[] buttons, ButtonGroup group)
{
    int selectedIndex = -1;
    for (int i = 0; i < buttons.length; i++)
    {
        if (buttons[i].isSelected())
        {
            selectedIndex = i;
        }
    }

    if (selectedIndex == -1)
    {
        showFeedback("you didn't select an answer!", Color.ORANGE);	//if the player tries to skip or move on without answering the question
        return;
    }

    boolean isCorrect = selectedIndex == gameData.getCorrectAnswer();	//compares the index of what the player picked with the correct answer's index

    if (isCorrect)
    {
		gameData.addOneToCorrectCount();	//increment the number of correct asnwers
		score += 150;	//add to score
		removeObstacleInFront(); //removes the obstacle that may be directly in front, or else they would lose a life
		showFeedback("you got it!", Color.GREEN);	//positive message
	}
	else
	{
		showFeedback("oops!", Color.RED);	//if player doesn't pick the right answer
		lives--;
	}


    if (gameData.getQuestionCount() >= 4) //as each level requres around 4 or more questions, it will bump the player up to the next level and gives harder questions
    {
        gameData.resetAll();
    }
    gameData.grabQuestionFromFile(level);	//grabs data from the file, for the next level
}

public void showFeedback(String message, Color color)
{
    JLabel feedbackLabel = new JLabel(message);
    feedbackLabel.setFont(new Font("Arial", Font.BOLD, 24));
    feedbackLabel.setForeground(color);
    feedbackLabel.setHorizontalAlignment(JLabel.CENTER);	//makes the jdialog appear in the center of the user's screen rather than in the corner
    JOptionPane.showMessageDialog(this, feedbackLabel, "Result", JOptionPane.PLAIN_MESSAGE);
}


		public void resetGame() //after the game is done everything is reset to the stats the positions of level one, the player wil have to play again from level 1
		{
			lives = threeLives;
			characterCount = 0;
			powerUpCount = 0;
			gameOver = false;
			score = 0;
			x = 400 - width / 4;
			y = 800 - width / 2 - 20;
			level = 1;
			powerUpsCollected = 0;
			powerUpsRequired = 5;
			npcFallSpeed = 4;
			powerUpFallSpeed = 3;
			timerStarted = false;
			startTime = 0;
			elapsedTime = 0;
            gameData.resetAll(); //reset question data as well
            gameData.setCurrentLevel(level); //set current level for question data

			for (int i = 0; i < characters.length; i++) //clears all the existing obstacles from the game
			{
				characters[i] = null;
			}
			for (int i = 0; i < powerUps.length; i++) //clears all the existing powerups from the game
			{
				powerUps[i] = null;
			}

			playAgain.setVisible(false);	//hide button until player has died
			exit.setVisible(false);	//same with playAgain, appears at the end when the player has died
			time.start();
			repaint();
		}
    public String formatTime(long ms) //this is the timer at the top left, it should work as soon as player moves the key thingy
    {
        long sec = ms / 1000;	//convert milliseconds to the total seconds
        long min = sec / 60;	//converts the total seconds to the total minutes
        sec = sec % 60;	//calculates the remaining seconds after taking in minutes
        long tenths = (ms % 1000) / 100; 		//miliseconds
        return String.format("%02d:%02d.%d", min, sec, tenths);
    }

    public void maybeGiveBoosterBenefit() //to make sure the player doesn't get a reward everytime as the booster are frequent
    {
		double chance = Math.random(); //uses math.random to generate a number to see what the uses's chance of not getting anything is
		if (chance < 0.8)
		{
			giveBoosterBenefit();	//however 80 percent of the time the user will get rewards
		}
		else
		{
			showFeedback("you can't expect a booster all time!", Color.LIGHT_GRAY);
		}
	}

    public void giveBoosterBenefit() //gives players rewards once the method above decides using via math.random
    {
    int booster = (int) (Math.random() * 4);

    if (booster == 0) //if at 0 boosters and collects one
    {

        if (lives < 5)
        {
            lives++;	//gives life
            showFeedback("lucky u! u got an extra life", Color.CYAN);
        }
        else
        {
            score += 100;	//adds to score
            showFeedback("ooo more points +100 ", Color.YELLOW);
        }
    }
    else if (booster == 1) //at one booster collected
    {
        score += 200;	//higher number of score increments
        showFeedback("extra lucky +200 !", Color.YELLOW);
    }
    else if (booster == 2)
    {
        npcFallSpeed = Math.max(2, npcFallSpeed - 4);	//makes the obstacles slow down by a little
        showFeedback("jackpot!! the obstacles slow down!", Color.GREEN);

    }
    else if (booster == 3)
    {

        for (int i = 0; i < characterCount; i++) //clears all obstacles of the current screen, motivates player to continue
        {
            if (characters[i] != null) characters[i].y = -1000;
        }
        showFeedback("DANGGG lets clear all the obstacles!", Color.PINK);
    }
}

public void saveScoreToLeaderboard()
{
    if (!gameOver && level <= 3) //checks if the game is not over and the current level is less than 3
    {
        return;	//returns without saving
    }
    String playerName = NamePage.getPlayerName();	//gets player name from namepage
    if (playerName == null || playerName.trim().isEmpty())
    {
        playerName = "anon";	//if the players name is invalid it is set to anon or anonymous
       
    }

    File ogFile = new File("leaderboard.txt");	//the original leaderboard file
    File check = new File("playercheck.txt");	//this is a temporary file that processes names

    PrintWriter pw = null;	//writes to check
    Scanner scanner = null;	//scanner reading from ogFile
    boolean playerFound = false;	//checks if the player's current score was found in the current file

    try
    {
        pw = new PrintWriter(new FileWriter(check));	//initialize

        if (ogFile.exists()) //checks if the original file exists
        {
            scanner = new Scanner(ogFile);
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty())
                {
                    if (line.startsWith(playerName + " - ")) //sees if there's an entry for thr same user and will overwrite it with the new score
                    {
                        playerFound = true;
                    }
                    else
                    {
                        pw.println(line);
                    }
                }
            }
            scanner.close();
        }

        int displayLevel = level;	//determines the level(s) the player has gotten through
        if (level > 3)
        {
            displayLevel = 3;
        }
        String newEntry = playerName + " - Score: " + score + " - Level: " + displayLevel;	//adds player score and level number
        pw.println(newEntry);

        pw.close();

        if (ogFile.exists())
        {
            ogFile.delete();	//deletes the older version of the leaderboard when updating it
        }
        check.renameTo(ogFile);	//this updates the leadderboard file and replaces the old one which will add to the new scores

        if (parent != null && parent.leaderboardPage != null)
        {
            parent.leaderboardPage.loadLeaderboard();
            parent.repaint();
        }

    }
    catch (Exception e)
    {
        System.err.println("Error saving score to leaderboard: " + e.getMessage());	//file exceptions with errors
    }
}





public void paintComponent(Graphics g)
{
    super.paintComponent(g);

    if (gameOver)
    {
        drawGameOverScreen(g);
        saveScoreToLeaderboard();
        return;
    }
    if (level == 1)
    {
        drawLevel1Content(g);	//draws level 1
    }
    else if (level == 2)
    {
        drawLevel2Content(g);	//draws level 2
    }
    else if (level == 3)
    {
        drawLevel3Content(g);	//draws level 3
    }
    else
    {
        drawWinScreen(g);	//win screen if beaten
        return;
    }

    drawGameUI(g);

    if (counter < 50)
    {
        drawLevelIntro(g);
    }
}

public void drawLevel1Content(Graphics g)
{

    if (level1Bg != null) //draws level 1 with respective images and background
    {
        g.drawImage(level1Bg, 0, 0, getWidth(), getHeight(), this);
    }
    for (int i = 0; i < characterCount; i++)
    {
        NPC npc = characters[i];
        if (npc != null && level1Obstacle != null)
        {
            g.drawImage(level1Obstacle, npc.x, npc.y, 1 + npc.size, 1 + npc.size, this);
        }
    }

    for (int i = 0; i < powerUpCount; i++)
    {
        PowerUp pu = powerUps[i];
        if (pu != null && pu.active && level1Powerup != null)
        {
            g.drawImage(level1Powerup, pu.x, pu.y, 1 + pu.size, 1 + pu.size, this);
        }
    }

    if (playerImageLevel1 != null)
    {
        g.drawImage(playerImageLevel1, x, y, 2 + width, 2+ width, this);
    }
}

public void drawLevel2Content(Graphics g)
{
    if (level2Bg != null) //draws level 2 with respective images and background
    {
        g.drawImage(level2Bg, 0, 0, getWidth(), getHeight(), this);
    }

    for (int i = 0; i < characterCount; i++)
    {
        NPC npc = characters[i];
        if (npc != null && level2Obstacle != null)
        {
            g.drawImage(level2Obstacle, npc.x, npc.y, 1 + npc.size, 1 + npc.size, this);
        }
    }
    for (int i = 0; i < powerUpCount; i++)
    {
        PowerUp pu = powerUps[i];
        if (pu != null && pu.active && level2Powerup != null)
        {
            g.drawImage(level2Powerup, pu.x, pu.y, 1 + pu.size, 1 + pu.size, this);
        }
    }
    if (playerImageLevel2 != null)
    {
        g.drawImage(playerImageLevel2, x, y, 2 + width, 2 + width, this);
    }
}

public void drawLevel3Content(Graphics g)
{

    if (level3Bg != null) //draws level 3 with respective images and background
    {
        g.drawImage(level3Bg, 0, 0, getWidth(), getHeight(), this);
    }

    for (int i = 0; i < characterCount; i++) 
    {
            NPC npc = characters[i];
            if (npc != null && level3Obstacle != null) 
            {
                g.drawImage(level3Obstacle, npc.x, npc.y, 1 + npc.size, 1 + npc.size, this);
            }
        }

        //draw the powerups
        for (int i = 0; i < powerUpCount; i++) 
        {
            PowerUp pu = powerUps[i];
            if (pu != null && pu.active && level3Powerup != null) 
            {
                g.drawImage(level3Powerup, pu.x, pu.y, 1 + pu.size, 1 + pu.size, this);
            }
        }

    if (playerImageLevel3 != null)
    {
        g.drawImage(playerImageLevel3, x, y, 2 + width, 2 + width, this);
    }
}

public void drawGameUI(Graphics g)
{
    g.setColor(new Color(169, 214, 245, 160));	//top corner rectangle that holds the user's ongoing game stats and progress
    g.fillRect(10, 10, 320, 105);

    g.setColor(Color.WHITE);
    g.setFont(new Font("Raleway", Font.BOLD, 18));
    g.drawString("level: " + level, 25, 40);	//level
    g.drawString("powerups: " + powerUpsCollected + "/" + powerUpsRequired, 25, 65);	//number of powerups collected
    g.drawString("lives: " + lives, 25, 90);	//lives that are still there

    g.setFont(new Font("Raleway", Font.BOLD, 20));
    String timerDisplay = formatTime(elapsedTime);
    g.setColor(new Color(220,255,220));
    g.drawString("time: " + timerDisplay, 180, 106);	//adds time

    g.setFont(new Font("Raleway", Font.BOLD, 22));
    g.setColor(Color.WHITE);
    g.drawString("score: " + score, getWidth() / 2 - 50, 50);	//current score
}

public void drawLevelIntro(Graphics g)
{
    String levelName = getLevelName();
    g.setFont(new Font("Raleway", Font.BOLD, 38));
    g.setColor(new Color(0,0,0,140));
    g.fillRect(getWidth()/2-150, 120, 310, 60);
    g.setColor(Color.WHITE);
    g.drawString("level " + level + ": " + levelName, getWidth()/2-135, 165);
}



public void drawGameOverScreen(Graphics g) //game overscreen
{

    if (level == 1 && level1Bg != null) //draws each level background
    {
        g.drawImage(level1Bg, 0, 0, getWidth(), getHeight(), this);
    }
    else if (level == 2 && level2Bg != null)
    {
        g.drawImage(level2Bg, 0, 0, getWidth(), getHeight(), this);
    }
    else if (level == 3 && level3Bg != null)
    {
        g.drawImage(level3Bg, 0, 0, getWidth(), getHeight(), this);
    }

    g.setColor(new Color(0, 0, 0, 180));
    g.fillRect(0, 0, getWidth(), getHeight());

    g.setColor(Color.RED);
    g.setFont(new Font("Raleway", Font.BOLD, 36));
    g.drawString("GAME OVER", getWidth()/2-110, getHeight() / 2 - 30);	//displayed message

    g.setFont(new Font("Raleway", Font.BOLD, 22));
    g.setColor(Color.WHITE);
    g.drawString("Final Score: " + score, getWidth() / 2 - 60, getHeight()/2+10);	//ending score
    g.drawString("End Level: " + level, getWidth()/2-60, getHeight()/2+40);	//last level player was on
    saveScoreToLeaderboard();

    playAgain.setVisible(true);
    exit.setVisible(true);
}

public void drawWinScreen(Graphics g) //when beating all 3 levels
{
    g.setColor(new Color(0,0,0,170));
    g.fillRect(getWidth()/2-200, getHeight()/2-150, 400, 300);

    g.setColor(new Color(100,255,100));
    g.setFont(new Font("Arial", Font.BOLD, 48));
    g.drawString("YOU WIN!!!", getWidth()/2-120, getHeight()/2-80);

    g.setFont(new Font("Arial", Font.BOLD, 24));
    g.setColor(Color.WHITE);
    g.drawString("final score: " + score, getWidth()/2-100, getHeight()/2-20);	//also displays ending stats are previous method
    g.drawString("total powerups: " + totalPowerupsCollected, getWidth()/2-100, getHeight()/2+20);

    g.setFont(new Font("Arial", Font.BOLD, 32));
    g.drawString("click to exit", getWidth()/2-100, getHeight()/2+80);
}

    public void actionPerformed(ActionEvent evt)
    {

        if (timerStarted && !gameOver && level <= 3)
        {
            elapsedTime = System.currentTimeMillis() - startTime;	//calculates the time since when the game started
        }

        int spawnInterval = 60 - (level - 1) * 10;	//determines the spawn interval of the powerups
        if (spawnInterval < 20)
        {
			spawnInterval = 20;	//makes sure it doesn't go below 40
		}

        if (counter % spawnInterval == 0 && powerUpCount < powerUps.length)
        {
            int px = (int)(Math.random() * (getWidth() - 30));	//randomly picks an x value from where the powerup will spawn and adds it to the array
            powerUps[powerUpCount++] = new PowerUp(px, 0, 30, powerUpFallSpeed);
        }

        for (int i = 0; i < powerUpCount; i++)
        {
            PowerUp pu = powerUps[i];
            if (pu != null && pu.active) 	//goes through all the active powerups to see if there is any collisions
            {
                pu.y += pu.speed;

                if (pu.x < x + width / 2 && pu.x + pu.size > x && pu.y < y + width / 2 && pu.y + pu.size > y)
                {
                    pu.active = false;
                    score += 50;

                    handlePowerUpCollision(pu);	//calls other method when the player collides with powerup for the question
                }

                if (pu.y > getHeight()) //if powerup goes uncollected
                {
                    pu.active = false;
                }
            }
        }

        if (powerUpsCollected >= powerUpsRequired) //when the number of powerups is met, the player moves to the next level with new requirements
        {
            level++;
            powerUpsCollected = 0;
			powerUpsRequired += 5;	//increasing the powerup requirement each level
			gameData.setCurrentLevel(level); //udates level in GameQuestionData

			JLabel levelUpLabel = new JLabel("Advanced to Level " + level + "!");	//display meassge when player is advancing to the next level
			levelUpLabel.setFont(new Font("Arial", Font.BOLD, 20));
			JOptionPane.showMessageDialog(this, levelUpLabel);

            if (level > 3) //sets the powerups required, speed, and the obstacles speed for each level
            {
                time.stop();
                repaint();
                return;
            }
            powerUpsCollected = 0;
            if (level == 2) //level 2 required stats
            {
                powerUpsRequired = 10;
                npcFallSpeed = 4;
                powerUpFallSpeed = 2;
            }
            else if (level == 3) //level 3 required stats
            {
                powerUpsRequired = 15;
                npcFallSpeed = 6;
                powerUpFallSpeed = 4;
            }
        }

        if (evt.getSource() == playAgain)	//if the player wants to play again resetGame is called and everything is reset
        {
            resetGame();
            return;
        }

        if(evt.getSource() == exit)	//if player wants to exit and check something else
        {
            if (parent != null)
            {
                playAgain.setVisible(false);
                exit.setVisible(false);
                time.stop();
                parent.showCard("menu");	//takes player the menu card
            }
            return;
        }

        if(!gameOver && level<=3)
        {
            for(int i = 0; i < characterCount; i++)
            {
                characters[i].y += characters[i].speed;

                if (characters[i].x < x + width / 2 && characters[i].x + characters[i].size > x && characters[i].y < y + width / 2 && characters[i].y + characters[i].size > y)
                {
                    lives--;	//if player collides with an obstacle they lose a life
                    characters[i].y = -1000; //removes block from view

                    if(lives <= 0) 	//if the number of lives becomes zero then its game over
                    {
                        gameOver = true;
                        time.stop();
                        playAgain.setVisible(true);
                        exit.setVisible(true);
                    }
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

           int charInterval;
			if(level == 1)
			{
				charInterval = 12; //makes the obstacles less frequent so there's less to dodge
			}
			else if(level == 2)
			{
				charInterval = 16;
			}
			else
			{
				charInterval = 20;
			}
            repaint();

		if (counter % charInterval == 0 && maxCharacters > characterCount)	//spawns a new obstacle if there is room
		{
			int x = (int)(Math.random() * (getWidth() - 40)); 		//randomizes the x coordinate but stays within bounds
			characters[characterCount] = new NPC(x, 0, 40, npcFallSpeed);
			characterCount ++; 		//increases amount of obstacles
		}


		if (level == 2 && counter % 120 == 0 && maxCharacters > characterCount)	//spawns new stationary object in level 2
		{
			int x = (int)(Math.random() * (getWidth() - 40));
			characters[characterCount] = new NPC(x, (int)(Math.random() * (getHeight() - 200)), 40, 0); //stationary obstacles
			characterCount ++;	//increases amount of obstacles
		}

			}
				repaint();
	}

    class GameQuestionData 
   {
    private String question;
    private String[] answerSet;
    private int correctAnswer;
    private boolean[] usedQuestions;
    private int currentLevel;
    private int totalQuestions;
    private int questionsAsked;
    private int correctCount;

    public GameQuestionData()
     {
        answerSet = new String[4];
        usedQuestions = new boolean[50]; //for all questions
        currentLevel = 1;
        countQuestions();
    }

    public void setCurrentLevel(int level) 
    {
        this.currentLevel = level;
        countQuestions();
    }

    public void countQuestions() 
    {
        String fileName = "level" + currentLevel + ".txt";
        Scanner scanner = null;
        totalQuestions = 0;
        
        try 
        {
            scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) 
            {
                scanner.nextLine(); //the question
                for (int i = 0; i < 4; i++) 
                {
					scanner.nextLine(); //asnwer to question
				}
                scanner.nextLine(); //the correct asnwer index
                totalQuestions++;
            }
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("Error counting questions: " + e.getMessage());
        }
        if (scanner != null) 
        {
            scanner.close();
        }

        //resets the tracking of questions when changing levels
        for (int i = 0; i < usedQuestions.length; i++) 
        {
            usedQuestions[i] = false;
        }
        questionsAsked = 0;
    }

    public void grabQuestionFromFile(int level) 
    {
        String fileName = "level" + currentLevel + ".txt";
        
        if (questionsAsked >= totalQuestions) 
        {
            //reset when used all questions
            for (int i = 0; i < usedQuestions.length; i++) 
            {
                usedQuestions[i] = false;
            }
            questionsAsked = 0;
        }

        //randomly picks a question that hasn't been used yet
        int questionNum;
        do 
        {
            questionNum = (int)(Math.random() * totalQuestions);
        } 
        while (usedQuestions[questionNum]);

        usedQuestions[questionNum] = true;
        questionsAsked++;

        Scanner fileScanner = null;
        try 
        {
            fileScanner = new Scanner(new File(fileName));
            
            //skips to the selected question
            for (int i = 0; i < questionNum * 6; i++) 
            {
                fileScanner.nextLine();
            }

            //reads in the question and answers
            question = fileScanner.nextLine();
            for (int i = 0; i < 4; i++) 
            {
                answerSet[i] = fileScanner.nextLine();
            }
            correctAnswer = Integer.parseInt(fileScanner.nextLine());
        } 
        catch (Exception e) 
        {
            System.out.println("Error loading question: " + e.getMessage());
        }
        if (fileScanner != null) 
        {
            fileScanner.close();
        }
    }

    public String getQuestion() 
    {
        return question;
    }

    public String getAnswer(int index) 
    {
        if (index >= 0 && index < 4) 
        {
            return answerSet[index];
        }
        return "";
    }

    public int getCorrectAnswer() 
    {
        return correctAnswer;
    }

    public int getQuestionCount() 
    {
        return questionsAsked;
    }

    public int getCorrectCount() 
    {
        return correctCount;
    }

    public void addOneToCorrectCount() 
    {
        correctCount++;
    }

    public void resetAll() 
    {
        for (int i = 0; i < usedQuestions.length; i++) 
        {
            usedQuestions[i] = false;
        }
        questionsAsked = 0;
        correctCount = 0;
    }
}

class QuestionActionListener implements ActionListener //this actually implements the actions and uses actionlistener , nested class to handle events
{
    private MGame gameInstance; 
    private int answerIndex;	//answer index from the question txt files from 0 to 3
    private JDialog dialog;


    public QuestionActionListener(MGame game, int idx, JDialog d)
    {
        this.gameInstance = game;
        this.answerIndex = idx;	//initalizes
        this.dialog = d;
    }

    public void actionPerformed(ActionEvent e) //similar to previous question method
    {
        boolean isCorrect = answerIndex == gameInstance.gameData.getCorrectAnswer();
        String feedback;
        Color feedbackColor;

        if (isCorrect)
        {
            gameInstance.gameData.addOneToCorrectCount();	//increments answer count
            gameInstance.score += 50;	//adds to score
            gameInstance.removeObstacleInFront(); 		//removes current obstacle in front
            gameInstance.powerUpsCollected++;
            feedback = "yay!! you're correct!!\n";
            feedbackColor = Color.GREEN;

            double chance = Math.random();
            if (chance < 0.9)
            {
                int booster = (int)(Math.random() * 4);
                String boosterMsg = "";

                if (booster == 0)
                {
                    if (gameInstance.lives < 5)
                    {
                        gameInstance.lives++;
                        boosterMsg = "lucky you! you earned an extra life!";
                    }
                    else
                    {
                        gameInstance.score += 100;	//adds to score
                        boosterMsg = "more points!! +100";
                    }
                }
                else if (booster == 1)
                {
                    gameInstance.score += 200; //adds to score
                    boosterMsg = "extra lucky bonus +200!";
                }
                else if (booster == 2)
                {
                    gameInstance.npcFallSpeed = Math.max(2, gameInstance.npcFallSpeed - 14);	//allows for player to easily navigate through
                    boosterMsg = "jackpot!! obstacles slow down!";
                }
                else if (booster == 3)
                {
                    for (int j = 0; j < gameInstance.characterCount; j++)
                    {
                        if (gameInstance.characters[j] != null) gameInstance.characters[j].y = -1000;
                    }
                    boosterMsg = "wow!! lets clear the obstacles!"; //motivates player to continue
                }
                feedback = feedback + boosterMsg;
            }
        }
        else
        {
            feedback = "uh oh! try again?";	//when player gets the question wrong
            feedbackColor = Color.RED;
            gameInstance.lives--;
            if (gameInstance.lives <= 0)
            {
                gameInstance.lives = 0; 		//if by the time they get the question wrong and they are down to zero lives, its game over
                gameInstance.gameOver = true;
                gameInstance.time.stop();
                gameInstance.playAgain.setVisible(true);
                gameInstance.exit.setVisible(true);
            }
        }


        JLabel feedbackLabel = new JLabel(feedback);
        feedbackLabel.setFont(new Font("Serif", Font.BOLD, 20));
        feedbackLabel.setForeground(feedbackColor);
        feedbackLabel.setHorizontalAlignment(JLabel.CENTER);
        JOptionPane.showMessageDialog(gameInstance, feedbackLabel, "Result", JOptionPane.PLAIN_MESSAGE);

        dialog.dispose();

        if (gameInstance.gameData.getQuestionCount() >= 4)
        {
            gameInstance.gameData.resetAll();
        }
        gameInstance.gameData.grabQuestionFromFile(gameInstance.level); //
        gameInstance.time.start();
    }
}
    public void keyPressed(KeyEvent evt)
    {
        int key = evt.getKeyCode();

        if (!timerStarted && !gameOver && level <= 3)
        {
            timerStarted = true;
            startTime = System.currentTimeMillis() - elapsedTime;
        }

        if (key == KeyEvent.VK_UP) //the incremented amount the user moves with each key pressed
        {
			y -= 30;
		}
        if (key == KeyEvent.VK_DOWN)
        {
			y += 30;
		}
        if (key == KeyEvent.VK_RIGHT)
        {
			x += 30;
		}
        if (key == KeyEvent.VK_LEFT)
        {
			x -= 30;
		}

        if (x < 0) //keeps player in boundaries
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

    public void keyTyped(KeyEvent evt) {}	//unused
    public void keyReleased(KeyEvent evt) {}

    public void mousePressed(MouseEvent e) {}


    public void mouseClicked(MouseEvent e) {}	//unused
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

}

    class NPC //x and y coordinate of the falling object/npc, and the size(height+width), and its speed
    {
        int x;
        int y;
        int size;
        int speed;
        public NPC(int x, int y, int size, int speed)
        {
            this.x = x;	//initializes all 4 variables
            this.y = y;
            this.size = size;
            this.speed = speed;
        }
    }


class PowerUp //holds the positions of x and y for the falling powerups and speed
{
    int x;
    int y;
    int size;
    int speed;
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
