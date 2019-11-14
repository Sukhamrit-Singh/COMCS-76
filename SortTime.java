package sample;

//  Sukhamrit Singh
//  SortTime
/*
This is a program that obtains the execution time of selection sort,
merge sort, quick sort, heap sort, and radix sort for input sizes of
50,000, 100,000, 200,000, 250,000, and 300,000.  The program then prints
a table and displays all the run time results (how long it took each
type of sort to completely sort all the numbers in the list)
 */

//  Import the necessary libraries
import java.util.*;

public class SortTime {
    public static void main(String[] args) {

        //  random numbers will be generated until this range
        final long MAX_NUMBER = 1000000;

        //  Creating 6 arrays with these sizes
        int[] arraySizes = {50000, 100000, 150000,
                200000, 250000, 300000};

        //  Printing the table title
        printTableTitle();

        //  For each array size, generate random numbers and run the sort
        //  algorithms
        for (int n=0; n<arraySizes.length; ++n) {

            int arraySize = arraySizes[n];

            int[] numberArray = new int[arraySize];
            Random random = new Random();
            //  Creating random numbers
            for (int i = 0; i < arraySize; i++) {
                numberArray[i] = (int)(Math.random() * MAX_NUMBER);
            }

            //  Printing out the array size
            System.out.print("\t" + arraySize + "\t|");


            //  Printing the selection sort time taken
            int[] narray = new int[arraySize];
            System.arraycopy(numberArray, 0, narray,
                    0, numberArray.length);
            long selectionTime = selectionSort(narray);
            System.out.print("\t\t" + selectionTime + "\t|");


            //  Printing the merge sort time taken
            narray = new int[arraySize];
            System.arraycopy(numberArray, 0, narray,
                    0, numberArray.length);
            long mergeTime = mergeSort(narray);
            System.out.print("\t\t" + mergeTime + "\t\t|");

            //  Printing the quick sort time taken
            narray = new int[arraySize];
            System.arraycopy(numberArray, 0, narray,
                    0, numberArray.length);
            long quickTime = quickSort(narray);
            System.out.print("\t\t" + quickTime + "\t\t|");


            //  Printing the heap sort time taken
            narray = new int[arraySize];
            System.arraycopy(numberArray, 0, narray,
                    0, numberArray.length);
            long heapTime = heapSort(narray);
            System.out.print("\t\t" + heapTime + "\t\t|");


            //  Printing the radix sort time taken
            narray = new int[arraySize];
            System.arraycopy(numberArray, 0, narray,
                    0, numberArray.length);
            long radixTime = radixSort(narray, MAX_NUMBER);
            System.out.print("\t\t" + radixTime + "\t\t");


            System.out.println("\n");
        }

    }

    //  Method to print the titles of the table
    private static void printTableTitle() {
        System.out.println("\n");
        System.out.println("ARRAY SIZE\t|\tSELECTION \t|\t  MERGE   \t|" +
                "\t  QUICK   \t|\t   HEAP   \t|\t  RADIX   \t");
        System.out.println("----------\t|\t----------\t|\t----------\t|" +
                "\t----------\t|\t----------\t|\t----------\t");
    }

    //  Selection Sort method using numberArray
    private static long selectionSort(int[] numberArray) {

        //  Start time for sort algorithm
        long startTime = System.currentTimeMillis();

        //  For numberArray, run selection sort
        for (int i = 0; i < numberArray.length - 1; i++) {
            int currentMin = numberArray[i];
            int currentMinIndex = i;

            for (int j = i + 1; j < numberArray.length; j++) {
                if (currentMin > numberArray[j]) {
                    currentMin = numberArray[j];
                    currentMinIndex = j;
                }
            }
            if (currentMinIndex != i) {
                numberArray[currentMinIndex] = numberArray[i];
                numberArray[i] = currentMin;
            }
        }

        //  End time for sort
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        return timeTaken;
    }

    //  Merge Sort method using numberArray
    private static long mergeSort(int[] numberArray) {

        //  Start time for sort algorithm
        long startTime = System.currentTimeMillis();

        //  Statement to check and run the merge sort algorithm
        if (numberArray.length > 1) {
            // Merge sort the first half
            int[] firstHalf = new int[numberArray.length / 2];
            System.arraycopy(numberArray, 0, firstHalf,
                    0, numberArray.length / 2);
            mergeSort(firstHalf);

            // Merge sort the second half
            int secondHalfLength = numberArray.length -
                    numberArray.length / 2;
            int[] secondHalf = new int[secondHalfLength];
            System.arraycopy(numberArray, numberArray.length / 2,
                    secondHalf, 0,
                    secondHalfLength);
            mergeSort(secondHalf);

            // Merge firstHalf with secondHalf into list
            merge(firstHalf, secondHalf, numberArray);
        }

        //  End time for sort
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        return timeTaken;
    }

    //  Helper method for merge sort algorithm
    private static void merge(int[] list1, int[] list2, int[] temp) {

        // Current index in list1
        int current1 = 0;

        // Current index in list2
        int current2 = 0;

        // Current index in temp
        int current3 = 0;

        //  Conditions to check and run the merge sort
        while (current1 < list1.length && current2 < list2.length) {
            if (list1[current1] < list2[current2])
                temp[current3++] = list1[current1++];
            else
                temp[current3++] = list2[current2++];
        }

        while (current1 < list1.length)
            temp[current3++] = list1[current1++];

        while (current2 < list2.length)
            temp[current3++] = list2[current2++];
    }

