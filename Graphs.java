package sample;

//  Sukhamrit Singh
//  Graphs
/*
This is a program that reads the data for a graph from  a file
and determines whether the graph is connected. The first line
in the file contains a number that indicates the number of
vertices (n). The vertices are labeled as 0, 1, 2, . . ., n -1.
Each subsequent line, with the format u v1 v2, . . ., describes
edges (u, v1), (u,v2), (u,v3), and so on.
 */

// import the necessary libraries
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

// Data set links
// Link given in project does not work so made my own
// http://www.sukhamrit.com/fileOne.txt
// http://www.sukhamrit.com/fileTwo.txt

public class Graphs {
    public static void main(String[] args) throws Exception {

        // Asks the user to enter a url
        System.out.print("Enter a file URL: ");

        Scanner sc = new Scanner(System.in);
        String fileUrl = sc.nextLine();

        //  try catch statement checking the url
        //  if the url exists, it carries out functions
        //  if not, print error statement
        try {
            URL url = new URL(fileUrl);

            //  Checking for response from url
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            int responseCode = huc.getResponseCode();

            //  open site
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));


            //  reading lines and finding vertices
            int numofVericies = 0;
            String firstLine = in.readLine();

            Scanner input;
            if ( firstLine != null ) {
                input = new Scanner(firstLine);
                numofVericies = input.nextInt();
            }

            // Create an array of vertices
            String[] vertices = new String[numofVericies];

            // Create a list of AbstractGraph.Edge objects
            ArrayList<AbstractGraph.Edge> edgeList = new ArrayList<>();

            //in.readLine();
            for (int j = 0; j < numofVericies; j++) {
                String s = in.readLine();
                String[] line = s.split("[\\s+]");
                String u = line[0];
                vertices[j] = u; // Add vertex

                // Add edges for vertex u
                for (int i = 1; i < line.length; i++) {
                    edgeList.add(new AbstractGraph.Edge(Integer.parseInt(u),
                            Integer.parseInt(line[i])));
                }
            }

            // Crate a graph
            Graph<String> graph = new UnweightedGraph<>(
                    Arrays.asList(vertices), edgeList);

            // Print the number of vertices
            System.out.println("The number of vertices is " + graph.getSize());

            // Print the edges
            for (int u = 0; u < numofVericies; u++) {
                System.out.print("Vertex " + graph.getVertex(u) + ":");
                for (Integer e : graph.getNeighbors(u))
                    System.out.print(" (" + u + ", " + e + ")");
                System.out.println();
            }

            // Have an instance tree of AbstractGraph.Tree
            AbstractGraph.Tree tree = graph.dfs(0);

            // Check if graph is connected and print results
            System.out.println("The graph is " +
                    (tree.getNumberOfVerticesFound() != graph.getSize() ?
                            "not " : "") + "connected");

            in.close();

            //  Printing if url is not found
        } catch (Exception e) {
            System.out.println("The file \"" + fileUrl + "\" does not exist.");
            System.exit(0);
        }

    }
}

abstract class AbstractGraph<V> implements Graph<V> {
    protected List<V> vertices = new ArrayList<>(); // Store vertices
    protected List<List<Edge>> neighbors = new ArrayList<>(); // Adjacendy lists

    /** Construct an empty graph */
    protected AbstractGraph() {
    }

    /** Construct a graph from vertices and edges stored in arrays */
    protected AbstractGraph(V[] vertices, int[][] edges) {
        for (int i = 0; i < vertices.length; i++)
            addVertex(vertices[i]);

        createAdjacencyLists(edges, vertices.length);
    }

    /** Construct a graph from vertices and edges stored in List */
    protected AbstractGraph(List<V> vertices, List<Edge> edges) {
        for (int i = 0; i < vertices.size(); i++)
            addVertex(vertices.get(i));

        createAdjacencyLists(edges, vertices.size());
    }

    /** Construct a graph for integer vertices 0, 1, 2 and edge list */
    protected AbstractGraph(List<Edge> edges, int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++)
            addVertex((V)(new Integer(i))); // vertices is {0, 1, ...}

