import java.awt.Color;

public class GameOfLife {
    private Square[][] grid;
    private int rows, cols;

    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new Square[rows][cols];
        initializeGrid();
    }

    public Square[][] getGrid() {
        return grid;
    }

    public void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Square();
            }
        }
    }

    public void toggleCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            grid[row][col].toggle();
        }
    }

    public void nextGeneration() {
        Square[][] newGrid = new Square[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newGrid[i][j] = new Square();
                int liveNeighbors = countLiveNeighbors(i, j);

                if (grid[i][j].isAlive()) {
                    newGrid[i][j].setAlive(liveNeighbors == 2 || liveNeighbors == 3);
                    newGrid[i][j].setColor(grid[i][j].getColor());
                } else if (liveNeighbors == 3) {
                    newGrid[i][j].setAlive(true);
                    newGrid[i][j].setColor(Math.random() < 0.01 
                        ? new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)) 
                        : calculateAverageColor(i, j));
                }
            }
        }
        grid = newGrid;
    }

    private int countLiveNeighbors(int row, int col) {
        int count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newRow = row + i, newCol = col + j;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol].isAlive()) {
                    count++;
                }
            }
        }

        return count;
    }

    private Color calculateAverageColor(int row, int col) {
        int red = 0, green = 0, blue = 0, count = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;

                int newRow = row + i, newCol = col + j;

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && grid[newRow][newCol].isAlive()) {
                    Color neighborColor = grid[newRow][newCol].getColor();
                    if (neighborColor != null) {
                        red += neighborColor.getRed();
                        green += neighborColor.getGreen();
                        blue += neighborColor.getBlue();
                        count++;
                    }
                }
            }
        }

        return count > 0 ? new Color(red / count, green / count, blue / count) : null;
    }

    public void randomizeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].setAlive(Math.random() < 0.5);
                grid[i][j].setColor(grid[i][j].isAlive() 
                    ? new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)) 
                    : null);
            }
        }
    }
}
