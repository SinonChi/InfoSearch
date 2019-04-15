package inverted_index;





import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static TFIDF.Util.parseForLink;
import static TFIDF.Util.readArticle;

public class Inverted_index {

    private static Set<String> dictionary = new HashSet<>();

    public static void createDictinary(String[] args) {

        System.out.println(getDocsCount("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\Inverted_Index_out"));
        Arrays.stream(new File("src/main/resources/dictionary").listFiles()).forEach(File::delete);

        int docsCounter;

        for (docsCounter = 1; docsCounter <= getDocsCount("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out"); docsCounter ++) {
            String article = readArticle("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out\\" + docsCounter + ".txt");
            String[] words = article.split(" ");
            dictionary.addAll(Arrays.asList(words));
            saveCurrentDictionary(docsCounter - 1);
            clearCurrentDictionary();
        }

        System.out.println(getDocsCount("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\Inverted_Index_out"));


    }



    public static void parse(String vector) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vector.length(); i++) {
            if (vector.charAt(i) == '1') {
                sb.append("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out\\").append(i + 1).append(".txt").append("\n");
            }
        }
        System.out.println(sb.toString());
    }

    public static String simpleFindArticle(String query) {
        StringBuilder sb = new StringBuilder();
        File f = new File("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\Inverted_Index_out\\" + query + ".txt");
        if(f.exists() && !f.isDirectory()) {
            String doc = readArticle(f.getPath());
            for (int i = 0; i < doc.length(); i++) {
                if (doc.charAt(i) == '1') {
                    sb.append(i+1).append("\n");
                }
            }
            return sb.toString();
        }
        return "К сожалению, ничего не удалось найти по запросу " + query;
    }

    public static void findArticles(String query) {
        String[] words = query.split(" ");
        List<String> mathesSet = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            mathesSet.add(findArticlesByWord(words[i]));
        }

        for (int i = 0; i < mathesSet.size(); i++) {
            System.out.println("vector for word " + i + ": " + mathesSet.get(i));
        }


        if (!mathesSet.isEmpty()) {
            String singleResult = "";

            for (int i = 0; i < getDocsCount("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out"); i++) {
                char firstChar = '1';
                int matchCount = 0;
                for (String s : mathesSet) {
                    if (s.charAt(i) == firstChar) {
                        matchCount++;
                    } else {
                        singleResult += '0';
                        break;
                    }
                }

                if (matchCount == mathesSet.size()) {
                    singleResult += '1';
                }
            }

            System.out.println("     total result: " + singleResult);
            parseForLink(singleResult);
        } else {
            System.out.println("К сожалению, ничего не удалось найти по запросу " + query);
        }
    }

    private static String findArticlesByWord(String word) {
        Set<String> results = new HashSet<>();
        File f = new File("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\Inverted_Index_out\\" + word + ".txt");
        if(f.exists() && !f.isDirectory()) {
            return readArticle(f.getPath());
        }
        return null;
    }

    private static void saveCurrentDictionary(int docsCounter) {
        File f = null;
        for (String s : dictionary) {
            f = new File("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\Inverted_Index_out\\" + s + ".txt");
            checkExisting(f.getPath());
            writeInIndexPos(f.getPath(), docsCounter);
        }
    }

    private static void clearCurrentDictionary() {
        dictionary.clear();
    }

    private static void showDictionary() {
        for (String word : dictionary) {
            System.out.println(word);
        }
    }

    private static long getDocsCount(String path) {
        try (Stream<Path> files = Files.list(Paths.get(path))) {
            return files.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static void checkExisting(String path) {
        File f = new File(path);
        if(f.exists() && !f.isDirectory()) {

        } else {
            try {
                f.createNewFile();
                StringBuilder stringBuilder = new StringBuilder((int) getDocsCount("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tokenizer_out"));
                for (int i = 0; i < stringBuilder.capacity(); i++) {
                    stringBuilder.append('0');
                }
                try (FileWriter fileWriter = new FileWriter(new File(path))) {
                    fileWriter.write(stringBuilder.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    try (BufferedReader r = Files.newBufferedReader(path, encoding)) {
        r.lines().forEach(System.out::println);
    }
     */

    private static void writeInIndexPos(String path, int  i) {
        String line = "0";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(new File(path)));
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder(line);
        stringBuilder.setCharAt(i, '1');

        try (FileWriter fileWriter = new FileWriter(new File(path))) {
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
