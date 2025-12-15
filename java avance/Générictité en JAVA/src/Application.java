import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MetierProduitImpl metier = new MetierProduitImpl();
        int choix;

        do {
            System.out.println("\n========= MENU =========");
            System.out.println("1. Afficher la liste des produits");
            System.out.println("2. Rechercher un produit par id");
            System.out.println("3. Ajouter un nouveau produit");
            System.out.println("4. Supprimer un produit par id");
            System.out.println("5. Quitter");
            System.out.print("Votre choix : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // pour vider lbuffer

            switch (choix) {
                case 1:
                    for (Produit p : metier.getAll()) {
                        System.out.println(p);
                    }
                    break;
                case 2:
                    System.out.print("Entrez l'id du produit : ");
                    long id = scanner.nextLong();
                    Produit p = metier.findById(id);
                    if (p != null) System.out.println(p);
                    else System.out.println("Produit non trouvé.");
                    break;
                case 3:
                    System.out.print("ID : ");
                    long newId = scanner.nextLong(); scanner.nextLine();
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Marque : ");
                    String marque = scanner.nextLine();
                    System.out.print("Prix : ");
                    double prix = scanner.nextDouble(); scanner.nextLine();
                    System.out.print("Description : ");
                    String desc = scanner.nextLine();
                    System.out.print("Stock : ");
                    int stock = scanner.nextInt(); scanner.nextLine();

                    Produit nouveauProduit = new Produit(newId, nom, marque, prix, desc, stock);
                    metier.add(nouveauProduit);
                    System.out.println("Produit ajouté.");
                    break;
                case 4:
                    System.out.print("Entrez l'id du produit à supprimer : ");
                    long delId = scanner.nextLong();
                    metier.delete(delId);
                    System.out.println("Produit supprimé s'il existait.");
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide.");
            }

        } while (choix != 5);

        scanner.close();
    }
}