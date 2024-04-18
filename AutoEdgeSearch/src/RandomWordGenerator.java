import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWordGenerator {
    public static void main(String[] args) {
        List<String> englishWords = fetchEnglishWords();
        int numberOfWords = 10;
        String[] randomWords = generateRandomWords(englishWords, numberOfWords);
        for (String word : randomWords) {
            System.out.println(word);
        }
    }

    public static List<String> fetchEnglishWords() {
        List<String> words = new ArrayList<>();
        char randomLetter = generateRandomLetter();
        System.out.println("Random letter: " + randomLetter);
        try {
            Document doc = Jsoup.connect("https://www.merriam-webster.com/browse/dictionary/" + randomLetter).get();
            Elements wordElements = doc.select(".entries ul li a");
            for (var element : wordElements) {
                words.add(element.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public static String[] generateRandomWords(List<String> words, int numberOfWords) {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();

        for (int i = 0; i < numberOfWords; i++) {
            randomStrings[i] = words.get(random.nextInt(words.size()));
        }
        return randomStrings;
    }


    public static char generateRandomLetter() {
        Random random = new Random();
        return (char) ('a' + random.nextInt(23));
    }
}

