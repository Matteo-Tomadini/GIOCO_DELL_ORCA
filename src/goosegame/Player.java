package goosegame;

import java.awt.Color;

public class Player {  //gestione del giocatore
	private String nome;
	private Board board;
	private Square currentSquare;

	private Color color;
	private int lastRoll;
	private boolean lastMoveWasBackwards;

	
	private boolean Prigioniero;
	private boolean isStuckedInWell;
	
	private int punteggio;

	
	private int innCounter;

	public Player(String name, Color color) {
		this.nome = nome;
		this.color = color;
		innCounter = 0;
		lastRoll = -1;
		lastMoveWasBackwards = false;
		punteggio = 0;
	}
	
	public int getPunteggio() {
		return this.punteggio;
	}
	
	public void incrementaPunteggio() {
		this.punteggio += 100;
	}

	public void setLastRoll(int roll) {
		lastRoll = roll;
	}

	public int getLastRoll() {
		return lastRoll;
	}

	public boolean wasLastMoveBackwards() {
		return lastMoveWasBackwards;
	}

	public Square moveTo(int squareNumber) {  //movimento del giocatore in una determinata casella
		Square oldSquare = currentSquare;
		int steps = squareNumber - getPosition();
		currentSquare = board.movePawn(color, steps);
		if (currentSquare.getNumber() - oldSquare.getNumber() < 0) {
			lastMoveWasBackwards = true;
		}
		else {
			lastMoveWasBackwards = false;
		}

		return currentSquare;
	}

	public Square move(int steps) {  //movimento del giocatore di un tot di caselle
		System.out.println("[Player:moveTo()] moving player " + steps + " steps starting from " + currentSquare.getNumber());
		Square oldSquare = currentSquare;
		currentSquare = board.movePawn(color, steps);
		if (currentSquare.getNumber() - oldSquare.getNumber() < 0) {
			lastMoveWasBackwards = true;
		}
		else {
			lastMoveWasBackwards = false;
		}

		return currentSquare;
	}

	public String getName() {
		return nome;
	}

	public void setSquare(Square s) {
		currentSquare = s;
	}

	public void setBoard(Board b) {
		board = b;
	}

	public Square getSquare() {
		return currentSquare;
	}

	public int getPosition() {
		return currentSquare.getNumber();
	}

	public void setInnCounter(int c) {
		innCounter = c;
	}

	public void decreaseInnCounter() {
		innCounter--;
	}

	public int getInnCounter() {
		return innCounter;
	}

	private boolean isStuckedInWell() {
		return isStuckedInWell;
	}

	public boolean isPrisoned() {
		return Prigioniero;
	}

	private boolean isDrinkingInInn() {
		if (innCounter > 0) {
			--innCounter;
			return true;
		}
		else {
			return false;
		}

	}

	public void release() {
		Prigioniero = false;
	}

	public void prison() {
		Prigioniero = true;
	}

	public void setStuckedInWell(boolean stucked) {
		isStuckedInWell = stucked;
	}

	public Color getColor() {
		return color;
	}

	public boolean isTrapped() {
		return isDrinkingInInn() || isStuckedInWell() || isPrisoned();
	}
}
