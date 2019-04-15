package TFIDF;







import java.io.File;

public class Main {



    public static void main(String[] args) {
        long docsAmount = Util.getDocsCount("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\Inverted_Index_out");
        File dir = new File("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\Inverted_Index_out"); //path указывает на директорию
        File[] arrFiles = dir.listFiles();
        for (File file : arrFiles) {
            String vector = Util.readArticle(file.getPath());
            double idf = TF_IDF.getIdf(vector);
            String tf_vector = TF_IDF.getTfVector(vector, file.getName());
            String tf_idf_vector = TF_IDF.getTfIdfVector(idf, tf_vector);
            System.out.println("idf of file " + file.getPath() + " is " + idf);
            System.out.println("tf vector " + tf_vector);
            System.out.println("tf-idf vector - " + tf_idf_vector);
            Util.writeArticle(tf_idf_vector, file.getName());
        }
    }

}
