/*\
 🌱 Maze - use union find to make a maze
 🌱 Union Find Maze Generation
 🌱 Seth Gorrin
 🌱 CIS 27 Spring 2021
\*/

import objectdraw.DrawingCanvas;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

// Class to generate mazes using the union find algorithm
public class Maze {
    public static final int DIRECTIONS = 4;

    private final LinkedList<Wall> removedWalls;
    private final Random randomNumber;

    // Create a maze using the WeightedQuickUnion union find algorithm from Sedgewick
    public Maze(int n, int m) {
        randomNumber = new Random();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(m * n);
        removedWalls = new LinkedList<>();

        while (uf.count() > 1) {
            Point first = getRandomPoint(n, m);
            Point second = getAdjacentPoint(first, n, m);

            if (uf.union((first.y * n) + first.x, (second.y * n) + second.x)) {
                removedWalls.add(new Wall(first, second));
            }
        }
    }

    // Print removed walls to the console
    public void printWalls() {
        for (Wall wall:removedWalls) {
            wall.printWall();
        }
    }

    // Send walls info to objectdraw to draw the maze
    public void drawWalls(DrawingCanvas canvas) {
        DrawMaze drawTheMaze = new DrawMaze();
        for (Wall wall:removedWalls) {
            drawTheMaze.drawRemovedWall(wall, drawTheMaze.soft(), canvas);
        }
    }

    // Randomly select a square
    private Point getRandomPoint(int n, int m) {
        int x = randomNumber.nextInt(n);
        int y = randomNumber.nextInt(m);
        return new Point(x, y);
    }

    // Randomly select an adjacent square to an already selected square, with care not to go off the edge
    private Point getAdjacentPoint(Point start, int n, int m) {
        int direction = randomNumber.nextInt(DIRECTIONS);

        // This was previously an ordinary switch,
        // but IntelliJ gave a persistent warning that it could be an enhanced switch,
        // which it did automatically
        switch (direction) {
            case 0 -> {
                if (start.y == 0)
                    return new Point(start.x, start.y + 1);
                return new Point(start.x, start.y - 1);
            }
            case 1 -> {
                if (start.x == n - 1)
                    return new Point(start.x - 1, start.y);
                return new Point(start.x + 1, start.y);
            }
            case 2 -> {
                if (start.y == m - 1)
                    return new Point(start.x, start.y - 1);
                return new Point(start.x, start.y + 1);
            }
            default -> {
                if (start.x == 0)
                    return new Point(start.x + 1, start.y);
                return new Point(start.x - 1, start.y);
            }
        }
    }

    // Use this main to only print the removed walls of a maze
    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int n = keyboard.nextInt();
        int m = keyboard.nextInt();
        Maze tenByTen = new Maze(n, m);
        tenByTen.printWalls();
    }
}