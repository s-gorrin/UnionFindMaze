/*\
 🌱 DrawMaze - draw the maze with objectdraw
 🌱 Union Find Maze
 🌱 Seth Gorrin
 🌱 CIS 27 Spring 2021
\*/

import objectdraw.DrawingCanvas;
import objectdraw.FilledRect;
import objectdraw.FilledRoundedRect;
import objectdraw.WindowController;

import java.awt.Color;

// Draw a maze using objectdraw
// things to change:
//      MAZE_N/MAZE_M: height and width in blocks
//      SCALE: pixels per block
//      SOFT_CORNERS: set true to use rounded rectangles to round the inside of corners
public class DrawMaze extends WindowController {
    public static final int MAZE_N = 10; // Width of maze (in blocks)
    public static final int MAZE_M = 10; // Height of maze (in blocks)

    // Maze drawing properties
    public static final int SCALE = 60; // Scale of maze (pixels per block) (minimum 10)
    public static final boolean SOFT_CORNERS = true; // create rounded corners if true
    public static final int WINDOW_WIDTH = MAZE_N * SCALE;
    public static final int WINDOW_HEIGHT = MAZE_M * SCALE;

    public static final Color WALL_COLOR = new Color(40, 100, 60);
    public static final Color PATH_COLOR = new Color(10, 225, 225);

    public static final int WALL_THICKNESS = SCALE / 10;
    public static final int PATH_BLOCK = (2 * SCALE) - (2 * WALL_THICKNESS);
    public static final int PATH_SIZE = SCALE - (2 * WALL_THICKNESS);
    public static final int ARC_SIZE = WALL_THICKNESS * 3;

    // Set the background/wall color, create entrance/exit, call union find and make a maze
    public void begin() {
        canvas.setBackground(WALL_COLOR);
        new FilledRect(0, WALL_THICKNESS, ARC_SIZE, PATH_SIZE, PATH_COLOR, canvas);
        new FilledRect(canvas.getWidth() - ARC_SIZE,
                canvas.getHeight() - (PATH_SIZE + WALL_THICKNESS),
                ARC_SIZE, PATH_SIZE, PATH_COLOR, canvas);

        Maze unionFindMaze = new Maze(MAZE_N, MAZE_M);
        unionFindMaze.printWalls();
        unionFindMaze.drawWalls(canvas);
    }

    // Let soft corners be specified from a const in DrawMaze and called from Maze
    public boolean soft() {
        return SOFT_CORNERS;
    }

    // Draw the walls that have been removed, aka the path of the maze
    public void drawRemovedWall(Wall wall, boolean SOFT_CORNERS, DrawingCanvas canvas) {
        int x;
        int y;
        int width = PATH_BLOCK;
        int height = PATH_SIZE;

        if (wall.getFirst().x == wall.getSecond().x) {
            if (wall.getFirst().y < wall.getSecond().y) {
                x = (wall.getFirst().x * SCALE) + WALL_THICKNESS;
                y = (wall.getFirst().y * SCALE) + WALL_THICKNESS;
            }
            else {
                x = (wall.getSecond().x * SCALE) + WALL_THICKNESS;
                y = (wall.getSecond().y * SCALE) + WALL_THICKNESS;
            }
            width = PATH_SIZE;
            height = PATH_BLOCK;
        }
        else {
            if (wall.getFirst().x < wall.getSecond().x) {
                x = (wall.getFirst().x * SCALE) + WALL_THICKNESS;
                y = (wall.getFirst().y * SCALE) + WALL_THICKNESS;
            }
            else {
                x = (wall.getSecond().x * SCALE) + WALL_THICKNESS;
                y = (wall.getSecond().y * SCALE) + WALL_THICKNESS;
            }
        }
        if (SOFT_CORNERS)
            new FilledRoundedRect(x, y, width, height, ARC_SIZE, ARC_SIZE, PATH_COLOR, canvas);
        else
            new FilledRect(x, y, width, height, PATH_COLOR, canvas);
    }

    // Use this main to draw a maze
    public static void main(String[] args) {
        new DrawMaze().startController(WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
