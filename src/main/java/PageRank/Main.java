package PageRank;


import TFIDF.Util;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    static HashMap<Integer, Double> pageRankMap = new HashMap();
    static int[][] linkTable;

    public static void main(String[] args) {
        double dampingFactor = 0.85;

        int docsCount = (int) Util.getDocsCount(("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\crawler_out")) - 1;
        ArrayList<String> links = new ArrayList();
        linkTable = new int[docsCount][docsCount];


        for (int i = 0; i < docsCount; i++) {
            links.add(Util.getLinkForThatDocNumber(String.valueOf(i)).replaceFirst("\\d*.txt:", ""));
        }


        for (int i = 1; i < docsCount + 1; i++) {
            for (int j = 0; j < links.size(); j++) {
                String article = Util.readArticle("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\crawler_out\\" + i + ".txt");
                if (article.contains(links.get(j))) {
                    System.out.println("article " + i + " contains link of file " + Util.getDocNumberFromLink(links.get(j)));
                    linkTable[i - 1][j] = 1;
                }
            }
        }

        Util.arrayToFile("C:\\Users\\qbbii\\IdeaProjects\\basic-crawler\\crawler_maven\\src\\main\\resources\\page_rank_out\\pr_out.txt", linkTable);

        double firstPageRang = 1.0/docsCount;
        for (int i = 1; i < docsCount + 1; i++) {
            pageRankMap.put(i, firstPageRang);
        }

//        printPageRank();

        int iterCount = 0;
        while (iterCount < 100) {
            for (int i = 1; i < docsCount + 1; i++) {
                double pageRank = (1 - dampingFactor) / docsCount + dampingFactor * getSumOfPageRanksForFile(i - 1);
                pageRankMap.put(i, pageRank);
            }
            iterCount++;
        }

        printPageRank();

    }

    private static double getSumOfPageRanksForFile(int i) {
        double sum = 0;
        for (int j = 0; j < linkTable.length - 1; j ++) {
            if (linkTable[j][i] == 1) {
                sum += pageRankMap.get(j + 1) / getCountOfLinksForThatDoc(j);
            }
        }
        return sum;
    }

    private static Double getCountOfLinksForThatDoc(int j) {
        double count = 0;
        for (int i = 0; i < linkTable.length; i++) {
            if (linkTable[j][i] == 1) {
                count++;
            }
        }
        return count;
    }

    public static void printPageRank() {
        for (Integer name: pageRankMap.keySet()){
            String key =name.toString();
            String value = pageRankMap.get(name).toString();
            System.out.println(key + " " + value);
        }
    }

}
