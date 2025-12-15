package com.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;

public class IncidentAnalysis {

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder()
                .appName("Incident Analysis")
                .master("local[*]")
                .getOrCreate();

        Dataset<Row> incidents = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("src/main/data/incidents.csv");

        incidents.printSchema();
        incidents.show(5);

        Dataset<Row> incidentsByService = incidents.groupBy("service")
                .count()
                .orderBy(functions.desc("count"));

        System.out.println("Number of incidents per service:");
        incidentsByService.show();

        Dataset<Row> incidentsWithYear = incidents.withColumn("year",
                functions.year(functions.to_date(functions.col("date"), "yyyy-MM-dd")));

        Dataset<Row> incidentsByYear = incidentsWithYear.groupBy("year")
                .count()
                .orderBy(functions.desc("count"));

        System.out.println("Years with the most incidents (top 2):");
        incidentsByYear.limit(2).show();

        spark.stop();
    }
}