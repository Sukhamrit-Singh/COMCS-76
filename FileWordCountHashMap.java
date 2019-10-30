// Sukhamrit Singh
//  FileWordCountHashMap
/*
This program reads the text from a text file. The text file is passed
 as a command-line argument. Words are delimited by white space
 characters, punctuation marks (, ; . : ?), quotation marks (' "), and
 parentheses. Count the words in a case-sensitive fashion (e.g., consider
 Good and good to be the same word). The words must start with a letter.
 Display the output of words in alphabetical order, with each word
 preceded by the number of times it occurs.
 */


import java.util.*;
import java.io.*;

public class FileWordCountHashMap {

    public static void main(String[] args) throws Exception {

        // Check if user specified file name in the command line.
        // only one argument should be specified
        if (args.length != 1) {
            System.out.println("");
            System.out.println("Usage: java -classpath <classpath> " +
                    "FileWordCountHashMap <textfile>");
            System.out.println("");
            System.exit(1);
        }

        // first argument is the file name
        String fileName = args[0];

        // Check if the specified text file exists
        File textFile = new File(fileName);
        if (!textFile.exists()) {
            System.out.println("The textfile " + fileName +
                    " does not exists.");
            System.exit(1);
        }

        // Create a TreeMap to hold words as key and  value (word count)
        SortedMap<String, Integer> wordsMap = new TreeMap<>();

        try (
                // read the file contents
              Scanner input = new Scanner(textFile);
        ) {

            // read file one line at a time
            while ( input.hasNext() ) {

                // read next line
                String line = input.nextLine();

                // split the line into words using space, comma,
                // semi-colon etc as delimeters
                String[] lineWords = line.split("[ @!~{}\\[\\]$#^&*\n\t\r.,;?'\")(]");

                // calling method to add words to the hash map
                storeWord(wordsMap, lineWords);
            }
        }


        //  Print the words in alphabetical order
        for( Map.Entry<String,Integer> entry : wordsMap.entrySet() ) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(value + "\t" + key);
        }
    }

    //  Method to find and store the words with their occurance count
    private static void storeWord(Map<String, Integer> wordsMap,
                                  String[] words) {

        // for every word in the words array
        for (int i = 0; i < words.length; i++) {

            //  Making sure that the words only contain letters,
            //  no special characters
            if (words[i].trim().length() > 0 &&
                    words[i].trim().matches("[A-Z|a-z]+")) {

                // covert to lowercase for case in-sensitive comparison
                String entry = words[i].toLowerCase();

                // ignore word with 0 length
                if ( entry.length() == 0 ) {
                    continue; // go to the next entry in the array
                }

                // check if the word starts with a letter
                boolean startsWithLetter =
                        Character.isLetter(entry.charAt(0));

                // ignore word that does not start with a letter
                if ( !startsWithLetter ) {
                    continue; // go to next entry in the array
                }

                // check if the entry alreay exist in hash map
                boolean entryExists = wordsMap.containsKey(entry);

                if ( entryExists) {

                    // if word entry exist, then get the current count
                    int count = wordsMap.get(entry);

                    // increment the count
                    count++;

                    // update the word entry count in the hashmap
                    wordsMap.put(entry, count);

                } else {
                    // the word does not exist in the hash map
                    // set the count to 1
                    int count = 1;

                    // add the word entry to hash map
                    wordsMap.put(entry, count);
                }
            }

        }
    }
}

