package sample;

//  Sukhamrit Singh
//  DetailedClock
/*
This program shows a modified ClockPane class in Section 14.12
of the course text to draw the clock with more details on hours
and minutes.  This clock shows the exact time it was created on
has a label at the bottom showing the time as well in military
time.
 */

//  Imports all the necessary libraries
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

//  Class DetailedClock that extends off Application
public class DetailedClock extends Application {

    //  Creating a global variable
    ClockPane clockPane;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        //  Creating an HBox
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        // Create a clock and a label
        Label lblCurrentTime = new Label();
        clockPane = new ClockPane(lblCurrentTime);
        Button stopBtn = new Button("Stop");
        Button startBtn = new Button("Start");

        // Place clock, label, buttons in border pane
        BorderPane pane = new BorderPane();
        pane.setCenter(clockPane);
        pane.setTop(lblCurrentTime);
        BorderPane.setAlignment(lblCurrentTime, Pos.TOP_CENTER);

        //  Adding the buttons to the hBox
        hBox.getChildren().add(startBtn);
        hBox.getChildren().add(stopBtn);

        //  Setting Button Alignments
        pane.setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        //  Setting actions for buttons
        startBtn.setOnAction(new StartClockHandler());
        stopBtn.setOnAction(new StopClockHandler());

        // Create a scene and place it in the stage
        Scene scene = new Scene(pane, 350, 350);
        primaryStage.setTitle("DisplayClock"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        //  Starts the clock
        clockPane.startClock();

    }

    class StartClockHandler implements EventHandler<ActionEvent> {
        @Override // Override the handle method
        public void handle(ActionEvent e) {
            clockPane.startClock();
        }
    }

    class StopClockHandler implements EventHandler<ActionEvent> {
        @Override // Override the handle method
        public void handle(ActionEvent e) {
            clockPane.stopClock();
        }
    }
}

//  Creates a ClockPane class that extends off Pane
class ClockPane extends Pane {

    //  Defining three private variables
    private int hour;
    private int minute;
    private int second;
    Timer timer; // = new Timer();;
    Label lblCurrentTime;

    /** Construct a default clock with the current time*/
    public ClockPane() {
        setCurrentTime();
    }

    /** Construct a clock with specified hour, minute, and second */
    public ClockPane(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    /** Return hour */
    public int getHour() {
        return hour;
    }

    /** Set a new hour */
    public void setHour(int hour) {
        this.hour = hour;
        paintClock();
    }

    /** Return minute */
    public int getMinute() {
        return minute;
    }

    /** Set a new minute */
    public void setMinute(int minute) {
        this.minute = minute;
        paintClock();
    }

    /** Return second */
    public int getSecond() {
        return second;
    }

    /** Set a new second */
    public void setSecond(int second) {
        this.second = second;
        paintClock();
    }

    /* Set the current time for the clock */
    public void setCurrentTime() {
        // Construct a calendar for the current date and time
        Calendar calendar = new GregorianCalendar();

        // Set current hour, minute and second
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);

        paintClock(); // Repaint the clock
    }

