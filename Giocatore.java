package MainPackage;

public class Giocatore {
	private String nome;
	private int punti;
	//simbolo
	public Giocatore(String nome) {
		this.nome = nome;
		this.punti = 0;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getPunti() {
		return punti;
	}
	public void setPunti(int punti) {
		this.punti = punti;
	}
	
	public void togliPunti(int punti)
	{
		this.punti=punti/2;
	}
	
	public void addPunti(int punti)
	{
		this.punti+=punti;
	}
	
	
}