        createAdjacencyLists(edges, numberOfVertices);
    }

    /** Construct a graph from integer vertices 0, 1, and edge array */
    protected AbstractGraph(int[][] edges, int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++)
            addVertex((V)(new Integer(i))); // vertices is {0, 1, ...}

        createAdjacencyLists(edges, numberOfVertices);
    }

    /** Create adjacency lists for each vertex */
    private void createAdjacencyLists(
            int[][] edges, int numberOfVertices) {
        for (int i = 0; i < edges.length; i++) {
            addEdge(edges[i][0], edges[i][1]);
        }
    }

    /** Create adjacency lists for each vertex */
    private void createAdjacencyLists(
            List<Edge> edges, int numberOfVertices) {
        for (Edge edge : edges) {
            addEdge(edge.u, edge.v);
        }
    }

    @Override /** Return the number of vertices in the graph */
    public int getSize() {
        return vertices.size();
    }

    @Override /** Return the vertices in the graph */
    public List<V> getVertices() {
        return vertices;
    }

    @Override /** Return the object of the specified vertex */
    public V getVertex(int index) {
        return vertices.get(index);
    }

    @Override /** Return the index for the specified vertex object */
    public int getIndex(V v) {
        return vertices.indexOf(v);
    }

    @Override /** Return the neighbors of the specified vertex */
    public List<Integer> getNeighbors(int index) {
        List<Integer> result = new ArrayList<>();
        for (Edge e : neighbors.get(index)) {
            result.add(e.v);
        }

        return result;
    }

    @Override /** Return the degree for a specified vertex */
    public int getDegree(int v) {
        return neighbors.get(v).size();
    }

    @Override /** Print the edges */
    public void printEdges() {
        for (int u = 0; u < neighbors.size(); u++) {
            System.out.print(getVertex(u) + " (" + u + "): ");
            for (Edge e : neighbors.get(u)) {
                System.out.print("(" + getVertex(e.u) + ", " +
                        getVertex(e.v) + ") ");
            }
            System.out.println();
        }
    }

    @Override /** Clear teh graph */
    public void clear() {
        vertices.clear();
        neighbors.clear();
    }

    @Override /** Add a vertex to the graph */
    public boolean addVertex(V vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            neighbors.add(new ArrayList<Edge>());
            return true;
        }
        else {
            return false;
        }
    }

    /** Add an edge to the graph */
    protected boolean addEdge(Edge e) {
        if (e.u < 0 || e.u > getSize() - 1)
            throw new IllegalArgumentException("No such index: " + e.u);

        if (e.u < 0 || e.v > getSize() - 1)
            throw new IllegalArgumentException("No such index: " + e.u);

        if (!neighbors.get(e.u).contains(e)) {
            neighbors.get(e.u).add(e);
            return true;
        }
        else {
            return false;
        }
    }

    @Override /** Add an edge to the graph */
    public boolean addEdge(int u, int v) {
        return addEdge(new Edge(u, v));
    }

    /** Edge inner class inside the AbstractGraph class */
    public static class Edge {
        public int u; // Starting vertex of the edge
        public int v; // Ending vertex of the edge

        /** Construct an edge for (u, v) */
        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }

        public boolean equals(Object o) {
            return u == ((Edge)o).u && v == ((Edge)o).v;
        }
    }

    @Override /** Obtain a DFS tree starting from vertex v */
    public Tree dfs (int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
            parent[i] = -1; // Initialize parent[i] to -1

        // Mark visited vertices
        boolean[] isVisited = new boolean[vertices.size()];

        // Recursively search
        dfs(v, parent, searchOrder, isVisited);

        // Return a search tree
        return new Tree(v, parent, searchOrder);
    }

    /** Recursive method for DFS search */
    private void dfs(int u, int[] parent, List<Integer> searchOrder,
                     boolean[] isVisited) {
        // Store the visited vertex
        searchOrder.add(u);
        isVisited[u] = true; // Vertex v visited

        for (Edge e : neighbors.get(u)) {
            if (!isVisited[e.v]) {
                parent[e.v] = u; // The parent of vertex e.v is u
                dfs(e.v, parent, searchOrder, isVisited); // Recursive search
            }
        }
    }

    @Override /** Starting bfs search from vertex v */
    public Tree bfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[vertices.size()];
        for (int i = 0; i < parent.length; i++)
            parent[i] = -1; // Initialize parent[i] to -1

        java.util.LinkedList<Integer> queue =
                new java.util.LinkedList<>(); // list used as a queue
        boolean[] isVisited = new boolean[vertices.size()];
        queue.offer(v); // Enqueue v
        isVisited[v] = true; // Mark it visited

        while (!queue.isEmpty()) {
            int u = queue.poll(); // Dequeue to u
            searchOrder.add(u); // u searched
            for (Edge e : neighbors.get(u)) {
                if (!isVisited[e.u]) {
                    queue.offer(e.v); // Enqueue w
                    parent[e.v] = u; // The parent of w is u
                    isVisited[e.v] = true; // Mark it visited
                }
            }
        }

        return new Tree(v, parent, searchOrder);
    }

    /** Tree inner class inside the AbstractGraph class */
    public class Tree {
        private int root; // The root of the tree
        private int[] parent; // Store the parent of each vertex
        private List<Integer> searchOrder; // Store the search order

        /** Construct a tree with root, parent, and searchOrder */
        public Tree(int root, int[] parent, List<Integer> searchOrder) {
            this.root = root;
            this.parent = parent;
            this.searchOrder = searchOrder;
        }

        /** Return the root of the tree */
        public int getRoot() {
            return root;
        }

        /** Return the parent vertex v */
        public int getParent(int v) {
            return parent[v];
        }

        /** Return an array representing search order */
        public List<Integer> getSearchOrder() {
            return searchOrder;
        }

        /** Return the number of vertices found */
        public int getNumberOfVerticesFound() {
            return searchOrder.size();
        }

        /** Return the path of vertices from a vertex to the root */
        public List<V> getPath(int index) {
            ArrayList<V> path = new ArrayList<>();

            do {
                path.add(vertices.get(index));
                index = parent[index];
            }
            while (index != -1);

            return path;
        }

        /** Print a path from the root to vertex v */
        public void printPath(int index) {
            List<V> path = getPath(index);
            System.out.print("A path form " + vertices.get(root) + " to " +
                    vertices.get(index) + ": ");
            for (int i = path.size() -1; i >= 0; i--)
                System.out.print(path.get(i) + " ");
        }

        /** Print the whole tree */
        public void printTree() {
            System.out.println("Root is: " + vertices.get(root));
            System.out.print("Edges: ");
            for (int i = 0; i < parent.length; i++) {
                if (parent[i] != -1) {
                    // Display an edge
                    System.out.print("(" + vertices.get(parent[i]) + ", " +
                            vertices.get(i) + ") ");
                }
            }
            System.out.println();
        }
    }
}

