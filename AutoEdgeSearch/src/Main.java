import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

public class Main {

    static char randomLetter = generateRandomLetter();
    static int numberOfWords = 15;


    public static void main(String[] args) throws IOException {
        List<String> englishWords = fetchEnglishWords();
        String[] randomWords = generateRandomWords(englishWords, numberOfWords);
        System.out.println("Generating " + numberOfWords + " Words with the starting letter " + randomLetter + " and opening tabs");
        for (String word : randomWords) {
            String URL = "https://www.bing.com/search?q=" + spaceReplacer(word);
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(URL));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
            System.out.println(word);
        }



        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe");


                    Thread.sleep(5000);

                    List<ProcessHandle> edgeProcesses = ProcessHandle.allProcesses()
                            .filter(ph -> ph.info().command().map(cmd -> cmd.contains("msedge.exe")).orElse(false))
                            .toList();

                    for (ProcessHandle edgeProcess : edgeProcesses) {
                        edgeProcess.destroy();
                    }

                    System.out.println("Microsoft Edge wurde beendet.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 5000);

    }



    public static List<String> fetchEnglishWords() {
        List<String> words = new ArrayList<>();
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

    public static String spaceReplacer(String string) {
        if (string.contains(" ")) {
            return string.replace(" ", "+");
        } else {
            return string;
        }
    }
}