
package inverted_index;



import stemmer.Stemmer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите слова, которые хотели бы найти");
        String query = sc.nextLine();
        Inverted_index.findArticles(Stemmer.stemQuery(query));
//        System.out.println(Inverted_index.simpleFindArticle(query));
    }
}
