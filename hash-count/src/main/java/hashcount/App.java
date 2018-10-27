package hashcount;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Hello world!
 */
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

        //byte[] bytes = new byte[1024];

        try {
            InputStream inputStream = new FileInputStream(file);
            return IOUtils.toString(inputStream, "UTF-16");
//            inputStream.read(bytes);
//            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {

        String string1 = readResource("string1.txt");
        String string2 = readResource("string2.txt");
        List<String> words1 = splitToWords(string1);
        List<String> words2 = splitToWords(string2);

    /* This is how to declare HashMap */
        HashMap<String, Integer> hmap = new HashMap<>();
        for (String item:words1) {
            //String item=words1[i]
            Integer count = hmap.getOrDefault(item, 0);
            hmap.put(item, count + 1);
        }
    }

}