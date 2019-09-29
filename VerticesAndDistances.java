package sample;

//  Sukhamrit Singh
//  VerticesAndDistances
/*
This program displays two circles with radius 10 at
locations (40, 40) and (120, 150) with a line connecting
the two circles. The distance between the circles is
displayed along the line connecting them. The user can drag
a circle. When that happens, the circle and line are moved,
and the distance between the circles is updated.
 */

//  Imports the necessary libraries
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//  Public class that extends off application
public class VerticesAndDistances extends Application {

    //  Creating a global border pane
    BorderPane pane = new BorderPane();

    @Override // Override the start method in the Application class
    public void start(Stage stage) throws Exception {

        //  Creating a constant to round numbers
        final double ROUND_BY = 100.0;

        // Add circle 1
        Circle circle1 = new Circle();
        circle1.setRadius(10);
        circle1.setCenterX(40);
        circle1.setCenterY(40);
        circle1.setStroke(Color.BLACK);
        circle1.setFill(Color.WHITE);

        // add circle 1
        Circle circle2 = new Circle();
        circle2.setRadius(10);
        circle2.setCenterX(120);
        circle2.setCenterY(150);
        circle2.setStroke(Color.BLACK);
        circle2.setFill(Color.WHITE);

        //  Creating a line to draw in between circles
        Line line = new Line((int) circle1.getCenterX(),
                (int) circle1.getCenterY(), (int) circle2.getCenterX(),
                (int) circle2.getCenterY());
        line.setStroke(Color.RED);

        //  Getting the distance in between circles so can write in text
        double distance = new Point2D((circle1.getCenterX()),
                circle1.getCenterY()).
                distance(circle2.getCenterX(), circle2.getCenterY());

        //  Text so that I can write down the distance between circles
        Text text = new Text((circle1.getCenterX() +
                circle2.getCenterX()) / 2, (circle1.getCenterY() +
                circle2.getCenterY()) / 2,
                Math.round(distance * ROUND_BY) / ROUND_BY + " ");

        //  Adding the circles, text, and line to the pane
        pane.getChildren().addAll(line, circle1, circle2, text);

        //  Action for the circle when dragged to move around
        circle1.setOnMouseDragged(e -> {
            //  Updates the circles coordinates when dragged
            circle1.setCenterX(e.getX());
            circle1.setCenterY(e.getY());

            //  Updates the line when dragged
            line.setStartX(e.getX());
            line.setStartY(e.getY());
            line.setEndX(circle2.getCenterX());
            line.setEndY(circle2.getCenterY());

            //  Creating a double to get the circles distance
            double d = new Point2D((circle1.getCenterX()),
                    circle1.getCenterY()).distance(circle2.getCenterX(),
                    circle2.getCenterY());

            //  Making sure to move text with the line
            text.setX((circle1.getCenterX() + circle2.getCenterX()) / 2);
            text.setY((circle1.getCenterY() + circle2.getCenterY()) / 2);

            //  Noting what to write in the text
            text.setText(Math.round(d * ROUND_BY) / ROUND_BY + " ");
        });

        //  Action for the circle when dragged to move around
        circle2.setOnMouseDragged(e -> {
            //  Updates the circles coordinates when dragged
            circle2.setCenterX(e.getX());
            circle2.setCenterY(e.getY());

            //  Updates the line when dragged
            line.setStartX(e.getX());
            line.setStartY(e.getY());
            line.setEndX(circle1.getCenterX());
            line.setEndY(circle1.getCenterY());

            //  Creating a double to get the circles distance
            double d = new Point2D((circle1.getCenterX()),
                    circle1.getCenterY()).distance(circle2.getCenterX(),
                    circle2.getCenterY());

            //  Making sure to move text with the line
            text.setX((circle1.getCenterX() + circle2.getCenterX()) / 2);
            text.setY((circle1.getCenterY() + circle2.getCenterY()) / 2);

            //  Noting what to write in the text
            text.setText(Math.round(d * ROUND_BY) / ROUND_BY + " ");
        });

        // add a scene
        Scene scene = new Scene(pane, 350, 350);
        stage.setTitle("VerticesAndDistances");
        stage.setScene(scene);

        //  Showing the scene
        stage.show();
    }
}