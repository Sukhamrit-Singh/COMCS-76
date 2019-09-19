package javafxprograms;
//  Sukhamrit Singh
//  javafxprograms.TestGeometric
/*
This is a program that should create a javafxprograms.Triangle object with these
sides and set the color and filled properties using the input. The
program should display the area, perimeter, color, and true or false
to indicate whether it is filled or not.
 */

//  Imports the necessary libraries
import java.util.Scanner;

public class TestGeometric {
    /** Main method */
    public static void main(String[] args) {

        //  Made a scanner to get user input
        Scanner input = new Scanner(System.in);

        //  Displays some text
        System.out.print("List all three sides: ");

        //  Created scanner objects to get the three sides
        double firstSide = input.nextDouble();
        double secondSide = input.nextDouble();
        double thirdSide = input.nextDouble();


        // Declare and initialize two geometric objects
        GeometricObject geoObject1 = new Circle(5);
        GeometricObject geoObject2 = new Rectangle(5, 3);
        GeometricObject geoObject3 = new Triangle(firstSide, secondSide, thirdSide);

        // Checking if the user input creates a real triangle
        if (firstSide + secondSide > thirdSide &&
                firstSide + thirdSide > secondSide &&
                secondSide + thirdSide > firstSide){

            //  Displays text
            System.out.print("What is the color: ");

            //  Creates a scanner object to get the color
            String color = input.next();

            //  Displays text
            System.out.print("Is it filled: ");

            //  Creates a scanner object to check if filled
            String checkFilled = input.next();

            //  If statement to check if filled or not
            if ("True".equals(checkFilled)) {

                //  Displays all the geometric properties
                displayGeometricObject(geoObject3);

                //  Displays the color
                System.out.println("The color of this object is: " +
                        color);

                //  Displays if it is filled
                System.out.println("This object is filled" + "\n");
            } else if ("False".equals(checkFilled)) {

                //  Displays all the geometric properties
                displayGeometricObject(geoObject3);

                //  Displays the color
                System.out.println("The color of this object is: " +
                        color);

                //  Displays if it is filled
                System.out.println("This object is not filled" + "\n");
            }
        } else
            //  Displays if it is not a valid triangle
            System.out.println("Does not form a valid triangle");

        //  Displays if they have common area's
        System.out.println("The two objects have the same area? " +
                equalArea(geoObject1, geoObject2));

        // Display circle
        displayGeometricObject(geoObject1);

        // Display rectangle
        displayGeometricObject(geoObject2);
    }

    /** A method for comparing the areas of two geometric objects */
    public static boolean equalArea(GeometricObject object1,
                                    GeometricObject object2) {
        return object1.getArea() == object2.getArea();
    }

    /** A method for displaying a geometric object */
    public static void displayGeometricObject(GeometricObject object) {
        System.out.println();
        System.out.println(object.getObjectType());
        System.out.println("Time object was created: " + object.getDateCreated());
        System.out.println("The area is " + object.getArea());
        System.out.println("The perimeter is " + object.getPerimeter());
    }
}

//  Defines an abstract class
abstract class GeometricObject {
    //  Defined all the properties
    private String color = "white";
    private boolean filled;
    private java.util.Date dateCreated;
    private String objectType = "";

    /** Construct a default geometric object */
    protected GeometricObject() {
        dateCreated = new java.util.Date();
    }

    /** Construct a geometric object with color and filled value */
    protected GeometricObject(String color, boolean filled) {
        dateCreated = new java.util.Date();
        this.color = color;
        this.filled = filled;
    }


    /** Return color */
    public String getColor() {
        return color;
    }

    /** Set a new color */
    public void setColor(String color) {
        this.color = color;
    }

    /** Return filled. Since filled is boolean,
     *  the get method is named isFilled */
    public boolean isFilled() {
        return filled;
    }

    /** Set a new filled */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    /** Return object Type */
    public String getObjectType() { return objectType; }

