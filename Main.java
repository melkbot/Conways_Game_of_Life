import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        int rows = 20, cols = 20; // Adjust the grid size as needed

        GameOfLife game = new GameOfLife(rows, cols);
        game.initializeGrid();

        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);

        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        JButton[][] buttons = new JButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                button.setOpaque(true);
                button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Add gray border

                int row = i, col = j;
                button.addActionListener(e -> {
                    game.toggleCell(row, col);
                    button.setBackground(game.getGrid()[row][col] 
                        ? new Color((int)(Math.random() * 256), (int)(Math.random() * 256), (int)(Math.random() * 256)) 
                        : Color.WHITE);
                });

                buttons[i][j] = button;
                gridPanel.add(button);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Simulation");
        frame.add(startButton, BorderLayout.SOUTH);

        final boolean[] running = {false};

        startButton.addActionListener(e -> {
            running[0] = !running[0];
            startButton.setText(running[0] ? "Stop Simulation" : "Start Simulation");

            if (running[0]) {
                new Thread(() -> {
                    while (running[0]) {
                        game.nextGeneration();
                        SwingUtilities.invokeLater(() -> {
                            for (int i = 0; i < rows; i++) {
                                for (int j = 0; j < cols; j++) {
                                    buttons[i][j].setBackground(game.getGrid()[i][j] 
                                        ? game.getColors()[i][j] 
                                        : Color.WHITE);
                                }
                            }
                        });
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
