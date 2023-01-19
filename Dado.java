package MainPackage;

public class Dado
{
    /**questa classe genera un numero a caso su un dado a sei facce (d6) standard
     *@return val intero: numero random (1 a 6) 
     */
    public static int tiraD6(){
        int min = 1;
        int max = 6;
        int val = (int)Math.floor(Math.random()*(max-min+1)+min);
        return val;
    }
    
    /**questa classe genera un numero a caso su un dado a sei facce (d6) standard ma aggiungendo un numero bonus
     *@param intero: bonus aggiunto al dado
     *@return val intero: numero random (1 a 6) + mod
     */
    public static int tiraD6(int mod){
        int min = 1;
        int max = 6;
        int val = (int)Math.floor(Math.random()*(max-min+1)+min) + mod;
        return val;
    }
    
    
    /**questa classe genera un numero a caso su un dado a facce variabili (dx)
     *@param numfacce intero: numero di facce del dado, variabile
     *@return val intero: numero random (1 a numfacce)
     */
    public static int tiraD(int numfacce){
        int min = 1;
        int max = 0 + numfacce;
        int val = (int)Math.floor(Math.random()*(max-min+1)+min);
        return val;
    }
    
    /**questa classe genera un numero a caso su un dado a facce variabili (dx) più un numero bonus (mod)
     *@param numfacce intero: numero di facce del dado, variabile
     *@param mod intero: bonus aggiunto al dado
     *@return val intero: numero random (1 a numfacce) + mod
     */
    public static int tiraD(int numfacce, int mod){
        int min = 1;
        int max = 0 + numfacce;
        int val = (int)Math.floor(Math.random()*(max-min+1)+min) + mod;
        return val;
    }
    
}
