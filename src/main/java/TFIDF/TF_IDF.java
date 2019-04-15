package TFIDF;



import java.io.File;
import java.text.DecimalFormat;

import static TFIDF.Util.readArticle;

public class TF_IDF {
    public static String getTf(String path,String term) {
        double matchCount = 0;
        double wordsCount = 0;
        term = term.substring(0,term.length() - 4);
        String article = readArticle(path);
        String[] words = article.split(" ");
        for (String word : words) {
            if (word.equals("")) {
                continue;
            }
            else {
                if (word.equals(term)) {
                    matchCount++;
                }
                wordsCount++;
            }
        }


        return new DecimalFormat("#0.00000").format(matchCount/wordsCount);
    }

    public static double getIdf(String vector) {
        int matchCounter = 0;
        for (int i = 0; i < vector.length(); i++) {
            if (vector.charAt(i) == '1') {
                matchCounter++;
            }
        }


        return Math.log10(vector.length()/matchCounter);
    }


    public static String getTfVector(String vector, String term) {
        String tf_vector = "";
        for (int i = 0; i < vector.length(); i++) {
            if (vector.charAt(i) == '1') {
                String tf = getTf("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out\\" + (i+1)  + ".txt", term);
                tf_vector+=tf + " ";
            } else {
                tf_vector+="0 ";
            }
        }
        return tf_vector;
    }

    public static String getTfIdfVector(double idf, String tf_vector) {
        String tf_idf_vector = "";
        String [] values = tf_vector.split(" ");
        for (String value : values) {
            if (value.equals("0")) {
                tf_idf_vector += "0 ";
                continue;
            } else {
                double tfidf = Double.valueOf(value.replace(",",".")) * idf;
                tf_idf_vector += tfidf + " ";
            }
        }
        return tf_idf_vector;
    }

    public static String getTfIdfVector(String tf_vector, String idf_vector) {
        StringBuilder vector = new StringBuilder();
        String[] tfValues = tf_vector.split(" ");
        String[] tf_idfValues = idf_vector.split(" ");
        for (int i = 0; i < tfValues.length; i++) {
            vector.append(Double.valueOf(tfValues[i]) * Double.valueOf(tf_idfValues[i])).append(" ");
        }
        return vector.toString();
    }

    public static String getTfIdfVector(String stemmedQuery, double tfValue) {
        String[] query_words = stemmedQuery.split(" ");
        StringBuilder vector = new StringBuilder();
        File dir = new File("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tfidf_out");
        File[] arrFiles = dir.listFiles();
        for (File arrFile : arrFiles) {
            double tfIdfValue = 0;
            for (int i = 0; i < query_words.length; i++) {
                if (arrFile.getName().equals(query_words[i] + ".txt")) {
                    tfIdfValue = getIdfOfTerm(query_words[i]) * tfValue;
                    break;
                }
            }
            vector.append(tfIdfValue).append(" ");
        }
        return vector.toString();
    }

    public static String getTfVector(String stemmedQuery) {
        StringBuilder vector = new StringBuilder();
        String[] words = stemmedQuery.split(" ");
        for (String word : words) {
            vector.append(1.0 / words.length).append(" ");
        }
        return vector.toString();
    }

    public static String getIdfVector(String stemmedQuery) {
        StringBuilder vector = new StringBuilder();
        String[] words = stemmedQuery.split(" ");
        for (String word : words) {
            vector.append(getIdfOfTerm(word)).append(" ");
        }
        return vector.toString();
    }

    private static double getIdfOfTerm(String word) {
        String vector = readArticle("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out\\" + word + ".txt");
        if (vector != null) {
            return getIdf(vector);
        } else {
            try {
                throw new Exception("Not such word in the dictionary");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return -1;
    }

    public static double getTfValue(String stemmedQuery) {
        String[] words = stemmedQuery.split(" ");
         return 1.0 / words.length;
    }
}