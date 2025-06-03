import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.SaveMode;

public class CsvProcessor {

    private SparkSession spark;

    public CsvProcessor() {
        // Initialiser SparkSession
        this.spark = SparkSession.builder()
                .appName("CsvProcessor")
                .master("local") // Utiliser "local" pour le développement
                .getOrCreate();
    }

    /**
     * Méthode pour lire un fichier CSV et afficher son contenu.
     */
    public void lireCsv(String nomFichier) {
        Dataset<Row> df = spark.read().option("header", "true").csv(nomFichier);
        df.show(); // Affiche le contenu du DataFrame
        System.out.println("Fichier CSV chargé : " + nomFichier);
    }

    /**
     * Méthode pour transformer un fichier CSV en ajoutant une colonne "Pays" avec la valeur "France".
     */
    public void transformerCsv(String fichierEntree, String fichierSortie) {
        Dataset<Row> df = spark.read().option("header", "true").csv(fichierEntree);
        Dataset<Row> dfTransforme = df.withColumn("Pays", org.apache.spark.sql.functions.lit("France"));
        dfTransforme.write().mode(SaveMode.Overwrite).option("header", "true").csv(fichierSortie);
        System.out.println("Transformation terminée, fichier créé : " + fichierSortie);
    }

    /**
     * Méthode pour charger un fichier CSV et afficher son contenu.
     */
    public void chargerCsv(String nomFichier) {
        lireCsv(nomFichier); // Réutiliser la méthode lireCsv pour afficher le contenu
    }

    // Exemple d'utilisation dans main
    public static void main(String[] args) {
        CsvProcessor processor = new CsvProcessor();

        String fichierInitial = "exemple.csv";
        String fichierTransforme = "exemple_transform.csv";

        // 1. Lire le fichier CSV initial
        processor.lireCsv(fichierInitial);

        // 2. Transformer le fichier CSV en ajoutant la colonne "Pays"
        processor.transformerCsv(String.valueOf(fichierInitial), fichierTransforme);

        // 3. Charger et afficher le fichier CSV transformé
        processor.chargerCsv(fichierTransforme);
    }
}
