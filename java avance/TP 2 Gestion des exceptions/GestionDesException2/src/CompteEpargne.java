public class CompteEpargne extends CompteBancaire {
    private double tauxInteret;

    public CompteEpargne(String numero, String titulaire, double soldeInitial, double tauxInteret) {
        super(numero, titulaire, soldeInitial);
        this.tauxInteret = tauxInteret;
    }

    public void genererInterets() {
        solde += solde * tauxInteret;
    }
}