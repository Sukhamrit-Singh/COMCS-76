package sample;

//  Sukhamrit Singh
//  Permutations
/*
This a recursive method to print all permutations of
a string. Then write a program to test the method,
by once again, prompting the user to enter a string
of characters.  Then there is a method calling a
helper method to find all the combinations of
displaying that string
 */

//  Imports the necessary libraries
import java.util.Scanner;

public class Permutations {

    //  Main method to display the results and get user input
    public static void main(String[] args) {

        //  While loop to keep getting user input
        while (true) {

            //  Creating a new scanner object
            Scanner input = new Scanner(System.in);

            // Displays a prompt to the user to enter the text
            System.out.print("Enter a string, or 0 to exit: ");

            //  Initializing the scanner object
            //  Trims leading and trailing white spaces
            String text = input.nextLine().trim();

            //  If the user enters 0, break loop
            if (text.equals("0"))
                break;

            //  Calling the permutation method for user input
            displayPermutation(text);

            //  Displaying an empty line
            System.out.println("");
        }
    }

    //  Display Permutation method that calls the helper method
    public static void displayPermutation(String s) {

        // Calling the recursive helper method to display the permutations
        displayPermutation("", s);
    }

    //  Recursive helper method for displaying the permutations
    public static void displayPermutation(String s1, String s2) {

        //  Base case to check when s2 has no more characters
        if (s2.isEmpty())
            //  Prints the output s1
            System.out.println(s1);

        // Recursive call to print all permutations
        else {

            // For loop to print combinations for all characters in string
            for(int i = 0; i < s2.length(); ++i) {

                // Creating a new string to concat the characters from s2
                String s3 = s1.concat("" + s2.charAt(i));

                //  Creating a string builder to move characters around
                StringBuilder sb = new StringBuilder(s2);

                //  Deleting the old characters from the input
                String s4 = sb.deleteCharAt(i).toString();

                //  Calling the display permutations method for
                //  these strings
                displayPermutation(s3, s4);

            }
        }
    }
}
