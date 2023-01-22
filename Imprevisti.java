package MainPackage;

public class Imprevisti {//eventi che svantaggiano i giocatori
	
	public void imprevisto(Giocatore primo,Giocatore secondo) {//primo = giocatore vittima dell'iprevisto, secondo = altro giocatore
		int tipoImprevisto = (int)(Math.random()*3);//numero random da 0 a 2
		
		if(tipoImprevisto == 0){
			//togli punti al giocatore
			imprevisto0(primo);
		}else if(tipoImprevisto == 1){
			//togli punti al giocatore e li dai all'altro
			imprevisto1(primo,secondo);
		}else{
			//manda indietro il giocatore
			imprevisto2(primo);
		}		
	}
	
	public void imprevisto0(Giocatore primo) {
		int puntiTolti = 50*((int)Math.random()*4+1);
		primo.togliPunti(puntiTolti);
	}
	
	public void imprevisto1(Giocatore primo,Giocatore secondo) {
		int puntiTrasferiti = 50*((int)Math.random()*4+1);
		primo.togliPunti(puntiTrasferiti);
		secondo.addPunti(puntiTrasferiti);
	}
	
	public void imprevisto2(Giocatore primo) {
		int casellePerse = ((int)Math.random()*10+1);
		primo.togliPos(casellePerse);
	}
	
	
}
