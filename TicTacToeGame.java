package sample;
//  Sukhamrit Singh
//  TicTacToeGame
/*
This is a program that displays a tic-tac-toe board,
as is shown in Figure 14.43b (of the text). A cell may
be X, O, or empty. What to display at each cell is
randomly decided by calling a Math.random method
in the program.  Then using an if statement, what
picture should be displayed is decided
 */

//  Imports the necessary libraries
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

//  Creating a class TicTacToeGame that extends off Application
public class TicTacToeGame extends Application{

    //  Creating a main method
    public static void main(String[] args) {

        //  Calling the method launch
        launch(args);
    }

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

        //  Defining 3 image objects that display a blank, an X,
        //  and an O
        Image ticTacBlank = new Image
                ("http://www.sukhamrit.com/images/tttblackbg.png");
        Image ticTacX = new Image
                ("http://www.sukhamrit.com/images/tttX.png");
        Image ticTacO = new Image
                ("http://www.sukhamrit.com/images/tttO.png");

        // Create a grid pane
        GridPane pane = new GridPane();

        //  Creating a for loop to have outputs in between the 3 rows
        for (int row = 0;  row < 3; row++) {

            //  Creating a for loop to have outputs in between
            //  the 3 columns
            for (int column = 0; column < 3; column++) {

                //  Defining a double to be the randomize the display
                double randomPick = Math.random()*1000;

                //  Defining a variable the gets the remainder of the
                //  random number to display an image
                int val = (int) randomPick % 3;

                //  Defining an ImageView object
                ImageView printImage;

                //  An if statement to check the value of val if a certain
                //  value
                if (val == 0) {
                    //  Displays the blank
                    printImage = new ImageView(ticTacBlank);
                } else if (val == 1) {
                    //  Displays the X
                    printImage = new ImageView(ticTacX);
                } else {
                    //  Displays the O
                    printImage = new ImageView(ticTacO);
                }

                //  Variables to decide the thickness of the margins
                int top = 8;
                int right = 8;
                int bottom = 8;
                int left = 8;

                //  Setting where the margins will be in the grid pane
                GridPane.setMargin(printImage, new Insets(top, right,
                        bottom, left));

                //  Adding the rows and columns to the pane
                pane.add(printImage, row, column);
            }
        }

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane);
        primaryStage.setTitle("ShowBorderPane"); // Set the stage title
        primaryStage.setScene(scene);  // Place the scene in the stage
        primaryStage.show();  // Display the stage
    }
}
