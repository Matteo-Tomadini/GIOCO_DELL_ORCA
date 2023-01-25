package goosegame;

import java.awt.Color;

public class Finish extends Square {
	public Finish(Color color, String text, int number, GooseGame game) {
		super(color, text, number, game);
	}
															//classe che fa concludere il gioco

	public void action(Player p, Square source) {

		game.ended(p);

	}
}

