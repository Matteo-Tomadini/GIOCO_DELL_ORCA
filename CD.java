public class CD extends  Prodotti {

    protected String genere;

    protected int numBrani;



    public CD(String genere,int numBrani,float prezzo)
    {
        super(prezzo);
        this.genere = genere;
        this.numBrani = numBrani;

    }

    public float getPrezzo() {
        return prezzo;
    }

    @Override
    public String toString() {
        return "CD{" +
                "genere='" + genere + '\'' +
                ", numBrani=" + numBrani +
                ", prezzo=" + prezzo +
                '}';
    }
}
