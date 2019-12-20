package sample;

//  Sukhamrit Singh
//  HuffmanTreeFinal
/*
This program enables the user to enter a text string and it
displays the Huffman coding tree based on the text as is shown
in Exhibit A. Display the weight of the subtree inside the
subtree’s root circle. It displays each leaf node’s character,
and display the encoded bits for the text in a label.  When the
user hits the Decode Text button, a bit string is decoded in the
label as shown in Exhibit B below.
 */


//  imports the necessary libraries
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

    private Label encodedTextLabel;
    private TextField stringTextField = new TextField("");
    private TextField bitTextField = new TextField();
    BorderPane pane;
    TreePane treePane;
    HuffmanTree huffTree;

    @Override
    public void start(Stage primaryStage) {

        // Display title
        primaryStage.setTitle("Huffman Coding");

        // Initialize buttons
        Button showTreeButton = new Button("Show Huffman Tree");
        Button decodeButton = new Button("Decode To Text");

        // add button action listeners
        showTreeButton.setOnAction(e -> displayTree());
        decodeButton.setOnAction(e -> decodeText());

        // Create Label for displaying encoded bit stream (and error messages)
        encodedTextLabel = new Label();

        // Create UI
        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.add(new Label("Enter a text:"), 0, 0);
        gridPane.add(stringTextField, 1, 0);
        gridPane.add(new Label("Enter a bit string:"), 0, 1);
        gridPane.add(bitTextField, 1, 1);
        gridPane.add(showTreeButton, 2, 0);
        gridPane.add(decodeButton, 2, 1);
        gridPane.setAlignment(Pos.TOP_CENTER);
        stringTextField.setAlignment(Pos.BOTTOM_RIGHT);
        bitTextField.setAlignment(Pos.BOTTOM_RIGHT);

        treePane = new TreePane();
        treePane.setVisible(false);

        pane = new BorderPane();
        pane.setTop(gridPane);
        BorderPane.setAlignment(gridPane,Pos.TOP_CENTER);

        pane.setBottom(encodedTextLabel);
        BorderPane.setAlignment(encodedTextLabel, Pos.BOTTOM_CENTER);

        // Display UI
        Scene scene = new Scene(pane, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
       Method to display Huffman Tree and encoded string
     */
    private void displayTree() {

        // Read user entered text
        String myText = stringTextField.getText();

        // Check if text is entered. Show error message if no text entered
        if ( myText.length() == 0 ) {
            System.out.println("Please enter text...");
            encodedTextLabel.setText("ERROR: Please enter text..");
            treePane.setVisible(true);
            return;
        }

        // add treePane
        pane.setCenter(treePane);
        BorderPane.setAlignment(treePane, Pos.BOTTOM_CENTER);
        treePane.setVisible(true);

        // Create Huffman Tree with user Text
        huffTree = new HuffmanTree(myText);
        treePane.setTree(huffTree);

        // Display encoded bit stream
        encodedTextLabel.setText(myText + " is encoded to "
                + huffTree.encode(myText));

    }

    /*
      Method to decode bit stream to ASCII text
     */
    private void decodeText() {

        // read bit stream
        String bitStream = bitTextField.getText();

        // check if bitStream is specified. Allow only 0's and 1's
        // Display error message and return if valid bit stream not specified
        if ( bitStream.length() == 0 || bitStream.matches("[a-zA-Z2-9]+")) {
            System.out.println("Please enter bit code text only...");
            encodedTextLabel.setText("ERROR: Please enter bit code text...");
            treePane.setVisible(true);
            return;
        }

        // get decoded ASCII text from bit Stream
        String decoded = huffTree.decodeText(bitStream);

        // Display decoded ASCII text
        encodedTextLabel.setText(bitStream + " is decoded to " +
                decoded);
    }
}

/*
    Node class
 */
class Node implements Comparable<Node> {

    char asciiChar;
    double weight;
    Node left;
    Node right;

    // Initialze Node with weight and ascii character
    Node(int weight, char asciiCh) {
        this(weight, asciiCh, null, null);
    }

    // Initialize Node with weight, ascii char, left and right node
    Node(double weight, char asciiCh, Node left, Node right) {
        this.weight = weight;
        this.asciiChar = asciiCh;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(weight, node.weight);
    }
}

/*
    Huffman Tree Class to Encode and Decode ASCII text
 */
class HuffmanTree {

    Map<Character, Integer> hashMap;
    Node root;

    // Initialize Huffman Tree with ASCII string input
    HuffmanTree(String input) {

        // initialize hash map
        hashMap = new HashMap<>();

        // For each character in the input string
        // get weight (repeat count) and store in hash map
        for (char c : input.toCharArray()) {
            if (!hashMap.containsKey(c)) {
                hashMap.put(c, 1);
            } else {
                hashMap.replace(c, hashMap.get(c) + 1);
            }
        }

        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (Entry<Character, Integer> entry : hashMap.entrySet()) {
            queue.add(new Node(entry.getValue(), entry.getKey()));
        }

        if (queue.size() == 1) {
            root = queue.poll();
            root.weight = hashMap.get(root.asciiChar);
            return;
        }

        while (queue.size() != 1) {
            Node leftNode = queue.poll();
            Node rightNode = queue.poll();

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

    /*
        Encode Ascii characters
     */
    public void encode(Node root, String s, Map<Character, String> hmap) {
        if (root == null) {
            return;
        }

        if (root.left == null && root.right == null) {
            if (!s.equals("")) {
                hmap.put(root.asciiChar, s);
            } else {
                hmap.put(root.asciiChar, "1");
            }
        }

        encode(root.left, s + "0", hmap);
        encode(root.right, s + "1", hmap);
    }

    /*
        Decode bit stream
     */
    public int decode(Node node, String inputString, int index,
                      StringBuilder out) {
        if (root == null) {
            return index;
        }

        if (node.left == null && node.right == null) {
            out.append(node.asciiChar);
            //System.out.println(node.asciiChar);
            return index;
        }

        index++;

        try {
            if (inputString.charAt(index) == '0') {
                index = decode(node.left, inputString, index, out);
            } else {
                index = decode(node.right, inputString, index, out);
            }
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Missing bits in the input bit stream text .." +e.toString());
            index--;
        }
        return index;
    }

    /*
        Huffman decode method for bit stream string
     */
    public String decodeText(String inputString) {
        StringBuilder out = new StringBuilder();
        int index = -1;

        while (index < inputString.length() - 1) {
            index = decode(root, inputString, index, out);
        }
        return out.toString();
    }

    /*
        Huffman encode method for ASCII string
     */
    public String encode(String testString) {
        StringBuilder outString = new StringBuilder();
        for (char c : testString.toCharArray()) {
            outString.append(getCode().get(c));
        }
        return outString.toString();
    }
}

/*
    Tree Pane class
 */
class TreePane extends Pane {

    // Specify the edge length
    private static double vGap = 65;

    // Specify the Vertex radius
    private static double radius = 20;

    HuffmanTree huffmanTree;

    /*
        Initialize empty Tree Pane
     */
    TreePane() {
    }

    /*
        Initialize Tree Pane with Huffman Tree
     */
    TreePane(HuffmanTree tree) {
        this.huffmanTree = tree;
        drawTree();
    }

    /*
        Set Huffman tree
     */
    public void setTree(HuffmanTree tree) {
        this.huffmanTree = tree;
        drawTree();
    }

    /*
      Draw Huffman tree
     */
    public void drawTree() {
        getChildren().clear();

        if (huffmanTree.root != null) {
            drawTree(huffmanTree.root, getWidth() / 2, vGap,
                    getWidth() / 4);
        }
    }

    /*
        Draw Huffman Tree for the given root node
     */
    private void drawTree(Node root, double x, double y, double hGap) {

        // Draw left edge of the Node
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

        // Draw right edge of the Node
        if (root.right != null) {
            double x2 = x + hGap;
            double y2 = y + vGap;
            getChildren().add(new Line(x, y, x2, y2));

            double xM = (x + x2) / 2.;
            double yM = (y + y2) / 2.;
            getChildren().add(new Text(xM, yM, "1"));

            drawTree(root.right, x2, y2, hGap / 2);
        }

        // Draw Vertex
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        getChildren().add(circle);

        // Draw Vertex weight
        String v = String.format("%.1f", root.weight);
        if (root.asciiChar == '\0') {
            // If not leaf node
            getChildren().add(new Text(x - 10, y + 4, v));
        } else {
            // If leaf node
            getChildren().add(new Text(x - 10, y + 4, v));

            // If leaf node, then display Vertex Character below
            getChildren().add(new Text(x - 10, y + 35,
                    String.format("%c",root.asciiChar)));
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
