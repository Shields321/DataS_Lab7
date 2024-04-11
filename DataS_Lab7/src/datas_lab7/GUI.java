package DataS_Lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI extends JFrame {
    private static ArrayList<Vertex> vertices = new ArrayList<>(); // List to store vertices
    private static JPanel drawingPanel; // Panel to draw the graph
    private Vertex selectedVertex = null; // Currently selected vertex
    private static ArrayList<Vertex> shortestPath = new ArrayList<>(); // List to store the shortest path
    private static int start = 0;
    static private int end = 0;

    public GUI() {
        setTitle("Graph Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Initialize drawing panel
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawGraph(g);
            }
        };
        drawingPanel.setPreferredSize(new Dimension(600, 400));
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY()); // Handle mouse click
                drawingPanel.repaint(); // Repaint the panel to reflect changes
            }
        });

        add(drawingPanel, BorderLayout.CENTER);
        pack();
    }

    public static void processText(String text, String text2) {
        start = Integer.parseInt(text);
        end = Integer.parseInt(text2);
        shortestPath = findShortestPath(vertices.get(start - 1), vertices.get(end - 1));
        drawingPanel.repaint(); // Redraw the graph with the updated shortest path
    }
    

    // Method to handle mouse click
    private void handleMouseClick(int x, int y) {
        // Check if any vertex is clicked
        for (Vertex vertex : vertices) {
            if (isClicked(vertex, x, y)) {
                if (selectedVertex == null) {
                    selectedVertex = vertex; // Select the clicked vertex
                } else {
                    if (selectedVertex != vertex) {
                        // Connect the selected vertex with the clicked vertex
                        selectedVertex.addAdjacentVertex(vertex);
                        vertex.addAdjacentVertex(selectedVertex);
                        selectedVertex = null; // Reset selected vertex
                    }
                }
                return;
            }
        }
        // If no vertex is clicked, add a new vertex
        addVertex(x, y);
    }

    // Method to check if a vertex is clicked
    private boolean isClicked(Vertex vertex, int x, int y) {
        int vx = vertex.getX();
        int vy = vertex.getY();
        return Math.pow(x - vx, 2) + Math.pow(y - vy, 2) <= 25; // Check if click is within the radius of the vertex
    }

    private void addVertex(int x, int y) {
        String id = "V" + (vertices.size() + 1); // Generate unique identifier for vertex
        Vertex newVertex = new Vertex(x, y, id); // Create the new vertex
        vertices.add(newVertex); // Add vertex to the list

        // Connect the new vertex to the selected vertex (if any)
        if (selectedVertex != null) {
            selectedVertex.addAdjacentVertex(newVertex); // Connect selected vertex to the new vertex
            newVertex.addAdjacentVertex(selectedVertex); // Connect new vertex to the selected vertex
        }

        selectedVertex = newVertex; // Update selectedVertex to the newly added vertex
    }

    private void drawGraph(Graphics g) {
        // Draw all edges
        for (Vertex vertex : vertices) {
            for (Vertex adjacent : vertex.getAdjacentVertices()) {
                drawEdge(g, vertex, adjacent);
            }
        }

        // Draw all vertices
        for (Vertex vertex : vertices) {
            drawVertex(g, vertex);
        }

        // Draw shortest path
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            drawShortestPath(g, shortestPath.get(i), shortestPath.get(i + 1));
        }
    }

    // Method to draw an edge between two vertices
    private void drawEdge(Graphics g, Vertex v1, Vertex v2) {
        g.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
    }

    // Method to draw a vertex
    private void drawVertex(Graphics g, Vertex vertex) {
        g.fillOval(vertex.getX() - 5, vertex.getY() - 5, 10, 10); // Draw vertex as a small circle
        g.drawString(vertex.getId(), vertex.getX() - 10, vertex.getY() - 10); // Display vertex ID
    }

    // Method to draw the shortest path
    private void drawShortestPath(Graphics g, Vertex v1, Vertex v2) {
        g.setColor(Color.RED); // Set color to red for the shortest path
        g.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
        g.setColor(Color.BLACK); // Reset color to black
    }

    // Method to find the shortest path between two vertices using BFS
    private static ArrayList<Vertex> findShortestPath(Vertex src, Vertex dest) {
        Queue<Vertex> queue = new LinkedList<>(); //This is used to get the vertexs
        int[] parent = new int[vertices.size()]; //This is used to get the parents of the shortest path
        for (int i = 0; i < parent.length; i++)// this is used to set every value in the parent array to a value of -1
            parent[i] = -1;
    
        boolean[] visited = new boolean[vertices.size()]; //this is used to know what vertices were visited
        int start = vertices.indexOf(src); // Index of the start vertex
        int end = vertices.indexOf(dest); // Index of the end vertex
        queue.add(src); //adding the first vertex to the path
        visited[start] = true; //setting visited vertexes to true
    
        while (!queue.isEmpty()) { //run as long as the queue is not empty
            Vertex current = queue.poll(); //Retrieves, but does not remove, the head of this queue.
            int currentIndex = vertices.indexOf(current);//gets the current index value from the current value
            if (currentIndex == end) { //Runs when the current index is the end vertex, and then stop the search
                break;
            }
            for (Vertex neighbor : current.getAdjacentVertices()) {// each value in getAdjacentVertices get stored in neighbor
                int neighborIndex = vertices.indexOf(neighbor); //get the index of the current neighbor
                if (!visited[neighborIndex]) { 
                    queue.add(neighbor);//if the current neighbor is not visited add that neighbor into the queue
                    visited[neighborIndex] = true;//set the value in the nisited array linked to neighborIndex to true
                    parent[neighborIndex] = currentIndex; // Set the parent of neighbor to current
                }
            }
        }
        // Build shortest path
        ArrayList<Vertex> path = new ArrayList<>(); //This arraylist is used to store the shortest path
        int currentIndex = end;
        while (currentIndex != -1) { // Traverse the parent array from end vertex to the start vertex
            path.add(vertices.get(currentIndex)); //this is adding the vertex in the shortest path. 
            currentIndex = parent[currentIndex]; //this is to go back to the starting vertex using the parents
        }
        Collections.reverse(path);//reverses the order
    
        Arrays.fill(visited, false); //uses the array class fill function to reset all of the vertices that were visted and sets them to false for future use
    
        return path;
    }
         
}
