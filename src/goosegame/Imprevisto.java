package goosegame;

import java.awt.Color;


	
	class Imprevisto extends Square {
	   public Imprevisto(Color color, String text, int number, GooseGame game) {
	      super(color, text, number, game);
	      NOTIFICATION = "%s, Sei capitato su un imprevisto,sarai spostato in avanti dello stesso numero di caselle di quando hai lanciato il dado";
	   }

	   /*
	    * Imprevisto
	    */
	   @Override
	   public void action(Player p, Square source) {
	      game.showMessage(String.format(NOTIFICATION, p.getName()));
	      int lastRoll = p.getLastRoll();
	      if (p.wasLastMoveBackwards()) {
	         lastRoll = -lastRoll;
	      }

	      Square newSquare = p.move(lastRoll);
	      newSquare.action(p, this);
	   }
	}


	
