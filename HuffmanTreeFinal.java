package sample;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HuffmanTreeFinal extends Application{

    //private TreePane treePane;
    //private String enteredString;
    private Label encodedText;
    private TextField stringTextField = new TextField("");
    private TextField bitTextField = new TextField();
    BorderPane pane;
    TreePane treePane;
    HuffmanTree huffTree;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Huffman Coding");

        Button showTree = new Button("Show Huffman Tree");
        Button decode = new Button("Decode To Text");
        encodedText = new Label();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("Enter a text:"), 0, 0);
        gridPane.add(stringTextField, 1, 0);
        gridPane.add(new Label("Enter a bit string:"), 0, 1);
        gridPane.add(bitTextField, 1, 1);
        gridPane.add(showTree, 2, 0);
        gridPane.add(decode, 2, 1);
        gridPane.setAlignment(Pos.TOP_CENTER);
        stringTextField.setAlignment(Pos.BOTTOM_RIGHT);
        bitTextField.setAlignment(Pos.BOTTOM_RIGHT);

        showTree.setOnAction(e -> displayTree());
        decode.setOnAction(e -> decodeText());


        treePane = new TreePane();
        treePane.setVisible(false);

        pane = new BorderPane();

        pane.setTop(gridPane);
        BorderPane.setAlignment(gridPane,Pos.TOP_CENTER);

        pane.setBottom(encodedText);
        BorderPane.setAlignment(encodedText, Pos.BOTTOM_CENTER);


        Scene scene = new Scene(pane, 600, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayTree() {

        String myText = stringTextField.getText();
        if ( myText.length() == 0 ) {
            System.out.println("Please enter text...");
            encodedText.setText("ERROR: Please enter text..");
            treePane.setVisible(true);
            return;
        }

        pane.setCenter(treePane);
        BorderPane.setAlignment(treePane, Pos.BOTTOM_CENTER);

        treePane.setVisible(true);
        huffTree = new HuffmanTree(myText);
        treePane.setTree(huffTree);

        encodedText.setText(myText + " is encoded to "
                + huffTree.encode(myText));

    }

    private void decodeText() {

        String myText = bitTextField.getText();

        if ( myText.length() == 0 || myText.matches("[a-zA-Z2-9]+")) {
            System.out.println("Please enter bit code text only...");
            encodedText.setText("ERROR: Please enter bit code text...");
            treePane.setVisible(true);
            return;
        }

        pane.setCenter(treePane);
        BorderPane.setAlignment(treePane, Pos.BOTTOM_CENTER);

        String decoded = huffTree.decodeText(myText);

        encodedText.setText(myText + " is decoded to " +
                decoded);
    }


}

class Node implements Comparable<Node> {

    char element;
    double weight;
    Node left;
    Node right;

    Node(int weight, char element) {

        this(weight, element, null, null);
    }

    Node(double weight, char element, Node left, Node right) {
        this.weight = weight;
        this.element = element;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(weight, node.weight);
    }
}

class HuffmanTree {

    Map<Character, Integer> hashMap;
    Node root;

    HuffmanTree(String input) {

        hashMap = new HashMap<>();

        for (char c : input.toCharArray()) {

            if (!hashMap.containsKey(c)) {
                hashMap.put(c, 1);
            } else {
                hashMap.replace(c, hashMap.get(c) + 1);
            }
        }

        System.out.println(hashMap);

        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (Entry<Character, Integer> entry : hashMap.entrySet()) {
            queue.add(new Node(entry.getValue(), entry.getKey()));
        }

        if (queue.size() == 1) {
            root = queue.poll();
            root.weight = hashMap.get(root.element);
            return;
        }

        while (queue.size() != 1) {
            Node leftNode = queue.poll();
            Node rightNode = queue.poll();

            System.out.println(leftNode.element);
            System.out.println(rightNode.element);

            double combinedWeight = leftNode.weight + rightNode.weight;
            queue.add(new Node(combinedWeight, '\0', leftNode, rightNode));
        }

        root = queue.remove();
    }

    public Map<Character, String> getCode() {
        if (root == null) {
            return null;
        }

        Map<Character, String> out = new HashMap<>();
        encode(root, "", out);
        return out;
    }

    public void encode(Node root, String s, Map<Character, String> codes) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            if (!s.equals("")) {
                codes.put(root.element, s);
            } else {
                codes.put(root.element, "1");
            }
        }

        encode(root.left, s + "0", codes);
        encode(root.right, s + "1", codes);
    }

    public int decode(Node node, String inputString, int index, StringBuilder out) {
        if (root == null) {
            return index;
        }

        if (node.left == null && node.right == null) {
            out.append(node.element);
            System.out.println(node.element);
            return index;
        }

        index++;

        if (inputString.charAt(index) == '0') {
            index = decode(node.left, inputString, index, out);
        } else {
            index = decode(node.right, inputString, index, out);
        }

        return index;
    }

    public String decodeText(String inputString) {
        StringBuilder out = new StringBuilder();
        int index = -1;

        while (index < inputString.length() - 1) {
            index = decode(root, inputString, index, out);
        }
        return out.toString();
    }

    public String encode(String testString) {
        StringBuilder outString = new StringBuilder();
        for (char c : testString.toCharArray()) {
            outString.append(getCode().get(c));
        }
        return outString.toString();
    }
}

class TreePane extends Pane {
    private static double vGap = 65;
    private static double radius = 20;
    HuffmanTree huffmanTree;

    TreePane() {
    }

    TreePane(HuffmanTree tree) {
        this.huffmanTree = tree;
        drawTree();
    }

    public void setTree(HuffmanTree tree) {
        this.huffmanTree = tree;
        drawTree();
    }

    public void drawTree() {
        getChildren().clear();

        if (huffmanTree.root != null) {
            drawTree(huffmanTree.root, getWidth() / 2, vGap, getWidth() / 4);
        }
    }

    private void drawTree(Node root, double x, double y, double hGap) {
        if (root.left != null) {
            double x2 = x - hGap;
            double y2 = y + vGap;
            getChildren().add(new Line(x, y, x2, y2));

            double xM = (x + x2) / 2.;
            double yM = (y + y2) / 2.;
            Text bitText = new Text("0");
            bitText.setX(xM - bitText.getLayoutBounds().getWidth());
            bitText.setY(yM);
            getChildren().add(bitText);

            drawTree(root.left, x2, y2, hGap / 2);
        }

        if (root.right != null) {
            double x2 = x + hGap;
            double y2 = y + vGap;
            getChildren().add(new Line(x, y, x2, y2));

            double xM = (x + x2) / 2.;
            double yM = (y + y2) / 2.;
            getChildren().add(new Text(xM, yM, "1"));

            drawTree(root.right, x2, y2, hGap / 2);
        }

        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        getChildren().add(circle);
        String v = String.format("%.1f", root.weight);
        if (root.element == '\0') {
            getChildren().add(new Text(x - 10, y + 4, v));

        } else {
            getChildren().add(new Text(x - 10, y + 4, v));
            getChildren().add(new Text(x - 10, y + 35, String.format("%c",root.element)));
        }
    }

    // Override SetWidth, SetHeight Methods To Refresh Clock
    @Override
    public void setWidth(double width) {
        super.setWidth(width);
        drawTree();
    }

    @Override
    public void setHeight(double height) {
        super.setHeight(height);
        drawTree();
    }

}
