package MainPackage;

class Casella {
    private int numero;
    private Casella successivo;
    private Casella precedente;
    private String titoloCasella;
    private boolean isImprevisto;

    public Casella(){
    	
        this(0,null,null);
    }

    public Casella(int numero, Casella successivo, Casella precedente){
        this.numero = numero;
        this.successivo = successivo;
        this.precedente=precedente;
    }
    
    public Casella(int numero){
        this.numero = numero;
    }

    public int getNumero(){
        return this.numero;
    }

    public Casella getSuccessivo(){
        return this.successivo;
    }

    public void setNumero(int numero){
        this.numero = numero;
    }

    public void setSuccessivo(Casella successivo){
        this.successivo = successivo;
    }

	public Casella getPrecedente() {
		return precedente;
	}

	public void setPrecedente(Casella precedente) {
		this.precedente = precedente;
	}
    
    
}