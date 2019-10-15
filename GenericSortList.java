package sample;

//  Sukhamrit Singh
//  GenericSortList
/*
This is a program to sort generic array lists
using the comparable method. It takes the given
list and prints the sorted list.
 */

//  Imports the necessary libraries
import java.util.ArrayList;

public class GenericSortList {
    public static void main(String[] args) {

        // Create an Integer arrayList
        ArrayList<Integer> numberList = new ArrayList<>();
        numberList.add(2);
        numberList.add(4);
        numberList.add(3);

        //  Print the original and sorted numbers list
        System.out.println("\nNUMBERS LIST");
        GenericSortList.printList("Original: ", numberList,
                numberList.size());
        GenericSortList.sort(numberList);
        GenericSortList.printList("  Sorted: ", numberList,
                numberList.size());

        //  Create a double arrayList
        ArrayList<Double> doubleList = new ArrayList<>();
        doubleList.add(3.4);
        doubleList.add(1.2);
        doubleList.add(-12.3);

        //  Print the original and sorted doubles list
        System.out.println("\nDOUBLES LIST");
        GenericSortList.printList("Original: ", doubleList,
                doubleList.size());
        GenericSortList.sort(doubleList);
        GenericSortList.printList("  Sorted: ", doubleList,
                doubleList.size());

        //  Create a string arrayList
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("Bob");
        stringList.add("Alice");
        stringList.add("Ted");
        stringList.add("Carol");

        //  Print the original and sorted strings list
        System.out.println("\nSTRINGS LIST");
        GenericSortList.printList("Original: ", stringList,
                stringList.size());
        GenericSortList.sort(stringList);
        GenericSortList.printList("  Sorted: ", stringList,
                stringList.size());
    }

    // Sort an array of comparable objects
    public static <E extends Comparable<E>> void sort(ArrayList<E> list) {
        E currentMin;
        int currentMinIndex;

        for (int i = 0; i < list.size() - 1; i++) {
            // Find the minimum in the list[i..list.length-1]
            currentMin = list.get(i);
            currentMinIndex = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (currentMin.compareTo(list.get(j)) > 0) {
                    currentMin = list.get(j);
                    currentMinIndex = j;
                }
            }

            // Swap list[i] with list[currentMinIndex] if necessary;
            if (currentMinIndex != i) {
                list.set(currentMinIndex, list.get(i));
                list.set(i, currentMin);
            }
        }
    }

    // Print an array of objects /
    public static <S, T extends Iterable<S>> void printList(
            String title, T list, int len) {

        //  Print list title
        System.out.print(title);

        //  Print every element in the list
        int i = 0;
        for (Object val: list) {
            System.out.print(val);

            // print ", " if it is not the last element
            if ( i < len-1 ) {
                System.out.print(", ");
            }
            ++i;
        }

        System.out.println();
    }
}
