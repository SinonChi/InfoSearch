package vector_search;



import TFIDF.TF_IDF;
import stemmer.Stemmer;
import TFIDF.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        Map<String, Double> rankedPages = new HashMap<>();
        Scanner sc = new Scanner(System.in);


        String query = "1";
        String stemmedQuery = Stemmer.stemQuery(query);
        double tfValue = TF_IDF.getTfValue(stemmedQuery);


        String idfVector = TF_IDF.getIdfVector(stemmedQuery);
        System.out.println(" idf = " +idfVector);
        String tf_idf_vector = TF_IDF.getTfIdfVector(stemmedQuery, tfValue);


        for (int i = 0; i < 100; i++) {
            String vectorOfTheFirstDoc = VectorSearch.getTfIdfDocVector(i);


            double vectorLength = VectorSearch.getVectorLength(vectorOfTheFirstDoc);


            double secondVectorLength = VectorSearch.getVectorLength(tf_idf_vector);


            double vectorLengthMulti = vectorLength * secondVectorLength;

            double scalarProduct = VectorSearch.getScalarProduct(vectorOfTheFirstDoc, tf_idf_vector);


            double cosAngle = VectorSearch.getCosign(scalarProduct, vectorLengthMulti);


            rankedPages.put(Util.getLinkForThatDocNumber(String.valueOf(i)), cosAngle);
        }


        rankedPages.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);

    }
}
