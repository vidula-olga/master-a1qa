package twostrings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static List<String> splitToWords(String value) {
        String[] words = value.split("[ ,\\.!\\(\\)]");
        ArrayList<String> result = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty())
                result.add(word);
        }
        return result;
    }

    private static String readResource(String fileName) {

        //Get file from resources folder
        ClassLoader classLoader = App.class.getClassLoader();
        File file = new File(classLoader.getSystemResource(fileName).getFile());

        byte[] bytes = new byte[1024];

        try {
            InputStream inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new String(bytes);

    }

    public static void main(String[] args) {
        String string1 = readResource("string1.txt");
        String string2 = readResource("string2.txt");
        List<String> words1 = splitToWords(string1);
        List<String> words2 = splitToWords(string2);

        for (String word : words1) {
            if (!words2.contains(word)) {
                System.out.println(word);
            }
        }


    }
}
