package spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.SaveMode;

import java.io.File;

public class DataOperator {

    // SparkSession initialisé une seule fois
    private SparkSession spark;

    public DataOperator() {
        this.spark = SparkSession.builder()
                .appName("Creating DataFrame")
                .master("local[*]")
                .getOrCreate();
    }

    public Dataset<Row> read(File file) {
        // Lire le CSV avec header
        Dataset<Row> df = spark.read()
                .option("header", "true")
                .csv(file.getAbsolutePath());
        return df;
    }

    public Dataset<Row> transform(Object object) {
        // Il faut caster object en Dataset<Row>
        Dataset<Row> df = (Dataset<Row>) object;
        Dataset<Row> objectTransforme = df.withColumn("Pays", org.apache.spark.sql.functions.lit("France"));
        return objectTransforme; // Retourner l'objet transformé
    }

    public void write(Object objectTransforme) {
        Dataset<Row> df = (Dataset<Row>) objectTransforme;
        // Écrire dans un dossier CSV (Spark crée dans un dossier) nommé "exemple_transforme.csv"
        String outputPath = "exemple_transforme.csv";
        df.write()
                .mode(SaveMode.Overwrite)
                .option("header", "true")
                .csv(outputPath);
        System.out.println("Fichier écrit dans : " + outputPath);
    }

    public static void main(String[] args) {
        // On crée l'instance
        DataOperator operator = new DataOperator();

        // Chemin vers un fichier CSV valide sur votre machine
        File fileInput = new File("example.csv");

        Dataset<Row> df = operator.read(fileInput); // Lire le fichier
        df.show();

        Dataset<Row> dfTransforme = operator.transform(df); // Transformer le DataFrame
        dfTransforme.show();

        operator.write(dfTransforme); // Écrire le DataFrame transformé
    }
}