    //  Quick Sort method to sort the items in an array
    private static long quickSort(int[] numberArray) {

        //  Start time for sort algorithm
        long startTime = System.currentTimeMillis();

        //  Tail recursive call for quickSort
        quickSort(numberArray, 0, numberArray.length - 1);

        //  End time for sort
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        return timeTaken;
    }

    //  Quick Sort helper method using numberArray
    private static void quickSort(int[] numberArray, int first, int last) {
        //  Statement to run quickSort algorithm
        if (last > first) {
            int pivotIndex = partition(numberArray, first, last);
            quickSort(numberArray, first, pivotIndex - 1);
            quickSort(numberArray, pivotIndex + 1, last);
        }
    }

    //  Helper method for the quick sort algorithm
    private static int partition(int[] numberArray, int first, int last) {
        // Choose the first element as the pivot
        int pivot = numberArray[first];

        // Index for forward search
        int low = first + 1;

        // Index for backward search
        int high = last;

        //  Statements to run the quick sort
        while (high > low) {
            // Search forward from left
            while (low <= high && numberArray[low] <= pivot)
                low++;

            // Search backward from right
            while (low <= high && numberArray[high] > pivot)
                high--;

            // Swap two elements in the list
            if (high > low) {
                int temp = numberArray[high];
                numberArray[high] = numberArray[low];
                numberArray[low] = temp;
            }
        }

        //  Checks to run the quick sort
        while (high > first && numberArray[high] >= pivot)
            high--;

        // Swap pivot with list[high]
        if (pivot > numberArray[high]) {
            numberArray[first] = numberArray[high];
            numberArray[high] = pivot;
            return high;
        } else {
            return first;
        }
    }

    //  Heap Sort method to sort the items in an array
    private static long heapSort(int[] numberArray) {

        //  Start time for sort algorithm
        long startTime = System.currentTimeMillis();


        // Create a Heap of integers
        Heap<Integer> heap = new Heap<Integer>();

        // Add elements to the heap
        for (int i = 0; i < numberArray.length; i++)
            heap.add(numberArray[i]);

        // Remove elements from the heap
        for (int i = numberArray.length - 1; i >= 0; i--)
            numberArray[i] = heap.remove();

        //  End time for sort
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        return timeTaken;
    }


    //  Heap sort helper class
    static class Heap<E extends Comparable<E>> {
        private java.util.ArrayList<E> list =
                new java.util.ArrayList<E>();

        // Create a default heap constructor
        public Heap() {
        }

        // Create a heap from an array of objects
        public Heap(E[] objects) {
            for (int i = 0; i < objects.length; i++)
                add(objects[i]);
        }

        // Add a new object into the heap
        public void add(E newObject) {
            list.add(newObject); // Append to the heap
            int currentIndex = list.size() - 1; // The index of the last node

            //  Statement to run the heap sort algorithm
            while (currentIndex > 0) {
                int parentIndex = (currentIndex - 1) / 2;
                // Swap if the current object is greater than its parent
                if (list.get(currentIndex).compareTo
                        (list.get(parentIndex)) > 0) {
                    E temp = list.get(currentIndex);
                    list.set(currentIndex, list.get(parentIndex));
                    list.set(parentIndex, temp);
                } else
                    break; // the tree is a heap now

                //  Set currentIndex to parentIndex
                currentIndex = parentIndex;
            }
        }

        // Remove the root from the heap
        public E remove() {
            if (list.size() == 0)
                return null;

            E removedObject = list.get(0);
            list.set(0, list.get(list.size() - 1));
            list.remove(list.size() - 1);


            //  Conditions to add on to the heap sort algorithm
            int currentIndex = 0;
            while (currentIndex < list.size()) {
                int leftChildIndex = 2 * currentIndex + 1;
                int rightChildIndex = 2 * currentIndex + 2;

                // Find the maximum between two children
                if (leftChildIndex >= list.size())
                    break; // The tree is a heap
                int maxIndex = leftChildIndex;
                if (rightChildIndex < list.size()) {
                    if (list.get(maxIndex).compareTo
                            (list.get(rightChildIndex)) < 0) {
                        maxIndex = rightChildIndex;
                    }
                }

                // Swap if the current node is less than the maximum
                if (list.get(currentIndex).compareTo
                        (list.get(maxIndex)) < 0) {
                    E temp = list.get(maxIndex);
                    list.set(maxIndex, list.get(currentIndex));
                    list.set(currentIndex, temp);
                    currentIndex = maxIndex;
                } else
                    break; // The tree is a heap
            }

            return removedObject;
        }

        //  Get the number of nodes in the tree
        public int getSize() {
            return list.size();
        }
    }

    //  Radix Sort method to sort the items in an array
    private static long radixSort(int[] numberArray, long maxNumber) {

        //  Start time for sort algorithm
        long startTime = System.currentTimeMillis();

        //  For loop to run the radix sort algorithm
        for (int order = 1; order < maxNumber; order *= 10) {
            @SuppressWarnings("unchecked")
            ArrayList<Integer>[] bucket = new ArrayList[10];

            for (int i = 0; i < bucket.length; i++) {
                bucket[i] = new java.util.ArrayList<>();
            }

            for (int i = 0; i < numberArray.length; i++) {
                bucket[(numberArray[i] / order) % 10].
                        add(numberArray[i]);
            }

            int k = 0;
            for (int i = 0; i < bucket.length; i++) {
                if (bucket[i] != null) {
                    for (int j = 0; j < bucket[i].size(); j++)
                        numberArray[k++] = bucket[i].get(j);
                }
            }
        }

        //  End time for sort
        long endTime = System.currentTimeMillis();
        long timeTaken = endTime - startTime;

        return timeTaken;
    }
}
