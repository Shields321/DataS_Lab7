
package datas_lab7;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private ArrayList<Vertex> vertices = new ArrayList<>(); // List to store vertices
    private JPanel drawingPanel; // Panel to draw the graph
    private Vertex selectedVertex = null; // Currently selected vertex

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
    // Draw edges
        for (Vertex vertex : vertices) {
            for (Vertex adjacent : vertex.getAdjacentVertices()) {            
                drawEdge(g, vertex, adjacent);
            }
        }

        // Draw vertices
        for (Vertex vertex : vertices) {
            drawVertex(g, vertex);
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
}

