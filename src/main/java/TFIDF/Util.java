package TFIDF;



import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

public class Util {

    public static String readArticle(String filePath) {
        String content = null;
        try {
            content = Files.lines(Paths.get(filePath)).reduce("", String::concat);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getDocsCount(String path) {
        try (Stream<Path> files = Files.list(Paths.get(path))) {
            return files.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void writeArticle(String text, String name) {
        try(FileWriter articleWriter = new FileWriter("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\tfidf_out\\"+ name))
        {
            articleWriter.write(text);
            articleWriter.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void parseForLink(String vector) {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\crawler_out\\index.txt"))) {

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < vector.length(); i++) {
                if (vector.charAt(i) == '1') {
                    System.out.println(r.readLine());
                } else {
                    r.readLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseForLink(Map<String, Double> rankedMap) {
        for (Map.Entry<String, Double> stringDoubleEntry : rankedMap.entrySet()) {
            String link = getLinkForThatDocNumber(stringDoubleEntry.getKey());
            System.out.println(link + " " + stringDoubleEntry.getValue());
        }

    }

    public static String getLinkForThatDocNumber(String key) {
        try (BufferedReader r = Files.newBufferedReader(Paths.get("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\crawler_out\\index.txt"))) {
            for (int i = 0; i < Integer.valueOf(key); i++) {
                r.readLine();
            }
            return r.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static String getDocNumberFromLink(String link) {
        String line;
        try (BufferedReader r = Files.newBufferedReader(Paths.get("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\crawler_out\\index.txt"))) {
            while ((line = r.readLine()) != null){
                if (line.contains(link)) {
                    return line.substring(0, line.indexOf("."));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static void arrayToFile(String dirName, int[][] board) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < board.length; i++)//for each row
        {
            for(int j = 0; j < board.length; j++)//for each column
            {
                builder.append(board[i][j]+"");//append to the output string
                if(j < board.length - 1)//if this is not the last row element
                    builder.append(" ");//then add comma (if you don't like commas you can use spaces)
            }
            builder.append("\n");//append new line at the end of the row
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(dirName));
            writer.write(builder.toString());//save the string representation of the board
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] readArrayFromFile(String fileName) {
        int[][] board = new int[100][100];
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line = "";
        int row = 0;
        while(true)
        {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] cols = line.split(" "); //note that if you have used space as separator you have to split on " "
            int col = 0;
            for(String  c : cols)
            {
                board[row][col] = Integer.parseInt(c);
                col++;
            }
            row++;
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return board;
    }

    public static void printList(ArrayList<String> list) {
        for (String s : list) {
            System.out.println(s);
        }
    }
}
