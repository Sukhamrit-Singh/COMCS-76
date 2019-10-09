package sample;

//  Sukhamrit Singh
//  ReverseOrder
/*
This is a program to test your recursive
method by prompting the user to enter a string
and then displays it in reverse order.  There is
method to display the reverse order and another
method which is a helper method getting the highest
character in the string as well
 */


//  Imports the necessary libraries
import java.util.Scanner;

public class ReverseOrder {

    //  Main method to display the results and ask for user input
    public static void main(String[] args) {

        //  Creating a while loop to keep collecting user data
        while (true) {

            //  Creates a new scanner object
            Scanner input  = new Scanner(System.in);

            //  Displaying some text
            System.out.print("Enter a sentence: ");

            //  Initializing the scanner object
            String test = input.nextLine();

            //  Calling the reverse display method for user input
            reverseDisplay(test);

            //  Displaying an empty line
            System.out.println("");
        }
    }

    //  Reverse display method that calls the helper method
    public static void reverseDisplay(String value) {

        //  Calling the recursive helper method to display reverse string
        reverseDisplay(value, value.length()-1);
    }

    //  Recursive helper method for displaying reverse string
    public static void reverseDisplay(String value, int high) {

        //  If there are more characters in string than display in reverse
        //  and move to the next character
        if ( high >= 0 ) {
            System.out.print(value.charAt(high));
            reverseDisplay(value, --high);
        }
    }
}


