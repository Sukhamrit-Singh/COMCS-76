package sample;

//  Sukhamrit Singh
//  CoordinatesList
/*
This is a program where a class named Point with tow data
fields x and y to represent a point's x- and y-coordinates.
Implement the Comparable interface for the comparing the
points on x-coordinates. If two points have the same x-coordinates,
compare their y-coordinates.  There is also a class named CompareY
where it compares the y coordinates instead.  The program randomly
creates 100 points and apply the Arrays.sort method to display the points
in increasing order of their x-coordinates, and increasing order of their
y-coordinates, respectively.
 */


//  Imports the necessary libraries
import java.util.Arrays;
import java.util.Comparator;

public class CoordinatesList {
    public static void main(String[] args) {

        //  Creates an array of 100 objects
        Point[] points = new Point[100];

        System.out.println("Sorting X-Coordinates\n");

        //  Creating random points
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point (Math.random() * 100,
                    Math.random() * 100);
        }

        // Using sort method to sort the x coordinates in increasing order
        Arrays.sort(points);


        //  printing the points with the sorted x coordinates
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i].getX() + ", " +
                    points[i].getY());
        }

        System.out.println("\nSorting Y-Coordinates\n");

        //  Sorting the y coordinates in increasing order
        Arrays.sort(points, new CompareY());

        //  Printing the points with sorted y coordinates
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i].getX() + ", " +
                    points[i].getY());
        }
    }
}

//  Creating class point implementing comparable
class Point implements Comparable<Point> {

    private double x;
    private double y;

    //  2 arg constructor
    Point (double x, double y) {
        this.x = x;
        this.y = y;

    }

    //  Getter and setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    //  Overriding the compareTo method
    @Override
    public int compareTo(Point pt) {

        /*
         If statement to check the comparisons
         of x coordinates.  If two x coordinates are
         equal, then it checks if the y coordinates are
         greater or less or equal to other y coordinate
         */
        if (this.x < pt.x ) {
            return -1;
        } else if (this.x > pt.x ) {
            return 1;
        } else {
            if (this.y < pt.y ) {
                return -1;
            } else if (this.y > pt.y ) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

//  Creating class CompareY and implements Comparator
class CompareY implements Comparator<Point> {

    //  Overrides the compare function
    @Override
    public int compare(Point pt1, Point pt2) {

        //  Defining data fields based on parameters
        double x1 = pt1.getX();
        double x2 = pt2.getX();
        double y1 = pt1.getY();
        double y2 = pt2.getY();

        /*
         If statement to check the comparisons
         of y coordinates.  If two y coordinates are
         equal, then it checks if the x coordinates are
         greater or less or equal to other x coordinate
         */
        if (y1 < y2 ) {
            return -1;
        } else if (y1 > y2) {
            return 1;
        } else {
            if (x1 < x2) {
                return -1;
            } else if (x1 > x2) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}