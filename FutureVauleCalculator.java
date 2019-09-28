package sample;

//  Sukhamrit Singh
//  FutureValueCalculator
/*
This program calculates the future value of
an investment at a given interest rate for a
specified number of years.  The program takes in
user input, which is the amount of money, the
number of years, and the annual interest.  At the
end, once the user clicks calculate, it find
the future value of the invested amount of money
 */

//  Imports the necessary libraries
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//  Class FutureValueCalculator that extends off Application
public class FutureVauleCalculator extends Application{

    //  Creating text fields for the user input
    private TextField tfInvestmentAmount = new TextField();
    private TextField tfNumberOfYears = new TextField();
    private TextField tfAnnualInterest = new TextField();
    private TextField tfFutureValue = new TextField();

    //  Button to calculate the users input and give an output
    private Button btCalculate = new Button("Calculate");

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {

        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("Investment Amount:"), 0, 0);
        gridPane.add(tfInvestmentAmount, 1, 0);
        gridPane.add(new Label("Number of Years:"), 0, 1);
        gridPane.add(tfNumberOfYears, 1, 1);
        gridPane.add(new Label("Annual Interest Rate:"), 0, 2);
        gridPane.add(tfAnnualInterest, 1, 2);
        gridPane.add(new Label("Future Value:"), 0, 3);
        gridPane.add(tfFutureValue, 1, 3);
        gridPane.add(btCalculate, 1, 4);

        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        tfInvestmentAmount.setAlignment(Pos.BOTTOM_RIGHT);
        tfNumberOfYears.setAlignment(Pos.BOTTOM_RIGHT);
        tfAnnualInterest.setAlignment(Pos.BOTTOM_RIGHT);
        tfFutureValue.setAlignment(Pos.BOTTOM_RIGHT);
        tfFutureValue.setEditable(false);
        GridPane.setHalignment(btCalculate, HPos.RIGHT);

        // Process events
        btCalculate.setOnAction(e -> calculateLoanPayment());

        // Create a scene and place it in the stage
        Scene scene = new Scene(gridPane, 400, 250);
        primaryStage.setTitle("FutureValueCalculator"); // Set title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    //  Method to calculate the loan payments
    private void calculateLoanPayment() {

        //  Defining the datatypes
        double interest = 0;
        int year = 0;
        double loanAmount = 0;

        // check valid loan Amount
        try {
            loanAmount = Double.parseDouble(tfInvestmentAmount.getText());
            if ( loanAmount <= 0 ) {
                System.out.println("Invalid loan amount ..");
                this.showError("Investment Amount cannot be " +
                        "negative");
                return;
            }
        } catch (Exception e) {
            System.out.println("Invalid loan amount ..");
            this.showError("Investment Amount is not valid");
            return;
        }

        // check valid year
        try {
            year = Integer.parseInt(tfNumberOfYears.getText());
            if ( year <= 0 ) {
                System.out.println("Invalid year ..");
                this.showError("Number of Years should be more " +
                        "than 0");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid year ..");
            this.showError("Number of Years is not valid");
            return;
        }

        // check valid interest
        try {
            interest = Double.parseDouble(tfAnnualInterest.getText());
            if ( interest < 0 ) {
                System.out.println("Invalid interest value ..");
                this.showError("Annual Interest Rate cannot be " +
                        "negative");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid interest value ..");
            this.showError("Annual Interest Rate is not valid");
            return;
        }

        // Create a loan object
        Loan loan = new Loan(interest, year, loanAmount);

        // Display monthly payment and total payment
        tfFutureValue.setText(String.format("$%.2f",
                loan.getFutureValue()));
    }

    // Method to display error message
    private void showError (String errorMsg) {

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Input Error");
        errorAlert.setContentText(errorMsg);
        errorAlert.showAndWait();
    }

    //  Creating a class loan to calculate al user inputs
    public class Loan {

        //  Defining the private data field
        private double annualInterestRate;
        private int numberOfYears;
        private double investmentAmount;
        private java.util.Date loanDate;

        /** Default constructor */
        public Loan() {

            this(2.5, 1, 1000);
        }

        /** Construct a loan with specified annual interest rate,
         number of years, and loan amount
         */
        public Loan(double annualInterestRate, int numberOfYears,
                    double loanAmount) {
            this.annualInterestRate = annualInterestRate;
            this.numberOfYears = numberOfYears;
            this.investmentAmount = loanAmount;
            loanDate = new java.util.Date();
        }

        /** Return annualInterestRate */
        public double getAnnualInterestRate() {

            return annualInterestRate;
        }

        /** Set a new annualInterestRate */
        public void setAnnualInterestRate(double annualInterestRate) {

            this.annualInterestRate = annualInterestRate;
        }

        /** Return numberOfYears */
        public int getNumberOfYears() {

            return numberOfYears;
        }

        /** Set a new numberOfYears */
        public void setNumberOfYears(int numberOfYears) {

            this.numberOfYears = numberOfYears;
        }

        /** Return loanAmount */
        public double getInvestmentAmountAmount() {

            return investmentAmount;
        }

        /** Set a newloanAmount */
        public void setInvestmentAmount(double loanAmount) {

            this.investmentAmount = loanAmount;
        }

        /** Find Future Value */
        public double getFutureValue() {
            double monthlyInterest = annualInterestRate / 1200;
            double futureValue = investmentAmount * Math.pow(1 +
                    monthlyInterest, numberOfYears * 12);
            return futureValue;
        }

        /** Return loan date */
        public java.util.Date getLoanDate() {

            return loanDate;
        }
    }

}
