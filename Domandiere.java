package MainPackage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Domandiere {
	
	/*
	 * funzione che legge e mette in una stringa line tutto il contenuto di un file di testo
	 * @return Line String
	 */
	public static void PrintFile() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("F:\\domandiere.txt"));
        String line = reader.readLine();
        while(line!=null) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
	
	/*
	 * funzione che torna una stringa dipendentemente dal numero della domanda scelta
	 * @param num intero: numero della domanda
	 * @return Line String
	 */
	public static String getDomanda(int num)  throws FileNotFoundException, IOException {
		BufferedReader readerDomanda = new BufferedReader(new FileReader("F:\\domandiere.txt"));
		String line = readerDomanda.readLine();
		int cnt = 0;
        while(cnt < num*7) {
            line = readerDomanda.readLine();
            cnt++;
        }
        return line;
	}
	
	/*
	 * funzione che ritorna la stringa della risposta A
	 * @param num intero: numero della domanda
	 * @return Line String
	 */
	public static String getRisposta1(int num)  throws FileNotFoundException, IOException {
		BufferedReader readerRisposta = new BufferedReader(new FileReader("F:\\domandiere.txt"));
		String line = readerRisposta.readLine();
		int cnt = 0;
        while(cnt < num*7+1) {
            line = readerRisposta.readLine();
            cnt++;
        }
        return line;
	}	

	/*
	 * funzione che ritorna la stringa della risposta B
	 * @param num intero: numero della domanda
	 * @return Line String
	 */	
	public static String getRisposta2(int num)  throws FileNotFoundException, IOException {
		BufferedReader readerRisposta = new BufferedReader(new FileReader("F:\\domandiere.txt"));
		String line = readerRisposta.readLine();
		int cnt = 0;
        while(cnt < num*7+2) {
            line = readerRisposta.readLine();
            cnt++;
        }
        return line;
	}

	/*
	 * funzione che ritorna la stringa della risposta C
	 * @param num intero: numero della domanda
	 * @return Line String
	 */	
	public static String getRisposta3(int num)  throws FileNotFoundException, IOException {
		BufferedReader readerRisposta = new BufferedReader(new FileReader("F:\\domandiere.txt"));
		String line = readerRisposta.readLine();
		int cnt = 0;
        while(cnt < num*7+3) {
            line = readerRisposta.readLine();
            cnt++;
        }
        return line;
	}
	
	/*
	 * funzione che ritorna la stringa della risposta D
	 * @param num intero: numero della domanda
	 * @return Line String
	 */	
	public static String getRisposta4(int num)  throws FileNotFoundException, IOException {
		BufferedReader readerRisposta = new BufferedReader(new FileReader("F:\\domandiere.txt"));
		String line = readerRisposta.readLine();
		int cnt = 0;
        while(cnt < num*7+4) {
            line = readerRisposta.readLine();
            cnt++;
        }
        return line;
	}
	
	/*
	 * funzione che ritorna la stringa della risposta corretta
	 * @param num intero: numero della domanda
	 * @return Line String
	 */	
	public static String getSoluzione(int num)  throws FileNotFoundException, IOException {
		BufferedReader readerRisposta = new BufferedReader(new FileReader("F:\\domandiere.txt"));
		String line = readerRisposta.readLine();
		int cnt = 0;
        while(cnt < num*7+5) {
            line = readerRisposta.readLine();
            cnt++;
        }
        return line;
	}
	
	/*
	 * funzione che ritorna il punteggio che la domanda darà se la risposta del giocatore sarà corretta
	 * @param num intero: numero della domanda
	 * @return number int: punteggio
	 */	
	public static int getPunteggio(int num)  throws FileNotFoundException, IOException {
		
		BufferedReader readerPunti = new BufferedReader(new FileReader("F:\\domandiere.txt"));
		String line = readerPunti.readLine();
		int cnt = 0;
        while(cnt < num*7+6) {
            line = readerPunti.readLine();
            cnt++;
        }
        
        int number = 0;
        	try{
        		number = Integer.parseInt(line);
        	}
        	catch (NumberFormatException ex){
        		ex.printStackTrace();
        	}
        
        return number;
	}
}
