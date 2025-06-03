import java.io.FileWriter;
import java.io.IOException;

public class FichierCsvVide {
    public static void main(String[] args) {
        String nomFichier = "donnees.csv";

        try (FileWriter writer = new FileWriter(nomFichier)) {
            // Rien à écrire ici, fichier vide
            System.out.println("✅ Fichier CSV vide créé : " + nomFichier);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

