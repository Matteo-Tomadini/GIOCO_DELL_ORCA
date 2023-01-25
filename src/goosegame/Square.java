package goosegame;

import javax.swing.JLabel;
import java.util.ArrayList;
import java.awt.Color;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class Square extends JLabel {

    protected int number;   
    protected Board board;
    protected GooseGame game;    
    private Box pawnsBox;
  
    
    protected Player player;
    private String DEFAULT_NOTIFICATION;
    protected String NOTIFICATION;

    private static final int WIDTH = 130;
    private static final int HEIGHT = 75;

    public Square(Color color, String text, int number, GooseGame game) {
	this.number = number;
	this.game = game;
	DEFAULT_NOTIFICATION = "%s, a new player arrived and you are sent back to square %d";
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
	setLayout(new GridLayout(2, 1));
        setOpaque(true);
        setBackground(color);
	setBorder(BorderFactory.createLineBorder(Color.black));
	JLabel textLabel = new JLabel();
	if (text != null) {
		textLabel.setText(String.format("<html><body style=\"text-align: left;  text-justify: inter-word;\">%s</body></html>",text));
		textLabel.setFont(new Font("Serif", Font.PLAIN, 15));
	}
	pawnsBox = new Box(BoxLayout.X_AXIS);
    add(textLabel);
   	add(pawnsBox);
    }



    public boolean addPawn(Pawn pawn) {		//aggiunge la pedina
	pawnsBox.add(pawn);
	pawnsBox.paintImmediately(0, 0, 5000, 5000);

	return true;
    }
    
    public boolean addPassingByPawn(Pawn pawn) {	
	pawnsBox.add(pawn);
	pawnsBox.paintImmediately(0, 0, 5000, 5000);
	return true;
    }

    public boolean addPawnWithRevalidate(Pawn pawn) { 
	    pawnsBox.add(pawn);
	    pawnsBox.revalidate();
	    pawnsBox.paintImmediately(0, 0, 5000, 5000);
	   
	    return true;
    }

    public boolean addPawnShort(Pawn pawn) {
	    addPassingByPawn(pawn);
	    try {
	    	TimeUnit.MILLISECONDS.sleep(500);
	    } catch (InterruptedException e) {
		    // do nothing.
	    }
	    removePassingByPawn(pawn);
	    return true;
    }

    public boolean removePassingByPawn(Pawn pawn) {	
	pawnsBox.remove(pawn);
	pawnsBox.paintImmediately(0, 0, 5000, 5000);
	return true;
    }
    
    public boolean removePawn(Pawn pawn) { //toglie la pedina dalla casella
	pawnsBox.remove(pawn);
	pawnsBox.paintImmediately(0, 0, 5000, 5000);
	player = null;
	return true;
    }

    public int getNumber() {
	    return number;
    }

    public void setPlayer(Player p) {
	    player = p;
    }

    public void reset() {
	    player = null;
	    pawnsBox.removeAll();
	    pawnsBox.revalidate();
	    pawnsBox.repaint();
    }

    /*
     * Se ci sono 2 o più giocatori sulla stessa casella il giocatore si sposta in avanti di 3 caselle
     */
    public void action(Player p, Square source) {
	if (player != null) { 
		
	
		System.out.println("I giocatori sono sulla stessa casella");
		String NOTIFICATION = "I Giocatori sono sulla stessa casella,verrai spostato in avanti di 3 caselle";
		game.showMessage(String.format(NOTIFICATION, p.getName()));
		int avanti = 3;		   

		Square newSquare = p.move(avanti);
		
		   }
	
	player = p;
    }



    }

