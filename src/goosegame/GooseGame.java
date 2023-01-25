package goosegame;


import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Timer;

import static goosegame.GooseGame.*;


public class GooseGame implements Runnable {


	private JSplitPane splitPane;
	private JSplitPane sidebar;
	private MessageArea messageArea;
	private Board board;
	private DiceRollPanel diceRollPanel;
	private Splash splash;
	private ArrayList<Player> players;
	private int currPlayer;
	private int lastRoll;
	private int numberOfRounds;
	private InitSplash initSplash;
	private static final String BOARD_DESCRIPTION_FILE = "C:\\Users\\Utente\\Desktop\\GOOSE_GAME_TROVATO\\src\\SquareLabels.txt";
	private static final String DOMANDE_FILE = "C:\\Users\\Utente\\Desktop\\GOOSE_GAME_TROVATO\\src\\goosegame\\images\\domandiere.txt";
	private static List<String> lista_domande;
	



	private static List<String> inizializzaDomande(String percorsoFileTxt) {
		List<String> lista_domande = Domandiere.readFile(percorsoFileTxt);   //lista che legge le cose contenute del file txt
		return lista_domande;
	}


	/*
	 * Genera una domanda casuale dal file 'domande.txt'
	 * ritorna una domanda presente nel file txt (con '|' come separatore)
	 */
	public static String generaDomandaCasuale() {
		int numeroCasuale = ThreadLocalRandom.current().nextInt(0, lista_domande.size());
		return lista_domande.get(numeroCasuale);
		
	}

	/*
	 * Parsa la domanda e ritorna un array di stringhe
	 * dove la prima stringa è l'indice della domanda, la seconda è la domanda e poi le risposte
	 */
	public static String[] parsingDomanda(String domandaConSeparatore) {
		String[] domanda = domandaConSeparatore.split("-");
		return domanda;
	}


	/*
	 * Ottiene la risposta corretta della domanda di indice 'Domanda'
	 * ritorna la risposta corretta (stringa)
	 */
	public static String ottieniRisposta(int numeroDomanda) {

		List<String> domande = Domandiere.readFile("C:\\Users\\Utente\\Desktop\\GOOSE_GAME_TROVATO\\src\\goosegame\\images\\domandiere.txt");
		
		for (String domanda : domande) {
			String[] split = domanda.split("-");
			System.out.println(Arrays.toString(split));
			int indice = Integer.parseInt(split[0]);
			
			if (indice == numeroDomanda) {
				System.out.println("Risposta corretta: " + split[6]);
				return split[6];
			}
		}
		return "-1";
	}


	public GooseGame(String boardDesc) {		//costruttore che setta i pannelli


		lista_domande = inizializzaDomande(DOMANDE_FILE);



		initSplash = new InitSplash();
		players = initSplash.getPlayers();
		initSplash.hideFrame();
		initSplash = null;

		ArrayList<Color> colors = new ArrayList<Color>(players.size());
		for (Player p: players) {
			colors.add(p.getColor());
		}
		board = new Board(boardDesc, colors, this);
		
		for (Player p: players) {
			p.setSquare(board.getSquare(0));
			p.setBoard(board);
		}
		currPlayer = 0;
		numberOfRounds = 0;

		messageArea = new MessageArea();
		diceRollPanel = new DiceRollPanel(this, board, messageArea);

		sidebar = new JSplitPane(JSplitPane.VERTICAL_SPLIT, diceRollPanel, messageArea);
		sidebar.setDividerLocation(0.33);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, board, sidebar);
		splitPane.setDividerLocation(0.75);

