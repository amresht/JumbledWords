
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


 
public class Jumble {

    private static final int MAX = 250000;							// define a max size of  hash
    private static final String NAME = "dictionary.txt";			// file name of the dictionary file
    private static final Map<String, Set<String>> map =
        new HashMap<String, Set<String>>(MAX);
	// Create a sorted hash map of the dictionary
    static {
        try {
			// open the dictionary file
            File file = new File(NAME);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)));
            String s;
			// for each word in the dictionary
            while ((s = in.readLine()) != null) {
				// get the sorted string of the current word
                String sorted = sort(s);
				// lookup the orted string in hashmap if exsits already
                Set<String> words = map.get(sorted);
                if (words == null) {
					// if not found create a new reference with this sorted word
                    words = new TreeSet<String>();
                    words.add(s);
                    map.put(sorted, words);
                } else {
					// if found add this word to existing hash
                    words.add(s);
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    

    public static void main(String... args) {
        if (args.length < 1) {
			// If there no arguments supplied then print help on usage

            ShowUsage();
        } else {
            for (String word : args) {
				// for each argument word supplied
                System.out.print(word + ": ");
				// Lookup the input word in the hash map  that we generated
                Set<String> words = map.get(sort(word));
                if (words != null) {
                    for (String s : words) {
                        System.out.print(s + " ");
                    }
                    System.out.println();
                } else {
                    System.out.println("no match.");
                }
            }
        }
    }
	
	
    // Method to sort the letters of a word alphabetically
    private static String sort(String s) {
        byte[] ba = s.toLowerCase().getBytes();
        Arrays.sort(ba);
        return new String(ba);
    }
		
	// Help to show the usage of the file
    private static void ShowUsage() {
        System.out.println(
            "Usage: java Jumble <word_to_unjumble> ");
    }


}
