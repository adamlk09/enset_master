public class CompteBancaire {
    protected String numero;
    protected double solde;
    protected String titulaire;

    public CompteBancaire(String numero, String titulaire, double soldeInitial) {
        this.numero = numero;
        this.titulaire = titulaire;
        this.solde = soldeInitial;
    }

    public void deposer(double montant) {
        if (montant > 0) {
            solde += montant;
        }
    }

    public void retirer(double montant) throws FondsInsuffisantsException {
        if (montant > solde) {
            throw new FondsInsuffisantsException("Fonds insuffisants pour le retrait.");
        }
        solde -= montant;
    }

    public void afficherSolde() {
        System.out.println("Compte: " + numero + " | Titulaire: " + titulaire + " | Solde: " + solde + " €");
    }

    public String getNumero() {
        return numero;
    }

    public void transferer(CompteBancaire cible, double montant)
            throws FondsInsuffisantsException, CompteInexistantException {
        if (cible == null) {
            throw new CompteInexistantException("Le compte cible est inexistant.");
        }
        this.retirer(montant);
        cible.deposer(montant);
    }
}