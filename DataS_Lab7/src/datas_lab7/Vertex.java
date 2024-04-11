package DataS_Lab7;

import java.util.ArrayList;

class Vertex {
    private int x;
    private int y;
    private String id; 
    private ArrayList<Vertex> adjacentVertices; // List to store adjacent vertices
    private int size = 0;

    public Vertex(int x, int y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.adjacentVertices = new ArrayList<>();
    }
    // Getter methods for x, y, and id
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return id;
    }

     public int getSize() {
        return size;
    }

    public void addAdjacentVertex(Vertex vertex) {
        size++;
        adjacentVertices.add(vertex);
    }

    // Method to get adjacent vertices
    public ArrayList<Vertex> getAdjacentVertices() {
        return adjacentVertices;
    }      
}
