package goosegame;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.io.*;



class Board extends JPanel {  //gestione della tabella utilizzando la classe Square

    public ArrayList<Pawn> pawns;  //arraylist di pedine grafica pedine
    private static final int WIDTH = 12;
    private static final int HEIGHT = 9;
    private static final int NUMBER_OF_SQUARES = 64;
    private Square[] squares;
    private GooseGame game;
    private int MAX_STEPS_DISPLAYED = 12;

    public Board(String filepath, ArrayList<Color> colors, GooseGame game)  {

	this.game = game;
	squares = new Square[NUMBER_OF_SQUARES];
        setLayout(new GridLayout(WIDTH, HEIGHT));
	try {
		BufferedReader br = new BufferedReader(new FileReader(filepath));
		String line = br.readLine();
		String[] tokens = line.split(" ", 4);
		Square square;
		int number;
        	for (int i = 0; i < WIDTH; i++)
        	{

       		     for (int j = 0; j < HEIGHT; j++)
        	     {
			if (tokens[0].equals(String.valueOf(i)) && tokens[1].equals(String.valueOf(j))) {
				number = Integer.parseInt(tokens[2]);
				switch (tokens[3]) {


					    case "ARRIVO":
						square = new Finish(Color.white, tokens[3], number, game);
						break;

						case "INIZIO":
						square = new Start(Color.white, tokens[3], number, game);
						break;

						case "IMPREVISTO":
						square = new Imprevisto(Color.ORANGE,tokens[3],number,game);
						break;	
						
						
					    default:
						square = new Square(Color.ORANGE, tokens[3], number, game);   //questa è¨ la casella di default uguale per tutto il programma						
						break;
				}

				squares[number] = square;
				line = br.readLine();
				if (line != null) {
					tokens = line.split(" ", 4);
				}
			}
			else {
				square = new Square(Color.white, null, -1, null);
			}
			add(square);
            	    }
                }
        }
	catch (Exception e) {
		e.printStackTrace();
	}

	pawns = new ArrayList<Pawn>(colors.size());
	Pawn pawn;
	for (Color c: colors) {
		pawn = new Pawn(c, squares[0]);
		pawns.add(pawn);
		squares[0].addPawn(pawn);
	}
   
    setVisible(true);

    }

    private void resetSquares() {
	    for (int i=0; i<NUMBER_OF_SQUARES; i++) {
		squares[i].reset();
	    }
    }
    private void creaPedine(ArrayList<Color> colors) {   //Arraylist delle pedine colorate
	pawns = new ArrayList<Pawn>(colors.size());
	Pawn pawn;
	for (Color c: colors) {
		pawn = new Pawn(c, squares[0]);
		pawns.add(pawn);
		squares[0].addPawn(pawn);
	}
    }

    public int getNewPosition(int oldPos, int roll) {
	int newPos = oldPos + roll;
	if (newPos < NUMBER_OF_SQUARES) {
		return newPos;
	}
	else {
		return (NUMBER_OF_SQUARES - 1) - (newPos - (NUMBER_OF_SQUARES-1));
	}
    }

    public Square getSquare(int number) {
	    return squares[number];
    }

    public int getNumberOfSquares() {
	    return NUMBER_OF_SQUARES;
    }

    public static String getColorName(Color c) {
    	for (Field f : Color.class.getFields()) {
        	try {
        	    if (f.getType() == Color.class && f.get(null).equals(c)) {
        	        return f.getName();
        	    }
        	} catch (java.lang.IllegalAccessException e) {
        	    // it should never get to here
        	} 
    	}
   	return "unknown";
    }

   public boolean addPawnToSquare(Color pawnColor, int squareIndex) {   //aggiunge la pedina colorata alla casella
	   Pawn pawn = getPawnByColor(pawnColor);
	   Square square = squares[squareIndex];
	   square.addPawn(pawn);
	   pawn.setCurrentSquare(square);
	   return true;
   }

   public boolean removePawnFromSquare(Pawn pawn, int squareIndex) {    //toglie la pedina colorata alla casella
	   Square square = squares[squareIndex];
	   square.removePawn(pawn);
	   pawn.setCurrentSquare(null);
	   return true;
   }

   private int[] getNextSquareNumber(int curr, int direction) {
	   int[] ret = new int[2];
	   int next_num;
	   int next_dir;
	   if (direction == 1) {
		next_num = curr + 1;
		if (next_num == NUMBER_OF_SQUARES-1) {
			next_dir = 0;
		}
		else if (next_num == NUMBER_OF_SQUARES) {
			next_num = curr - 1;
			next_dir = 0;
		}
		else {
			next_dir = 1;
		}
	   }
	   else {
		   next_num = curr - 1;
		   next_dir = 0;
	   }

	   ret[0] = next_num;
	   ret[1] = next_dir;

	   return ret;
   }

   /*
    * Muove la pedina finchè non arriva alla casella 63 e se il giocatore non fa un lancio perfetto, la pedina torna indietro.
    */
   private Square movePawn(Pawn pawn, int steps) {
	   int squareNumber = pawn.getSquareNumber();
	   Square currSquare;
	   int direction = steps > 0 ? 1 : 0;
	   pawn.removeFromCurrentSquare();

	   if (Math.abs(steps) > MAX_STEPS_DISPLAYED) {
		   squareNumber = getNewPosition(squareNumber, steps);
		   System.out.println("[Board:movePawn()] squareNumber=" + squareNumber);
	   }
	   else {
	   	int[] pair;

	   	for (int i=1; i<Math.abs(steps); i++) {
			pair = getNextSquareNumber(squareNumber, direction);
			squareNumber = pair[0];
			direction = pair[1];
			currSquare = squares[squareNumber];
			currSquare.addPawnShort(pawn);
		   }

	   	pair = getNextSquareNumber(squareNumber, direction);
	   	squareNumber = pair[0];
	   }
	   currSquare = squares[squareNumber];
	   currSquare.addPawnWithRevalidate(pawn);
	   pawn.setCurrentSquare(currSquare);

	   return currSquare;
   }

   public void reset(ArrayList<Color> colors) {
	   resetSquares();
	   creaPedine(colors);
   }

   public Square movePawn(Color pawnColor, int steps) {
	   Pawn pawn = getPawnByColor(pawnColor);
	   return movePawn(pawn, steps);
   }

   public void movePawnImmediately(Pawn pawn, int squareNumber) {
	   pawn.removeFromCurrentSquare();
	   Square square = squares[squareNumber];
	   square.addPawnWithRevalidate(pawn);
	   pawn.setCurrentSquare(square);
   }

   public void movePawnImmediately(Color pawnColor, int squareNumber) {
	   Pawn pawn = getPawnByColor(pawnColor);
	   movePawnImmediately(pawn, squareNumber);
   }

   public void movePawn(Color pawnColor, Square destination) {
	   Pawn pawn = getPawnByColor(pawnColor);
	   int start = pawn.getSquareNumber();
	   int dest = destination.getNumber();
	   int steps = dest - start; 
	   movePawn(pawn, steps);
   }

   private Pawn getPawnByColor(Color pawnColor) {
	for (Pawn p : pawns) {
		if (p.getColor().equals(pawnColor)) {
			return p;
		}
	}

	return null;
  }


}
