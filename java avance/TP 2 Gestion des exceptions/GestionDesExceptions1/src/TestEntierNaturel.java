public class TestEntierNaturel {
    public static void main(String[] args) {
        try {
            EntierNaturel entier = new EntierNaturel(2);
            System.out.println("Valeur initiale : " + entier.getVal());

            entier.decrementer();
            System.out.println("Après décrémentation : " + entier.getVal());

            entier.decrementer();
            System.out.println("Encore décrémentation : " + entier.getVal());

            //ceci va lancer une exception car la val deviendrait negative
            entier.decrementer();

        } catch (NombreNegatifException e) {
            System.err.println("Exception attrapée : " + e.getMessage());
            System.err.println("Valeur erronée : " + e.getValeurErronée());
        }

        try {
            //test du constructeur avec une val négative
            EntierNaturel mauvais = new EntierNaturel(-10);
        } catch (NombreNegatifException e) {
            System.err.println("Erreur à la création : " + e.getMessage());
        }

        try {
            EntierNaturel entier = new EntierNaturel(5);
            entier.setVal(-100);  //test d setter
        } catch (NombreNegatifException e) {
            System.err.println("Erreur lors du setVal : " + e.getMessage());
        }
    }
}