		splash = new Splash(board);
	}

	public Player getPlayerByPawn(Pawn pawn) {
		Color c = pawn.getColor();
		for (Player p: players) {
			if (p.getColor().equals(c)) {
				return p;
			}
		}

		return null;
	}

	/*
	 * Avvia una nuova dialog per inserire la risposta
	 * ritorna la risposta data dall'utente
	 */
	public String showMessage(String msg) {
		String s = JOptionPane.showInputDialog(msg, "");
		return s;
		
	}

	private Player getCurrentPlayer() {		//ritorna il player che sta giocando al momento
		return players.get(currPlayer);
	}

	public void repeatRoll() {    //ripete il roll di dadi
		//dicesRolled(lastRoll);
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	private void setNextPlayer() {	//setta il prossimo player
		while (true) {
			currPlayer = (currPlayer + 1) % players.size();
			if (currPlayer == 0) {
				++numberOfRounds;
			}
			if (!getCurrentPlayer().isTrapped()) {
				break;
			}
			else {
				showMessage(String.format("%s, you lose your turn.", getCurrentPlayer().getName()));
			}
		}
	}

	public void dicesRolled(int dice_1, int dice_2) {  //fa il random del dado + gestione delle domande con il file txt
		int newPos, roll;
		Player player = getCurrentPlayer();
		Square oldSquare = player.getSquare();
		roll = dice_1 + dice_2;
		player.setLastRoll(roll);
		Square newSquare = player.move(roll);


		String domanda = generaDomandaCasuale();
		String[] domandaSplittata = parsingDomanda(domanda);

		String risposta = ottieniRisposta(Integer.parseInt(domandaSplittata[0]));


		String msg = "Domanda: " + domandaSplittata[1] + "\n" + domandaSplittata[2] + " - " + domandaSplittata[3] + " - " + domandaSplittata[4] + " - " + domandaSplittata[5];
		String ripostaUtente = showMessage(msg);
		
		Player p = getCurrentPlayer();
		if (risposta.equals(ripostaUtente)) {
			p.incrementaPunteggio();
		}
		messageArea.appendMessage("Giocatore" + currPlayer + " - punti: " + p.getPunteggio());
		
		try {
		
			Thread.sleep(1000);						//pulire lo schermo del punteggio
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
;
		messageArea.appendMessage("");
		messageArea.appendMessage("");
		messageArea.appendMessage("");
		messageArea.appendMessage("");
		messageArea.appendMessage("");
		
		
		/**
		 * 
		 * Per ottenere il punteggio del giocatore che "adesso" sta gioxcando:
		 * Player p = getCurrentPlayer();
		 * p.getPunteggio();
		 * 
		 */

		newSquare.action(player, oldSquare);
		setNextPlayer();
	}

	private ArrayList<Color> getPlayerColors()  {					//arraylist delle pedine
		ArrayList<Color> colors = new ArrayList<Color>(players.size());
		for (Player p: players) {
			colors.add(p.getColor());
		}

		return colors;
	}

	private void initPlayers() {		//setta i player
		for (Player p: players) {
			p.setSquare(board.getSquare(0));
			p.setBoard(board);
		}
	}

	public void run() {					//fa runnare la schermata inziziale
		players = initSplash.getPlayers();
		initSplash.hideFrame();
		initSplash = null;

		initPlayers();
		ArrayList<Color> colors = getPlayerColors();
		SwingUtilities.invokeLater(new Runnable() {
                   @Override
                   public void run() {
		      board.reset(colors);
                   }
                });

		currPlayer = 0;
		numberOfRounds = 0;
		messageArea.reset();
	}

	private void startNewGame() {			//fa iniziare una nuova partita
		initSplash = new InitSplash();
		Thread thread = new Thread(this);
		thread.start();
	}

	private boolean playAgain() {			//chiede se il giocatore vuole fare una nuova partita
		String message = "Start new game?";
		return JOptionPane.showConfirmDialog(board, message, null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
	}

	private void announceWinner(Player winner) {	//annuncia il vincitore
		String message = String.format("%s congratulations, you won!", winner.getName());
		showMessage(message);
	}

	public void ended(Player winner) {  //conclude il gioco
		announceWinner(winner);
		if (playAgain()) {
			startNewGame();
		}
		else {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
                String boardDescriptionFile = args.length > 0 ? args[0] : BOARD_DESCRIPTION_FILE;
		GooseGame game = new GooseGame(boardDescriptionFile);

		//Create and set up the window.
	        JFrame frame = new JFrame("GooseGame");
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	frame.getContentPane().add(game.getSplitPane());
        	frame.setBackground(Color.BLACK);
        	//Display the window.
       	 	frame.pack();
        	frame.setVisible(true);
	}
}

class Splash {		//schermata iniziale


	private JDialog dialog;
	private JOptionPane optionPane;
	private Board board;
	private Timer timer;
	private int duration = 3000; // milliseconds

	
	static JTextField t;


	static JFrame f;

	
	static JButton c;


	String data;
	public Splash(Board b) {


		final JLabel label = new JLabel();
		label.setBounds(20,150, 200,50);


		JLabel l1=new JLabel();
		l1.setBounds(220,40, 80,30);

		JButton c = new JButton("Inserisci la tua risposta");
		c.setBounds(120,120, 170,30);

		final JTextField text = new JTextField();
		text.setBounds(200,70, 100,30);

		String domanda = generaDomandaCasuale();
		String[] domandaSplittata = parsingDomanda(domanda);
		String risposta = ottieniRisposta(Integer.parseInt(domandaSplittata[0]));

		c.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(text.getText().equals(risposta))
				{
					
					System.out.println("corretta");
					text.setText("");


				}
				else {

					System.out.println("sbagliata");
					text.setText("");

				}



			}
		});





		board = b;
		optionPane = new JOptionPane("Message", JOptionPane.INFORMATION_MESSAGE);
		dialog = optionPane.createDialog(board, "Title");
		dialog.setPreferredSize(new Dimension(400, 270));
		dialog.add(c);
		dialog.add(text);
		dialog.add(label);

		dialog.pack();



		timer = new Timer(duration, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
			}
		});
	}

	public void setDuration(int d) {
		duration = d;
		timer = new Timer(duration, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				dialog.setVisible(false);
			}
		});
		}

		


	public void show(String msg) {		//fa vedere a terminale stringa
		System.out.println("[Spash:show()]");
		dialog.setLocationRelativeTo(board);
		optionPane.setMessage(String.format("<html><body style=\"text-align: left;  text-justify: inter-word;\">%s</body></html>",msg));
		timer.setRepeats(false);
		timer.start();
		dialog.setVisible(true);
	}

	public void hide() {		//nascondo il dialog
		dialog.setModal(false);
		dialog.setVisible(false);
	}


}

