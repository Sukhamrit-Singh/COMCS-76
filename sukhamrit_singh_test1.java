package sample;

//  Sukhamrit Singh
//  sukhamrit_singh_test1
/*
This is a program that uses recursion to display
concentric circles.  A radius will be imputed and the
concentric circles will have a 10 pixel gap between them.
The largest circle in the display will also have a 10
distance gap with the borders
 */

//  Import all the necessary libraries
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;

public class sukhamrit_singh_test1 extends Application{

    //  Creating a global variable so that I can use
    // in the recursion method to add the circles
    private static Pane pane = new Pane();

    //  overriding the start method coming with Application
    @Override
    public void start(Stage stage) throws Exception {

        //  Calling recursive method to draw the circles
        drawCircles(5);

        //  Creating a scene to add the pane of circles
        Scene scene = new Scene(pane, 200, 200);

        stage.setTitle("ShowCircle"); // Set the stage title
        stage.setScene(scene); // Place the scene in the stage
        stage.show(); // Display the stage
    }

    //  Recursive method to draw the circles according to
    //  parameter radius
    public static void drawCircles (int radius) {

        //  Creating a circle object
        Circle circle = new Circle();

        //  Defining data type diameter
        int diameter  = 200;

        //  Creating a base case for the recursion loop
        if (radius * 2 >= diameter) {
            return;

        } else {
            //  Setting the radius of the circle
            circle.setRadius(radius);

            //  Setting strock of the circle, and making filled
            circle.setStroke(Color.BLACK);
            circle.setFill(Color.WHITE);

            //  Setting the centers of the circle
            circle.setCenterX(diameter / 2);
            circle.setCenterY(diameter / 2);

            //  Tail recursion to draw circle, and next add 10 to radius
            drawCircles(radius + 10);

            //  Adding the circles to the pane
            pane.getChildren().addAll(circle);
        }
    }
}
