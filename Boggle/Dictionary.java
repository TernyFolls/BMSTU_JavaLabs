import java.io.*;
import java.util.*;

public class Dictionary {

    public Dictionary() {

    }


    public static TreeSet<String> getWords(String file) throws FileNotFoundException {
        
        Scanner input =  new Scanner(new File(file));
       
        TreeSet<String> words = new TreeSet<String>();
        while(input.hasNext()) {
            words.add(input.next());
        }
        input.close();
        return words;
    }
}
