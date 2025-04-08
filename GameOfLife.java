import java.util.Random;
import java.awt.Color;

public class GameOfLife {
    private boolean[][] grid;
    private Color[][] colors;
    private int rows, cols;

    public GameOfLife(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        grid = new boolean[rows][cols];
        colors = new Color[rows][cols];
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public Color[][] getColors() {
        return colors;
    }

    public void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = false;
                colors[i][j] = null;
            }
        }
    }

    public void toggleCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            grid[row][col] = !grid[row][col];
            colors[row][col] = grid[row][col] ? new Color(0, 0, 0) : null;
        }
    }

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
}