class MessageArea extends JSplitPane {
	private JTextArea textArea;
	private JScrollPane messagePane;

	private final static String newline = "\n";

	public MessageArea() {
		setOrientation(JSplitPane.VERTICAL_SPLIT);
		textArea = new JTextArea("Benvenuti nel gioco dell'orca" + newline);
		textArea.setFont(new Font("Serif", Font.ITALIC, 16));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		messagePane = new JScrollPane(textArea);
		messagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		messagePane.setPreferredSize(new Dimension(350, 250));



		setLeftComponent(messagePane);

		setDividerLocation(0.5);
	}



	public void appendMessage(String message) {		//appende il messaggio nell'area di testo sul gioco
		textArea.append(message + newline);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	public void reset() {		//resetta lo spazio della textarea
		textArea.setText("");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}


class DiceRollPanel extends JPanel {		//gestione del random del dado e della sua animazione
	private JLabel prompt;
	private JLabel firstDice;
	private JLabel secondDice;
	private JPanel dicesBox;
	private JPanel buttonsBox;
	private JButton rollButton;
	private ImageIcon[] diceIcons;
	private int NUMBER_OF_ROLLS = 4;
	private int MILLISECONDS_BETWEEN_ROLLS = 1000;
	private Board board;
	private MessageArea messageArea;
	private GooseGame game;
	private static final String DICE_ICONS_DIRECTORY = "C:\\Users\\Utente\\Downloads\\Gioco_dell_orca\\src\\goosegame\\images";

	public DiceRollPanel(GooseGame g, Board b, MessageArea m) {
	
		game = g;
		board = b;
		messageArea = m;
	
		setPreferredSize(new Dimension(350, 150));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		prompt = new JLabel();
		prompt.setText("Hello");
		prompt.setHorizontalAlignment(JLabel.CENTER);
		add(prompt, c);

		diceIcons = new ImageIcon[6];
		for (int i=0; i<6; i++) {
			diceIcons[i] = getDiceImageIcon(i+1);
		}

		firstDice = new JLabel();
		secondDice = new JLabel();
		firstDice.setIcon(diceIcons[2]);
		secondDice.setIcon(diceIcons[5]);
		c.insets = new Insets(10,0,0,4);  
		c.gridwidth=1;
		c.gridx = 0;
		c.gridy = 1;
		add(firstDice, c);
		c.insets = new Insets(10,4,0,0); 
		c.gridx = 1;
		add(secondDice, c);

		
		rollButton = new JButton("Roll");
		rollButton.addActionListener(new ActionListener() {
   			@Override
			public void actionPerformed(ActionEvent e) {
       				rollDices();
   			}
		});
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=2;
		c.insets = new Insets(10, 0, 0, 0);
		add(rollButton, c);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	private static ImageIcon getDiceImageIcon(int number) {
		String wdir = System.getProperty("user.dir");
		String delim;
		if (wdir.contains("/")) {
			delim = "/";
		}
		else {
			delim = "\\\\";
		}
		String[] dentries = wdir.split(delim);
		String leaf = dentries[dentries.length-1];
		String filepath;
		if (leaf.equals("src")) {
		filepath = "goosegame" + delim + "images" + delim;
		}
		else {
			filepath = "src" + delim + "goosegame" + delim + "images" + delim;
		}
		
		filepath = filepath + "dice_" + number + ".png";
		BufferedImage img = null;
		try {
 			img = ImageIO.read(new File(filepath));
		} catch (IOException e) {
    			e.printStackTrace();
		}
		Image dimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        	return new ImageIcon(dimg);

	}

	private int getRandomDiceRoll() {
		// returns int in [0,5] range
		return ThreadLocalRandom.current().nextInt(0, 6);
	}

	public void rollDices() {
		SwingUtilities.invokeLater(new Runnable() {
                   @Override
                   public void run() {
                      rollButton.setEnabled(false);
                   }
                });

		int dice_1=0, dice_2=0;
        	for (int i = 1; i <= NUMBER_OF_ROLLS; i++) {
		    dice_1 = getRandomDiceRoll();
		    dice_2 = getRandomDiceRoll();
		    firstDice.setIcon(diceIcons[dice_1]);
		    secondDice.setIcon(diceIcons[dice_2]);
		    firstDice.paintImmediately(0, 0, 5000, 5000);
		    secondDice.paintImmediately(0, 0, 5000, 5000);
		    if (i == NUMBER_OF_ROLLS) {
			prompt.setText("You got " + (dice_1 + dice_2 + 2));
			prompt.paintImmediately(0, 0, 5000, 5000);
		    }
	            try {
        	       TimeUnit.MILLISECONDS.sleep(MILLISECONDS_BETWEEN_ROLLS);
            	    } catch (InterruptedException e) {
                       throw new RuntimeException(e);
            	    }
        	}
		
	        try {
        	   TimeUnit.MILLISECONDS.sleep(MILLISECONDS_BETWEEN_ROLLS);
            	} catch (InterruptedException e) {
                   throw new RuntimeException(e);
            	}

		game.dicesRolled(dice_1+1, dice_2+1);
                //game.dicesRolled(15, 16);

		SwingUtilities.invokeLater(new Runnable() {
                   @Override
                   public void run() {
                      rollButton.setEnabled(true);
                   }
                });
	}
}
