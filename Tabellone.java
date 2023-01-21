package MainPackage;

class Tabellone {
    private Casella primo;      // riferimento al primo nodo della lista
    private Casella ultimo;     // riferimento all'ultimo nodo della lista
    private int lunghezza;   // numero di elementi inseriti nella lista

    public Tabellone() {
        primo = null;
        ultimo = null;
        lunghezza = 0;
    }
    
    public Tabellone(int[] array) {
        this();
        for(int i=0; i<array.length; i++) {
            inserisciUltimo(array[i]);
        }
    }
    
    public Tabellone(Tabellone nuovoTabellone) {
        this();
        Casella nodo = nuovoTabellone.primo;
        for(int i=0; i<nuovoTabellone.lunghezza; i++) {
            this.inserisciUltimo(nodo.getNumero());
            nodo = nodo.getSuccessivo();
        }
    }

    // verifica se la lista e' vuota
    public boolean vuota(){
        return lunghezza == 0;
    }

    public int lunghezza(){
        return lunghezza;
    }

    public int getPrimoNumero(){
        return primo.getNumero();
    }

    public int getUltimoNumero(){
        return ultimo.getNumero();
    }

    // Inserisce un nuovo elemento nella lista al primo posto
    public void inserisciPrimo(int numero){
        primo = new Casella(numero,primo,null);
        if (vuota())
            ultimo = primo;
        lunghezza++;
    }

    // Inserisce un nuovo elemento nella lista in ultima posizione
    public void inserisciUltimo(int numero){
        if (vuota())
            inserisciPrimo(numero);
        else {
        	Casella prec=ultimo;
            ultimo.setSuccessivo(new Casella(numero,null,prec));
            ultimo = ultimo.getSuccessivo();
            lunghezza++;
        }
    }
    
    public void inserisci(int elemento, int posizione) {
        Casella nuovoNodo = new Casella(elemento);
        Casella precedente = nodoIn(posizione-1);
        Casella seguente = precedente.getSuccessivo();
        precedente.setSuccessivo(nuovoNodo);
        nuovoNodo.setSuccessivo(seguente);
        lunghezza++;
    }
    
    public Casella nodoIn(int posizione) {
        Casella nodo = primo;
        for(int salto=0; salto<posizione; salto++) {
            nodo = nodo.getSuccessivo();
        }
        return nodo;
    }
    
    public int elementoIn(int posizione) {
        return nodoIn(posizione).getNumero();
    }
    
    public String toString() {
        String s = "";
        Casella nodo = primo;
        for(int salto=1; salto<=lunghezza; salto++) {
            s += nodo.getNumero() + " ";
            nodo = nodo.getSuccessivo();
        }
        return s;
    }
    
    public void cancella(int posizione) {
        // Cancella primo nodo
        if(posizione==0) {
            primo = primo.getSuccessivo();
        }
        // Cancella ultimo nodo
        else if(posizione==lunghezza-1) {
            Casella penultimo = nodoIn(lunghezza-2);
            penultimo = null;
            ultimo = penultimo;
        }
        // Cancella nodo in mezzo
        else {
            Casella precedente = nodoIn(posizione-1);
            Casella daEliminare = precedente.getSuccessivo();
            precedente.setSuccessivo(daEliminare.getSuccessivo());
        }
        lunghezza--;
    }
    
    
    

} 