    //  Creating a clock pane method
    public ClockPane(Label lblCurrentTime) {
        this.lblCurrentTime = lblCurrentTime;

    }
    //  Creating Start clock method
    public void startClock() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Running: " + new java.util.Date());
                Platform.runLater(()->{
                    setCurrentTime();

                    String timeString = getHour() + ":" + getMinute() + ":" + getSecond();
                    lblCurrentTime.setText(timeString);
                });
            }
        }, 0, 1000);
    }

    //  Creating stop clock method
    public void stopClock() {
        timer.cancel();
    }

    /** Paint the clock */
    private void paintClock() {
        // Initialize clock parameters
        double clockRadius =
                Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
        double centerX = getWidth() / 2;
        double centerY = getHeight() / 2;

        // Draw circle
        Circle circle = new Circle(centerX, centerY, clockRadius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);

        //  Labeling all the hours on the clock
        Text t1 = new Text(centerX - 5, centerY - clockRadius + 22,
                "12");
        Text t2 = new Text(centerX - clockRadius + 12, centerY + 5,
                "9");
        Text t3 = new Text(centerX + clockRadius - 22, centerY + 3,
                "3");
        Text t4 = new Text(centerX - 3, centerY + clockRadius - 12,
                "6");
        Text t5 = new Text(centerX + clockRadius / 2.5,
                centerY - clockRadius / 1.6, "1");
        Text t6 = new Text(centerX + clockRadius / 1.5,
                centerY - clockRadius / 2.6, "2");
        Text t7 = new Text(centerX + clockRadius / 1.5,
                centerY + clockRadius / 2.25, "4");
        Text t8 = new Text(centerX + clockRadius / 2.5,
                centerY + clockRadius / 1.35, "5");
        Text t9 = new Text(centerX - clockRadius / 2.3,
                centerY + clockRadius / 1.35, "7");
        Text t10 = new Text(centerX - clockRadius / 1.31,
                centerY + clockRadius / 2.25, "8");
        Text t11 = new Text(centerX - clockRadius / 1.31,
                centerY - clockRadius / 2.6, "10");
        Text t12 = new Text(centerX - clockRadius / 2.3,
                centerY - clockRadius / 1.6, "11");

        // Draw second hand
        double sLength = clockRadius * 0.8;
        double secondX = centerX + sLength *
                Math.sin(second * (2 * Math.PI / 60));
        double secondY = centerY - sLength *
                Math.cos(second * (2 * Math.PI / 60));
        Line sLine = new Line(centerX, centerY, secondX, secondY);
        sLine.setStroke(Color.RED);

        // Draw minute hand
        double mLength = clockRadius * 0.65;
        double xMinute = centerX + mLength *
                Math.sin(minute * (2 * Math.PI / 60));
        double minuteY = centerY - mLength *
                Math.cos(minute * (2 * Math.PI / 60));
        Line mLine = new Line(centerX, centerY, xMinute, minuteY);
        mLine.setStroke(Color.BLUE);

        // Draw hour hand
        double hLength = clockRadius * 0.5;
        double hourX = centerX + hLength *
                Math.sin((hour % 12 + minute / 60.0) *
                        (2 * Math.PI / 12));
        double hourY = centerY - hLength *
                Math.cos((hour % 12 + minute / 60.0) *
                        (2 * Math.PI / 12));
        Line hLine = new Line(centerX, centerY, hourX, hourY);
        hLine.setStroke(Color.GREEN);

        //  Adding the hands, and numbers to the clock
        getChildren().clear();
        getChildren().addAll(circle, t1, t2, t3, t4, t5, t6, t7,
                t8, t9, t10, t11, t12, sLine, mLine, hLine);

        //  Draw Minute Tick Marks
        for (int i = 0; i < 60; i++) {
            double startX = centerX + clockRadius *
                    Math.cos(2 * i * Math.PI / 60);
            double startY = centerY + clockRadius *
                    Math.sin(2 * i * Math.PI / 60);
            double endX = startX - (clockRadius * .05) *
                    Math.cos(2 * i * Math.PI / 60);
            double endY = startY - (clockRadius * .05) *
                    Math.sin(2 * i * Math.PI / 60);

            //  Adding the minute tick marks to the clock
            getChildren().add(new Line(startX, startY, endX, endY));
        }

        //  Draw Hour Tick Marks
        for (int i = 0; i < 12; i++) {
            double startX = centerX + clockRadius *
                    Math.cos(2 * i * Math.PI / 12);
            double startY = centerY + clockRadius *
                    Math.sin(2 * i * Math.PI / 12);
            double endX = startX - (clockRadius * .1) *
                    Math.cos(2 * i * Math.PI / 12);
            double endY = startY - (clockRadius * .1) *
                    Math.sin(2 * i * Math.PI / 12);

            //  Adding the hour tick marks to the clock
            getChildren().add(new Line(startX, startY, endX, endY));
        }

    }

    //  Overriding the setWidth method that adjusts the clocks width
    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        paintClock();
    }

    //  Overriding the setHeight method that adjusts the clocks height
    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        paintClock();
    }
}
