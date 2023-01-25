package goosegame;

import java.awt.Color;


	
	class Goose extends Square {
	   public Goose(Color color, String text, int number, GooseGame game) {
	      super(color, text, number, game);
	      NOTIFICATION = "%s, Sei capitato su un imprevisto,prego rilancia il dado";
	   }

	   /*
	    * The players that lands on this type of square, repeats his roll.
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


	
