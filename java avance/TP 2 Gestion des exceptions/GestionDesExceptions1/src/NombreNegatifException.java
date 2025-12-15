public class NombreNegatifException extends Exception {
    private int valeurErronée;

    public NombreNegatifException(int valeur) {
        super("Valeur négative interdite : " + valeur);
        this.valeurErronée = valeur;
    }

    public int getValeurErronée() {
        return valeurErronée;
    }
}