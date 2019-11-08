//  Sukhamrit Singh
//  PointDistanceAlgorithms
/*
This program is used for finding the closest
pair of points using a divide-and-conquer approach.
First sort the points in increasing order of x-coordinates.
For the points with the same x-coordinates, sort on y-coordinates.
The result should be a sorted collection of points denoted by S.
Then divide S into two subsets, S1 and S2, of equal size about the
midpoint of the sorted list S. Include the midpoint in S1. Recursively
find the closest pair in S1 and S2. Let d1 and d2 denote the distance
of closest pairs in the two subsets, respectively. Finally, find the
closest pair between a point in S1 and a point in S2 and denote the
distance between them as d3. The closest pair is the one with distance
equal to the minimum of {d1, d2, d3}.
*/

//  Import the necessary libraries
import java.util.*;

public class PointDistanceAlgorithms {

    public static void main(String[] args) {

        //  Creates an array of 100 objects
        Point[] points = new Point[100];

        System.out.println("100 Random Points\n");

        //  Creating random points
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point (Math.random() * 100,
                    Math.random() * 100);
            System.out.println("(" + points[i].getX() + ", " +
                    points[i].getY() + ")");
        }

        //  Calls the Pair class and initializes the points
        Pair pair = Pair.getClosestPair(points);

        //  Prints out the results
        System.out.println("\nThe closest two points are " + "(" + pair.getPoint1().getX() + ", "
                + pair.getPoint1().getY() + ") " + "and " + "(" + pair.getPoint2().getX() +  ", "
                + pair.getPoint2().getY() + ")\n");
        System.out.println("The distance between them is " + pair.getDistance());
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

//  Creating a class pair to get and calculate the closest pairs
class Pair{


    private Point point1;
    private Point point2;


    public Pair(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Point getPoint1() {
        return point1;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }


    public double getDistance()
    {
        return distance(point1,point2);
    }
    /** Calculate the distance between two points p1 and p2 */
    public static double distance(Point point1, Point point2)
    {
        return distance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }
    /** Calculate the distance between points (x1, y1) and (x2, y2) */
    public static double distance(double x1, double y1, double x2, double y2)
    {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }


    //  Method to sort the points and return the distance
    //  of the closest points
    public static Pair getClosestPair(Point[] points)
    {
        Arrays.sort(points);
        Point[] orderY = points.clone();
        Arrays.sort(orderY, new CompareY());
        return distance(points, 0, points.length - 1, orderY);
    }

    //  Returns the closest pair of points
    public static Pair getClosestPair(double[][] points)
    {
        Point[] points2 = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            points2[i] = new Point(points[i][0], points[i][1]);
        }
        return getClosestPair(points2);
    }


    //Return the distance of the closest pair of points
    //This is a recursive
    public static Pair distance(Point[] pointsOrderedOnX,
                                int low, int high,
                                Point[] pointsOrderedOnY)
    {
        if (low >= high)
        {
            return null;

        } else if (low + 1 == high)
        {
            return new Pair(pointsOrderedOnX[low],
                    pointsOrderedOnX[high]); // only 1 pair present
        }

        /*
         Divide S into two subsets, S1 and S2, using the midpoint
         */

        int midPoint = (low + high) / 2; // divide into 2 subsets

        // return the closet pair on the left side
        Pair leftPair = distance(pointsOrderedOnX, low, midPoint, pointsOrderedOnY);

        // return the closet pair on the right side
        Pair rightPair = distance(pointsOrderedOnX, midPoint + 1, high, pointsOrderedOnY);

        double d = 0;
        Pair p = null;

        if (leftPair == null && rightPair == null)  // no pairs present on both sides
        {
            d = 0;
            p = null;
        } else if (leftPair == null)  // no pairs on the left side, then the right pair is the shortest
        {
            d = rightPair.getDistance(); // get d2
            p = rightPair;
        } else if (rightPair == null) // no pairs on the right side, then the left pair is the shortest
        {
            d = leftPair.getDistance(); //get d1
            p = leftPair;
        } else
        {
            // get the minimum distance of d1 and d2: d = min(d1,d2)
            d = Math.min(leftPair.getDistance(), rightPair.getDistance());
            // get the point that has minimum distance d
            if(leftPair.getDistance() <= rightPair.getDistance())
            {
                p =leftPair;
            }else
            {
                p=rightPair;
            }

        }
		// Obtain stripL and stripR algorithms

        //create list stripL and stripR to hold the points
        ArrayList<Point> stripL = new ArrayList<Point>();
        ArrayList<Point> stripR = new ArrayList<Point>();
        for (int i = 0; i < pointsOrderedOnY.length; i++)
        {
            if ((pointsOrderedOnY[i].getX() <= pointsOrderedOnX[midPoint].getX())&&
                    (pointsOrderedOnX[midPoint].getX() - pointsOrderedOnY[i].getX() <= d))
            {
                stripL.add(pointsOrderedOnY[i]);
            }else if((pointsOrderedOnY[i].getX() > pointsOrderedOnX[midPoint].getX()) &&
                    (pointsOrderedOnY[i].getX() - pointsOrderedOnX[midPoint].getX() <=d ))
            {
                stripR.add(pointsOrderedOnY[i]);
            }
        }

        int r = 0;
        for (int i = 0; i < stripL.size(); i++)
        {
            while (r < stripR.size() && stripR.get(r).getY() <= stripL.get(i).getY() - d)
            {
                r++;
            }
            int r1 = r;
            while (r1 < stripR.size() && Math.abs(stripR.get(r1).getY() - stripL.get(i).getY()) <=  d)
            {
                if (distance(stripL.get(i), stripR.get(r1)) < d) {
                    d = distance(stripL.get(i), stripR.get(r1));
                    p.point1 = stripL.get(i);
                    p.point2 = stripR.get(r1);
                }
                r1++;
            }
        }

        return p;
    }
}