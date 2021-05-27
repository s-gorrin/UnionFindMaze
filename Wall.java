/*\
 🌱 Wall - a pair of Points
 🌱 Union Find Maze Generation
 🌱 Seth Gorrin
 🌱 CIS 27 Spring 2021
\*/

import java.awt.Point;

// A wall class consisting of two Points, which make up the wall between them
public class Wall {
    private final Point first;
    private final Point second;

    public Wall(Point a, Point b) {
        first = a;
        second = b;
    }

    public Point getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }

    // Print a formatted Wall
    public void printWall() {
        System.out.println("[(" + first.x + ", " + first.y + "), (" + second.x + ", " + second.y + ")]");
    }
}