    /** set object Type **/
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }


    /** Get dateCreated */
    public java.util.Date getDateCreated() {
        return dateCreated;
    }

    //  Overrides the method to display all geometric properties
    @Override
    public String toString() {
        return "created on " + dateCreated + "\ncolor: " + color +
                " and filled: " + filled;
    }

    /** Abstract method getArea */
    public abstract double getArea();

    /** Abstract method getPerimeter */
    public abstract double getPerimeter();
}

//  Creates a circle class that extends off the parent class
class Circle extends GeometricObject {

    //  Defining the properties
    private double radius;

    //  Defining the default property
    public Circle() {
        super.setObjectType("CIRCLE");
    }

    //  Creates the one value constructor
    public Circle(double radius) {
        super.setObjectType("CIRCLE");
        this.radius = radius;
    }

    /** Return radius */
    public double getRadius() {
        return radius;
    }

    /** Set a new radius */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    @Override /** Return area */
    public double getArea() {
        return radius * radius * Math.PI;
    }

    /** Return diameter */
    public double getDiameter() {
        return 2 * radius;
    }

    @Override /** Return perimeter */
    public double getPerimeter() {
        return 2 * radius * Math.PI;
    }

    /* Print the circle info */
    public void printCircle() {
        System.out.println("The circle is created " + getDateCreated() +
                " and the radius is " + radius);
    }
}

//  Creating a rectangle class
class Rectangle extends GeometricObject {

    //  Defining the properties
    private double width;
    private double height;

    //  Defining the default constructor
    public Rectangle() {
        super.setObjectType("RECTANGLE");
    }

    //  Creating a two value constructor
    public Rectangle(double width, double height) {
        super.setObjectType("RECTANGLE");
        this.width = width;
        this.height = height;
    }

    /** Return width */
    public double getWidth() {
        return width;
    }

    /** Set a new width */
    public void setWidth(double width) {
        this.width = width;
    }

    /** Return height */
    public double getHeight() {
        return height;
    }

    /** Set a new height */
    public void setHeight(double height) {
        this.height = height;
    }

    @Override /** Return area */
    public double getArea() {
        return width * height;
    }

    @Override /** Return perimeter */
    public double getPerimeter() {
        return 2 * (width + height);
    }
}

//  Creating a triangle class
class Triangle extends GeometricObject {

    //  Defining all the properties
    private double sideOne;
    private double sideTwo;
    private double sideThree;

    //  Creating a default constructor
    public Triangle() {
        super.setObjectType("TRIANGLE");
    }

    //  Creating a three value constructor
    public Triangle(double sideOne, double sideTwo, double sideThree) {
        super.setObjectType("TRIANGLE");
        this.sideOne = sideOne;
        this.sideTwo = sideTwo;
        this.sideThree = sideThree;
    }

    //  Return sideOne
    public double getSideOne() {
        return sideOne;
    }

    //  Sets sideOne
    public void setSideOne(double sideOne) {
        this.sideOne = sideOne;
    }

    //  Return sideTwo
    public double getSideTwo() {
        return sideTwo;
    }

    //  Sets sideTwo
    public void setSideTwo(double sideTwo) {
        this.sideTwo = sideTwo;
    }

    //  Returns sideThree
    public double getSideThree() {
        return sideThree;
    }

    //  Sets sideThree
    public void setSideThree(double sideThree) {
        this.sideThree = sideThree;
    }

    @Override /** Return area */
    public double getArea() {
        double semiPerimeter = (sideOne + sideTwo + sideThree) / 2;

        double area = Math.sqrt(semiPerimeter * (semiPerimeter - sideOne) *
                (semiPerimeter - sideTwo) * (semiPerimeter - sideThree));

        return area;
    }

    @Override /** Return perimeter */
    public double getPerimeter() {
        return sideOne + sideTwo + sideThree;
    }
}