interface Graph<V> {
    /** Returns the number of vertices in the graph */
    public int getSize();

    /** Return the vertices in the graph */
    public java.util.List<V> getVertices();

    /** Return the object for the specified vertex index */
    public V getVertex(int index);

    /** Return the index for the specified vertex object */
    public int getIndex(V v);

    /** Return the neighbors of vertex with the specified index */
    public java.util.List<Integer> getNeighbors(int index);

    /** Return the degree for a specified vertex */
    public int getDegree(int v);

    /** Print the edges */
    public void printEdges();

    /** Clears the graph */
    public void clear();

    /** Add a vertex to the graph */
    public boolean addVertex(V vertex);

    /** Add an edge to the graph */
    public boolean addEdge(int u, int v);

    /** Obtains a depth-first search tree starting from v */
    public AbstractGraph<V>.Tree dfs(int v);

    /** Obtains a breadth-first search tree starting from v */
    public AbstractGraph<V>.Tree bfs(int v);
}

class UnweightedGraph<V> extends AbstractGraph<V> {
    /** Construct an empty graph */
    public UnweightedGraph() {
    }

    /** Construct a graph from vertices and edges stored in arrays */
    public UnweightedGraph(V[] vertices, int[][] edges) {
        super(vertices, edges);
    }

    /** Construct a graph from vertices and edges stored in List */
    public UnweightedGraph(List<V> vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    /** Construct a graph for interger vertices 0, 1, 2 and edge list */
    public UnweightedGraph(List<Edge> edges, int numberOfVertices) {
        super(edges, numberOfVertices);
    }

    /** Construct a graph from integer vertices 0, 1, and edge array */
    public UnweightedGraph(int[][] edges, int numberOfVertices) {
        super(edges, numberOfVertices);
    }
}