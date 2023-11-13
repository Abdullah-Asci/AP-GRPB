package iteration2;
import java.util.Scanner;

public class selection {
    public static void main(String[] args) {
        // Supposons une liste d'employés ici
        String[] employes = {"Employé 1", "Employé 2", "Employé 3"};

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Sélectionnez un employé (ou tapez 'q' pour quitter) :");

            // Affiche la liste des employés
            for (int i = 0; i < employes.length; i++) {
                System.out.println((i + 1) + ". " + employes[i]);
            }

            // Lire la sélection de l'utilisateur
            String choix = scanner.nextLine();

            if (choix.equals("q")) {
                System.out.println("Au revoir !");
                break;
            }

            try {
                int selection = Integer.parseInt(choix);

                if (selection >= 1 && selection <= employes.length) {
                    String employeSelectionne = employes[selection - 1];

                    // L'utilisateur a sélectionné un employé
                    System.out.println("Vous avez sélectionné : " + employeSelectionne);

                    //modifier ou supprimer
                    System.out.println("Que voulez-vous faire avec cet employé ?");
                    System.out.println("1. Modifier");
                    System.out.println("2. Supprimer");

                    String choixAction = scanner.nextLine();

                    if (choixAction.equals("1")) {
                        // Code pour modifier l'employé
                        System.out.println("Vous avez choisi de modifier l'employé " + employeSelectionne);
                    } else if (choixAction.equals("2")) {
                        // Code pour supprimer l'employé
                        System.out.println("Vous avez choisi de supprimer l'employé " + employeSelectionne);
                    } else {
                        System.out.println("Choix invalide.");
                    }
                } else {
                    System.out.println("Sélection invalide. Veuillez choisir un nombre entre 1 et " + employes.length);
                }
            } catch (NumberFormatException e) {
                System.out.println("Choix invalide. Veuillez choisir un nombre valide.");
            }
        }

        scanner.close();
    }
}