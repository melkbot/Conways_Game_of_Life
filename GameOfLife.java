import java.awt.Color;

public class GameOfLife {
    // Instance variables
    // The grid is represented as a 2D boolean array
    // where true indicates a live cell and false indicates a dead cell
    // The colors array stores the color of each cell (r, g, b)
    private boolean[][] grid;
    private Color[][] colors;
    private int rows, cols;

    // Constructors
    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new boolean[rows][cols];
        colors = new Color[rows][cols];
    }

    // Getters
    public boolean[][] getGrid() {
        return grid;
    }
    public Color[][] getColors() {
        return colors;
    }

    // Creates blank grid and initializes colors to null
    // This method is called when the simulation starts or resets
    public void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = false;
                colors[i][j] = null;
            }
        }
    }

    // This method toggles the state of a cell at the specified row and column
    // If the cell is currently alive, it becomes dead and its color is set to null
    // If the cell is currently dead, it becomes alive and a random color is assigned to it
    public void toggleCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            grid[row][col] = !grid[row][col];
            colors[row][col] = grid[row][col] ? new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)) : null;
        }
    }

    // This method updates the grid to the next generation based on the rules of Conway's Game of Life
    // It creates a new grid and colors array to store the next generation's state
    // It iterates through each cell in the current grid and applies the rules:
    // 1. Any live cell with fewer than two live neighbors dies (underpopulation).
    // 2. Any live cell with two or three live neighbors lives on to the next generation.
    // 3. Any live cell with more than three live neighbors dies (overpopulation).
    // 4. Any dead cell with exactly three live neighbors becomes a live cell (reproduction).
    public void nextGeneration() {
        boolean[][] newGrid = new boolean[rows][cols];
        Color[][] newColors = new Color[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int liveNeighbors = countLiveNeighbors(i, j);

                if (grid[i][j]) {
                    newGrid[i][j] = liveNeighbors == 2 || liveNeighbors == 3;
                    newColors[i][j] = colors[i][j];
                } else if (liveNeighbors == 3) {
                    newGrid[i][j] = true;
                    newColors[i][j] = calculateAverageColor(i, j);
                }
            }
        }

        grid = newGrid;
        colors = newColors;
    }

    // This method counts the number of live neighbors for a cell at the specified row and column
    // It checks the eight surrounding cells and counts how many of them are alive
    private int countLiveNeighbors(int row, int col) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newRow = row + i, newCol = col + j;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol]) {
                    count++;
                }
            }
        }

        return count;
    }

    // This method calculates the average color of the live neighbors for a cell at the specified row and column
    // It sums the RGB values of the live neighbors and divides by the count of live neighbors
    private Color calculateAverageColor(int row, int col) {
        int red = 0, green = 0, blue = 0, count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newRow = row + i, newCol = col + j;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol]) {
                    Color neighborColor = colors[newRow][newCol];
                    if (neighborColor != null) {
                        red += neighborColor.getRed();
                        green += neighborColor.getGreen();
                        blue += neighborColor.getBlue();
                        count++;
                    }
                }
            }
        }

        return count > 0 ? new Color(red / count, green / count, blue / count) : new Color(0, 0, 0);
    }

    // This method randomizes the grid and assigns random colors to live cells
    public void randomizeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = Math.random() < 0.5; // 50% chance of being alive
                colors[i][j] = grid[i][j] ? new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)) : null;
            }
        }
    }
}
