import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<CompteBancaire> comptes = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        //creation de comptes
        CompteBancaire c1 = new CompteCourant("001", "Alice", 500, 200);
        CompteBancaire c2 = new CompteEpargne("002", "Bob", 1000, 0.05);

        comptes.add(c1);
        comptes.add(c2);

        //fffichage les comptes
        for (CompteBancaire c : comptes) {
            c.afficherSolde();
        }

        //les operations
        try {
            c1.retirer(600); //autorisé avec découvert
            c1.afficherSolde();

            c2.deposer(200);
            ((CompteEpargne)c2).genererInterets();
            c2.afficherSolde();

            c1.transferer(c2, 50);
            c1.afficherSolde();
            c2.afficherSolde();

        } catch (FondsInsuffisantsException | CompteInexistantException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

        //la suppression d’un compte
        comptes.removeIf(compte -> compte.getNumero().equals("001"));
        System.out.println("\nAprès suppression du compte 001:");
        for (CompteBancaire c : comptes) {
            c.afficherSolde();
        }

        sc.close();
    }
}